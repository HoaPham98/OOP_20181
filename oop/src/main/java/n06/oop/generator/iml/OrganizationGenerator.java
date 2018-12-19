package n06.oop.generator.iml;

import n06.oop.database.ConnectionManager;
import n06.oop.model.entities.Organization;
import n06.oop.model.entities.Source;
import n06.oop.model.vocabulary.ENT;
import n06.oop.model.vocabulary.PROP;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.repository.RepositoryConnection;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class OrganizationGenerator extends BaseGenerator<Organization> {

    public OrganizationGenerator() {
        super();
        filePath += "/" + getName() + ".txt";
        init(filePath);
    }

    @Override
    public void generateData(int num) {
        final RepositoryConnection conn = ConnectionManager.getConnection();
        conn.begin();
        try {
            Collections.shuffle(dataList);
            IntStream.range(0,num).parallel().forEach(count -> {
                int i = count % dataList.size();
                String name = dataList.get(i);

                Organization organization = new Organization();
                organization.setId("ORGANIZATION_" + (count + 1));
                organization.setName(name);
                organization.setValuation(ThreadLocalRandom.current().nextInt(1000000, 1000000001));
                organization.setDescription("Tổ chức: " + name);
                List<Source> sources = generateSources(ThreadLocalRandom.current().nextInt(1,6));
                organization.setSources(sources);

                Model model = createModel(organization);

                conn.add(model);
            });
            conn.commit();
        } catch (Throwable t) {
            conn.rollback();
            t.printStackTrace();
        }
    }

    @Override
    public Model createModel(Organization item) {
        ModelBuilder builder = new ModelBuilder();
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
