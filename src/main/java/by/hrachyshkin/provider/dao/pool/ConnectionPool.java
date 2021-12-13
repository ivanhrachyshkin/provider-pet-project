package by.hrachyshkin.provider.dao.pool;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConnectionPool {

    @Getter
    private static final ConnectionPool INSTANCE = new ConnectionPool();
    private String url;
    private String user;
    private String password;
    private int poolMaxSize;
    private int checkConnectionTimeout;

    private List<Connection> freeConnections;
    private List<Connection> usedConnections = new ArrayList<>();
    private ReentrantLock lock = new ReentrantLock();

    public void init(final String driver,
                     final String url,
                     final String user,
                     final String password,
                     final int poolStartSize,
                     final int poolMaxSize,
                     final int checkConnectionTimeout) throws PoolException {

        try {
            Class.forName(driver);

            this.url = url;
            this.user = user;
            this.password = password;
            this.poolMaxSize = poolMaxSize;
            this.checkConnectionTimeout = checkConnectionTimeout;

            this.freeConnections = Stream
                    .generate(() -> createConnection(url, user, password))
                    .limit(poolStartSize)
                    .collect(Collectors.toList());

        } catch (ClassNotFoundException e) {
            throw new PoolException(e.getMessage());
        }
    }

    public Connection getConnection(final ResourceBundle rb)
            throws PoolException {

        try {
            lock.lock();
            if (freeConnections.isEmpty()) {
                if (usedConnections.size() < poolMaxSize) {
                    freeConnections.add(createConnection(url, user, password));
                } else {
                    throw new PoolException(rb.getString(
                            "pool.maximum.exception"));
                }
            }

            Connection connection =
                    freeConnections.remove(freeConnections.size() - 1);
            if (!isValidConnection(connection, checkConnectionTimeout)) {
                connection = createConnection(url, user, password);
            }

            usedConnections.add(connection);

            return connection;

        } finally {
            lock.unlock();
        }
    }

    public void releaseConnection(final Connection connection) {

        try {
            lock.lock();
            freeConnections.add(connection);
            usedConnections.remove(connection);
        } finally {
            lock.unlock();
        }
    }

    public void destroy() {
        usedConnections.forEach(this::releaseConnection);
        freeConnections.forEach(ConnectionPool::closeConnection);
        freeConnections.clear();
    }

    @SneakyThrows
    private static Connection createConnection(final String url,
                                               final String user,
                                               final String password) {
        return DriverManager.getConnection(url, user, password);
    }

    @SneakyThrows
    private static void closeConnection(final Connection connection) {
        connection.close();
    }

    @SneakyThrows
    private static boolean isValidConnection(final Connection connection,
                                             final int checkConnectionTimeout) {
        return connection.isValid(checkConnectionTimeout);
    }
}
