package n06.oop.generator;

import n06.oop.database.ConnectionManager;
import n06.oop.config.Setting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DatabaseGenerator {

    private static Logger logger = LoggerFactory.getLogger(DatabaseGenerator.class);

    static final String[] TYPES = {"person", "country", "location", "event", "organization", "time"};

    private List<IGenerator> iGenerators;
    private List<RelationGenerator> relationGenerators;

    public DatabaseGenerator() {
        iGenerators = new ArrayList<>();
        relationGenerators = new ArrayList<>();
        for (String type : TYPES) {
            IGenerator iGenerator = GeneratorFactory.getGenerator(type);
            iGenerators.add(iGenerator);
        }
    }

    /**
     * Tạo giả lập database
     *
     * @param numEntity   số lượng thực thể
     * @param numRelation số lượng quan hệ
     */
    public void generator(int numEntity, int numRelation) {

        ConnectionManager.getInstance().getConnection().clear();

        List<Integer> parts = GeneratorUtils.splitIntoParts(numEntity, TYPES.length);

        Map<String, Integer> sizeOfType = new LinkedHashMap<>();

        for (int i = 0; i < TYPES.length; i++) {
            long start = System.currentTimeMillis();
            sizeOfType.put(TYPES[i], parts.get(i));
            logger.info("Start gening " + TYPES[i]);
            iGenerators.get(i).generateData(parts.get(i));
            long finish = System.currentTimeMillis();

            logger.info("Done gening " + TYPES[i] + ": " + (finish - start));
        }

        List<Integer> parts2 = GeneratorUtils.splitIntoParts(numRelation, Setting.MAX_CONCURRENT);


        for (int i = 0; i < Setting.MAX_CONCURRENT; i++) {
            long start = System.currentTimeMillis();

            RelationGenerator relationGenerator = new RelationGenerator(sizeOfType);
            relationGenerator.generateRelation(parts2.get(i));

            long finish = System.currentTimeMillis();

            logger.info("Gen time: " + (finish - start));
        }

    }

}
