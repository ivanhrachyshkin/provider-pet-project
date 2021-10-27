package by.hrachyshkin.dao;

import by.hrachyshkin.Constants;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SchemaDAO extends BaseDAO {

    private final Path schemaPath;

    public SchemaDAO(final DataSource dataSource, final Path schemaPath) {

        super(dataSource);
        this.schemaPath = schemaPath;
    }

    public void init() throws DAOException {

        try (final Connection connection = dataSource.getConnection();
             final Statement statement = connection.createStatement()) {

            final String query = Files.readString(schemaPath);
            statement.executeUpdate(query);

        } catch (SQLException | IOException e) {
            throw new DAOException(Constants.SCHEMA_DAO_INIT_ERROR, e);
        }
    }
}
