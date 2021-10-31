package by.hrachyshkin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Discount {

    private final int id;
    private final String name;
    private final Type type;
    private final int value;

    public enum Type {
        PERCENTAGE,
        FIXED
    }

    public Discount(String name, Type type, int value) {
        this.id = -1;
        this.name = name;
        this.type = type;
        this.value = value;
    }
}
