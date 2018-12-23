package n06.oop.generator;

import n06.oop.database.ConnectionManager;
import n06.oop.database.Setting;
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

    private ExecutorService executorService;
    private List<IGenerator> iGenerators;

    public DatabaseGenerator() {
        executorService = Executors.newFixedThreadPool(Setting.MAX_CONCURRENT);
        iGenerators = new ArrayList<>();
        for (String type: TYPES) {
            IGenerator iGenerator = GeneratorFactory.getGenerator(type);
            iGenerators.add(iGenerator);
        }
    }

    /**
     * Tạo giả lập database
     * @param numEntity số lượng thực thể
     * @param numRelation số lượng quan hệ
     */
    public void generator(int numEntity, int numRelation) {

        ConnectionManager.getConnection().clear();

        List<Integer> parts = GeneratorUtils.splitIntoParts(numEntity, TYPES.length);
        List<Future> futures = new ArrayList<>();

        Map<String, Integer> sizeOfType = new LinkedHashMap<>();

        for(int i=0; i<TYPES.length; i++) {
            final IGenerator iGenerator = iGenerators.get(i);
            final int num = parts.get(i);
            final String name = TYPES[i];
            sizeOfType.put(name, num);
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

            System.out.println(ConnectionManager.repositoryId);

            final List<Integer> parts2 = GeneratorUtils.splitIntoParts(numRelation, Setting.MAX_CONCURRENT);


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

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        executorService.shutdown();
    }

    public void cancel() {
        if (executorService != null) {
            executorService.shutdownNow();
            System.out.println("Shutdown: " + executorService.isShutdown());
        }
    }

}
