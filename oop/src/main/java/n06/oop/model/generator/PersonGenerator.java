package n06.oop.model.generator;

import n06.oop.model.entities.Person;
import n06.oop.model.entities.Source;
import n06.oop.model.vocabulary.Entity;
import n06.oop.model.vocabulary.Property;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.model.vocabulary.RDF;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class PersonGenerator extends BaseGenerator {

    public PersonGenerator() {
        super();
        filePath += "/" + getName() + ".txt";
        init(filePath);
    }

    @Override
    public void generateData(int num) {
        Person person = new Person();
        conn.begin();
        long start = System.currentTimeMillis();
        try {
            for (int count = 0; count < num; count++) {
                int rand = ThreadLocalRandom.current().nextInt(0, dataList.size());
                String name = dataList.get(rand);

                person.setId("PERSON_" + (count + 1));
                person.setName(name);
                person.setDescription("Đây là " + name);
                Source source = new Source();
                List<Source> sources = new ArrayList<>();
                sources.add(source);
                person.setSources(sources);

                ModelBuilder builder = new ModelBuilder();
                Model model = builder
                        .subject(Entity.NAMESPACE + person.getId())
                        .add(RDF.TYPE, Entity.PERSON)
                        .add(Property.NAME, name)
                        .add(Property.DESCRIPTION, person.getDescription())
                        .build();

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
    public String getName() {
        return "person";
    }
}
