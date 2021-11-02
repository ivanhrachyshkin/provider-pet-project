package by.hrachyshkin.entity;

import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public final class Traffic {

    private final Integer id;
    private final Integer subscriptionId;
    private final Integer value;
    private final Date date;


    public Traffic(final Integer subscriptionId,
                   final Integer value,
                   final Date date) {
        this(null, subscriptionId, value, date);
    }
}
