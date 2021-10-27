package by.hrachyshkin.dao;

import by.hrachyshkin.Constants;
import by.hrachyshkin.dao.entity_dao.user_dao.UserDAOImpl;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.nio.file.Path;
import java.nio.file.Paths;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DAOFactory {

    @Getter
    private static final DAOFactory INSTANCE = new DAOFactory();

    @Getter(AccessLevel.NONE)
    private final Path schemaPath = getSchemaPath();
    @Getter(AccessLevel.NONE)
    private final DataSource dataSource = getDataSource();

    private final SchemaDAO schemaDAO = new SchemaDAO(dataSource, schemaPath);
    private final UserDAOImpl userDAOImpl = new UserDAOImpl(dataSource);

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
