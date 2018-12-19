package n06.oop.generator.iml;

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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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
        Country country = null;
        conn.begin();
        long start = System.currentTimeMillis();
        try {
            for (int count = 0; count < num; count++) {
                int rand = ThreadLocalRandom.current().nextInt(0, countries.size());

                country = countries.get(rand);
                country.setId("COUNTRY_" + count);
                List<Source> sources = generateSources(ThreadLocalRandom.current().nextInt(1,6));
                country.setSources(sources);
                Model model = createModel(country);
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
    public Model createModel(Country item) {
        builder = new ModelBuilder();
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
