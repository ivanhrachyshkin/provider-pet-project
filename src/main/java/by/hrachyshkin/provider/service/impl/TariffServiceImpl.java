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

@RequiredArgsConstructor
public class TariffServiceImpl implements TariffService {

    private final Transaction transactionImpl;

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
    public List<Tariff> findAndFilterByType(final Tariff.Type type) throws ServiceException {

        try {
            final TariffDao tariffDao = transactionImpl.createDao(DaoKeys.TARIFF_DAO);
            final List<Tariff> tariffs = tariffDao.findAndFilterByType(type);
            transactionImpl.commit();
            return tariffs;

        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Tariff> findAndFilterAndSort(final Tariff.Type type) throws ServiceException {

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
                throw new ServiceException("Can't find tariff by id because tariff doesn't exist");
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
            final List<Subscription> subscriptions = subscriptionDao.findAndFilter(accountId);

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
                throw new ServiceException("Can't add tariff because of empty input");
            }

                if (tariffDao.isExistByName(tariff.getName())) {
                    throw new ServiceException("Can't add tariff because is already exists");
                }

            if (tariff.getPrice() < 0) {
                throw new ServiceException("Can't add tariff because of negative price");
            }

            if (tariff.getSpeed() < 0) {
                throw new ServiceException("Can't add tariff because of negative speed");
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
                throw new ServiceException("Can't update tariff because of empty input");
            }

            if (!tariffDao.isExistById(tariff.getId())) {
                throw new ServiceException("Can't update current tariff because tariff doesn't exist");
            }

            if (tariffDao.isExistByNotIdAndName(tariff.getId(), tariff.getName())) {
                throw new ServiceException("Can't update current tariff");
            }

            if (tariff.getPrice() < 0) {
                throw new ServiceException("Can't add tariff because of negative price");
            }

            if (tariff.getSpeed() < 0) {
                throw new ServiceException("Can't add tariff because of negative speed");
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
                throw new ServiceException("Can't delete current tariff because tariff doesn't exist");
            }

            if (subscriptionDao.isExistByTariffId(id)) {
                throw new ServiceException("Can't delete current tariff because tariff has subscriptions");
            }

            if (promotionDao.isExistByTariffId(id)) {
                throw new ServiceException("Can't delete current tariff because tariff has promotions");
            }

            tariffDao.delete(id);
            transactionImpl.commit();

        } catch (TransactionException | DaoException e) {
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

}
