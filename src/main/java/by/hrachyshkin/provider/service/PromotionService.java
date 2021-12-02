package by.hrachyshkin.provider.service;

import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Promotion;

import java.util.List;

public interface PromotionService extends Service<Promotion> {

    List<Promotion> findAndFilterByTariffId(Integer tariffId)
            throws ServiceException, TransactionException;

    void deleteByTariffAndDiscount(Integer tariffId, Integer discountId)
            throws ServiceException, TransactionException;

}
