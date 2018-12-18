package n06.oop.model.entities;

import n06.oop.database.Setting;
import org.cyberborean.rdfbeans.annotations.RDFBean;


@RDFBean(Setting.PREFIX_PERSON)
public class Person extends BaseEntity {
    @Override
    public String getType() {
        return "person";
    }

}
