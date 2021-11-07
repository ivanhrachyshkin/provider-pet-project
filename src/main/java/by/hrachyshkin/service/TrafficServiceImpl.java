package by.hrachyshkin.service;

import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.dao.entity_dao.Dao;
import by.hrachyshkin.dao.entity_dao.DaoKeys;
import by.hrachyshkin.dao.transaction.Transaction;
import by.hrachyshkin.dao.transaction.TransactionException;
import by.hrachyshkin.entity.Traffic;
import by.hrachyshkin.entity.criteria.Filter;
import by.hrachyshkin.entity.criteria.Sort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TrafficServiceImpl extends ServiceImpl implements Service<Traffic> {

    private final Transaction transaction;


    @Override
    public void add(Traffic traffic) throws ServiceException {

    }

    @Override
    public List<Traffic> find(Filter filter, Sort sort) throws ServiceException {
        return null;
    }

    @Override
    public Traffic findOneById(Integer id) throws ServiceException {
        return null;
    }

    @Override
    public void update(Traffic traffic) throws ServiceException {

    }

    @Override
    public void delete(Integer id) throws ServiceException {

    }
}
