package db;

import java.io.InputStream;
import java.util.Properties;

public class DBProperties {
    private static Properties props = new Properties();

    static {
        InputStream is = DBProperties.class.getResourceAsStream("/database.properties");
        try {
            props.load(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String name) {
        return props.getProperty(name);
    }

}
