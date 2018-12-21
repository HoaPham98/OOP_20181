package n06.oop.generator;

import n06.oop.database.ConnectionManager;
import n06.oop.database.Setting;
import n06.oop.relationship.RelationGenerator;
import n06.oop.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DatabaseGenerator {

    private static Logger logger = LoggerFactory.getLogger(DatabaseGenerator.class);

    public static final String[] TYPES = {"person", "country", "location", "event", "organization", "time"};

    /**
     * Tạo giả lập database
     * @param numEntity số lượng thực thể
     * @param numRelation số lượng quan hệ
     */
    public void generator(int numEntity, int numRelation) {

        ConnectionManager.getConnection().clear();

        List<IGenerator> iGenerators = new ArrayList<>();
        for (String type: TYPES) {
            IGenerator iGenerator = GeneratorFactory.getGenerator(type);
            iGenerators.add(iGenerator);
        }

        List<Integer> parts = Utils.splitIntoParts(numEntity, TYPES.length);
        ExecutorService executorService = Executors.newFixedThreadPool(Setting.MAX_CONCURRENT);
        List<Future> futures = new ArrayList<>();

        for(int i=0; i<TYPES.length; i++) {
            final IGenerator iGenerator = iGenerators.get(i);
            final int num = parts.get(i);
            final String name = TYPES[i];
            final int count = i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    long start = System.currentTimeMillis();
                    logger.info("Start gening " + name);
                    iGenerator.generateData(num);
                    long finish = System.currentTimeMillis();

                    logger.info("Done gening " + name + ": " + (finish - start));
                }
            };

            futures.add(executorService.submit(runnable));
        }

        try {
            for (Future future : futures) {
                future.get();
            }

            futures = new ArrayList<>();

            final List<Integer> parts2 = Utils.splitIntoParts(numRelation, Setting.MAX_CONCURRENT);
            Map<String, Integer> sizeOfType = Utils.getSizeOfType();

            for (int i=0; i < Setting.MAX_CONCURRENT; i++) {
                int finalI = i;
                Runnable runnable = () -> {
                    long start = System.currentTimeMillis();

                    RelationGenerator relationGenerator = new RelationGenerator(sizeOfType);
                    relationGenerator.generateRelation(parts2.get(finalI));

                    long finish = System.currentTimeMillis();

                    logger.info("Gen time: " + (finish - start));
                };

                futures.add(executorService.submit(runnable));
            }

            for (Future future : futures) {
                future.get();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        executorService.shutdown();

    }

}
