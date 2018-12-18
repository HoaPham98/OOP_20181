package n06.oop.model.entities;

public class Country extends BaseEntity {
    private String isoCode;
    private int population;

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    @Override
    public String getType() {
        return "country";
    }
}
