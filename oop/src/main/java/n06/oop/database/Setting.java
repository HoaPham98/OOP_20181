package n06.oop.database;

import org.eclipse.rdf4j.model.Namespace;
import org.eclipse.rdf4j.model.impl.SimpleNamespace;

public class Setting {

    /**
     * CONFIG FOR DATABASE SERVER
     */
    public static final String URL = "http://localhost:7200/";
    public static final String REPO_NAME = "oop_v2";

    /**
     * CONFIG CONCURRENT
     */
    public static final int MAX_CONCURRENT = 6;
}
