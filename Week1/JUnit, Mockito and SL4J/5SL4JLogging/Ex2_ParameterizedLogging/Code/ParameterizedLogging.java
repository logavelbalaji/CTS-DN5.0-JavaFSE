import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParameterizedLogging {
    private static final Logger logger = LoggerFactory.getLogger(ParameterizedLogging.class);

    public static void main(String[] args) {
        String name = "Aarav Sharma";
        int amount = 500;
        logger.info("Transaction processed for user {} with amount Rs. {}", name, amount);
    }
}
