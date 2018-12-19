package n06.oop.generator.iml;

import n06.oop.model.entities.Location;
import n06.oop.model.entities.Source;
import n06.oop.model.vocabulary.ENT;
import n06.oop.model.vocabulary.PROP;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.model.vocabulary.RDF;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class LocationGenerator extends BaseGenerator<Location> {

    public LocationGenerator() {
        super();
        filePath += "/" + getName() + ".txt";
        init(filePath);
    }

    @Override
    public void generateData(int num) {
        Location location = new Location();
        conn.begin();
        long start = System.currentTimeMillis();
        try {
            for (int count = 0; count < num; count++) {
                int rand = ThreadLocalRandom.current().nextInt(0, dataList.size());
                String name = dataList.get(rand);

                location.setId("LOCATION_" + (count + 1));
                location.setName(name);
                location.setDescription("Địa điểm: " + name);
                List<Source> sources = generateSources(ThreadLocalRandom.current().nextInt(1,6));
                location.setSources(sources);

                Model model = createModel(location);

                conn.add(model);
            }
            conn.commit();
            long end = System.currentTimeMillis();
            long time = end - start;
            System.out.println(time);
        } catch (Throwable t) {
            conn.rollback();
        }
    }

    @Override
    public Model createModel(Location item) {
        builder = new ModelBuilder();
        builder.subject(ENT.NAMESPACE + item.getId())
                .add(RDF.TYPE, ENT.LOCATION)
                .add(PROP.NAME, item.getName())
                .add(PROP.DESCRIPTION, item.getDescription());

        item.getSources().forEach(source -> {
            builder.add(PROP.SOURCE, source.toString());
        });

        return builder.build();
    }

    @Override
    public String getName() {
        return "location";
    }
}
