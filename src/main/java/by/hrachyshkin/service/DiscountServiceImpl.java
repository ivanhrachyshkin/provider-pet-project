package by.hrachyshkin.service;

import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.dao.entity_dao.DaoKeys;
import by.hrachyshkin.dao.entity_dao.discount_dao.DiscountDao;
import by.hrachyshkin.dao.transaction.Transaction;
import by.hrachyshkin.dao.transaction.TransactionException;
import by.hrachyshkin.entity.Discount;
import by.hrachyshkin.entity.criteria.Filter;
import by.hrachyshkin.entity.criteria.Sort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class DiscountServiceImpl extends ServiceImpl implements Service<Discount> {

    private final Transaction transaction;

    public boolean isExist(final Integer id, final String name) throws ServiceException {

        try {
            final DiscountDao discountDao = transaction.createDao(DaoKeys.DISCOUNT_DAO);
            if (id != null) {
                return discountDao.isExistById(id);
            } else return discountDao.isExistByName(name);

        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void add(final Discount discount) throws ServiceException {

        try {
            DiscountDao discountDao = transaction.createDao(DaoKeys.DISCOUNT_DAO);
            discountDao.add(discount);

        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Discount> find(final Filter filter, final Sort sort) throws ServiceException {

        return null;
    }

    @Override
    public Discount findOneById(final Integer id) throws ServiceException {

        try {
            final DiscountDao discountDao = transaction.createDao(DaoKeys.DISCOUNT_DAO);
            return discountDao.findOneById(id);

        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void update(final Discount discount) throws ServiceException {

        try {
            final DiscountDao discountDao = transaction.createDao(DaoKeys.DISCOUNT_DAO);
            discountDao.updateStatus(discount);

        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(final Integer id) throws ServiceException {

        try {
            final DiscountDao discountDao = transaction.createDao(DaoKeys.DISCOUNT_DAO);
            discountDao.delete(id);

        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
