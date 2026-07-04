import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyServiceTest {
    @Test
    public void testMultipleReturns() {
        ExternalApi mockApi = Mockito.mock(ExternalApi.class);
        when(mockApi.getSeqData()).thenReturn("First").thenReturn("Second");
        MyService service = new MyService(mockApi);
        String result = service.fetchConsecutive();
        assertEquals("First & Second", result);
    }
}
