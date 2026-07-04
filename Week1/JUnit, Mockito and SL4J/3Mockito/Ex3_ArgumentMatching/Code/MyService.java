public class MyService {
    private ExternalApi api;

    public MyService(ExternalApi api) {
        this.api = api;
    }

    public String processAndFormat(String input) {
        return api.formatData(input);
    }
}
