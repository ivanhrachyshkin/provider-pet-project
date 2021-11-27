package by.hrachyshkin.provider.service.impl;

import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Account;
import by.hrachyshkin.provider.service.AccountService;
import by.hrachyshkin.provider.service.ServiceException;
import by.hrachyshkin.provider.service.ServiceFactory;
import by.hrachyshkin.provider.service.ServiceKeys;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AccountServiceImplTest {

    @BeforeClass
    public void beforeClass() {
        DataBaseConnection.getINSTANCE().contextInitialized();
    }

    Account jackWhite = new Account(1,
            "a@outlook.com",
            Account.Role.ADMINISTRATOR,
            "Jack White",
            "+1-993-631-7614",
            "1619 New Hampshire Avenue., N.W. Washington, DC 20009",
            0.0f);

    Account edgarPoe = new Account(2,
            "b@outlook.com",
            Account.Role.BLOCKED,
            "Edgar Allan Poe",
            "+1-727-456-7504",
            "321 Mia Field Apt. 340 New Lilianfort, NE 72310",
            500.0f);

    Account charlesPalahniuk = (new Account(3,
            "c@outlook.com",
            Account.Role.USER,
            "Charles Palahniuk",
            "+1-914-733-7350",
            "60441 Piper Inlet Wilfredton, GA 46024",
            9500.0f));

    Account addedAccount = new Account(
                "added@outlook.com",
                        "9372273Aaa",
                Account.Role.USER,
                "Added name",
                        "+1-914-733-7350",
                        "60441 Piper Inlet Wilfredton, GA 46024",
                        100.0f);

    @Test
    public void ShouldReturnAccount_On_IsExistByEmailAndPassword_Pos() throws ServiceException, TransactionException {

        final AccountService accountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.ACCOUNT_SERVICE);
        Assert.assertTrue(accountService.isExistByEmailAndPassword("a@outlook.com", "user"));
    }

    @Test
    public void ShouldReturnAccount_On_IsExistByEmailAndPassword_Neg() throws ServiceException, TransactionException {

        final AccountService accountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.ACCOUNT_SERVICE);
        Assert.assertFalse(accountService.isExistByEmailAndPassword("a@ouook.com", "user"));
    }

    @Test
    public void ShouldReturnAccount_On_FindOneById_Pos() throws ServiceException, TransactionException {

        final AccountService accountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.ACCOUNT_SERVICE);
        Assert.assertEquals(accountService.findOneById(1), jackWhite);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldReturnAccount_On_FindOneById_Exception() throws ServiceException, TransactionException {

        final AccountService accountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.ACCOUNT_SERVICE);
        accountService.findOneById(1000);
    }

    @Test
    public void ShouldReturnAccounts_On_Find_Pos() throws ServiceException, TransactionException {

        final AccountService accountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.ACCOUNT_SERVICE);
        final List<Account> accounts = new ArrayList<>();
        accounts.add(jackWhite);
        accounts.add(edgarPoe);
        accounts.add(charlesPalahniuk);
        Assert.assertEquals(accountService.find(), accounts);
    }

    @Test
    public void ShouldReturnAccounts_On_Find_Neg() throws ServiceException, TransactionException {

        final AccountService accountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.ACCOUNT_SERVICE);
        final List<Account> accounts = new ArrayList<>();
        accounts.add(jackWhite);
        accounts.add(charlesPalahniuk);
        accounts.add(edgarPoe);
        Assert.assertFalse(accountService.find().equals(accounts));
    }

    @Test
    public void ShouldReturnAccount_On_FindAndSortByName_Pos() throws ServiceException, TransactionException {

        final AccountService accountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.ACCOUNT_SERVICE);
        final List<Account> accounts = new ArrayList<>();
        accounts.add(charlesPalahniuk);
        accounts.add(edgarPoe);
        accounts.add(jackWhite);
        Assert.assertEquals(accountService.findAndSortByName(0), accounts);
    }

    @Test
    public void ShouldReturnAccount_On_FindAndSortByName_Neg() throws ServiceException, TransactionException {

        final AccountService accountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.ACCOUNT_SERVICE);
        final List<Account> accounts = new ArrayList<>();
        accounts.add(charlesPalahniuk);
        accounts.add(jackWhite);
        accounts.add(edgarPoe);
        Assert.assertFalse(accountService.findAndSortByName(0).equals(accounts));
    }

    @Test
    public void ShouldReturnAccount_On_FindOneByEmail_Pos() throws ServiceException, TransactionException {

        final AccountService accountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.ACCOUNT_SERVICE);
        Assert.assertEquals(accountService.findOneByEmail("a@outlook.com"), jackWhite);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldReturnAccount_On_FindOneByEmail_Exception() throws ServiceException, TransactionException {

        final AccountService accountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.ACCOUNT_SERVICE);
        accountService.findOneByEmail("a@outlasdook.com");
    }

    @Test
    public void ShouldAddAccount_On_Add_Pos() throws ServiceException, TransactionException {

        final AccountService accountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.ACCOUNT_SERVICE);
        accountService.add(addedAccount);
        Assert.assertTrue(accountService.isExistByEmailAndPassword("added@outlook.com", "9372273Aaa"));

        accountService.delete(accountService.findOneByEmail("added@outlook.com").getId());
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldAddAccount_On_Add_EmptyEmail_Exception() throws ServiceException, TransactionException {

        final AccountService accountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.ACCOUNT_SERVICE);
        Account testAccount = (new Account(
                "",
                "9372273Aaa",
                Account.Role.USER,
                "Added name",
                "+1-914-733-7350",
                "60441 Piper Inlet Wilfredton, GA 46024",
                100.0f));
        accountService.add(testAccount);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldAddAccount_On_Add_EmptyPassword_Exception() throws ServiceException, TransactionException {

        AccountService accountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.ACCOUNT_SERVICE);
        Account testAccount = (new Account(
                "added@outlook.com",
                "",
                Account.Role.USER,
                "Added name",
                "+1-914-733-7350",
                "60441 Piper Inlet Wilfredton, GA 46024",
                100.0f));
        accountService.add(testAccount);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldAddAccount_On_Add_InvalidPassword_Exception() throws ServiceException, TransactionException {

        AccountService accountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.ACCOUNT_SERVICE);
        Account testAccount = (new Account(
                "added@outlook.com",
                "user",
                Account.Role.USER,
                "Added name",
                "+1-914-733-7350",
                "60441 Piper Inlet Wilfredton, GA 46024",
                100.0f));
        accountService.add(testAccount);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldAddAccount_On_Add_NullRole_Exception() throws ServiceException, TransactionException {

        AccountService accountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.ACCOUNT_SERVICE);
        Account testAccount = (new Account(
                "added@outlook.com",
                "9372273Aaa",
                null,
                "Added name",
                "+1-914-733-7350",
                "60441 Piper Inlet Wilfredton, GA 46024",
                100.0f));
        accountService.add(testAccount);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldAddAccount_On_Add_NullName_Exception() throws ServiceException, TransactionException {

        AccountService accountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.ACCOUNT_SERVICE);
        Account testAccount = (new Account(
                "added@outlook.com",
                "9372273Aaa",
                Account.Role.USER,
                null,
                "+1-914-733-7350",
                "60441 Piper Inlet Wilfredton, GA 46024",
                100.0f));
        accountService.add(testAccount);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldAddAccount_On_Add_EmptyName_Exception() throws ServiceException, TransactionException {

        AccountService accountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.ACCOUNT_SERVICE);
        Account testAccount = (new Account(
                "added@outlook.com",
                "9372273Aaa",
                Account.Role.USER,
                "",
                "+1-914-733-7350",
                "60441 Piper Inlet Wilfredton, GA 46024",
                100.0f));
        accountService.add(testAccount);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldAddAccount_On_Add_EmptyPhone_Exception() throws ServiceException, TransactionException {

        AccountService accountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.ACCOUNT_SERVICE);
        Account testAccount = (new Account(
                "added@outlook.com",
                "9372273Aaa",
                Account.Role.USER,
                "Added name",
                "",
                "60441 Piper Inlet Wilfredton, GA 46024",
                100.0f));
        accountService.add(testAccount);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldAddAccount_On_Add_EmptyAddress_Exception() throws ServiceException, TransactionException {

        AccountService accountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.ACCOUNT_SERVICE);
        Account testAccount = (new Account(
                "added@outlook.com",
                "9372273Aaa",
                Account.Role.USER,
                "Added name",
                "+1-914-733-7350",
                "",
                100.0f));
        accountService.add(testAccount);
    }

    @Test
    public void ShouldUpdateAccount_On_Update() throws ServiceException, TransactionException {

        final AccountService accountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.ACCOUNT_SERVICE);
        accountService.add(addedAccount);
        final Account updated = new Account(
                accountService.findOneByEmail("added@outlook.com").getId(),
                "updated@outlook.com",
                "9372273Aaa",
                Account.Role.USER,
                "Updated name",
                "+1-914-733-7350",
                "60441 Piper Inlet Wilfredton, GA 46024",
                100.0f);
        accountService.update(updated);
        Assert.assertTrue(accountService.isExistByEmailAndPassword("updated@outlook.com", "9372273Aaa"));

        accountService.delete(accountService.findOneByEmail("updated@outlook.com").getId());
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldUpdateAccount_On_Update_IdNull_exception() throws ServiceException, TransactionException {

        final AccountService accountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.ACCOUNT_SERVICE);
        final Account testAccount = new Account(null,
                "updated@outlook.com",
                "9372273Aaa",
                Account.Role.USER,
                "Updated name",
                "+1-914-733-7350",
                "60441 Piper Inlet Wilfredton, GA 46024",
                100.0f);
        accountService.update(testAccount);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldUpdateAccount_On_Update_EmptyEmail_exception() throws ServiceException, TransactionException {

        final AccountService accountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.ACCOUNT_SERVICE);
        final Account testAccount = new Account(
                accountService.findOneByEmail("added@outlook.com").getId(),
                "",
                "9372273Aaa",
                Account.Role.USER,
                "Updated name",
                "+1-914-733-7350",
                "60441 Piper Inlet Wilfredton, GA 46024",
                100.0f);
        accountService.update(testAccount);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldUpdateAccount_On_Update_NullRole_exception() throws ServiceException, TransactionException {

        final AccountService accountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.ACCOUNT_SERVICE);
        final Account testAccount = new Account(
                accountService.findOneByEmail("added@outlook.com").getId(),
                "updated@outlook.com",
                "9372273Aaa",
                null,
                "Updated name",
                "+1-914-733-7350",
                "60441 Piper Inlet Wilfredton, GA 46024",
                100.0f);
        accountService.update(testAccount);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldUpdateAccount_On_Update_EmptyName_exception() throws ServiceException, TransactionException {

        final AccountService accountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.ACCOUNT_SERVICE);
        final Account testAccount = new Account(
                accountService.findOneByEmail("added@outlook.com").getId(),
                "updated@outlook.com",
                "9372273Aaa",
                Account.Role.USER,
                "",
                "+1-914-733-7350",
                "60441 Piper Inlet Wilfredton, GA 46024",
                100.0f);
        accountService.update(testAccount);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldUpdateAccount_On_Update_EmptyPhone_exception() throws ServiceException, TransactionException {

        final AccountService accountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.ACCOUNT_SERVICE);
        final Account testAccount = new Account(
                accountService.findOneByEmail("added@outlook.com").getId(),
                "updated@outlook.com",
                "9372273Aaa",
                Account.Role.USER,
                "Updated name",
                "",
                "60441 Piper Inlet Wilfredton, GA 46024",
                100.0f);
        accountService.update(testAccount);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldUpdateAccount_On_Update_EmptyAddress_exception() throws ServiceException, TransactionException {

        final AccountService accountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.ACCOUNT_SERVICE);
        final Account testAccount = new Account(
                accountService.findOneByEmail("added@outlook.com").getId(),
                "updated@outlook.com",
                "9372273Aaa",
                Account.Role.USER,
                "Updated name",
                "+1-914-733-7350",
                "",
                100.0f);
        accountService.update(testAccount);
    }

    @Test
    public void ShouldDeleteAccount_On_Delete_Pos() throws ServiceException, TransactionException {

        final AccountService accountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.ACCOUNT_SERVICE);
        accountService.add(addedAccount);
        accountService.delete(accountService.findOneByEmail("added@outlook.com").getId());
        Assert.assertFalse(accountService.isExistByEmailAndPassword("added@outlook.com", "9372273Aaa"));
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldDeleteAccount_On_Delete_Exception() throws ServiceException, TransactionException {

        final AccountService accountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.ACCOUNT_SERVICE);
        accountService.delete(1000);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldDeposit_On_Deposit_EmptyCard_Exception() throws ServiceException, TransactionException {

        final AccountService accountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.ACCOUNT_SERVICE);
        accountService.deposit(1, "", 10.0f, LocalDate.of(2040, 10,10));
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldDeposit_On_Deposit_InvalidCard_Exception() throws ServiceException, TransactionException {

        final AccountService accountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.ACCOUNT_SERVICE);
        accountService.deposit(1, "11111111", 10.0f, LocalDate.of(2040, 10,10));
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldDeposit_On_Deposit_NullDeposit_Exception() throws ServiceException, TransactionException {

        final AccountService accountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.ACCOUNT_SERVICE);
        accountService.deposit(1, "4496550182593597", null, LocalDate.of(2040, 10,10));
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldDeposit_On_Deposit_NegativeDeposit_Exception() throws ServiceException, TransactionException {

        final AccountService accountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.ACCOUNT_SERVICE);
        accountService.deposit(1, "4496550182593597", -10.0f, LocalDate.of(2040, 10,10));
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldDeposit_On_Deposit_NullValidity_Exception() throws ServiceException, TransactionException {

        final AccountService accountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.ACCOUNT_SERVICE);
        accountService.deposit(1, "4496550182593597", 10.0f, null);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldDeposit_On_Deposit_ValidityIsBefore_Exception() throws ServiceException, TransactionException {

        final AccountService accountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.ACCOUNT_SERVICE);
        accountService.deposit(1, "4496550182593597", 10.0f, LocalDate.of(2010, 10,10));
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldDeposit_On_Deposit_AccountExist_Exception() throws ServiceException, TransactionException {

        final AccountService accountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.ACCOUNT_SERVICE);
        accountService.deposit(10000, "4496550182593597", 10.0f, LocalDate.of(2040, 10,10));
    }

}