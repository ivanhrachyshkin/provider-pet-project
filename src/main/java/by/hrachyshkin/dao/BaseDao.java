package by.hrachyshkin.dao;

import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;

import javax.sql.DataSource;

@RequiredArgsConstructor
public abstract class BaseDao {

    protected final DataSource dataSource;

    protected String encrypt(final String password) {

        return DigestUtils.md5Hex(password.toUpperCase());
    }
}
