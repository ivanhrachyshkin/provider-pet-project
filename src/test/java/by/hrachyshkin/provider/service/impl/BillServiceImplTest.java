package by.hrachyshkin.provider.service.impl;

import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Bill;
import by.hrachyshkin.provider.service.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BillServiceImplTest {

    @BeforeClass
    public void beforeClass() {
        DataBaseConnection.getINSTANCE().contextInitialized();
    }

    private final Bill bill1 = new Bill(1, 4.0f, LocalDate.of(2021, 11, 1), false);
    private final Bill bill2 = new Bill(1, 5.0f, LocalDate.of(2021, 4, 2), false);
    private final Bill bill3 = new Bill(1, 7.0f, LocalDate.of(2021, 12, 3), false);
    private final Bill bill4 = new Bill(2, 2.0f, LocalDate.of(2021, 4, 1), false);
    private final Bill bill5 = new Bill(2, 4.0f, LocalDate.of(2021, 2, 2), false);
    private final Bill bill6 = new Bill(2, 3.0f, LocalDate.of(2021, 12, 3), false);
    private final Bill bill7 = new Bill(3, 6.0f, LocalDate.of(2021, 4, 4), false);
    private final Bill bill8 = new Bill(3, 8.0f, LocalDate.of(2021, 3, 5), false);
    private final Bill bill9 = new Bill(3, 8.0f, LocalDate.of(2021, 3, 5), false);


    @Test
    public void ShouldReturnBills_On_Find_Pos() throws ServiceException, TransactionException {

        final BillService billService = ServiceFactory.getINSTANCE().getService(ServiceKeys.BILL);
        final List<Bill> bills = new ArrayList<>();
        bills.add(bill1);
        bills.add(bill2);
        bills.add(bill3);
        bills.add(bill4);
        bills.add(bill5);
        bills.add(bill6);
        bills.add(bill7);
        bills.add(bill8);
        bills.add(bill9);
        Assert.assertEquals(billService.find(), bills);
    }

    @Test
    public void ShouldReturnBills_On_FindAndSortByDate() throws ServiceException, TransactionException {

        final BillService billService = ServiceFactory.getINSTANCE().getService(ServiceKeys.BILL);
        final List<Bill> bills = new ArrayList<>();
        bills.add(bill5);
        bills.add(bill8);
        bills.add(bill9);
        bills.add(bill4);
        bills.add(bill2);
        bills.add(bill7);
        bills.add(bill1);
        bills.add(bill6);
        bills.add(bill3);
        Assert.assertEquals(billService.findAndSortByDate(), bills);
    }

    @Test
    public void ShouldReturnBills_On_FindAndFilterBySubscriptionId() throws ServiceException, TransactionException {

        final BillService billService = ServiceFactory.getINSTANCE().getService(ServiceKeys.BILL);
        final List<Bill> bills = new ArrayList<>();
        bills.add(bill1);
        bills.add(bill2);
        bills.add(bill3);
        Assert.assertEquals(billService.findAndFilterBySubscriptionId(1), bills);
    }

    @Test
    public void ShouldReturnBills_On_FindAndSortOffset() throws ServiceException, TransactionException {

        final BillService billService = ServiceFactory.getINSTANCE().getService(ServiceKeys.BILL);
        final List<Bill> bills = new ArrayList<>();
        bills.add(bill2);
        bills.add(bill1);
        bills.add(bill3);
        Assert.assertEquals(billService.findAndFilterAndSortOffset(1,0), bills);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void ShouldThrowException_On_FindOneById_Exception() throws ServiceException, TransactionException {

        final BillService billService = ServiceFactory.getINSTANCE().getService(ServiceKeys.BILL);
        billService.findOneById(1);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void ShouldThrowException_On_Update_Exception() throws ServiceException, TransactionException {

        final BillService billService = ServiceFactory.getINSTANCE().getService(ServiceKeys.BILL);
        billService.update(new Bill(1, 10.0f, LocalDate.now(), false));
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void ShouldThrowException_On_Delete_Exception() throws ServiceException, TransactionException {

        final BillService billService = ServiceFactory.getINSTANCE().getService(ServiceKeys.BILL);
        billService.delete(1);
    }


}