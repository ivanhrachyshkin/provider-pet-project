package by.hrachyshkin.provider.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Subscription extends Model {

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
