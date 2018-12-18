package n06.oop.generator.iml;

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

    static final DateTimeFormatter DATE_FORMMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public TimeGenerator() {
        super();
    }

    @Override
    public void generateData(int num) {
        Time time = new Time();
        for (int i=0; i<num; i++) {
            int year = ThreadLocalRandom.current().nextInt(2015, 2019);
            int day = ThreadLocalRandom.current().nextInt(1,366);

            LocalDate date = LocalDate.ofYearDay(year, day);

            time.setId("TIME_" + i);
            time.setName(date.format(DateTimeFormatter.ofPattern("dd-MM")));
            time.setDate(date);
            time.setDescription("Ngày: " + time.getName());

            List<Source> sources = generateSources(ThreadLocalRandom.current().nextInt(1,6));
            time.setSources(sources);

            Model model = createModel(time);

            conn.add(model);
        }
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
