package by.hrachyshkin.dao.entity_dao.tariff_dao;

import by.hrachyshkin.dao.BaseDao;
import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.entity.Criteria;
import by.hrachyshkin.entity.Tariff;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TariffDaoImpl extends BaseDao implements TariffDao {

    private static final String CREATE_TARIFF_QUERY =
            "INSERT " +
                    "INTO tariffs (name, type, speed, price) " +
                    "VALUES (?, ?, ?, ?) " +
                    "ON CONFLICT DO NOTHING";

    private static final String FIND_ALL_TARIFFS_QUERY =
            "SELECT id, name, type, speed, price " +
                    "FROM discounts";

    private static final String FIND_ALL_TARIFFS_QUERY_WITH_SORT =
            "SELECT id, name, type, speed, price " +
                    "FROM tariffs " +
                    "ORDER BY ? ?";

    private static final String FIND_ALL_TARIFFS_QUERY_BY_FILTER =
            "SELECT id, name, type, speed, price " +
                    "FROM tariffs " +
                    "WHERE ? = ?";

    private static final String UPDATE_TARIFF_QUERY =
            "INSERT INTO accounts (name, type, speed, price) " +
                    "VALUES ?, ?, ?, ?" +
                    "WHERE id = ? " +
                    "ON CONFLICT DO UPDATE";

    private static final String DELETE_TARIFF_BY_ID_QUERY =
            "DELETE id, name, type, speed, price " +
                    "FROM tariffs " +
                    "WHERE id = ?";

    public TariffDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void create(final Tariff tariff) throws DaoException {

        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(CREATE_TARIFF_QUERY)) {

            statement.setString(1, tariff.getName());
            statement.setInt(2, tariff.getType().ordinal());
            statement.setInt(3, tariff.getSpeed());
            statement.setDouble(3, tariff.getPrice());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Can't create tariff", e);
        }
    }

    @Override
    public List<Tariff> find(final Criteria criteria) throws DaoException {

        try (final Connection connection = dataSource.getConnection()) {

            String query;
            if (criteria.getSorting() != null) {
                query = FIND_ALL_TARIFFS_QUERY_WITH_SORT;
            } else if (criteria.getFilter() != null) {
                query = FIND_ALL_TARIFFS_QUERY_BY_FILTER;
            } else query = FIND_ALL_TARIFFS_QUERY;

            try (final PreparedStatement statement = connection.prepareStatement(query);
                 final ResultSet resultSet = statement.executeQuery()) {

                if (criteria.getSorting() != null) {
                    statement.setString(1, criteria.getSorting().getColumn());
                    statement.setString(2, criteria.getSorting().getDirection().name());
                } else if (criteria.getFilter() != null) {
                    statement.setString(1, criteria.getFilter().getColumn());
                    statement.setString(2, criteria.getFilter().getPattern());
                }


                final List<Tariff> tariffs = new ArrayList<>();
                while (resultSet.next()) {
                    final Tariff tariff = new Tariff(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            Tariff.Type.values()[resultSet.getInt(3)],
                            resultSet.getInt(4),
                            resultSet.getDouble(5));
                    tariffs.add(tariff);
                }

                return tariffs;
            }
        } catch (Exception e) {
            throw new DaoException("Can't find required discounts");
        }
    }

    @Override
    public void update(final Tariff tariff) throws DaoException {

        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(UPDATE_TARIFF_QUERY)) {

            statement.setString(1, tariff.getName());
            statement.setInt(2, tariff.getType().ordinal());
            statement.setInt(3, tariff.getSpeed());
            statement.setDouble(4, tariff.getPrice());

            statement.setInt(5, tariff.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Can't add tariff", e);
        }
    }

    @Override
    public void delete(final int id) throws DaoException {

        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(DELETE_TARIFF_BY_ID_QUERY)) {

            statement.setInt(1, id);
            statement.executeQuery();

        } catch (SQLException e) {
            throw new DaoException("Can't delete tariff", e);
        }
    }
}
