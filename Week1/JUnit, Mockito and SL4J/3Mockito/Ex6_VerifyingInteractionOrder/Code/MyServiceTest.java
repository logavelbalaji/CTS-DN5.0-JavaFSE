import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import static org.mockito.Mockito.inOrder;

public class MyServiceTest {
    @Test
    public void testInteractionOrder() {
        ExternalApi mockApi = Mockito.mock(ExternalApi.class);
        MyService service = new MyService(mockApi);
        service.runProcess();
        InOrder inOrder = inOrder(mockApi);
        inOrder.verify(mockApi).setupConnection();
        inOrder.verify(mockApi).sendData();
    }
}
