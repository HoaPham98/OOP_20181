package n06.oop.model;

import n06.oop.database.Setting;
import org.cyberborean.rdfbeans.annotations.RDF;
import org.cyberborean.rdfbeans.annotations.RDFBean;

import java.util.Date;


@RDFBean(Setting.PREFIX_PERSON)
public class Person extends BaseModel{
    private int age;
    private Date birthday;

    @RDF(Setting.PREFIX_PROPERTY + ":age")
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @RDF(Setting.PREFIX_PROPERTY + ":birthday")
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
