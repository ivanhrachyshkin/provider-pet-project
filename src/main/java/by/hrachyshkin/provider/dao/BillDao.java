package by.hrachyshkin.provider.dao;

import by.hrachyshkin.provider.model.Bill;

import java.time.LocalDate;
import java.util.List;

public interface BillDao extends Dao<Bill> {

    boolean isExists(Integer subscriptionId,
                     Float value,
                     LocalDate date)
            throws DaoException;

    boolean isExistsOpenBills(Integer subscriptionId) throws DaoException;

    List<Bill> findAndSortByDate() throws DaoException;

    List<Bill> findAndFilterBySubscriptionId(Integer subscriptionId)
            throws DaoException;

    List<Bill> findAndFilterAndSortOffset(Integer subscriptionId, int offset)
            throws DaoException;

    void updateBillStatus(Integer subscriptionId, Float value, LocalDate date)
            throws DaoException;
}
