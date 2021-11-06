package by.hrachyshkin.service;

import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.dao.entity_dao.Dao;
import by.hrachyshkin.dao.entity_dao.DaoKeys;
import by.hrachyshkin.dao.transaction.Transaction;
import by.hrachyshkin.dao.transaction.TransactionException;
import by.hrachyshkin.entity.Promotion;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PromotionServiceImpl extends ServiceImpl implements Service<Promotion> {

    private final Transaction transaction;

    @Override
    public List<Promotion> find() throws ServiceException {

        try {
            Dao<Promotion> accountDao = transaction.createDao(DaoKeys.PROMOTION_DAO);

            return accountDao.find();

        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
