import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class SimpleAppTest {
    @Test
    public void testCheckLoanEligibility() {
        SimpleApp app = new SimpleApp();
        assertTrue(app.checkLoanEligibility(65, 35000.00));
        assertFalse(app.checkLoanEligibility(25, 35000.00));
    }
}
