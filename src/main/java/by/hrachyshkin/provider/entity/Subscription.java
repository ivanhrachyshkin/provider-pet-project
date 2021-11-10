package by.hrachyshkin.provider.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class Subscription {

    private final Integer id;
    private final Integer accountId;
    private final Integer tariffId;

    public Subscription(final Integer accountId,
                        final Integer tariffId) {
        this.id = -1;
        this.accountId = accountId;
        this.tariffId = tariffId;
    }
}
