package by.hrachyshkin.provider.dao;

import by.hrachyshkin.provider.model.Traffic;

import java.util.List;

public interface TrafficDao extends Dao<Traffic> {

    boolean isExists(Traffic traffic) throws DaoException;

    List<Traffic> findAndSortByDate() throws DaoException;

    List<Traffic> findAndFilterBySubscriptionId(Integer subscriptionId)
            throws DaoException;

    List<Traffic> findAndFilterAndSortOffset(Integer subscriptionId, int offset)
            throws DaoException;

}
