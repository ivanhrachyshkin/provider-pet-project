package by.hrachyshkin.provider.model;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Promotion implements Model {

    private final Integer tariffId;
    private final Integer discountId;
}
