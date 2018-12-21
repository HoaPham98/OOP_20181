package n06.oop;

import n06.oop.database.ConnectionManager;
import n06.oop.database.Setting;
import n06.oop.generator.DatabaseGenerator;
import n06.oop.relationship.RelationGenerator;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args)
    {
        System.out.println( "Hello World!" );


        DatabaseGenerator databaseGenerator = new DatabaseGenerator();

        long t1 = System.currentTimeMillis();
        ConnectionManager.setRepository(Setting.REPO_NAME_1m_2m);
        databaseGenerator.generator(1000000, 2000000);

        System.out.println("Total gen time: " + (System.currentTimeMillis() - t1));

    }
}
