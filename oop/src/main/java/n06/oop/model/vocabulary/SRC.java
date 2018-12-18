//package n06.oop.model.vocabulary;
//
//import org.eclipse.rdf4j.model.IRI;
//import org.eclipse.rdf4j.model.Namespace;
//import org.eclipse.rdf4j.model.ValueFactory;
//import org.eclipse.rdf4j.model.impl.SimpleNamespace;
//import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
//
//public class SRC {
//    public static final String NAMESPACE = "http://nhom06/source#";
//    public static final String PREFIX = "src";
//    public static final Namespace NS = new SimpleNamespace(PREFIX, NAMESPACE);
//
//    public static final IRI LINK;
//    public static final IRI DATE;
//
//
//    static {
//        ValueFactory factory = SimpleValueFactory.getInstance();
//        LINK = factory.createIRI(NAMESPACE, "link");
//        DATE = factory.createIRI(NAMESPACE, "date");
//
//    }
//}
