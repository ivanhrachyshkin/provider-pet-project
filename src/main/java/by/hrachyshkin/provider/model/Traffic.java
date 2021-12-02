package by.hrachyshkin.provider.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public final class Traffic implements Model {

    private final Integer subscriptionId;
    private final Integer value;
    private final LocalDate date;
}
