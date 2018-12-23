package n06.oop.database;

import n06.oop.config.Setting;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.manager.RemoteRepositoryManager;
import org.eclipse.rdf4j.repository.manager.RepositoryManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionManager {

    private static Logger logger =
            LoggerFactory.getLogger(ConnectionManager.class);

    private RepositoryManager repositoryManager;
    private String repositoryId;
    private Repository repository;
    private RepositoryConnection connection;

    private static ConnectionManager instance = new ConnectionManager();

    public static ConnectionManager getInstance() {
        return instance;
    }

    /**
     *  Thiết lập kết nối tới database
     */
    public ConnectionManager() {
        RepositoryManager repositoryManager  = new RemoteRepositoryManager(Setting.URL);
        repositoryManager.initialize();
        repositoryId = Setting.REPO_NAME_15m_17m;
        setRepositoryManager(repositoryManager);

        repository = repositoryManager.getRepository(repositoryId);
    }

    public void setRepositoryManager(RepositoryManager repositoryManager) {
        this.repositoryManager = repositoryManager;
    }

    public void setRepository(String repositoryId) {
        this.repositoryId = repositoryId;
        repository = repositoryManager.getRepository(repositoryId);
        connection = repository.getConnection();
        System.out.println(repository.toString());
    }

    public Repository getRepository() {
        return repository;
    }

    public RepositoryConnection getConnection() {
        connection = connection != null ? connection : repository.getConnection();

        return connection;
    }

    public static void release() {

    }
}
