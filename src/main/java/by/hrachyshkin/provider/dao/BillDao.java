package by.hrachyshkin.provider.dao;

import by.hrachyshkin.provider.model.Bill;

import java.time.LocalDate;
import java.util.List;

public interface BillDao extends Dao<Bill> {

    boolean isExists(final Integer subscriptionId, final Float value, final LocalDate date) throws DaoException;

    List<Bill> findAndSortByDate() throws DaoException;

    List<Bill> findAndFilterBySubscriptionId(Integer subscriptionId) throws DaoException;

    List<Bill> findAndFilterAndSort(Integer subscriptionId) throws DaoException;

    void updateBillStatus(final Integer subscriptionId, final Float value, final LocalDate date) throws DaoException;
}
