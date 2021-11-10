package by.hrachyshkin.provider.dao.entity_dao.bill_dao;

import by.hrachyshkin.provider.dao.DaoException;
import by.hrachyshkin.provider.dao.entity_dao.Dao;
import by.hrachyshkin.provider.entity.Bill;

import java.util.List;

public interface BillDao extends Dao<Bill> {

    boolean isExists(final Bill bill) throws DaoException;

    List<Bill> findAndFilterBySubscriptionId(Integer subscriptionId) throws DaoException;

    List<Bill> findAndSortByDate() throws DaoException;

    List<Bill> findAndFilterAndSort(Integer subscriptionId) throws DaoException;
}
