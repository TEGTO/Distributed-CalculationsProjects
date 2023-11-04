package myLogger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MyLogger
{
    private static final Logger logger = LogManager.getLogger(MyLogger.class);
    public static void printInfoMessage(String s)
    {
        logger.info(s);
    }
    public static void printErrorMessage(String s)
    {
        logger.error(s);
    }
}
