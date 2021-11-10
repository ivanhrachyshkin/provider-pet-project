package by.hrachyshkin.provider.dao.entity_dao.traffic_dao;

import by.hrachyshkin.provider.dao.DaoException;
import by.hrachyshkin.provider.dao.entity_dao.Dao;
import by.hrachyshkin.provider.entity.Traffic;

import java.util.List;

public interface TrafficDao extends Dao<Traffic> {


    boolean isExists(final Traffic traffic) throws DaoException;

    List<Traffic> findAndSortByDate() throws DaoException;

    List<Traffic> findAndFilterBySubscriptionId(Integer subscriptionId) throws DaoException;

    List<Traffic> findAndFilterAndSort(Integer subscriptionId) throws DaoException;
    
}
