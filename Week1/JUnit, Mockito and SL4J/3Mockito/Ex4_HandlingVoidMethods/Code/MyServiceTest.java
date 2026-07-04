import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doNothing;
import static org.mockito.ArgumentMatchers.anyString;

public class MyServiceTest {
    @Test
    public void testVoidMethod() {
        ExternalApi mockApi = Mockito.mock(ExternalApi.class);
        doNothing().when(mockApi).logMessage(anyString());
        MyService service = new MyService(mockApi);
        service.log("Aarav");
        verify(mockApi).logMessage("Aarav");
    }
}
