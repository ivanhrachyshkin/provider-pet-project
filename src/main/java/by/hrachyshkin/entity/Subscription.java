package by.hrachyshkin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class Subscription {

    private final int id;
    private final int accountId;
    private final int tariffId;
    private final Date dateFrom;
    private final Date dateTo;


    public Subscription(int accountId, int tariffId, Date dateFrom, Date dateTo) {
        this.id = -1;
        this.accountId = accountId;
        this.tariffId = tariffId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }
}
