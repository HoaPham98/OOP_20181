package n06.oop.relationship;

import n06.oop.database.ConnectionManager;
import n06.oop.model.vocabulary.ENT;
import n06.oop.model.vocabulary.REL;
import org.apache.commons.io.IOUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.repository.RepositoryConnection;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import static n06.oop.database.Setting.TYPES;

import n06.oop.utils.Utils;

public class RelationGenerator {
    public static final Map<String, Map<String, List<String>>> RULES;

    private Map<String, Integer> sizeOfType;
    private RepositoryConnection conn;
    private ValueFactory factory;

    static {
        Map<String, Map<String, List<String>>> map = new HashMap<>();
        try {
            InputStream inputStream = RelationGenerator.class.getResourceAsStream("/relation/rules.json");
            String jsonText = IOUtils.toString(inputStream, "utf-8");


            JSONObject json = new JSONObject(jsonText);

            for (String type : TYPES) {
                JSONObject obj1 = json.getJSONObject(type);
                Map<String, List<String>> map1 = new HashMap<>();
                for (String type1 : TYPES) {
                    JSONArray arr = obj1.getJSONArray(type1);
                    List<String> values = new ArrayList<>();
                    for (int i = 0; i < arr.length(); i++) {
                        values.add(arr.getString(i));
                    }
                    map1.put(type1, values);
                }
                map.put(type, map1);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        RULES = map;
    }

    public RelationGenerator(Map<String, Integer> sizeOfType) {
        // Khởi tạo connection
        conn = ConnectionManager.getConnection();
        factory = SimpleValueFactory.getInstance();
        this.sizeOfType = sizeOfType;
    }

    public void generateRelation(int num) {
        try {
            conn.begin();
            IntStream.range(0, num).forEach(count -> {
                String type1 = Utils.getRandomFromList(TYPES);
                String type2 = Utils.getRandomFromList(TYPES);
                List<String> rels = RULES.get(type1).get(type2);
                if (rels.size() == 0 || sizeOfType.get(type1) == 0 || sizeOfType.get(type2) == 0) {
                    return;
                }
                String relation = Utils.getRandomFromList(rels);
                relation = Utils.nameToIRIString(relation);

                String id1 = type1.toUpperCase() + "_" + ThreadLocalRandom.current().nextInt(0, sizeOfType.get(type1));
                String id2 = type2.toUpperCase() + "_" + ThreadLocalRandom.current().nextInt(0, sizeOfType.get(type2));

                IRI iri1 = factory.createIRI(ENT.NAMESPACE, id1);
                IRI iri2 = factory.createIRI(ENT.NAMESPACE, id2);

                IRI iriRel = factory.createIRI(REL.NAMESPACE, relation);

                Statement nameStatement = factory.createStatement(iri1, iriRel, iri2);

                conn.add(nameStatement);


            });
            conn.commit();
        } catch (Throwable t) {
            conn.rollback();
            t.printStackTrace();
        }
    }

}
