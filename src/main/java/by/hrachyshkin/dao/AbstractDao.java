package by.hrachyshkin.dao;

import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;

import javax.sql.DataSource;
import java.sql.Connection;

@RequiredArgsConstructor
public abstract class AbstractDao {

    protected final Connection connection;

    protected String encrypt(final String password) {

        return DigestUtils.md5Hex(password.toUpperCase());
    }
}
