package by.hrachyshkin.provider.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class Promotion {

    private final Integer tariffId;
    private final Integer discountId;
}
