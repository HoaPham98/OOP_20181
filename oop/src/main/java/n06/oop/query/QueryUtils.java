package n06.oop.query;

import n06.oop.database.ConnectionManager;
import n06.oop.utils.StringUtils;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.QueryEvaluationException;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.RepositoryConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class QueryUtils {
    public static QueryResult query(String sparql) throws QueryEvaluationException {
        RepositoryConnection conn = ConnectionManager.getInstance().getConnection();

        long t1 = System.currentTimeMillis();

        TupleQuery query = conn.prepareTupleQuery(sparql);
        TupleQueryResult result = query.evaluate();

        List<String> headersList = result.getBindingNames();
        String[] headers = headersList.toArray(new String[0]);

        List<String[]> map = new ArrayList<>();

        while (result.hasNext()) {
            BindingSet solution = result.next();
            List<String> data = new ArrayList<>();
            for (String header: headers) {
                Value v  = solution.getValue(header);
                String value = v != null ? v.stringValue() : "";
                data.add(value);
            }
            map.add(data.toArray(new String[0]));
        }

        String[][] dataMap = map.toArray(new String[0][]);
        long time = System.currentTimeMillis() - t1;

        QueryResult queryResult = new QueryResult();
        queryResult.setHeader(headers);
        queryResult.setData(dataMap);
        queryResult.setTimeExec(time);

        return queryResult;
    }

    public static Map<String, String> loadData(String file) {
        Map<String, String> map = new LinkedHashMap<>();
        try {
            InputStream inputStream = QueryUtils.class.getResourceAsStream(file);
            InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String name = null;
            String line;
            while ((line = reader.readLine()) != null) {
                line = StringUtils.removeUTF8BOM(line);
                if (line.startsWith("-")) {
                    String sql = stringBuilder.toString();
                    if (name != null && !sql.isEmpty()) {
                        map.put(name, sql);
                        stringBuilder = new StringBuilder();
                    }
                    name = line;
                    continue;
                }
                stringBuilder.append("\n");
                stringBuilder.append(line);
            }
            String sql = stringBuilder.toString();
            if (name != null && !sql.isEmpty()) {
                map.put(name, sql);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }
}
