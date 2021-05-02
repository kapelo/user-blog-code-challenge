package util;

import org.apache.logging.log4j.Logger;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class PropertyValues {
    private static final Properties prop = new Properties();
    static final String BASE_URL = PropertyValues.loadPropertyValues();
    private static Logger logger = TestLogger.logger;

    private static String loadPropertyValues() {
        String propFileName = "config.properties";
        InputStream inputStream = PropertyValues.class.getClassLoader().getResourceAsStream(propFileName);
        String url = System.getenv("BASE_URL");

        if (inputStream != null) {
            try {
                prop.load(inputStream);
            } catch (IOException e) {
                logger.error(e.getMessage());
            }

            url = prop.getProperty("url");
        } else if (url == null) {
            logger.error("Either '" + propFileName + "' is not found or environment variable is not set!");
            try {
                throw new FileNotFoundException("Either '" + propFileName + "' is not found or environment variable is not set!");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        return url;
    }
}
