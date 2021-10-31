package by.hrachyshkin.dao;

import by.hrachyshkin.Constants;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.nio.file.Path;
import java.nio.file.Paths;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DaoFactory {

    @Getter
    private static final DaoFactory INSTANCE = new DaoFactory();

    @Getter(AccessLevel.NONE)
    private final Path schemaPath = getSchemaPath();
    @Getter(AccessLevel.NONE)
    private final DataSource dataSource = getDataSource();

    private final SchemaDao schemaDAO = new SchemaDao(dataSource, schemaPath);
    private final AccountDaoImpl accountDAOImpl = new AccountDaoImpl(dataSource);

    private Path getSchemaPath() {

        try {
            return Paths.get(getClass().getClassLoader().getResource(Constants.SCHEMA_RESOURCE).toURI());

        } catch (final Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private DataSource getDataSource() {

        try {
            final InitialContext initialContext = new InitialContext();
            return (DataSource) initialContext.lookup(Constants.DATASOURCE_NAME);

        } catch (final Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }
}
