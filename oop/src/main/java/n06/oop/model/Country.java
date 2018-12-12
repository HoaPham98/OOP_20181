package n06.oop.model;

import n06.oop.database.Setting;
import org.cyberborean.rdfbeans.annotations.RDFBean;
import org.cyberborean.rdfbeans.annotations.RDFSubject;

@RDFBean(Setting.PREFIX_COUNTRY)
public class Country extends BaseModel{
    private String isoCode;

    @RDFSubject(prefix = Setting.PREFIX_PROPERTY + "/isoCode")
    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }
}
