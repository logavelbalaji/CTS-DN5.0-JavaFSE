import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DifferentAppendersExample {
    private static final Logger logger = LoggerFactory.getLogger(DifferentAppendersExample.class);

    public static void main(String[] args) {
        logger.debug("Debug level log message");
        logger.info("Info level log message for Aarav Sharma");
        logger.warn("Warning level log message for Bhavna Patel");
        logger.error("Error level log message with amount Rs. 1000");
    }
}
