import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.doThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyServiceTest {
    @Test
    public void testVoidMethodException() throws Exception {
        ExternalApi mockApi = Mockito.mock(ExternalApi.class);
        doThrow(new IllegalArgumentException("Error occurred")).when(mockApi).executeAction();
        MyService service = new MyService(mockApi);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            service.doAction();
        });
        assertEquals("Error occurred", exception.getMessage());
    }
}
