package myLogger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MyLogger
{
    public static final Logger logger = LogManager.getLogger(MyLogger.class);
    public static void printInfoMessage(String s)
    {
        logger.info(s);
    }
}
