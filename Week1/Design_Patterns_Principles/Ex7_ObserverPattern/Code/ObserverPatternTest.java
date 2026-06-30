public class ObserverPatternTest {
    public static void main(String[] args) {
        StockMarket googleStock = new StockMarket("Google", 175.50);

        Observer mobileApp = new MobileApp("Google Finance Mobile");
        Observer webApp = new WebApp("Google Finance Web Dashboard");

        googleStock.registerObserver(mobileApp);
        googleStock.registerObserver(webApp);

        googleStock.setPrice(178.20);
        System.out.println();

        googleStock.deregisterObserver(mobileApp);

        googleStock.setPrice(180.45);
    }
}
