package by.hrachyshkin.entity.criteria;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public final class Sort {

    public enum Direction {
        ASC,
        DESC
    }

    private final String column;
    private final Direction direction;
}
