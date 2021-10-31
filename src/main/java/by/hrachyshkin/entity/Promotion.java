package by.hrachyshkin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Promotion {

    private final int id;
    private final int tariffId;
    private final int promotionId;
    private final Date fromDate;
    private final Date toDate;

    public Promotion(int tariffId, int promotionId, Date fromDate, Date toDate) {
        this.id = -1;
        this.tariffId = tariffId;
        this.promotionId = promotionId;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }
}
