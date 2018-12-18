package n06.oop.model.entities;

import java.time.LocalDate;

public class Time extends BaseEntity {

    private LocalDate date;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String getType() {
        return "time";
    }
}
