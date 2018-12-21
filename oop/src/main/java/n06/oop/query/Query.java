package n06.oop.query;

import n06.oop.database.ConnectionManager;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.QueryEvaluationException;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.RepositoryConnection;

import java.util.ArrayList;
import java.util.List;

public class Query {
    public static QueryResult query(String sparql) throws QueryEvaluationException {
        RepositoryConnection conn = ConnectionManager.getConnection();

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
                String value = solution.getValue(header).stringValue();
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
}
