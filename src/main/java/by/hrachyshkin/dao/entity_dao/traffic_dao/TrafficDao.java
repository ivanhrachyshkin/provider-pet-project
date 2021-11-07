package by.hrachyshkin.dao.entity_dao.traffic_dao;

import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.dao.entity_dao.Dao;
import by.hrachyshkin.entity.Traffic;
import by.hrachyshkin.entity.criteria.Filter;
import by.hrachyshkin.entity.criteria.Sort;

import java.util.List;

public interface TrafficDao extends Dao<Traffic> {


    boolean isExistBySubscriptionId(final Integer subscriptionId) throws DaoException;

    List<Traffic> findAndSortByDate() throws DaoException;

    List<Traffic> findAndFilterBySubscriptionId(Integer subscriptionId) throws DaoException;

    List<Traffic> findAndFilterAndSort(Integer subscriptionId) throws DaoException;
    
}
