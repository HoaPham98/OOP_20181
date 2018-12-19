package n06.oop.generator.iml;

import n06.oop.database.ConnectionManager;
import n06.oop.model.entities.Source;
import n06.oop.model.entities.Time;
import n06.oop.model.vocabulary.ENT;
import n06.oop.model.vocabulary.PROP;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.repository.RepositoryConnection;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class TimeGenerator extends BaseGenerator<Time> {

    static final DateTimeFormatter DATE_FORMMAT = DateTimeFormatter.ofPattern("dd-MM");

    public TimeGenerator() {
        super();
    }

    @Override
    public void generateData(int num) {
        final RepositoryConnection conn = ConnectionManager.getConnection();
        List<Model> models = new ArrayList<>();
        conn.begin();
        try {
            IntStream.range(0,num).parallel().forEach(count -> {
                int year = ThreadLocalRandom.current().nextInt(2015, 2019);
                int day = ThreadLocalRandom.current().nextInt(1, 366);

                LocalDate date = LocalDate.ofYearDay(year, day);

                Time time = new Time();
                time.setId("TIME_" + count);
                time.setName(date.format(DATE_FORMMAT));
                time.setDate(date);
                time.setDescription("Ngày: " + time.getName());

                List<Source> sources = generateSources(ThreadLocalRandom.current().nextInt(1, 6));
                time.setSources(sources);

                Model model = createModel(time);

                models.add(model);
            });
            models.forEach(model -> conn.add(model));
            conn.commit();
        } catch (Throwable t) {
            conn.rollback();
            t.printStackTrace();
        }
    }

    @Override
    public Model createModel(Time item) {
        ModelBuilder builder = new ModelBuilder();
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
