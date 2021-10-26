package by.hrachyshkin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class User {

    public User(String login, String password, String name, String surname, String email, String passport) {
        this.id = -1L;
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.passport = passport;
    }

    public User(long id, String login, String name, String surname, String email, String passport) {
        this.id = id;
        this.login = login;
        this.password = null;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.passport = passport;
    }

    private final long id;
    private final String login;
    private final String password;
    private final String name;
    private final String surname;
    private final String email;
    private final String passport;
}
