package by.hrachyshkin.service;

import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.dao.entity_dao.Dao;
import by.hrachyshkin.dao.entity_dao.DaoKeys;
import by.hrachyshkin.dao.transaction.Transaction;
import by.hrachyshkin.dao.transaction.TransactionException;
import by.hrachyshkin.entity.Bill;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BillServiceImpl extends ServiceImpl implements Service<Bill> {

    private final Transaction transaction;

    @Override
    public List<Bill> find() throws ServiceException {

        try {
            Dao<Bill> accountDao = transaction.createDao(DaoKeys.BILL_DAO);

            return accountDao.find();

        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
