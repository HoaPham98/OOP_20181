package n06.oop.generator.impl;

import n06.oop.model.entities.Event;
import n06.oop.model.entities.Source;
import n06.oop.model.vocabulary.ENT;
import n06.oop.model.vocabulary.PROP;
import org.eclipse.rdf4j.model.Model;
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
    public Event createEntity(int count) {
        Event event = new Event();

        int rand = ThreadLocalRandom.current().nextInt(0, dataList.size());
        String name = dataList.get(rand);

        event.setId("EVENT_" + (count + 1));
        event.setName(name);
        event.setDescription("Sự kiện: " + name);
        List<Source> sources = generateSources();
        event.setSources(sources);

        return event;
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
