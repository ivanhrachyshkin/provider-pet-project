package by.hrachyshkin.provider.service.impl;

import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Traffic;
import by.hrachyshkin.provider.service.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TrafficServiceImplTest {

    @BeforeClass
    public void beforeClass() {
        DataBaseConnection.getINSTANCE().contextInitialized();
    }

    private final Traffic traffic1 = new Traffic(1, 111, LocalDate.of(2021, 4, 1));
    private final Traffic traffic2 = new Traffic(1, 112, LocalDate.of(2021, 4, 4));
    private final Traffic traffic3 = new Traffic(1, 113, LocalDate.of(2021, 4, 8));
    private final Traffic traffic4 = new Traffic(2, 221, LocalDate.of(2021, 1, 1));
    private final Traffic traffic5 = new Traffic(2, 222, LocalDate.of(2021, 2, 1));
    private final Traffic traffic6 = new Traffic(2, 223, LocalDate.of(2021, 4, 16));
    private final Traffic traffic7 = new Traffic(3, 331, LocalDate.of(2021, 12, 1));
    private final Traffic traffic8 = new Traffic(3, 332, LocalDate.of(2021, 2, 11));
    private final Traffic traffic9 = new Traffic(3, 333, LocalDate.of(2021, 4, 20));

    @Test
    public void ShouldReturnTraffics_On_Find_Pos() throws ServiceException, TransactionException {

        final TrafficService trafficService = ServiceFactory.getINSTANCE().getService(ServiceKeys.TRAFFIC);
        final List<Traffic> traffics = new ArrayList<>();
        traffics.add(traffic1);
        traffics.add(traffic2);
        traffics.add(traffic3);
        traffics.add(traffic4);
        traffics.add(traffic5);
        traffics.add(traffic6);
        traffics.add(traffic7);
        traffics.add(traffic8);
        traffics.add(traffic9);
        Assert.assertEquals(trafficService.find(), traffics);
    }

    @Test
    public void ShouldReturnTraffics_On_FindAndSortByDate_Pos() throws ServiceException, TransactionException {

        final TrafficService trafficService = ServiceFactory.getINSTANCE().getService(ServiceKeys.TRAFFIC);
        final List<Traffic> traffics = new ArrayList<>();
        traffics.add(traffic4);
        traffics.add(traffic5);
        traffics.add(traffic8);
        traffics.add(traffic1);
        traffics.add(traffic2);
        traffics.add(traffic3);
        traffics.add(traffic6);
        traffics.add(traffic9);
        traffics.add(traffic7);
        Assert.assertEquals(trafficService.findAndSortByDate(), traffics);
    }

    @Test
    public void ShouldReturnTraffics_On_FindAndFilter_Pos() throws ServiceException, TransactionException {

        final TrafficService trafficService = ServiceFactory.getINSTANCE().getService(ServiceKeys.TRAFFIC);
        final List<Traffic> traffics = new ArrayList<>();
        traffics.add(traffic1);
        traffics.add(traffic2);
        traffics.add(traffic3);
        Assert.assertEquals(trafficService.findAndFilterBySubscriptionId(1), traffics);
    }

    @Test
    public void ShouldReturnTraffics_On_FindAndFilterAndSortByDate_Pos() throws ServiceException, TransactionException {

        final TrafficService trafficService = ServiceFactory.getINSTANCE().getService(ServiceKeys.TRAFFIC);
        final List<Traffic> traffics = new ArrayList<>();
        traffics.add(traffic1);
        traffics.add(traffic2);
        traffics.add(traffic3);
        Assert.assertEquals(trafficService.findAndFilterAndSortOffset(1, 0), traffics);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void ShouldReturnTraffics_On_FindOneById_Exception() throws ServiceException, TransactionException {

        final TrafficService trafficService = ServiceFactory.getINSTANCE().getService(ServiceKeys.TRAFFIC);
        trafficService.findOneById(1);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void ShouldReturnTraffics_On_Update_Exception() throws ServiceException, TransactionException {

        final TrafficService trafficService = ServiceFactory.getINSTANCE().getService(ServiceKeys.TRAFFIC);
        trafficService.update(null);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void ShouldReturnTraffics_On_Delete_Exception() throws ServiceException, TransactionException {

        final TrafficService trafficService = ServiceFactory.getINSTANCE().getService(ServiceKeys.TRAFFIC);
        trafficService.delete(1);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldReturnTraffics_On_Add_NullValue_Exception() throws ServiceException, TransactionException {

        final TrafficService trafficService = ServiceFactory.getINSTANCE().getService(ServiceKeys.TRAFFIC);
        trafficService.add(new Traffic(1, null, LocalDate.now()));
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldReturnTraffics_On_Add_NegativeValue_Exception() throws ServiceException, TransactionException {

        final TrafficService trafficService = ServiceFactory.getINSTANCE().getService(ServiceKeys.TRAFFIC);
        trafficService.add(new Traffic(1, -10, LocalDate.now()));
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldReturnTraffics_On_Add_NullDate_Exception() throws ServiceException, TransactionException {

        final TrafficService trafficService = ServiceFactory.getINSTANCE().getService(ServiceKeys.TRAFFIC);
        trafficService.add(new Traffic(1, -10, null));
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldReturnTraffics_On_Add_Exist_Exception() throws ServiceException, TransactionException {

        final TrafficService trafficService = ServiceFactory.getINSTANCE().getService(ServiceKeys.TRAFFIC);
        trafficService.add(new Traffic(-1, 10, LocalDate.now()));
    }
}