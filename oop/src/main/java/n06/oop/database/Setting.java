package n06.oop.database;

import org.eclipse.rdf4j.model.Namespace;
import org.eclipse.rdf4j.model.impl.SimpleNamespace;

public class Setting {

    /**
     * CONFIG FOR DATABASE SERVER
     */
    public static final String URL = "http://localhost:7200/";
    public static final String REPO_NAME = "oop_v2";
    public static final String REPO_NAME_100_200 = "oop_100-200";
    public static final String REPO_NAME_5k_7k = "oop_5k-7k";
    public static final String REPO_NAME_60k_80k = "oop_60k-80k";
    public static final String REPO_NAME_1m_2m = "oop_1m-2m";
    public static final String REPO_NAME_15m_17m = "oop_v2";

    /**
     * CONFIG CONCURRENT
     */
    public static final int MAX_CONCURRENT = 6;

    /**
     *  CONST
     */
    public static final String[] TYPES = {"person", "country", "location", "event", "organization", "time"};
}
