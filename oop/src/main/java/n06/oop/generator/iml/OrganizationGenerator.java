package n06.oop.generator.iml;

import n06.oop.model.entities.Organization;
import n06.oop.model.entities.Source;
import n06.oop.model.vocabulary.ENT;
import n06.oop.model.vocabulary.PROP;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.util.ModelBuilder;
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
    public void generateData(int num) {
        Organization organization = new Organization();
        long start = System.currentTimeMillis();
        try {
            conn.begin();
            for (int count = 0; count < num; count++) {
                int rand = ThreadLocalRandom.current().nextInt(0, dataList.size());
                String name = dataList.get(rand);

                organization.setId("ORGANIZATION_" + (count + 1));
                organization.setName(name);
                organization.setValuation(ThreadLocalRandom.current().nextInt(1000000, 1000000001));
                organization.setDescription("Tổ chức: " + name);
                List<Source> sources = generateSources(ThreadLocalRandom.current().nextInt(1,6));
                organization.setSources(sources);

                Model model = createModel(organization);

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
    public Model createModel(Organization item) {
        builder = new ModelBuilder();
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
