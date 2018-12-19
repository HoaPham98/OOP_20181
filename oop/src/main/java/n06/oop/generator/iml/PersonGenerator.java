package n06.oop.generator.iml;

import n06.oop.model.entities.Person;
import n06.oop.model.entities.Source;
import n06.oop.model.vocabulary.ENT;
import n06.oop.model.vocabulary.PROP;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.vocabulary.RDF;

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
                List<Source> sources = generateSources(ThreadLocalRandom.current().nextInt(1,6));
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
            t.printStackTrace();
        }
    }

    @Override
    public Model createModel(Person item) {
        builder.subject(ENT.NAMESPACE + item.getId())
                .add(RDF.TYPE, ENT.PERSON)
                .add(PROP.NAME, item.getName())
                .add(PROP.DESCRIPTION, item.getDescription());

        item.getSources().forEach(source -> {
            builder.add(PROP.SOURCE, source.toString());
        });

        return builder.build();
    }

    @Override
    public String getName() {
        return "person";
    }
}
