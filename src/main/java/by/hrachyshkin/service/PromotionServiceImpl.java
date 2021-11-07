package by.hrachyshkin.service;

import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.dao.entity_dao.Dao;
import by.hrachyshkin.dao.entity_dao.DaoKeys;
import by.hrachyshkin.dao.transaction.Transaction;
import by.hrachyshkin.dao.transaction.TransactionException;
import by.hrachyshkin.entity.Promotion;
import by.hrachyshkin.entity.criteria.Filter;
import by.hrachyshkin.entity.criteria.Sort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PromotionServiceImpl extends ServiceImpl implements Service<Promotion> {

    private final Transaction transaction;

    @Override
    public void add(Promotion promotion) throws ServiceException {

    }

    @Override
    public List<Promotion> find(Filter filter, Sort sort) throws ServiceException {
        return null;
    }

    @Override
    public Promotion findOneById(Integer id) throws ServiceException {
        return null;
    }

    @Override
    public void update(Promotion promotion) throws ServiceException {

    }

    @Override
    public void delete(Integer id) throws ServiceException {

    }
}
