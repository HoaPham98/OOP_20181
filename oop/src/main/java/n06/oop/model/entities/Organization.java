package n06.oop.model.entities;

public class Organization extends BaseEntity {
    private int valuation;

    public int getValuation() {
        return valuation;
    }

    public void setValuation(int valuation) {
        this.valuation = valuation;
    }

    @Override
    public String getType() {
        return "organization";
    }
}
