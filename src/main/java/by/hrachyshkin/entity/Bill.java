package by.hrachyshkin.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.sql.Date;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public final class Bill {

    private final Integer id;
    private final Integer subscriptionId;
    private final Float value;
    private final Date date;
    private final Boolean status;

    public Bill(final Integer subscriptionId,
                final Float value,
                final Date date,
                final Boolean status
    ) {
        this(null, subscriptionId, value, date, status);
    }
}
