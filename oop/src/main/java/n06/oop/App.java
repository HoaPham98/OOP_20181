package n06.oop;

import n06.oop.database.ConnectionManager;
import n06.oop.model.BaseModel;
import n06.oop.model.Country;
import n06.oop.model.Source;
import org.cyberborean.rdfbeans.RDFBeanManager;
import org.cyberborean.rdfbeans.exceptions.RDFBeanException;
import org.eclipse.rdf4j.repository.RepositoryConnection;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String args[] )
    {
        System.out.println( "Hello World!" );
        Source source = new Source("http://google.com", "2018-12-11");
        List<Source> sourceList = new ArrayList<>();
        sourceList.add(source);
        BaseModel country = new BaseModel();
        country.setId("COUNTRY_001");
        country.setName("Viet Nam");
        country.setDescription("Asean_country");
        country.setSources(sourceList);
//        country.setIsoCode("Vi");

        try (RepositoryConnection con = ConnectionManager.getConnection()) {
            RDFBeanManager manager = new RDFBeanManager(con);
            // ... do something with RDFBeanManager
            manager.add(country);
        } catch (RDFBeanException e) {
            e.printStackTrace();
        }
    }
}
