package n06.oop.generator.impl;

import n06.oop.model.entities.Organization;
import n06.oop.model.entities.Source;
import n06.oop.model.vocabulary.ENT;
import n06.oop.model.vocabulary.PROP;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.vocabulary.RDF;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class OrganizationGenerator extends BaseGenerator<Organization> {

    public OrganizationGenerator() {
        super();
        filePath += "/" + getName() + ".txt";
        init(filePath);
    }

    @Override
    public Organization createEntity(int count) {
        Organization organization = new Organization();

        int rand = ThreadLocalRandom.current().nextInt(0, dataList.size());
        String name = dataList.get(rand);

        organization.setId("ORGANIZATION_" + (count + 1));
        organization.setName(name);
        organization.setValuation(ThreadLocalRandom.current().nextInt(1000000, 1000000001));
        organization.setDescription("Tổ chức: " + name);
        List<Source> sources = generateSources();
        organization.setSources(sources);

        return organization;
    }

    @Override
    public Model createModel(Organization item) {
        builder.subject(ENT.NAMESPACE + item.getId())
                .add(RDF.TYPE, ENT.ORGANIZATION)
                .add(PROP.NAME, item.getName())
                .add(PROP.DESCRIPTION, item.getDescription())
                .add(PROP.VALUATION, item.getValuation());

        item.getSources().forEach(source -> {
            builder.add(PROP.SOURCE, source.toString());
        });

        return builder.build();
    }

    @Override
    public String getName() {
        return "organization";
    }
}
