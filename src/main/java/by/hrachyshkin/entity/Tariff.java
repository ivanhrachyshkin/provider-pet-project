package by.hrachyshkin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Tariff {

    private final int id;
    private final String name;
    private final Type type;
    private final int speed;
    private final double price;

    public enum Type {
        LIMITED,
        UNLIMITED
    }

    public Tariff(String name, Type type, int speed, double price) {
        this.id = -1;
        this.name = name;
        this.type = type;
        this.speed = speed;
        this.price = price;
    }
}
