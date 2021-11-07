package by.hrachyshkin.service;

import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.dao.entity_dao.DaoKeys;
import by.hrachyshkin.dao.entity_dao.tariff_dao.TariffDao;
import by.hrachyshkin.dao.transaction.Transaction;
import by.hrachyshkin.dao.transaction.TransactionException;
import by.hrachyshkin.entity.Tariff;
import by.hrachyshkin.entity.criteria.Filter;
import by.hrachyshkin.entity.criteria.Sort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TariffServiceImpl extends ServiceImpl implements Service<Tariff> {

    private final Transaction transaction;

    public boolean isExist(final Integer id, final String name) throws ServiceException {

        try {
            final TariffDao tariffDao = transaction.createDao(DaoKeys.ACCOUNT_DAO);
            if (id != null) {
                return tariffDao.isExistById(id);
            } else return tariffDao.isExistByName(name);

        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void add(final Tariff tariff) throws ServiceException {

        try {
            TariffDao tariffDao = transaction.createDao(DaoKeys.ACCOUNT_DAO);
            tariffDao.add(tariff);

        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Tariff> find(final Filter filter, final Sort sort) throws ServiceException {

        return null;
    }

    @Override
    public Tariff findOneById(final Integer id) throws ServiceException {

        try {
            final TariffDao tariffDao = transaction.createDao(DaoKeys.ACCOUNT_DAO);
            return tariffDao.findOneById(id);

        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void update(final Tariff tariff) throws ServiceException {

        try {
            final TariffDao tariffDao = transaction.createDao(DaoKeys.ACCOUNT_DAO);
            tariffDao.updateStatus(tariff);

        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(final Integer id) throws ServiceException {

        try {
            final TariffDao tariffDao = transaction.createDao(DaoKeys.ACCOUNT_DAO);
            tariffDao.delete(id);

        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

}
