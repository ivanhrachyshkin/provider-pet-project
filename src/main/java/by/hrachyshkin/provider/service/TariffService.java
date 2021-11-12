package by.hrachyshkin.provider.service;

import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Tariff;

import java.util.List;

public interface TariffService extends Service<Tariff>{

    boolean isExist(final Integer id) throws ServiceException;

    List<Tariff> findAndSortBySpeedAndPrice() throws ServiceException;

    List<Tariff> findAndFilterByType(final Tariff.Type type) throws ServiceException;

    List<Tariff> findTariffsForAccountId(final Integer accountId) throws ServiceException, TransactionException;

    List<Tariff> findAndFilterAndSort(final Tariff.Type type) throws ServiceException;
}
