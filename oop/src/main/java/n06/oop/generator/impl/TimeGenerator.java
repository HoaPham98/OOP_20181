package n06.oop.generator.impl;

import n06.oop.model.entities.Source;
import n06.oop.model.entities.Time;
import n06.oop.model.vocabulary.ENT;
import n06.oop.model.vocabulary.PROP;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.vocabulary.RDF;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class TimeGenerator extends BaseGenerator<Time> {

//    static final DateTimeFormatter DATE_FORMMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public TimeGenerator() {
        super();
    }

    @Override
    public Time createEntity(int count) {
        Time time = new Time();
        int year = ThreadLocalRandom.current().nextInt(1940, 2019);
        int day = ThreadLocalRandom.current().nextInt(1, 366);

        LocalDate date = LocalDate.ofYearDay(year, day);

        time.setId("TIME_" + count);
        time.setName(date.format(DateTimeFormatter.ofPattern("dd-MM")));
        time.setDate(date);
        time.setDescription("Ngày: " + time.getName());

        List<Source> sources = generateSources();
        time.setSources(sources);
        return time;
    }

    @Override
    public Model createModel(Time item) {
        builder.subject(ENT.NAMESPACE + item.getId())
                .add(RDF.TYPE, ENT.TIME)
                .add(PROP.NAME, item.getName())
                .add(PROP.DESCRIPTION, item.getDescription())
                .add(PROP.DATE, item.getDate());

        item.getSources().forEach(source -> {
            builder.add(PROP.SOURCE, source.toString());
        });

        return builder.build();
    }
}
