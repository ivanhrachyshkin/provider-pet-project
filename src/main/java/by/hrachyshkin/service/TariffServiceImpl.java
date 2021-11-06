package by.hrachyshkin.service;

import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.dao.entity_dao.Dao;
import by.hrachyshkin.dao.entity_dao.DaoKeys;
import by.hrachyshkin.dao.transaction.Transaction;
import by.hrachyshkin.dao.transaction.TransactionException;
import by.hrachyshkin.entity.Tariff;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TariffServiceImpl extends ServiceImpl implements Service<Tariff> {

    private final Transaction transaction;

    @Override
    public List<Tariff> find() throws ServiceException {

        try {
            Dao<Tariff> accountDao = transaction.createDao(DaoKeys.TARIFF_DAO);

            return accountDao.find();

        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
