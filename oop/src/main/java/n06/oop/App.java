package n06.oop;

import n06.oop.generator.DatabaseGenerator;

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
        databaseGenerator.generator(20000, 40000);

        System.out.println("Total gen time: " + (System.currentTimeMillis() - t1));
    }
}
