package n06.oop.generator.impl;

import n06.oop.database.ConnectionManager;
import n06.oop.model.entities.BaseEntity;
import n06.oop.model.entities.Source;
import n06.oop.generator.IGenerator;
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
import java.util.concurrent.ThreadLocalRandom;

public class BaseGenerator<T extends BaseEntity> implements IGenerator<T> {

    static final int MAX_STATMENT = 200000;

    protected List<String> dataList;
    protected String filePath = "/data";
    protected RepositoryConnection conn;
    protected ModelBuilder builder;
    protected ValueFactory factory;

    public BaseGenerator() {
        conn = ConnectionManager.getInstance().getConnection();
        factory = SimpleValueFactory.getInstance();
        builder = new ModelBuilder();
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

    public List<Source> generateSources() {
        int num = ThreadLocalRandom.current().nextInt(1,4);
        List<Source> sources = new ArrayList<>();
        for(int i=0; i<num; i++) {
            Source source = new Source();
            sources.add(source);
        }

        return sources;
    }

    @Override
    public void generateData(int num) {
        conn.begin();
        try {
            for (int count = 0; count < num; count++) {
                T item = createEntity(count);
                Model model = createModel(item);
                if (model.size() >= MAX_STATMENT){
                    conn.add(model);
                    builder = new ModelBuilder();
                }
            }
            conn.add(builder.build());
            conn.commit();
//            conn.close();
        } catch (Throwable t) {
            conn.rollback();
            t.printStackTrace();
        }
    }

    @Override
    public T createEntity(int count) {
        return null;
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
