package by.hrachyshkin.provider.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Promotion extends Model {

    private final Integer tariffId;
    private final Integer discountId;
}
