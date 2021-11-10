package by.hrachyshkin.provider.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.sql.Date;
import java.time.LocalDate;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public final class Bill {

    private final Integer subscriptionId;
    private final Float value;
    private final LocalDate date;
    private final Boolean status;
}
