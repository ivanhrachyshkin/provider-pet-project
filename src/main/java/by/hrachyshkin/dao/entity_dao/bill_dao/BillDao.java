package by.hrachyshkin.dao.entity_dao.bill_dao;

import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.dao.entity_dao.Dao;
import by.hrachyshkin.entity.Bill;

import java.util.List;

public interface BillDao extends Dao<Bill> {

    boolean isExistBySubscriptionId(final Integer subscriptionId) throws DaoException;

    List<Bill> findAndFilterBySubscriptionId(Integer subscriptionId) throws DaoException;

    List<Bill> findAndSortByDate() throws DaoException;

    List<Bill> findAndFilterAndSort(Integer subscriptionId) throws DaoException;
}
