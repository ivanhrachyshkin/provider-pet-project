package by.hrachyshkin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Bill {

    private final int id;
    private final int subscriptionId;
    private final double sum;
    private final boolean status;

    public Bill(int subscriptionId, double sum, boolean status) {
        this.id = -1;
        this.subscriptionId = subscriptionId;
        this.sum = sum;
        this.status = status;
    }
}
