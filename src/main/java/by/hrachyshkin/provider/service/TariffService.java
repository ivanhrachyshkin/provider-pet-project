package by.hrachyshkin.provider.service;

import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Tariff;

import java.util.List;

public interface TariffService extends Service<Tariff> {

    boolean isExist(Integer id) throws ServiceException;

    List<Tariff> findAndSortBySpeedAndPrice(Integer offset) throws ServiceException;

    List<Tariff> findAndFilterByType(Tariff.Type type, int offset) throws ServiceException;

    List<Tariff> findTariffsForSubscription(Integer accountId) throws ServiceException, TransactionException;

    List<Tariff> findAndFilterAndSortBySpeedAndPrice(Tariff.Type type) throws ServiceException;
}
