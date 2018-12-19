package n06.oop.generator.iml;

import n06.oop.database.ConnectionManager;
import n06.oop.database.Setting;
import n06.oop.model.entities.BaseEntity;
import n06.oop.model.entities.Source;
import n06.oop.generator.IGenerator;
import n06.oop.model.vocabulary.ENT;
import n06.oop.model.vocabulary.PROP;
import n06.oop.utils.Utils;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.repository.RepositoryConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class BaseGenerator<T extends BaseEntity> implements IGenerator<T> {

    protected List<String> dataList;
    protected String filePath = "/data";
    protected RepositoryConnection conn;
    protected ModelBuilder builder;
    protected ValueFactory factory;

    public BaseGenerator() {
        conn = ConnectionManager.getConnection();
        builder = new ModelBuilder().setNamespace(ENT.NS).setNamespace(PROP.NS);
        factory = SimpleValueFactory.getInstance();
    }

    public void init(String filePath) {
        dataList = new ArrayList<>();
        try {
            InputStream inputStream = this.getClass().getResourceAsStream(filePath);
            InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);
            String line;
            while((line = reader.readLine()) != null)
            {
                dataList.add(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Source> generateSources(int num) {
        List<Source> sources = new ArrayList<>();
        for(int i=0; i<num; i++) {
            Source source = new Source();
            sources.add(source);
        }

        return sources;
    }

    @Override
    public void generateData(int num) {

    }

    @Override
    public Model createModel(T item) {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }
}
