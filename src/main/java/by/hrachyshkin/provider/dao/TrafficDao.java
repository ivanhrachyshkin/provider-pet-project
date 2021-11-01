package by.hrachyshkin.provider.dao;

import by.hrachyshkin.provider.model.Traffic;

import java.util.List;

public interface TrafficDao extends Dao<Traffic> {


    boolean isExists(final Traffic traffic) throws DaoException;

    List<Traffic> findAndSortByDate() throws DaoException;

    List<Traffic> findAndFilterBySubscriptionId(Integer subscriptionId) throws DaoException;

    List<Traffic> findAndFilterAndSortOffset(Integer subscriptionId, final int offset) throws DaoException;
    
}
