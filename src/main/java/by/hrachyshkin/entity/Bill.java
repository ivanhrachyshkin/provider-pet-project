package by.hrachyshkin.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.sql.Date;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public final class Bill {

    private final Integer subscriptionId;
    private final Float value;
    private final Date date;
    private final Boolean status;
}
