package by.hrachyshkin.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

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

    private final Integer id;
    private final String email;
    private final String password;
    private final Role role;
    private final String name;
    private final String phone;
    private final String address;
    private final Float balance;

    public Account(final String email,
                   final String password,
                   final Role role,
                   final String name,
                   final String phone,
                   final String address,
                   final Float balance) {
        this(null, email, password, role, name, phone, address, balance);
    }
}
