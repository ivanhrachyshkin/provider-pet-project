package by.hrachyshkin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class Promotion {

    private final int id;
    private final int tariffId;
    private final int discountId;
    private final Date dateFrom;
    private final Date dateTo;

    public Promotion(int tariffId, int discountId, Date dateFrom, Date dateTo) {
        this.id = -1;
        this.tariffId = tariffId;
        this.discountId = discountId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }
}
