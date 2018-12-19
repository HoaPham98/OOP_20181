package n06.oop.generator.iml;

import n06.oop.database.ConnectionManager;
import n06.oop.model.entities.Event;
import n06.oop.model.entities.Source;
import n06.oop.model.vocabulary.ENT;
import n06.oop.model.vocabulary.PROP;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.repository.RepositoryConnection;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class EventGenerator extends BaseGenerator<Event> {

    public EventGenerator() {
        super();
        filePath += "/" + getName() + ".txt";
        init(filePath);
    }

    @Override
    public void generateData(int num) {
        final RepositoryConnection conn = ConnectionManager.getConnection();
        conn.begin();
        try {
            Collections.shuffle(dataList);
            IntStream.range(0,num).parallel().forEach(count -> {
                int i = count % dataList.size();
                String name = dataList.get(i);
                Event event = new Event();
                event.setId("EVENT_" + (count + 1));
                event.setName(name);
                event.setDescription("Sự kiện: " + name);
                List<Source> sources = generateSources(ThreadLocalRandom.current().nextInt(1,6));
                event.setSources(sources);

                Model model = createModel(event);

                conn.add(model);
            });
            conn.commit();
        } catch (Throwable t) {
            conn.rollback();
        }
    }

    @Override
    public Model createModel(Event item) {
        ModelBuilder builder = new ModelBuilder();
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
