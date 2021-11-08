package by.hrachyshkin.service;

import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.dao.entity_dao.DaoKeys;
import by.hrachyshkin.dao.entity_dao.subscription_dao.SubscriptionDao;
import by.hrachyshkin.dao.entity_dao.tariff_dao.TariffDao;
import by.hrachyshkin.dao.transaction.Transaction;
import by.hrachyshkin.dao.transaction.TransactionException;
import by.hrachyshkin.entity.Subscription;
import by.hrachyshkin.entity.Tariff;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class TariffServiceImpl extends ServiceImpl implements Service<Tariff> {

    private final Transaction transaction;

    public boolean isExistBySubscriptionId(final Integer id, final String name) throws ServiceException {

        try {
            final TariffDao tariffDao = transaction.createDao(DaoKeys.TARIFF_DAO);
            if (id != null) {
                return tariffDao.isExistById(id);
            } else return tariffDao.isExistByName(name);

        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Tariff> find() throws ServiceException {

        try {
            final TariffDao tariffDao = transaction.createDao(DaoKeys.TARIFF_DAO);
            return tariffDao.find();
        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public List<Tariff> findAndSortBySpeedAndPrice() throws ServiceException {

        try {
            final TariffDao tariffDao = transaction.createDao(DaoKeys.TARIFF_DAO);
            return tariffDao.findAndSortBySpeedAndPrice();
        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public List<Tariff> findAndFilterByType(final Tariff.Type type) throws ServiceException {

        try {
            final TariffDao tariffDao = transaction.createDao(DaoKeys.TARIFF_DAO);
            return tariffDao.findAndFilterByType(type);
        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public List<Tariff> findAndFilterAndSort(final Tariff.Type type) throws ServiceException {

        try {
            final TariffDao tariffDao = transaction.createDao(DaoKeys.TARIFF_DAO);
            return tariffDao.findAndFilterAndSort(type);
        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }


    @Override
    public Tariff findOneById(final Integer id) throws ServiceException {

        try {
            final TariffDao tariffDao = transaction.createDao(DaoKeys.TARIFF_DAO);
            return tariffDao.findOneById(id);

        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public List<Tariff> findTariffsForAccountId(final Integer accountId) throws ServiceException {

        try {
            final TariffDao tariffDao = transaction.createDao(DaoKeys.TARIFF_DAO);
            final SubscriptionDao subscriptionDao = transaction.createDao(DaoKeys.SUBSCRIPTION_DAO);

            final List<Tariff> tariffs = tariffDao.find();
            final List<Tariff> accountTariffs = new ArrayList<>();
            final List<Subscription> subscriptions = subscriptionDao.findAndFilter(accountId);

            for (Subscription subs : subscriptions) {
                for (Tariff tariff : tariffs) {
                    if (subs.getTariffId().equals(tariff.getId())) {
                        accountTariffs.add(tariff);
                    }
                }
            }
            return accountTariffs;

        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void add(final Tariff tariff) throws ServiceException {

        try {
            TariffDao tariffDao = transaction.createDao(DaoKeys.TARIFF_DAO);
            tariffDao.add(tariff);
        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void update(final Tariff tariff) throws ServiceException {

        try {
            final TariffDao tariffDao = transaction.createDao(DaoKeys.TARIFF_DAO);
            tariffDao.update(tariff);

        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(final Integer id) throws ServiceException {

        try {
            final TariffDao tariffDao = transaction.createDao(DaoKeys.TARIFF_DAO);
            tariffDao.delete(id);

        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

}
