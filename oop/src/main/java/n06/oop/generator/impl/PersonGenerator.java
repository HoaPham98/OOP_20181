package n06.oop.generator.impl;

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
    public Person createEntity(int count) {
        Person person = new Person();
        int rand = ThreadLocalRandom.current().nextInt(0, dataList.size());
        String name = dataList.get(rand);

        person.setId("PERSON_" + (count + 1));
        person.setName(name);
        person.setDescription("Đây là " + name);
        List<Source> sources = generateSources();
        person.setSources(sources);

        return person;
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
