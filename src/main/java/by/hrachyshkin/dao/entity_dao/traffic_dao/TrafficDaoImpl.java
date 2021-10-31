package by.hrachyshkin.dao.entity_dao.traffic_dao;

import by.hrachyshkin.dao.BaseDao;
import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.entity.Criteria;
import by.hrachyshkin.entity.Discount;
import by.hrachyshkin.entity.Traffic;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrafficDaoImpl extends BaseDao implements TrafficDao {

    private static final String CREATE_TRAFFIC_QUERY =
            "INSERT " +
                    "INTO traffics (subscription_id, value, date) " +
                    "VALUES (?, ?, ?) " +
                    "ON CONFLICT DO NOTHING";

    private static final String FIND_ALL_TRAFFICS_QUERY =
            "SELECT id, name, type, value " +
                    "FROM traffics";

    private static final String FIND_ALL_TRAFFICS_QUERY_WITH_SORT =
            "SELECT id, subscription_id, value, date " +
                    "FROM traffics " +
                    "ORDER BY ? ?";

    private static final String FIND_ALL_TRAFFICS_QUERY_BY_FILTER =
            "SELECT id, subscription_id, value, date " +
                    "FROM traffics " +
                    "WHERE ? = ?";

    private static final String UPDATE_TRAFFIC_QUERY =
            "INSERT INTO traffics (subscription_id, value, date) " +
                    "VALUES ?, ?, ?" +
                    "WHERE id = ? " +
                    "ON CONFLICT DO UPDATE";

    private static final String DELETE_TRAFFIC_BY_ID_QUERY =
            "DELETE id, subscription_id, value, date " +
                    "FROM traffics " +
                    "WHERE id = ?";


    public TrafficDaoImpl(DataSource dataSource) {
        super(dataSource);
    }


    @Override
    public void create(final Traffic traffic) throws DaoException {

        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(CREATE_TRAFFIC_QUERY)) {

            statement.setInt(1, traffic.getSubscription_id());
            statement.setInt(2, traffic.getValue());
            statement.setDate(3, traffic.getDate());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Can't create discount", e);
        }
    }

    @Override
    public List<Traffic> find(final Criteria criteria) throws DaoException {

        try (final Connection connection = dataSource.getConnection()) {

            String query;
            if (criteria.getSorting() != null) {
                query = FIND_ALL_TRAFFICS_QUERY_WITH_SORT;
            } else if (criteria.getFilter() != null) {
                query = FIND_ALL_TRAFFICS_QUERY_BY_FILTER;
            } else query = FIND_ALL_TRAFFICS_QUERY;

            try (final PreparedStatement statement = connection.prepareStatement(query);
                 final ResultSet resultSet = statement.executeQuery()) {

                if (criteria.getSorting() != null) {
                    statement.setString(1, criteria.getSorting().getColumn());
                    statement.setString(2, criteria.getSorting().getDirection().name());
                } else if (criteria.getFilter() != null) {
                    statement.setString(1, criteria.getFilter().getColumn());
                    statement.setString(2, criteria.getFilter().getPattern());
                }


                final List<Traffic> traffics = new ArrayList<>();
                while (resultSet.next()) {
                    final Traffic traffic = new Traffic(
                            resultSet.getInt(1),
                            resultSet.getInt(2),
                            resultSet.getInt(3),
                            resultSet.getDate(4));
                    traffics.add(traffic);
                }

                return traffics;
            }
        } catch (Exception e) {
            throw new DaoException("Can't find required discounts");
        }
    }

    @Override
    public void update(final Traffic traffic) throws DaoException {

        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(UPDATE_TRAFFIC_QUERY)) {

            statement.setInt(1, traffic.getSubscription_id());
            statement.setInt(2, traffic.getValue());
            statement.setDate(3, traffic.getDate());

            statement.setInt(4, traffic.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Can't add discount", e);
        }
    }

    @Override
    public void delete(final int id) throws DaoException {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(DELETE_TRAFFIC_BY_ID_QUERY)) {

            statement.setInt(1, id);
            statement.executeQuery();

        } catch (SQLException e) {
            throw new DaoException("Can't delete discount", e);
        }
    }
}
