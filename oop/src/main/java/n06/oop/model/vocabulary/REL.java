package n06.oop.model.vocabulary;

import org.eclipse.rdf4j.model.Namespace;
import org.eclipse.rdf4j.model.impl.SimpleNamespace;

public class REL {
    public static final String NAMESPACE = "http://nhom06/relationship#";
    public static final String PREFIX = "rel";
    public static final Namespace NS = new SimpleNamespace(PREFIX, NAMESPACE);
}
