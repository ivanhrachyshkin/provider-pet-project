package by.hrachyshkin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Subscription {

    private final int id;
    private final int accountId;
    private final int tariffId;
    private final Date fromDate;
    private final Date toDate;


    public Subscription(int accountId, int tariffId, Date fromDate, Date toDate) {
        this.id = -1;
        this.accountId = accountId;
        this.tariffId = tariffId;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }
}
