package by.hrachyshkin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Subscription {

    private final int id;
    private final int accountId;
    private final int tariffId;
    private final LocalDate fromDate;
    private final LocalDate toDate;


    public Subscription(int accountId, int tariffId, LocalDate fromDate, LocalDate toDate) {
        this.id = -1;
        this.accountId = accountId;
        this.tariffId = tariffId;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }
}
