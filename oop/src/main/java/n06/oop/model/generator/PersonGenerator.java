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

public class PersonGenerator extends BaseGenerator<Person> {

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

                Model model = createModel(person);

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
    public Model createModel(Person item) {
        Model model = builder.subject("model:" + item.getId())
                .add(RDF.TYPE, Entity.PERSON)
                .add(Property.NAME, item.getName())
                .add(Property.DESCRIPTION, item.getDescription())
                .build();

        return model;
    }

    @Override
    public String getName() {
        return "person";
    }
}
