package by.hrachyshkin.provider.dao.entity_dao.tariff_dao;

import by.hrachyshkin.provider.dao.DaoException;
import by.hrachyshkin.provider.dao.entity_dao.Dao;
import by.hrachyshkin.provider.entity.Tariff;

import java.util.List;

public interface TariffDao extends Dao<Tariff> {

    boolean isExistById(Integer id) throws DaoException;

    boolean isExistByName(String name) throws DaoException;

    List<Tariff> findAndSortBySpeedAndPrice() throws DaoException;

    List<Tariff> findAndFilterByType(Tariff.Type type) throws DaoException;

    List<Tariff> findAndFilterAndSort(Tariff.Type type) throws DaoException;
}
