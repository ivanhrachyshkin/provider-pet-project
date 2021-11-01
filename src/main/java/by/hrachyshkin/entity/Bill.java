package by.hrachyshkin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class Bill {

    private final int id;
    private final int subscriptionId;
    private final double sum;
    private final Date date;
    private final boolean status;

    public Bill(int subscriptionId, double sum, Date date, boolean status) {
        this.id = -1;
        this.subscriptionId = subscriptionId;
        this.sum = sum;
        this.date = date;
        this.status = status;
    }
}
