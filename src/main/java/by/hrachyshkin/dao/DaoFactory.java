package by.hrachyshkin.dao;

import by.hrachyshkin.Constants;
import by.hrachyshkin.dao.entity_dao.EntityDao;
import by.hrachyshkin.dao.entity_dao.account_dao.AccountDaoImpl;
import by.hrachyshkin.dao.entity_dao.bill_dao.BillDaoImpl;
import by.hrachyshkin.dao.entity_dao.discount_dao.DiscountDaoImpl;
import by.hrachyshkin.dao.entity_dao.promotion_dao.PromotionDaoImpl;
import by.hrachyshkin.dao.entity_dao.subscription_dao.SubscriptionDaoImpl;
import by.hrachyshkin.dao.entity_dao.tariff_dao.TariffDaoImpl;
import by.hrachyshkin.dao.entity_dao.traffic_dao.TrafficDaoImpl;
import by.hrachyshkin.entity.*;
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
    private final EntityDao<Account> accountDaoImpl = new AccountDaoImpl(dataSource);
    private final EntityDao<Bill> BillDaoImpl = new BillDaoImpl(dataSource);
    private final EntityDao<Discount> discountDaoImpl = new DiscountDaoImpl(dataSource);
    private final EntityDao<Promotion> promotionDaoImpl = new PromotionDaoImpl(dataSource);
    private final EntityDao<Subscription> subscriptionDaoImpl = new SubscriptionDaoImpl(dataSource);
    private final EntityDao<Tariff> tariffDaoImpl = new TariffDaoImpl(dataSource);
    private final EntityDao<Traffic> trafficDaoImpl = new TrafficDaoImpl(dataSource);

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
