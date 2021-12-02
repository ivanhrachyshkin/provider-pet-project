package by.hrachyshkin.provider.model;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Subscription implements Model {

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
