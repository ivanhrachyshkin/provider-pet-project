package by.hrachyshkin.provider.model;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public final class Tariff implements Model {

    @ToString
    public enum Type {
        TRAFFICKED,
        UNLIMITED
    }

    private final Integer id;
    private final String name;
    private final Type type;
    private final Integer speed;
    private final Float price;

    public Tariff(final String name,
                  final Type type,
                  final Integer speed,
                  final Float price) {
        this(null, name, type, speed, price);
    }
}
