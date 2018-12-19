package n06.oop;

import n06.oop.database.ConnectionManager;
import n06.oop.generator.DatabaseGenerator;
import n06.oop.relationship.RelationGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args)
    {
        System.out.println( "Hello World!" );

        DatabaseGenerator.generator(1000, 2000);

    }
}
