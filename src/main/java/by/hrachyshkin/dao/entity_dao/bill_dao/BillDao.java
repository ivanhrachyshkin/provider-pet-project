package by.hrachyshkin.dao.entity_dao.bill_dao;

import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.dao.entity_dao.Dao;
import by.hrachyshkin.entity.Bill;
import by.hrachyshkin.entity.criteria.Filter;
import by.hrachyshkin.entity.criteria.Sort;

import java.util.List;

public interface BillDao extends Dao<Bill> {

    boolean isExistBySubscriptionId(final Integer subscriptionId) throws DaoException;

    List<Bill> findAndFilter(Filter filter) throws DaoException;

    List<Bill> findAndSort(Sort sort) throws DaoException;//hardcode

    List<Bill> findAndFilterAndSort(Filter filter, Sort sort) throws DaoException;
}
