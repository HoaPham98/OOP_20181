package n06.oop.model;

import n06.oop.database.Setting;
import org.cyberborean.rdfbeans.annotations.RDF;
import org.cyberborean.rdfbeans.annotations.RDFBean;

@RDFBean(Setting.PREFIX_ORGANIZATION)
public class Organization extends BaseModel {
    private String address;
    private int yearFounded;

    @RDF(Setting.PREFIX_PROPERTY + ":address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @RDF(Setting.PREFIX_PROPERTY + ":yearFounded")
    public int getYearFounded() {
        return yearFounded;
    }

    public void setYearFounded(int yearFounded) {
        this.yearFounded = yearFounded;
    }
}
