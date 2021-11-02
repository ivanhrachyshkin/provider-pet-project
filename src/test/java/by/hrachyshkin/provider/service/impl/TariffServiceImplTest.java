package by.hrachyshkin.provider.service.impl;

import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Subscription;
import by.hrachyshkin.provider.model.Tariff;
import by.hrachyshkin.provider.service.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class TariffServiceImplTest {

    @BeforeClass
    public void beforeClass() {
        DataBaseConnection.getINSTANCE().contextInitialized();
    }

    private final Tariff tariff1 = new Tariff(1, "Home 3", Tariff.Type.TRAFFICKED, 3000, 10.0f);
    private final Tariff tariff2 = new Tariff(2, "Work 6", Tariff.Type.TRAFFICKED, 6000, 15.0f);
    private final Tariff tariff3 = new Tariff(3, "Relax 5", Tariff.Type.UNLIMITED, 5000, 15.0f);


    @Test
    public void ShouldReturnBoolean_On_isExist_Pos() throws ServiceException, TransactionException {

        final TariffService tariffService = ServiceFactory.getINSTANCE().getService(ServiceKeys.TARIFF_SERVICE);
        Assert.assertTrue(tariffService.isExist(1));
    }

    @Test
    public void ShouldReturnTariffs_On_Find_Pos() throws ServiceException, TransactionException {

        final TariffService tariffService = ServiceFactory.getINSTANCE().getService(ServiceKeys.TARIFF_SERVICE);
        final List<Tariff> tariffs = new ArrayList<>();
        tariffs.add(tariff1);
        tariffs.add(tariff2);
        tariffs.add(tariff3);

        Assert.assertEquals(tariffService.find(), tariffs);
    }

    @Test
    public void ShouldReturnTariffs_On_FindAndSortBySpeedAndPrice_Pos() throws ServiceException, TransactionException {

        final TariffService tariffService = ServiceFactory.getINSTANCE().getService(ServiceKeys.TARIFF_SERVICE);
        final List<Tariff> tariffs = new ArrayList<>();
        tariffs.add(tariff2);
        tariffs.add(tariff3);
        tariffs.add(tariff1);
        Assert.assertEquals(tariffService.findAndSortBySpeedAndPrice(0), tariffs);
    }

    @Test
    public void ShouldReturnTariffs_On_FindAndFilterByType_Pos() throws ServiceException, TransactionException {

        final TariffService tariffService = ServiceFactory.getINSTANCE().getService(ServiceKeys.TARIFF_SERVICE);
        final List<Tariff> tariffs = new ArrayList<>();
        tariffs.add(tariff1);
        tariffs.add(tariff2);
        Assert.assertEquals(tariffService.findAndFilterByType(Tariff.Type.TRAFFICKED, 0), tariffs);
    }

    @Test
    public void ShouldReturnTariffs_On_FindAndFilterByTypeAndSortBySpeedAndPrice_Pos() throws ServiceException, TransactionException {

        final TariffService tariffService = ServiceFactory.getINSTANCE().getService(ServiceKeys.TARIFF_SERVICE);
        final List<Tariff> tariffs = new ArrayList<>();
        tariffs.add(tariff2);
        tariffs.add(tariff1);
        Assert.assertEquals(tariffService.findAndFilterByTypeAndSortBySpeedAndPrice(Tariff.Type.TRAFFICKED), tariffs);
    }

    @Test
    public void ShouldReturnTariff_On_FindOneById_Pos() throws ServiceException, TransactionException {

        final TariffService tariffService = ServiceFactory.getINSTANCE().getService(ServiceKeys.TARIFF_SERVICE);
        Assert.assertEquals(tariffService.findOneById(1), tariff1);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldThrowException_On_FindOneById_Exception() throws ServiceException, TransactionException {

        final TariffService tariffService = ServiceFactory.getINSTANCE().getService(ServiceKeys.TARIFF_SERVICE);
        tariffService.findOneById(-1);
    }

    @Test
    public void ShouldReturnTariffs_On_FindTariffsForSubscription_Exception() throws ServiceException, TransactionException {

        final TariffService tariffService = ServiceFactory.getINSTANCE().getService(ServiceKeys.TARIFF_SERVICE);
        final List<Tariff> tariffs = new ArrayList<>();
        tariffs.add(tariff1);
        tariffs.add(tariff2);
        tariffs.add(tariff3);
        Assert.assertEquals(tariffService.findTariffsForSubscription(1), tariffs);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldThrowException_On_Add_EmptyName_Exception() throws ServiceException, TransactionException {

        final TariffService tariffService = ServiceFactory.getINSTANCE().getService(ServiceKeys.TARIFF_SERVICE);
        Tariff addedTariff = new Tariff("", Tariff.Type.TRAFFICKED, 10000, 20.0f);
        tariffService.add(addedTariff);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldThrowException_On_Add_ExistName_Exception() throws ServiceException, TransactionException {

        final TariffService tariffService = ServiceFactory.getINSTANCE().getService(ServiceKeys.TARIFF_SERVICE);
        Tariff addedTariff = new Tariff("Work 6", Tariff.Type.TRAFFICKED, 10000, 20.0f);
        tariffService.add(addedTariff);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldThrowException_On_Add_NullType_Exception() throws ServiceException, TransactionException {

        final TariffService tariffService = ServiceFactory.getINSTANCE().getService(ServiceKeys.TARIFF_SERVICE);
        Tariff addedTariff = new Tariff("Work", null, 10000, 20.0f);
        tariffService.add(addedTariff);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldThrowException_On_Add_NullSpeed_Exception() throws ServiceException, TransactionException {

        final TariffService tariffService = ServiceFactory.getINSTANCE().getService(ServiceKeys.TARIFF_SERVICE);
        Tariff addedTariff = new Tariff("Work", Tariff.Type.TRAFFICKED, null, 20.0f);
        tariffService.add(addedTariff);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldThrowException_On_Add_NegativeSpeed_Exception() throws ServiceException, TransactionException {

        final TariffService tariffService = ServiceFactory.getINSTANCE().getService(ServiceKeys.TARIFF_SERVICE);
        Tariff addedTariff = new Tariff("Work", Tariff.Type.TRAFFICKED, -10, 20.0f);
        tariffService.add(addedTariff);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldThrowException_On_Add_NullPrice_Exception() throws ServiceException, TransactionException {

        final TariffService tariffService = ServiceFactory.getINSTANCE().getService(ServiceKeys.TARIFF_SERVICE);
        Tariff addedTariff = new Tariff("Work", Tariff.Type.TRAFFICKED, 10, null);
        tariffService.add(addedTariff);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldThrowException_On_Add_NegativePrice_Exception() throws ServiceException, TransactionException {

        final TariffService tariffService = ServiceFactory.getINSTANCE().getService(ServiceKeys.TARIFF_SERVICE);
        Tariff addedTariff = new Tariff("Work", Tariff.Type.TRAFFICKED, 10, -20.0f);
        tariffService.add(addedTariff);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldThrowException_On_Update_EmptyName_Exception() throws ServiceException, TransactionException {

        final TariffService tariffService = ServiceFactory.getINSTANCE().getService(ServiceKeys.TARIFF_SERVICE);
        Tariff updatedTariff = new Tariff("", Tariff.Type.TRAFFICKED, 10000, 20.0f);
        tariffService.update(updatedTariff);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldThrowException_On_Update_ExistName_Exception() throws ServiceException, TransactionException {

        final TariffService tariffService = ServiceFactory.getINSTANCE().getService(ServiceKeys.TARIFF_SERVICE);
        Tariff updatedTariff = new Tariff(3,"Work 6", Tariff.Type.TRAFFICKED, 10000, 20.0f);
        tariffService.update(updatedTariff);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldThrowException_On_Update_NullType_Exception() throws ServiceException, TransactionException {

        final TariffService tariffService = ServiceFactory.getINSTANCE().getService(ServiceKeys.TARIFF_SERVICE);
        Tariff updatedTariff = new Tariff("Work", null, 10000, 20.0f);
        tariffService.update(updatedTariff);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldThrowException_On_Update_NullSpeed_Exception() throws ServiceException, TransactionException {

        final TariffService tariffService = ServiceFactory.getINSTANCE().getService(ServiceKeys.TARIFF_SERVICE);
        Tariff updatedTariff = new Tariff("Work", Tariff.Type.TRAFFICKED, null, 20.0f);
        tariffService.update(updatedTariff);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldThrowException_On_Update_NegativeSpeed_Exception() throws ServiceException, TransactionException {

        final TariffService tariffService = ServiceFactory.getINSTANCE().getService(ServiceKeys.TARIFF_SERVICE);
        Tariff updatedTariff = new Tariff(1,"Work", Tariff.Type.TRAFFICKED, -10, 20.0f);
        tariffService.update(updatedTariff);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldThrowException_On_Update_NullPrice_Exception() throws ServiceException, TransactionException {

        final TariffService tariffService = ServiceFactory.getINSTANCE().getService(ServiceKeys.TARIFF_SERVICE);
        Tariff updatedTariff = new Tariff("Work", Tariff.Type.TRAFFICKED, 10, null);
        tariffService.update(updatedTariff);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldThrowException_On_Update_NegativePrice_Exception() throws ServiceException, TransactionException {

        final TariffService tariffService = ServiceFactory.getINSTANCE().getService(ServiceKeys.TARIFF_SERVICE);
        Tariff updatedTariff = new Tariff(1,"Work", Tariff.Type.TRAFFICKED, 10, -20.0f);
        tariffService.update(updatedTariff);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldThrowException_On_Update_ExistById_Exception() throws ServiceException, TransactionException {

        final TariffService tariffService = ServiceFactory.getINSTANCE().getService(ServiceKeys.TARIFF_SERVICE);
        Tariff updatedTariff = new Tariff(-11,"Work", Tariff.Type.TRAFFICKED, 10, -20.0f);
        tariffService.update(updatedTariff);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldThrowException_On_Delete_Exist_Exception() throws ServiceException, TransactionException {

        final TariffService tariffService = ServiceFactory.getINSTANCE().getService(ServiceKeys.TARIFF_SERVICE);
        tariffService.delete(-1);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldThrowException_On_Delete_SubscriptionExists_Exception() throws ServiceException, TransactionException {

        final TariffService tariffService = ServiceFactory.getINSTANCE().getService(ServiceKeys.TARIFF_SERVICE);
        tariffService.delete(1);
    }
}