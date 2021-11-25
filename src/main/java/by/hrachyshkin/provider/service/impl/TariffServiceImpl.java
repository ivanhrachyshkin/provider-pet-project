package by.hrachyshkin.provider.service.impl;

import by.hrachyshkin.provider.dao.DaoException;
import by.hrachyshkin.provider.dao.DaoKeys;
import by.hrachyshkin.provider.dao.PromotionDao;
import by.hrachyshkin.provider.dao.SubscriptionDao;
import by.hrachyshkin.provider.dao.TariffDao;
import by.hrachyshkin.provider.dao.Transaction;
import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Subscription;
import by.hrachyshkin.provider.model.Tariff;
import by.hrachyshkin.provider.service.ServiceException;
import by.hrachyshkin.provider.service.TariffService;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@RequiredArgsConstructor
public class TariffServiceImpl implements TariffService {

    private final Transaction transactionImpl;
    private final ResourceBundle rb;

    @Override
    public boolean isExist(final Integer id) throws ServiceException {

        try {
            final TariffDao tariffDao = transactionImpl.createDao(DaoKeys.TARIFF_DAO);
            final boolean value = tariffDao.isExistById(id);
            transactionImpl.commit();
            return value;

        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Tariff> find() throws ServiceException {

        try {
            final TariffDao tariffDao = transactionImpl.createDao(DaoKeys.TARIFF_DAO);
            final List<Tariff> tariffs = tariffDao.find();
            transactionImpl.commit();
            return tariffs;

        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Tariff> findAndSortBySpeedAndPrice(final Integer offset) throws ServiceException {

        try {
            final TariffDao tariffDao = transactionImpl.createDao(DaoKeys.TARIFF_DAO);
            final List<Tariff> tariffs = tariffDao.findAndSortBySpeedAndPrice(offset);
            transactionImpl.commit();
            return tariffs;

        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Tariff> findAndFilterByType(final Tariff.Type type, final int offset) throws ServiceException {

        try {
            final TariffDao tariffDao = transactionImpl.createDao(DaoKeys.TARIFF_DAO);
            final List<Tariff> tariffs = tariffDao.findAndFilterByType(type, offset);
            transactionImpl.commit();
            return tariffs;

        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Tariff> findAndFilterByTypeAndSortBySpeedAndPrice(final Tariff.Type type) throws ServiceException {

        try {
            final TariffDao tariffDao = transactionImpl.createDao(DaoKeys.TARIFF_DAO);
            final List<Tariff> tariffs = tariffDao.findAndFilterAndSort(type);
            transactionImpl.commit();
            return tariffs;

        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }


    @Override
    public Tariff findOneById(final Integer id) throws ServiceException {

        try {
            final TariffDao tariffDao = transactionImpl.createDao(DaoKeys.TARIFF_DAO);

            if (!tariffDao.isExistById(id)) {
                throw new ServiceException(rb.getString("tariff.find.one.by.id.exist.exception"));
            }

            final Tariff tariff = tariffDao.findOneById(id);
            transactionImpl.commit();
            return tariff;

        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Tariff> findTariffsForSubscription(final Integer accountId) throws ServiceException, TransactionException {

        try {
            final TariffDao tariffDao = transactionImpl.createDao(DaoKeys.TARIFF_DAO);
            final SubscriptionDao subscriptionDao = transactionImpl.createDao(DaoKeys.SUBSCRIPTION_DAO);

            final List<Tariff> tariffs = tariffDao.find();
            final List<Tariff> accountTariffs = new ArrayList<>();
            final List<Subscription> subscriptions = subscriptionDao.findAndFilterByAccountId(accountId);

            for (Subscription subs : subscriptions) {
                for (Tariff tariff : tariffs) {
                    if (subs.getTariffId().equals(tariff.getId())) {
                        accountTariffs.add(tariff);
                    }
                }
            }
            transactionImpl.commit();
            return accountTariffs;

        } catch (TransactionException | DaoException e) {
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void add(final Tariff tariff) throws ServiceException, TransactionException {

        try {
            final TariffDao tariffDao = transactionImpl.createDao(DaoKeys.TARIFF_DAO);

            if (tariff.getName().isEmpty()
                    || tariff.getType() == null
                    || tariff.getSpeed() == null
                    || tariff.getPrice() == null) {
                throw new ServiceException(rb.getString("tariff.add.empty.input.exception"));
            }

                if (tariffDao.isExistByName(tariff.getName())) {
                    throw new ServiceException(rb.getString("tariff.add.exist.exception"));
                }

            if (tariff.getPrice() < 0) {
                throw new ServiceException(rb.getString("tariff.add.negative.exception"));
            }

            if (tariff.getSpeed() < 0) {
                throw new ServiceException(rb.getString("tariff.add.negative.exception"));
            }

            tariffDao.add(tariff);
            transactionImpl.commit();

        } catch (TransactionException | DaoException e) {
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void update(final Tariff tariff) throws ServiceException, TransactionException {

        try {
            final TariffDao tariffDao = transactionImpl.createDao(DaoKeys.TARIFF_DAO);

            if (tariff.getName().isEmpty()
                    || tariff.getType() == null
                    || tariff.getSpeed() == null
                    || tariff.getPrice() == null) {
                throw new ServiceException(rb.getString("tariff.update.empty.input.exception"));
            }

            if (!tariffDao.isExistById(tariff.getId())) {
                throw new ServiceException(rb.getString("tariff.update.exist.exception"));
            }

            if (tariffDao.isExistByNotIdAndName(tariff.getId(), tariff.getName())) {
                throw new ServiceException(rb.getString("tariff.update.exist.not.id.exception"));
            }

            if (tariff.getPrice() < 0) {
                throw new ServiceException(rb.getString("tariff.update.negative.exception"));
            }

            if (tariff.getSpeed() < 0) {
                throw new ServiceException(rb.getString("tariff.update.negative.exception"));
            }

            tariffDao.update(tariff);
            transactionImpl.commit();

        } catch (TransactionException | DaoException e) {
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(final Integer id) throws ServiceException, TransactionException {

        try {
            final TariffDao tariffDao = transactionImpl.createDao(DaoKeys.TARIFF_DAO);
            final SubscriptionDao subscriptionDao = transactionImpl.createDao(DaoKeys.SUBSCRIPTION_DAO);
            final PromotionDao promotionDao = transactionImpl.createDao(DaoKeys.PROMOTION_DAO);

            if (!tariffDao.isExistById(id)) {
                throw new ServiceException(rb.getString("tariff.delete.exist.exception"));
            }

            if (subscriptionDao.isExistByTariffId(id)) {
                throw new ServiceException(rb.getString("tariff.delete.subscriptions.exception"));
            }

            if (promotionDao.isExistByTariffId(id)) {
                throw new ServiceException(rb.getString("tariff.delete.promotions.exception"));
            }

            tariffDao.delete(id);
            transactionImpl.commit();

        } catch (TransactionException | DaoException e) {
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

}
