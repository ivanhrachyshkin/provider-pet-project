package by.hrachyshkin.entity;

import lombok.*;

import javax.management.relation.Role;
import java.math.BigDecimal;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public final class Account {

    @ToString
    public enum Role {
        ADMINISTRATOR,
        BLOCKED,
        USER
    }

    private final BigDecimal id;
    private final String email;
    private final String password;
    private final Role role;
    private final String name;
    private final Integer phone;
    private final String address;
    private final Float balance;

    public Account(final String email,
                   final String password,
                   final Role role,
                   final String name,
                   final Integer phone,
                   final String address,
                   final Float balance) {
        this(null, email, password, role, name, phone, address, balance);
    }
}
