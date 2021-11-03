package by.hrachyshkin.dao.entity_dao.subscription_dao;

import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.dao.entity_dao.EntityDao;
import by.hrachyshkin.entity.Account;
import by.hrachyshkin.entity.Subscription;
import by.hrachyshkin.entity.criteria.Filter;
import by.hrachyshkin.entity.criteria.Sort;

import java.util.List;

public interface SubscriptionDao extends EntityDao<Subscription> {


    boolean isExistById(Integer id) throws DaoException;

    boolean isExistByAccountIdAndTariffId(final Integer accountId, final Integer tariffId) throws DaoException;

    List<Subscription> findAndSort(Sort sort) throws DaoException;

    List<Subscription> findAndFilter(Filter filter) throws DaoException;

    List<Subscription> findAndFilterAndSort(Filter filter, Sort sort) throws DaoException;
}
