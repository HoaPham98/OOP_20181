package n06.oop.model.vocabulary;

        import org.eclipse.rdf4j.model.IRI;
        import org.eclipse.rdf4j.model.Namespace;
        import org.eclipse.rdf4j.model.ValueFactory;
        import org.eclipse.rdf4j.model.impl.SimpleNamespace;
        import org.eclipse.rdf4j.model.impl.SimpleValueFactory;

public class Property {
    public static final String NAMESPACE = "http://nhom06/property#";
    public static final String PREFIX = "prop";
    public static final Namespace NS = new SimpleNamespace("prop", "http://nhom06/property#");

    public static final IRI NAME;
    public static final IRI DESCRIPTION;
    public static final IRI SOURCE;


    static {
        ValueFactory factory = SimpleValueFactory.getInstance();
        NAME = factory.createIRI(NAMESPACE, "name");
        DESCRIPTION = factory.createIRI(NAMESPACE, "description");
        SOURCE = factory.createIRI(NAMESPACE, "source");
    }
}
