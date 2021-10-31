package by.hrachyshkin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Traffic {

    private final int id;
    private final int subscription_id;
    private final int value;
    private final Date date;

    public Traffic(int subscription_id, int value, Date date) {
        this.id = -1;
        this.subscription_id = subscription_id;
        this.value = value;
        this.date = date;
    }
}
