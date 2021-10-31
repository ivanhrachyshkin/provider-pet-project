package by.hrachyshkin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.management.relation.Role;
import java.math.BigDecimal;


@Data
@AllArgsConstructor
public class Account {

    private final int id;
    private final String name;
    private final String email;
    private final String password;
    private final String phone;
    private final String address;
    private final Role role;
    private final double balance;

    public enum Role {
        ADMIN,
        USER,
        BLOCKED
    }

    public Account(String name, String email, String password, String phone, String address, Role role, double balance) {
        this.id = -1;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.role = role;
        this.balance = balance;
    }
}
