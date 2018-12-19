package n06.oop.generator.iml;

import n06.oop.database.ConnectionManager;
import n06.oop.model.entities.Country;
import n06.oop.model.entities.Source;
import n06.oop.model.vocabulary.ENT;
import n06.oop.model.vocabulary.PROP;
import org.apache.commons.io.IOUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.repository.RepositoryConnection;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class CountryGenerator extends BaseGenerator<Country> {

    private List<Country> countries;

    public CountryGenerator() {
        super();
        filePath += "/country.json";
        init(filePath);
    }

    @Override
    public void init(String filePath) {
        try {
            InputStream inputStream = this.getClass().getResourceAsStream(filePath);
            String jsonText = IOUtils.toString(inputStream, "utf-8");

            countries = new ArrayList<>();
            JSONObject obj = new JSONObject(jsonText);
            JSONArray jsonArray = obj.getJSONObject("countries").getJSONArray("country");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                Country country = new Country();
                country.setName(json.getString("countryName"));
                country.setDescription("Quốc gia: " + country.getName());
                country.setIsoCode(json.getString("countryCode"));
                int po = json.getInt("population");
                country.setPopulation(po);
                countries.add(country);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void generateData(int num) {
        final RepositoryConnection conn = ConnectionManager.getConnection();
        conn.begin();
        try {
            Collections.shuffle(dataList);
            IntStream.range(0,num).parallel().forEach(count -> {
                int i = count % countries.size();
                Country country = countries.get(i);
                country.setId("COUNTRY_" + count);
                List<Source> sources = generateSources(ThreadLocalRandom.current().nextInt(1,6));
                country.setSources(sources);
                Model model = createModel(country);
                conn.add(model);
            });
            conn.commit();
        } catch (Throwable t) {
            conn.rollback();
        }
    }

    @Override
    public Model createModel(Country item) {
        ModelBuilder builder = new ModelBuilder();
        builder.subject(ENT.NAMESPACE + item.getId())
                .add(RDF.TYPE, ENT.COUNTRY)
                .add(PROP.NAME, item.getName())
                .add(PROP.DESCRIPTION, item.getDescription())
                .add(PROP.POPULATION, item.getPopulation())
                .add(PROP.ISO_CODE, item.getIsoCode());

        item.getSources().forEach(source -> {
            builder.add(PROP.SOURCE, source.toString());
        });

        return builder.build();
    }
}
