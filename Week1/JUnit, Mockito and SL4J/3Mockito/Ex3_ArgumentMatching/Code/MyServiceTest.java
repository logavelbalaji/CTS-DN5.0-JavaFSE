import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;

public class MyServiceTest {
    @Test
    public void testArgumentMatching() {
        ExternalApi mockApi = Mockito.mock(ExternalApi.class);
        when(mockApi.formatData(anyString())).thenReturn("Formatted: Aarav");
        MyService service = new MyService(mockApi);
        String result = service.processAndFormat("Aarav");
        assertEquals("Formatted: Aarav", result);
        verify(mockApi).formatData(anyString());
    }
}
