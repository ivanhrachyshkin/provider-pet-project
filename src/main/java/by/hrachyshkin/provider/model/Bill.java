package by.hrachyshkin.provider.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public final class Bill implements Model {

    private final Integer subscriptionId;
    private final Float value;
    private final LocalDate date;
    private final Boolean status;
}
