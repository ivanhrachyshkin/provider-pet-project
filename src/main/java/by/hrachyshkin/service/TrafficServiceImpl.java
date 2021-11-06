package by.hrachyshkin.service;

import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.dao.entity_dao.Dao;
import by.hrachyshkin.dao.entity_dao.DaoKeys;
import by.hrachyshkin.dao.transaction.Transaction;
import by.hrachyshkin.dao.transaction.TransactionException;
import by.hrachyshkin.entity.Traffic;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TrafficServiceImpl extends ServiceImpl implements Service<Traffic> {

    private final Transaction transaction;

    @Override
    public List<Traffic> find() throws ServiceException {

        try {
            Dao<Traffic> accountDao = transaction.createDao(DaoKeys.TRAFFIC_DAO);

            return accountDao.find();

        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
