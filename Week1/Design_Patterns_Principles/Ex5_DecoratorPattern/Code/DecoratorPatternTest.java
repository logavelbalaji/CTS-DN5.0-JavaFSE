public class DecoratorPatternTest {
    public static void main(String[] args) {
        Notifier emailNotifier = new EmailNotifier();
        emailNotifier.send("Disk space is 90% full.");
        System.out.println();

        Notifier emailAndSMSNotifier = new SMSNotifierDecorator(new EmailNotifier());
        emailAndSMSNotifier.send("Database connection lost.");
        System.out.println();

        Notifier fullyDecoratedNotifier = new SlackNotifierDecorator(new SMSNotifierDecorator(new EmailNotifier()));
        fullyDecoratedNotifier.send("Server went down!");
    }
}
