package n06.oop.model.vocabulary;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Namespace;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleNamespace;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;

public class ENT {
    public static final String NAMESPACE = "http://nhom06/model#";
    public static final String PREFIX = "model";
    public static final Namespace NS = new SimpleNamespace(PREFIX, NAMESPACE);

    public static final IRI COUNTRY;
    public static final IRI PERSON;
    public static final IRI TIME;
    public static final IRI ORGANIZATION;
    public static final IRI EVENT;
    public static final IRI LOCATION;


    static {
        ValueFactory factory = SimpleValueFactory.getInstance();
        COUNTRY = factory.createIRI(NAMESPACE, "country");
        PERSON = factory.createIRI(NAMESPACE, "person");
        TIME = factory.createIRI(NAMESPACE, "time");
        EVENT = factory.createIRI(NAMESPACE, "event");
        ORGANIZATION = factory.createIRI(NAMESPACE, "organization");
        LOCATION = factory.createIRI(NAMESPACE, "location");
    }
}
