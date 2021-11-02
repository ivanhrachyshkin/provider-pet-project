package by.hrachyshkin.provider.service.impl;

import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Promotion;
import by.hrachyshkin.provider.service.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class PromotionServiceImplTest {

    @BeforeClass
    public void beforeClass() {
        DataBaseConnection.getINSTANCE().contextInitialized();
    }

    Promotion promotion1 = new Promotion(1, 2);
    Promotion promotion2 = new Promotion(1, 3);
    Promotion promotion3 = new Promotion(2, 1);
    Promotion promotion4 = new Promotion(2, 2);
    Promotion promotion5 = new Promotion(2, 3);
    Promotion promotion6 = new Promotion(3, 3);
    Promotion promotion7 = new Promotion(3, 1);
    Promotion promotion8 = new Promotion(3, 2);

    @Test
    public void ShouldReturnPromotion_On_Find_Pos() throws ServiceException, TransactionException {

        final PromotionService promotionService = ServiceFactory.getINSTANCE().getService(ServiceKeys.PROMOTION_SERVICE);
        final List<Promotion> promotions = new ArrayList<>();
        promotions.add(promotion1);
        promotions.add(promotion2);
        promotions.add(promotion3);
        promotions.add(promotion4);
        promotions.add(promotion5);
        promotions.add(promotion6);
        promotions.add(promotion7);
        promotions.add(promotion8);
        Assert.assertEquals(promotionService.find(), promotions);
    }

    @Test
    public void ShouldReturnPromotion_On_FindAndFilterByTariffId_Pos() throws ServiceException, TransactionException {

        final PromotionService promotionService = ServiceFactory.getINSTANCE().getService(ServiceKeys.PROMOTION_SERVICE);
        final List<Promotion> promotions = new ArrayList<>();
        promotions.add(promotion1);
        promotions.add(promotion2);
        Assert.assertEquals(promotionService.findAndFilterByTariffId(1), promotions);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void ShouldThrowException_On_FindOneById_Exception() throws ServiceException, TransactionException {

        final PromotionService promotionService = ServiceFactory.getINSTANCE().getService(ServiceKeys.PROMOTION_SERVICE);
        promotionService.findOneById(1);
    }

    @Test
    public void ShouldAddPromotion_On_Add_Pos() throws ServiceException, TransactionException {

        final PromotionService promotionService = ServiceFactory.getINSTANCE().getService(ServiceKeys.PROMOTION_SERVICE);
        final Promotion promotion = new Promotion(1, 1);
        promotionService.add(promotion);
        final List<Promotion> promotions = new ArrayList<>();
        promotions.add(promotion1);
        promotions.add(promotion2);
        promotions.add(promotion);
        Assert.assertEquals(promotionService.findAndFilterByTariffId(1), promotions);
        promotionService.deleteByTariffAndDiscount(1, 1);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldThrowException_On_Add_Exception() throws ServiceException, TransactionException {

        final PromotionService promotionService = ServiceFactory.getINSTANCE().getService(ServiceKeys.PROMOTION_SERVICE);
        final Promotion promotion = new Promotion(2, 2);
        promotionService.add(promotion);
    }

    @Test
    public void ShouldDeletePromotion_On_DeleteByTariffAndDiscount_Pos() throws ServiceException, TransactionException {

        final PromotionService promotionService = ServiceFactory.getINSTANCE().getService(ServiceKeys.PROMOTION_SERVICE);
        final Promotion promotion = new Promotion(1, 1);
        promotionService.add(promotion);
        final List<Promotion> promotions = new ArrayList<>();
        promotions.add(promotion1);
        promotions.add(promotion2);
        promotionService.deleteByTariffAndDiscount(1, 1);
        Assert.assertEquals(promotionService.findAndFilterByTariffId(1), promotions);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldThrowException_On_DeleteByTariffAndDiscount_Exception() throws ServiceException, TransactionException {

        final PromotionService promotionService = ServiceFactory.getINSTANCE().getService(ServiceKeys.PROMOTION_SERVICE);
        promotionService.deleteByTariffAndDiscount(8,8);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void ShouldThrowException_On_Update_Exception() throws ServiceException, TransactionException {

        final PromotionService promotionService = ServiceFactory.getINSTANCE().getService(ServiceKeys.PROMOTION_SERVICE);
        promotionService.update(new Promotion(1,1));
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void ShouldThrowException_On_Delete_Exception() throws ServiceException, TransactionException {

        final PromotionService promotionService = ServiceFactory.getINSTANCE().getService(ServiceKeys.PROMOTION_SERVICE);
        promotionService.delete(1);
    }
}