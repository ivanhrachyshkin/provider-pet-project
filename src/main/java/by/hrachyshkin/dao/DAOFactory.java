package by.hrachyshkin.dao;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.Validate;

import javax.naming.InitialContext;
import javax.sql.DataSource;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DAOFactory {

    @Getter
    public static final DAOFactory INSTANCE = new DAOFactory();
    @Getter(AccessLevel.NONE)
    private final DataSource dataSource = getDataSource();
    private final UserDAO userDAO = new UserDAO(dataSource);

    {
        try {
            userDAO.init();
        } catch (DAOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private DataSource getDataSource() {

        try {
            final InitialContext initialContext = new InitialContext();
            final DataSource dataSource =
                    (DataSource) initialContext.lookup("java:/comp/env/jdbc/training-java-project-final");

            return Validate.notNull(dataSource, "Data source not found!");

        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }
}
