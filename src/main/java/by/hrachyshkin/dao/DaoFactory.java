package by.hrachyshkin.dao;

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

    private Path getSchemaPath() {

        try {
            return Paths.get(getClass().getClassLoader().getResource("insert_data_database_provider.sql").toURI());

        } catch (final Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private DataSource getDataSource() {

        try {
            final InitialContext initialContext = new InitialContext();
            return (DataSource) initialContext.lookup("java:/comp/env/jdbc/training-java-project-final");
// datasource creation
        } catch (final Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }
}
