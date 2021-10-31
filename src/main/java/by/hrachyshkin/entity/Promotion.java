package by.hrachyshkin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Promotion {

    private final int id;
    private final String name;
    private final String text;
    private final int discount_percent;

    public Promotion(final String name, final String text, final int discount_percent) {
        this.id = -1;
        this.name = name;
        this.text = text;
        this.discount_percent = discount_percent;
    }
}
