package n06.oop.model.entities;

public class Location extends BaseEntity {
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String getType() {
        return "location";
    }
}
