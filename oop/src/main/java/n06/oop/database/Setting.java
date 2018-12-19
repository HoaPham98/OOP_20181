package n06.oop.database;

import org.eclipse.rdf4j.model.Namespace;
import org.eclipse.rdf4j.model.impl.SimpleNamespace;

public class Setting {

    /**
     * CONFIG FOR DATABASE SERVER
     */
    public static final String URL = "http://localhost:7200/";
    public static final String REPO_NAME = "oop";

    /**
     * CONFIG PREFIX
     */



    public static final String PREFIX_PROPERTY = "http://nhom06/property#";
    public static final String PREFIX_SOURCE = "http://nhom06/source#";
    public static final String PREFIX_RELATIONSHIP = "http://nhom06/relationship#";

    public static final String PEEFIX_MODEL = "http://nhom06/model#";
    public static final String PREFIX_COUNTRY = PEEFIX_MODEL + "country";
    public static final String PREFIX_PERSON = PEEFIX_MODEL + "person";
    public static final String PREFIX_ORGANIZATION = PEEFIX_MODEL + "organization";
    public static final String PREFIX_EVENT = PEEFIX_MODEL + "event";
    public static final String PREFIX_TIME = PEEFIX_MODEL + "time";
    public static final String PREFIX_LOCATION = PEEFIX_MODEL + "location";
}
