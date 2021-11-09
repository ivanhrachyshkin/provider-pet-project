package by.hrachyshkin.dao.entity_dao.traffic_dao;

import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.dao.entity_dao.Dao;
import by.hrachyshkin.entity.Traffic;

import java.util.List;

public interface TrafficDao extends Dao<Traffic> {


    boolean isExists(final Traffic traffic) throws DaoException;

    List<Traffic> findAndSortByDate() throws DaoException;

    List<Traffic> findAndFilterBySubscriptionId(Integer subscriptionId) throws DaoException;

    List<Traffic> findAndFilterAndSort(Integer subscriptionId) throws DaoException;
    
}
