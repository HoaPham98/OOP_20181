package n06.oop;

import n06.oop.database.ConnectionManager;
import n06.oop.model.generator.PersonGenerator;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.RepositoryConnection;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args)
    {
        System.out.println( "Hello World!" );
        PersonGenerator generator = new PersonGenerator();

        generator.generateData(100);

        RepositoryConnection conn = ConnectionManager.getConnection();
        String queryString = "PREFIX model: <http://nhom06/model#>";
        queryString += "SELECT ?s ?n \n";
        queryString += "WHERE { \n";
        queryString += "    model:PERSON_1 ?s ?n \n";
        queryString += "}";
        TupleQuery query = conn.prepareTupleQuery(queryString);
        // A QueryResult is also an AutoCloseable resource, so make sure it gets
        // closed when done.
        try (TupleQueryResult result = query.evaluate()) {
            // we just iterate over all solutions in the result...
            while (result.hasNext()) {
                BindingSet solution = result.next();
                // ... and print out the value of the variable bindings
                // for ?s and ?n
                System.out.println("?s = " + solution.getValue("s"));
                System.out.println("?n = " + solution.getValue("n"));
            }
        }
    }
}
