package by.hrachyshkin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.management.relation.Role;


@Data
@AllArgsConstructor
public class Account {

    private final int id;
    private final String login;
    private final String password;
    private final int role;
    private final int profileId;
    private final int tariffId;
    private final int balanceId;
    private final boolean blocked;

    public Account(String login, String password, int role, int profileId, int tariffId, int balanceId, boolean blocked) {
        this.id = -1;
        this.login = login;
        this.password = password;
        this.role = role;
        this.profileId = profileId;
        this.tariffId = tariffId;
        this.balanceId = balanceId;
        this.blocked = blocked;
    }

    public Account(int id, String login, int role, int profileId, int tariffId, int balanceId, boolean blocked) {
        this.id = id;
        this.login = login;
        this.password = null;
        this.role = role;
        this.profileId = profileId;
        this.tariffId = tariffId;
        this.balanceId = balanceId;
        this.blocked = blocked;
    }

}
