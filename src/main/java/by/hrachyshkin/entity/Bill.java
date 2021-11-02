package by.hrachyshkin.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public final class Bill {

    private final Integer id;
    private final Integer subscription_id;
    private final Date date;
    private final Float value;
    private final Boolean status;

    public Bill(final Integer subscription_id,
                final Date date,
                final Float value,
                final Boolean status
                ) {
        this(null, subscription_id, date, value, status);
    }
}
