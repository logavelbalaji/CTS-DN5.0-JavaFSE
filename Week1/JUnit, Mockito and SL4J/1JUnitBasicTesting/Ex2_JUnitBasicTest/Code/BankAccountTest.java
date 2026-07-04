import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class BankAccountTest {
    @Test
    public void testDeposit() {
        BankAccount account = new BankAccount("Aarav Sharma", 1000.00);
        account.deposit(500.00);
        assertEquals(1500.00, account.getBalance(), 0.001);
    }

    @Test
    public void testWithdrawSuccess() {
        BankAccount account = new BankAccount("Aarav Sharma", 1000.00);
        assertTrue(account.withdraw(400.00));
        assertEquals(600.00, account.getBalance(), 0.001);
    }

    @Test
    public void testWithdrawFailure() {
        BankAccount account = new BankAccount("Aarav Sharma", 1000.00);
        assertFalse(account.withdraw(1200.00));
        assertEquals(1000.00, account.getBalance(), 0.001);
    }
}
