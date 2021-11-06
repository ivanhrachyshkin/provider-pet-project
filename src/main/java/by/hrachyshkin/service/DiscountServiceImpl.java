package by.hrachyshkin.service;

import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.dao.entity_dao.Dao;
import by.hrachyshkin.dao.entity_dao.DaoKeys;
import by.hrachyshkin.dao.transaction.Transaction;
import by.hrachyshkin.dao.transaction.TransactionException;
import by.hrachyshkin.entity.Discount;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class DiscountServiceImpl extends ServiceImpl implements Service<Discount> {

    private final Transaction transaction;

    @Override
    public List<Discount> find() throws ServiceException {

        try {
            Dao<Discount> accountDao = transaction.createDao(DaoKeys.DISCOUNT_DAO);

            return accountDao.find();

        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
