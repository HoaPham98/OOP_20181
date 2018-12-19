package n06.oop.database;

import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.impl.TreeModel;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.config.RepositoryConfig;
import org.eclipse.rdf4j.repository.config.RepositoryConfigSchema;
import org.eclipse.rdf4j.repository.manager.RemoteRepositoryManager;
import org.eclipse.rdf4j.repository.manager.RepositoryManager;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFParser;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.rio.helpers.StatementCollector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class ConnectionManager {

    private static Logger logger =
            LoggerFactory.getLogger(ConnectionManager.class);

    private final static ThreadLocal<RepositoryConnection> LOCAL = new ThreadLocal<>();
    private static Repository repository;


    /**
     *  Thiết lập kết nối tới database
     */
    static {
        try {
            // Instantiate a local repository manager and initialize it
            RepositoryManager repositoryManager = new RemoteRepositoryManager(Setting.URL);
            repositoryManager.initialize();

            // Instantiate a repository graph model
            TreeModel graph = new TreeModel();

            // Read repository configuration file
            InputStream config = ConnectionManager.class.getResourceAsStream("/repo-defaults.ttl");
            RDFParser rdfParser = Rio.createParser(RDFFormat.TURTLE);
            rdfParser.setRDFHandler(new StatementCollector(graph));

            rdfParser.parse(config, RepositoryConfigSchema.NAMESPACE);

            config.close();

            // Retrieve the repository node as a resource
            Model model = graph.filter(null, RDF.TYPE, RepositoryConfigSchema.REPOSITORY);
            Iterator<Statement> iterator = model.iterator();
            if (!iterator.hasNext())
                throw new RuntimeException("Oops, no <http://www.openrdf.org/config/repository#> subject found!");
            Statement statement = iterator.next();
            Resource repositoryNode =  statement.getSubject();

            // Create a repository configuration object and add it to the repositoryManager
            RepositoryConfig repositoryConfig = RepositoryConfig.create(graph, repositoryNode);
            repositoryManager.addRepositoryConfig(repositoryConfig);

            // Get the repository from repository manager, note the repository id set in configuration .ttl file
            Repository repository = repositoryManager.getRepository(Setting.REPO_NAME);

            ConnectionManager.setRepository(repository);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ConnectionManager() {

    }

    public static void setRepository(Repository repository) {
        ConnectionManager.repository = repository;
    }

    public static RepositoryConnection getConnection() {
        RepositoryConnection conn = LOCAL.get();
        if (conn == null || !conn.isOpen()) {
            conn = repository.getConnection();
            LOCAL.set(conn);
        }
        return conn;
    }



    public static void release() {
        RepositoryConnection conn = LOCAL.get();
        if (null != conn) {
            conn.close();
            LOCAL.remove();
        }
    }
}
