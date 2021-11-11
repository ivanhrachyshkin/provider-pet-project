package by.hrachyshkin.provider.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public final class Discount extends Model {

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
