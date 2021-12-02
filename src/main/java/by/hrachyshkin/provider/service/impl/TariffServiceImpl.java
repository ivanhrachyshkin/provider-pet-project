package by.hrachyshkin.provider.service.impl;

import by.hrachyshkin.provider.dao.*;
import by.hrachyshkin.provider.model.Subscription;
import by.hrachyshkin.provider.model.Tariff;
import by.hrachyshkin.provider.service.ServiceException;
import by.hrachyshkin.provider.service.TariffService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Represents operations for Tariffs.
 *
 * @see Tariff
 */
@RequiredArgsConstructor
public class TariffServiceImpl implements TariffService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(TariffServiceImpl.class);
    private final Transaction transactionImpl;
    private final ResourceBundle rb;

    @Override
    public boolean isExist(final Integer id)
            throws ServiceException, TransactionException {

        try {
            LOGGER.debug("method isExist starts ");
            final TariffDao tariffDao =
                    transactionImpl.createDao(DaoKeys.TARIFF);
            final boolean value = tariffDao.isExistById(id);
            LOGGER.debug("method isExist finish ");
            transactionImpl.commit();
            return value;

        } catch (TransactionException | DaoException e) {
            LOGGER.error(e.getMessage());
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Tariff> find() throws ServiceException, TransactionException {

        try {
            LOGGER.debug("method find starts ");
            final TariffDao tariffDao =
                    transactionImpl.createDao(DaoKeys.TARIFF);
            final List<Tariff> tariffs = tariffDao.find();
            LOGGER.debug("method find finish ");
            transactionImpl.commit();
            return tariffs;

        } catch (TransactionException | DaoException e) {
            LOGGER.error(e.getMessage());
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Tariff> findAndSortBySpeedAndPrice(final Integer offset)
            throws ServiceException, TransactionException {

        try {
            LOGGER.debug("method findAndSortBySpeedAndPrice starts ");
            final TariffDao tariffDao =
                    transactionImpl.createDao(DaoKeys.TARIFF);
            final List<Tariff> tariffs =
                    tariffDao.findAndSortBySpeedAndPrice(offset);
            LOGGER.debug("method findAndSortBySpeedAndPrice finish ");
            transactionImpl.commit();
            return tariffs;

        } catch (TransactionException | DaoException e) {
            LOGGER.error(e.getMessage());
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Tariff> findAndFilterByType(final Tariff.Type type,
                                            final int offset)
            throws ServiceException, TransactionException {

        try {
            LOGGER.debug("method findAndFilterByType starts ");
            final TariffDao tariffDao =
                    transactionImpl.createDao(DaoKeys.TARIFF);
            final List<Tariff> tariffs =
                    tariffDao.findAndFilterByType(type, offset);
            LOGGER.debug("method findAndFilterByType finish ");
            transactionImpl.commit();
            return tariffs;

        } catch (TransactionException | DaoException e) {
            LOGGER.error(e.getMessage());
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Tariff> findAndFilterAndSort(final Tariff.Type type)
            throws ServiceException, TransactionException {

        try {
            LOGGER.debug("method findAndFilterByTypeAndSortBySpeedAndPrice"
                    + "starts ");
            final TariffDao tariffDao =
                    transactionImpl.createDao(DaoKeys.TARIFF);
            final List<Tariff> tariffs =
                    tariffDao.findAndFilterAndSort(type);
            LOGGER.debug("method findAndFilterByTypeAndSortBySpeedAndPrice"
                    + "finish ");
            transactionImpl.commit();
            return tariffs;

        } catch (TransactionException | DaoException e) {
            LOGGER.error(e.getMessage());
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }


    @Override
    public Tariff findOneById(final Integer id)
            throws ServiceException, TransactionException {

        try {
            LOGGER.debug("method findOneById starts ");
            final TariffDao tariffDao =
                    transactionImpl.createDao(DaoKeys.TARIFF);

            if (!tariffDao.isExistById(id)) {
                LOGGER.error(rb.getString("tariff.find.one.by.id.exist"
                        + ".exception"));
                transactionImpl.rollback();
                throw new ServiceException(rb.getString("tariff.find"
                        + ".one.by.id.exist.exception"));
            }

            final Tariff tariff = tariffDao.findOneById(id);
            LOGGER.debug("method findOneById finish ");
            transactionImpl.commit();
            return tariff;

        } catch (TransactionException | DaoException e) {
            LOGGER.error(e.getMessage());
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * Find tariffs for subscription.
     *
     * @param accountId account's id
     * @throws ServiceException in case of transaction or dao exception
     */
    @Override
    public List<Tariff> findTariffsForSubscription(final Integer accountId)
            throws ServiceException, TransactionException {

        try {
            LOGGER.debug("method findTariffsForSubscription starts ");
            final TariffDao tariffDao =
                    transactionImpl.createDao(DaoKeys.TARIFF);
            final SubscriptionDao subscriptionDao =
                    transactionImpl.createDao(DaoKeys.SUBSCRIPTION);

            final List<Tariff> tariffs = tariffDao.find();
            final List<Tariff> accountTariffs = new ArrayList<>();
            final List<Subscription> subscriptions =
                    subscriptionDao.findAndFilterByAccountId(accountId);

            for (Subscription subs : subscriptions) {
                for (Tariff tariff : tariffs) {
                    if (subs.getTariffId().equals(tariff.getId())) {
                        accountTariffs.add(tariff);
                    }
                }
            }
            transactionImpl.commit();
            LOGGER.debug("method findTariffsForSubscription finish ");
            return accountTariffs;

        } catch (TransactionException | DaoException e) {
            LOGGER.error(e.getMessage());
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void add(final Tariff tariff)
            throws ServiceException, TransactionException {

        try {
            LOGGER.debug("method isExistByEmailAndPassword starts ");
            final TariffDao tariffDao =
                    transactionImpl.createDao(DaoKeys.TARIFF);

            if (tariff.getName().isEmpty()
                    || tariff.getType() == null
                    || tariff.getSpeed() == null
                    || tariff.getPrice() == null) {
                LOGGER.error(rb.getString("tariff.add.empty.input"
                        + ".exception"));
                transactionImpl.rollback();
                throw new ServiceException(rb.getString("tariff.add"
                        + ".empty.input.exception"));
            }

            if (tariffDao.isExistByName(tariff.getName())) {
                LOGGER.error(rb.getString("tariff.add.exist.exception"));
                transactionImpl.rollback();
                throw new ServiceException(rb.getString("tariff.add.exist"
                        + ".exception"));
            }

            if (tariff.getPrice() < 0) {
                LOGGER.error(rb.getString("tariff.add.negative.exception"));
                transactionImpl.rollback();
                throw new ServiceException(rb.getString("tariff.add"
                        + ".negative.exception"));
            }

            if (tariff.getSpeed() < 0) {
                LOGGER.error(rb.getString("tariff.add.negative.exception"));
                transactionImpl.rollback();
                throw new ServiceException(rb.getString("tariff"
                        + ".add.negative.exception"));
            }

            tariffDao.add(tariff);
            LOGGER.debug("method isExistByEmailAndPassword finish ");
            transactionImpl.commit();

        } catch (TransactionException | DaoException e) {
            LOGGER.error(e.getMessage());
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void update(final Tariff tariff)
            throws ServiceException, TransactionException {

        try {
            LOGGER.debug("method update starts ");
            final TariffDao tariffDao =
                    transactionImpl.createDao(DaoKeys.TARIFF);

            if (tariff.getName().isEmpty()
                    || tariff.getType() == null
                    || tariff.getSpeed() == null
                    || tariff.getPrice() == null) {
                LOGGER.error(rb.getString("tariff.update.empty.input"
                        + ".exception"));
                transactionImpl.rollback();
                throw new ServiceException(rb.getString("tariff.update"
                        + ".empty.input.exception"));
            }

            if (!tariffDao.isExistById(tariff.getId())) {
                LOGGER.error(rb.getString("tariff.update.exist.exception"));
                transactionImpl.rollback();
                throw new ServiceException(rb.getString("tariff"
                        + ".update.exist.exception"));
            }

            if (tariffDao.isExistByNotIdAndName(
                    tariff.getId(), tariff.getName())) {
                LOGGER.error(rb.getString("tariff.update"
                        + ".exist.not.id.exception"));
                transactionImpl.rollback();
                throw new ServiceException(rb.getString("tariff.update"
                        + ".exist.not.id.exception"));
            }

            if (tariff.getPrice() < 0) {
                LOGGER.error(rb.getString("tariff.update.negative"
                        + ".exception"));
                transactionImpl.rollback();
                throw new ServiceException(rb.getString("tariff.update"
                        + ".negative.exception"));
            }

            if (tariff.getSpeed() < 0) {
                LOGGER.error(rb.getString("tariff.update.negative"
                        + ".exception"));
                transactionImpl.rollback();
                throw new ServiceException(rb.getString("tariff.update"
                        + ".negative.exception"));
            }

            tariffDao.update(tariff);
            LOGGER.debug("method update finish ");
            transactionImpl.commit();

        } catch (TransactionException | DaoException e) {
            LOGGER.error(e.getMessage());
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(final Integer id)
            throws ServiceException, TransactionException {

        try {
            LOGGER.debug("method delete starts ");
            final TariffDao tariffDao =
                    transactionImpl.createDao(DaoKeys.TARIFF);
            final SubscriptionDao subscriptionDao =
                    transactionImpl.createDao(DaoKeys.SUBSCRIPTION);
            final PromotionDao promotionDao =
                    transactionImpl.createDao(DaoKeys.PROMOTION);

            if (!tariffDao.isExistById(id)) {
                LOGGER.error(rb.getString("tariff.delete.exist"
                        + ".exception"));
                transactionImpl.rollback();
                throw new ServiceException(rb.getString("tariff.delete"
                        + ".exist.exception"));
            }

            if (subscriptionDao.isExistByTariffId(id)) {
                LOGGER.error(rb.getString("tariff.delete.subscriptions"
                        + ".exception"));
                transactionImpl.rollback();
                throw new ServiceException(rb.getString("tariff.delete"
                        + ".subscriptions.exception"));
            }

            if (promotionDao.isExistByTariffId(id)) {
                LOGGER.error(rb.getString("tariff.delete.promotions"
                        + ".exception"));
                transactionImpl.rollback();
                throw new ServiceException(rb.getString("tariff.delete"
                        + ".promotions.exception"));
            }

            tariffDao.delete(id);
            LOGGER.debug("method delete finish ");
            transactionImpl.commit();

        } catch (TransactionException | DaoException e) {
            LOGGER.error(e.getMessage());
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

}
