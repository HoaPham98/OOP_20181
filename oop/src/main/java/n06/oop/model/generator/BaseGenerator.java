package n06.oop.model.generator;

import n06.oop.database.ConnectionManager;
import org.eclipse.rdf4j.repository.RepositoryConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class BaseGenerator implements IGenerator {

    protected List<String> dataList;
    protected String filePath = "/data";
    protected RepositoryConnection conn;

    public BaseGenerator() {
        conn = ConnectionManager.getConnection();
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
            dataList.size();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void generateData(int num) {

    }

    @Override
    public String getName() {
        return null;
    }
}
