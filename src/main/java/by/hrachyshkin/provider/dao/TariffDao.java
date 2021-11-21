package by.hrachyshkin.provider.dao;

import by.hrachyshkin.provider.model.Tariff;

import java.util.List;

public interface TariffDao extends Dao<Tariff> {

    boolean isExistById(Integer id) throws DaoException;

    boolean isExistByName(String name) throws DaoException;

    boolean isExistByNotIdAndName(final Integer id, final String name) throws DaoException;

    List<Tariff> findAndSortBySpeedAndPrice(int offset) throws DaoException;

    List<Tariff> findAndFilterByType(Tariff.Type type, int offset) throws DaoException;

    List<Tariff> findAndFilterAndSort(Tariff.Type type) throws DaoException;
}
