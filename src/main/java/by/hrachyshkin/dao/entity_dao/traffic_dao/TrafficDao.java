package by.hrachyshkin.dao.entity_dao.traffic_dao;

import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.dao.entity_dao.EntityDao;
import by.hrachyshkin.entity.Traffic;
import by.hrachyshkin.entity.criteria.Filter;
import by.hrachyshkin.entity.criteria.Sort;

import java.util.List;

public interface TrafficDao extends EntityDao<Traffic> {


    boolean isExistBySubscriptionId(final Integer subscriptionId) throws DaoException;

    List<Traffic> findAndSort(Sort sort) throws DaoException;

    List<Traffic> findAndFilter(Filter filter) throws DaoException;

    List<Traffic> findAndFilterAndSort(Filter filter, Sort sort) throws DaoException;
    
}
