package n06.oop.generator;

import n06.oop.App;
import n06.oop.relationship.RelationGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DatabaseGenerator {

    private static Logger logger = LoggerFactory.getLogger(DatabaseGenerator.class);

    public static final String[] TYPES = {"person", "country", "location", "event", "organization", "time"};

    public static void generator(int numEntity, int numRelation) {

        List<IGenerator> iGenerators = new ArrayList<>();
        for (String type: TYPES) {
            IGenerator iGenerator = GeneratorFactory.getDao(type);
            iGenerators.add(iGenerator);
        }

        List<Integer> parts = splitIntoParts(numEntity, TYPES.length);
        long start = System.currentTimeMillis();
        for(int i=0; i<TYPES.length; i++) {
            iGenerators.get(i).generateData(parts.get(i));
        }
        long finish = System.currentTimeMillis();

        logger.info("Gen time: " + (finish - start));

        start = System.currentTimeMillis();

        RelationGenerator relationGenerator = new RelationGenerator();
        relationGenerator.generateRelation(numRelation);

        finish = System.currentTimeMillis();

        logger.info("Gen time: " + (finish - start));

    }

    private static List<Integer> splitIntoParts(int whole, int parts) {
        List<Integer> arr = new ArrayList<>(parts);
        int remain = whole;
        int partsLeft = parts;
        for (int i = 0; partsLeft > 0; i++) {
            int size = (remain + partsLeft - 1) / partsLeft; // rounded up, aka ceiling
            arr.add(size);
            remain -= size;
            partsLeft--;
        }
        Collections.shuffle(arr);
        return arr;
    }
}
