package n06.oop.generator.iml;

import n06.oop.model.entities.Event;
import n06.oop.model.entities.Source;
import n06.oop.model.vocabulary.ENT;
import n06.oop.model.vocabulary.PROP;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.model.vocabulary.RDF;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class EventGenerator extends BaseGenerator<Event> {

    public EventGenerator() {
        super();
        filePath += "/" + getName() + ".txt";
        init(filePath);
    }

    @Override
    public void generateData(int num) {
        Event event = new Event();
        conn.begin();
        long start = System.currentTimeMillis();
        try {
            for (int count = 0; count < num; count++) {
                int rand = ThreadLocalRandom.current().nextInt(0, dataList.size());
                String name = dataList.get(rand);

                event.setId("EVENT_" + (count + 1));
                event.setName(name);
                event.setDescription("Sự kiện: " + name);
                List<Source> sources = generateSources(ThreadLocalRandom.current().nextInt(1,6));
                event.setSources(sources);

                Model model = createModel(event);

                if (model.size() >= MAX_STATMENT){
                    conn.add(model);
                    builder = new ModelBuilder();
                }
            }
            conn.add(builder.build());
            conn.commit();
            long end = System.currentTimeMillis();
            long time = end - start;
            System.out.println(time);
        } catch (Throwable t) {
            conn.rollback();
        }
    }

    @Override
    public Model createModel(Event item) {
        builder.subject(ENT.NAMESPACE + item.getId())
                .add(RDF.TYPE, ENT.EVENT)
                .add(PROP.NAME, item.getName())
                .add(PROP.DESCRIPTION, item.getDescription());

        item.getSources().forEach(source -> {
            builder.add(PROP.SOURCE, source.toString());
        });

        return builder.build();
    }

    @Override
    public String getName() {
        return "event";
    }
}
