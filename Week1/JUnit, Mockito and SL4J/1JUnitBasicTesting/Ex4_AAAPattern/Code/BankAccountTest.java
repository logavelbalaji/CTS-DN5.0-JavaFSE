import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class BankAccountTest {
    private BankAccount account;

    @Before
    public void setUp() {
        account = new BankAccount(1000.00);
    }

    @After
    public void tearDown() {
        account = null;
    }

    @Test
    public void testDeposit() {
        account.deposit(500.00);
        assertEquals(1500.00, account.getBalance(), 0.001);
    }

    @Test
    public void testWithdraw() {
        account.withdraw(200.00);
        assertEquals(800.00, account.getBalance(), 0.001);
    }
}
