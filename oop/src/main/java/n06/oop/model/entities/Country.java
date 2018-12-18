package n06.oop.model.entities;

import n06.oop.database.Setting;
import org.cyberborean.rdfbeans.annotations.RDF;
import org.cyberborean.rdfbeans.annotations.RDFBean;

@RDFBean(Setting.PREFIX_COUNTRY)
public class Country extends BaseEntity {
    private String isoCode;

    @RDF(value = Setting.PREFIX_PROPERTY + "isoCode")
    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    @Override
    public String getType() {
        return "country";
    }
}
