package by.hrachyshkin.provider.service;

import by.hrachyshkin.provider.entity.Promotion;

import java.util.List;

public interface PromotionService extends Service<Promotion> {

    List<Promotion> findAndFilterByTariffId(final Integer tariffId) throws ServiceException;

    void deleteByTariffAndDiscount(final Integer tariffId, final Integer discountId) throws ServiceException;

}
