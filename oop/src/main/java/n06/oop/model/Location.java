package n06.oop.model;

import n06.oop.database.Setting;
import org.cyberborean.rdfbeans.annotations.RDF;
import org.cyberborean.rdfbeans.annotations.RDFBean;

@RDFBean(Setting.PREFIX_LOCATION)
public class Location extends BaseModel {
    private String address;

    @RDF(Setting.PREFIX_PROPERTY + ":address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
