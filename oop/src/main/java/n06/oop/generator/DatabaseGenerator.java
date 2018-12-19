package n06.oop.generator;

import n06.oop.App;
import n06.oop.database.ConnectionManager;
import n06.oop.relationship.RelationGenerator;
import n06.oop.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DatabaseGenerator {

    private static Logger logger = LoggerFactory.getLogger(DatabaseGenerator.class);

    public static final String[] TYPES = {"person", "country", "location", "event", "organization", "time"};

    private int countDone = 0;

    public void generator(int numEntity, int numRelation) {

        ConnectionManager.getConnection().clear();

        List<IGenerator> iGenerators = new ArrayList<>();
        for (String type: TYPES) {
            IGenerator iGenerator = GeneratorFactory.getDao(type);
            iGenerators.add(iGenerator);
        }

        List<Integer> parts = Utils.splitIntoParts(numEntity, TYPES.length);

        for(int i=0; i<TYPES.length; i++) {
            final IGenerator iGenerator = iGenerators.get(i);
            final int num = parts.get(i);
            final String name = TYPES[i];
            final int count = i;
            Thread thread = new Thread(() -> {
                long start = System.currentTimeMillis();
                logger.info("Start gening " + name);
                iGenerator.generateData(num);
                long finish = System.currentTimeMillis();

                logger.info("Done gening " + name + ": " + (finish - start));

                countDone++;

                if (countDone == TYPES.length) {
                    long t1 = System.currentTimeMillis();

                    RelationGenerator relationGenerator = new RelationGenerator();
                    relationGenerator.generateRelation(numRelation);

                    long t2 = System.currentTimeMillis();

                    logger.info("Gen time: " + (t2 - t1));
                }
            });
            thread.start();
        }
    }

}
