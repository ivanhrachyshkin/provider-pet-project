package by.hrachyshkin.entity;

import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public final class Discount {

    @ToString
    public enum Type {
        PERCENTAGE,
        COEFFICIENT
    }

    private final Integer id;
    private final String name;
    private final Type type;
    private final Integer value;
    private final Date dateFrom;
    private final Date dateTo;

    public Discount(final String name,
                    final Type type,
                    final Integer value,
                    final Date dateFrom,
                    final Date dateTo) {
        this(null, name, type, value, dateFrom, dateTo);
    }
}
