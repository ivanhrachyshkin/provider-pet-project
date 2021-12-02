package by.hrachyshkin.provider.service.impl;

import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Discount;
import by.hrachyshkin.provider.service.DiscountService;
import by.hrachyshkin.provider.service.ServiceException;
import by.hrachyshkin.provider.service.ServiceFactory;
import by.hrachyshkin.provider.service.ServiceKeys;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DiscountServiceImplTest {


    @BeforeClass
    public void beforeClass() {
        DataBaseConnection.getINSTANCE().contextInitialized();
    }

    private final Discount discount1 = new Discount(1, "Winter Is Coming", Discount.Type.PERCENTAGE, 30,
            LocalDate.of(2021, 12, 1), LocalDate.of(2022, 2, 27));
    private final Discount discount2 = new Discount(2, "Summer Is Coming", Discount.Type.PERCENTAGE, 20,
            LocalDate.of(2021, 6, 1), LocalDate.of(2022, 8, 30));
    private final Discount discount3 = new Discount(3, "Spring Is Coming", Discount.Type.PERCENTAGE, 40,
            LocalDate.of(2021, 3, 1), LocalDate.of(2022, 5, 30));

    @Test
    public void ShouldReturnDiscount_On_isExistByName_Pos() throws ServiceException, TransactionException {

        final DiscountService discountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.DISCOUNT);
        Assert.assertTrue(discountService.isExistByName("Winter Is Coming"));
    }

    @Test
    public void ShouldReturnDiscounts_On_Find_Pos() throws ServiceException, TransactionException {

        final DiscountService discountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.DISCOUNT);
        final List<Discount> discounts = new ArrayList<>();
        discounts.add(discount1);
        discounts.add(discount2);
        discounts.add(discount3);
        Assert.assertEquals(discountService.find(), discounts);
    }

    @Test
    public void ShouldReturnDiscounts_On_FindAndSortByValue_Pos() throws ServiceException, TransactionException {

        final DiscountService discountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.DISCOUNT);
        final List<Discount> discounts = new ArrayList<>();
        discounts.add(discount3);
        discounts.add(discount1);
        discounts.add(discount2);
        Assert.assertEquals(discountService.findAndSortByValue(0), discounts);
    }

    @Test
    public void ShouldReturnDiscounts_On_FindAndFilterByTypeSortByValue_Pos() throws ServiceException, TransactionException {

        final DiscountService discountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.DISCOUNT);
        final List<Discount> discounts = new ArrayList<>();
        discounts.add(discount3);
        discounts.add(discount1);
        discounts.add(discount2);
        Assert.assertEquals(discountService.findAndFilterByTypeAndSortByValue(Discount.Type.PERCENTAGE), discounts);
    }

    @Test
    public void ShouldReturnDiscounts_On_FindOneById_Pos() throws ServiceException, TransactionException {

        final DiscountService discountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.DISCOUNT);
        Assert.assertEquals(discountService.findOneById(1), discount1);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldReturnDiscounts_On_FindOneById_Exception() throws ServiceException, TransactionException {

        final DiscountService discountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.DISCOUNT);
        discountService.findOneById(10000);
    }

    @Test
    public void ShouldReturnDiscountsForPromotion_On_FindDiscountsForPromotion_Pos() throws ServiceException, TransactionException {

        final DiscountService discountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.DISCOUNT);
        final List<Discount> discounts = new ArrayList<>();
        discounts.add(discount3);
        discounts.add(discount1);
        discounts.add(discount2);
        Assert.assertEquals(discountService.findDiscountsForPromotion(3), discounts);
    }

    @Test
    public void ShouldReturnDiscount_On_FindOneById_Pos() throws ServiceException, TransactionException {

        final DiscountService discountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.DISCOUNT);
        Assert.assertEquals(discountService.findOneById(3), discount3);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldReturnDiscount_On_FindOneById_Exception() throws ServiceException, TransactionException {

        final DiscountService discountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.DISCOUNT);
        discountService.findOneById(10000);
    }

    @Test
    public void ShouldAddDiscount_On_Add_Pos() throws ServiceException, TransactionException {

        final DiscountService discountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.DISCOUNT);
        final Discount added = new Discount("Added Name", Discount.Type.COEFFICIENT, 10,
                LocalDate.of(2022, 1, 10), LocalDate.of(2023, 1, 10));
        discountService.add(added);

        discountService.update(new Discount(discountService.findAndFilterByTypeOffset(Discount.Type.COEFFICIENT, 0).get(0).getId(),
                "Added Name", Discount.Type.COEFFICIENT, 10,
                LocalDate.of(2022, 1, 10), LocalDate.of(2023, 1, 10))
        );

        Assert.assertTrue(discountService.isExistByName("Added Name"));
        discountService.delete(discountService.findAndFilterByTypeOffset(Discount.Type.COEFFICIENT, 0).get(0).getId());
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldAddDiscount_On_Add_EmptyName_Exception() throws ServiceException, TransactionException {

        final DiscountService discountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.DISCOUNT);
        final Discount added = new Discount("", Discount.Type.COEFFICIENT, 10,
                LocalDate.of(2022, 1, 10), LocalDate.of(2023, 1, 10));
        discountService.add(added);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldAddDiscount_On_Add_NullType_Exception() throws ServiceException, TransactionException {

        final DiscountService discountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.DISCOUNT);
        final Discount added = new Discount("Added Name", null, 10,
                LocalDate.of(2022, 1, 10), LocalDate.of(2023, 1, 10));
        discountService.add(added);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldAddDiscount_On_Add_NullValue_Exception() throws ServiceException, TransactionException {

        final DiscountService discountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.DISCOUNT);
        final Discount added = new Discount("Added Name", Discount.Type.COEFFICIENT, null,
                LocalDate.of(2022, 1, 10), LocalDate.of(2023, 1, 10));
        discountService.add(added);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldAddDiscount_On_Add_NullDateFrom_Exception() throws ServiceException, TransactionException {

        final DiscountService discountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.DISCOUNT);
        final Discount added = new Discount("Added Name", Discount.Type.COEFFICIENT, 10,
                null, LocalDate.of(2023, 1, 10));
        discountService.add(added);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldAddDiscount_On_Add_NullDateTo_Exception() throws ServiceException, TransactionException {

        final DiscountService discountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.DISCOUNT);
        final Discount added = new Discount("Added Name", Discount.Type.COEFFICIENT, 10,
                LocalDate.of(2022, 1, 10), null);
        discountService.add(added);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldAddDiscount_On_Add_Exists_Exception() throws ServiceException, TransactionException {

        final DiscountService discountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.DISCOUNT);
        final Discount added = new Discount("Winter Is Coming", Discount.Type.COEFFICIENT, 10,
                LocalDate.of(2022, 1, 10), LocalDate.of(2023, 1, 10));
        discountService.add(added);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldAddDiscount_On_Add_DateFromIsAfter_Exception() throws ServiceException, TransactionException {

        final DiscountService discountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.DISCOUNT);
        final Discount added = new Discount("Added Name", Discount.Type.COEFFICIENT, 10,
                LocalDate.of(2024, 1, 10), LocalDate.of(2023, 1, 10));
        discountService.add(added);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldAddDiscount_On_Add_NegativeValue_Exception() throws ServiceException, TransactionException {

        final DiscountService discountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.DISCOUNT);
        final Discount added = new Discount("Added Name", Discount.Type.COEFFICIENT, -10,
                LocalDate.of(2022, 1, 10), LocalDate.of(2023, 1, 10));
        discountService.add(added);
    }

    @Test
    public void ShouldUpdateDiscount_On_Add_Pos() throws ServiceException, TransactionException {

        final DiscountService discountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.DISCOUNT);

        final Discount added = new Discount("Added Name", Discount.Type.COEFFICIENT, 10,
                LocalDate.of(2022, 1, 10), LocalDate.of(2023, 1, 10));
        discountService.add(added);

        final Discount updated = new Discount(discountService.findAndFilterByTypeOffset(Discount.Type.COEFFICIENT, 0).get(0).getId(), "Updated Name", Discount.Type.COEFFICIENT, 10,
                LocalDate.of(2022, 1, 10), LocalDate.of(2023, 1, 10));
        discountService.update(updated);

        Assert.assertTrue(discountService.isExistByName("Updated Name"));
        discountService.delete(discountService.findAndFilterByTypeOffset(Discount.Type.COEFFICIENT, 0).get(0).getId());
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldUpdateDiscount_On_Add_EmptyName_Exception() throws ServiceException, TransactionException {

        final DiscountService discountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.DISCOUNT);
        final Discount updated = new Discount("", Discount.Type.COEFFICIENT, 10,
                LocalDate.of(2022, 1, 10), LocalDate.of(2023, 1, 10));
        discountService.update(updated);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldUpdateDiscount_On_Add_NullType_Exception() throws ServiceException, TransactionException {

        final DiscountService discountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.DISCOUNT);
        final Discount updated = new Discount("Added Name", null, 10,
                LocalDate.of(2022, 1, 10), LocalDate.of(2023, 1, 10));
        discountService.update(updated);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldUpdateDiscount_On_Add_NullValue_Exception() throws ServiceException, TransactionException {

        final DiscountService discountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.DISCOUNT);
        final Discount updated = new Discount("Added Name", Discount.Type.COEFFICIENT, null,
                LocalDate.of(2022, 1, 10), LocalDate.of(2023, 1, 10));
        discountService.update(updated);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldUpdateDiscount_On_Add_NullDateFrom_Exception() throws ServiceException, TransactionException {

        final DiscountService discountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.DISCOUNT);
        final Discount updated = new Discount("Added Name", Discount.Type.COEFFICIENT, 10,
                null, LocalDate.of(2023, 1, 10));
        discountService.update(updated);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldUpdateDiscount_On_Add_NullDateTo_Exception() throws ServiceException, TransactionException {

        final DiscountService discountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.DISCOUNT);
        final Discount updated = new Discount("Added Name", Discount.Type.COEFFICIENT, 10,
                LocalDate.of(2022, 1, 10), null);
        discountService.update(updated);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldUpdateDiscount_On_Add_Exists_Exception() throws ServiceException, TransactionException {

        final DiscountService discountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.DISCOUNT);
        final Discount updated = new Discount(100,"Winter Is Coming", Discount.Type.COEFFICIENT, 10,
                LocalDate.of(2022, 1, 10), LocalDate.of(2023, 1, 10));
        discountService.update(updated);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldUpdateDiscount_On_Add_DateFromIsAfter_Exception() throws ServiceException, TransactionException {

        final DiscountService discountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.DISCOUNT);
        final Discount updated = new Discount(1,"Added Name", Discount.Type.COEFFICIENT, 10,
                LocalDate.of(2024, 1, 10), LocalDate.of(2023, 1, 10));
        discountService.update(updated);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldUpdateDiscount_On_Add_NegativeValue_Exception() throws ServiceException, TransactionException {

        final DiscountService discountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.DISCOUNT);
        final Discount updated = new Discount(1,"Added Name", Discount.Type.COEFFICIENT, -10,
                LocalDate.of(2022, 1, 10), LocalDate.of(2023, 1, 10));
        discountService.update(updated);
    }

}