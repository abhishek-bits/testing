package bank;

import matcher.BankAccountMatcher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BankAccountTest {

    private BankAccount bankAccount;

    @Mock
    List<BankAccount> bankAccounts;

    // Arrange
    @BeforeEach
    public void setUp() {
        bankAccount = new BankAccount(500.0);
    }

    @AfterEach
    public void tearDown() {

    }

    @Test
    public void withdrawTest() {
        // Act (Do)
        bankAccount.withdraw(100.0);

        // Assert (Test)
        assertEquals(400.0, bankAccount.getBalance());
    }

    @Test
    public void depositTest() {
        bankAccount.deposit(100.0);
        assertEquals(600.0, bankAccount.getBalance());
    }

    @Test
    public void depositAndWithdrawTest() {
        Double currBal;
        bankAccount.deposit(105.45);
        assertEquals((currBal = 605.45), bankAccount.getBalance());
        bankAccount.withdraw(51.32);
        assertEquals((currBal = 605.45 - 51.32), bankAccount.getBalance());
        bankAccount.deposit(23.10);
        assertEquals(currBal + 23.10, bankAccount.getBalance());
    }

    @Test
    public void withdrawMaxValueTest() {
        bankAccount.withdraw(150.0);
        assertEquals(400.0, bankAccount.getBalance());
    }

    // It is also common to create tests that deal with
    // incorrect input, or some other issue that throws a known exception
    @Test
    public void deposit_negativeValueTest() {
//        assertThrows(
//                NumberFormatException.class,
//                () -> bankAccount.deposit(-100.0)
//        );
        // OR
        NumberFormatException thrown = assertThrows(
                NumberFormatException.class,
                () -> bankAccount.deposit(-100.0)
        );
        assertTrue(thrown.getMessage().contains("Negative"));
    }

    @Test
    public void test_custom_bank_account_matcher() {
        BankAccount otherBankAccount = new BankAccount(500.0);
        bankAccounts.add(otherBankAccount);

        verify(bankAccounts, times(1))
                .add(argThat(new BankAccountMatcher(bankAccount)));
    }
}

