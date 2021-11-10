package by.hrachyshkin.provider.entity;

import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

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
    private final LocalDate dateFrom;
    private final LocalDate dateTo;

    public Discount(final String name,
                    final Type type,
                    final Integer value,
                    final LocalDate dateFrom,
                    final LocalDate dateTo) {
        this(null, name, type, value, dateFrom, dateTo);
    }
}