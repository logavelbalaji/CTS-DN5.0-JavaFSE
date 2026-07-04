public class MyService {
    private ExternalApi api;

    public MyService(ExternalApi api) {
        this.api = api;
    }

    public void doAction() throws Exception {
        api.executeAction();
    }
}
