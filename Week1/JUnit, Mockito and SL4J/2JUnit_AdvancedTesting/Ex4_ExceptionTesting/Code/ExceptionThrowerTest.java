import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExceptionThrowerTest {
    @Test
    public void testThrowException() {
        ExceptionThrower thrower = new ExceptionThrower();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            thrower.throwException();
        });
        assertEquals("Invalid argument provided", exception.getMessage());
    }
}
