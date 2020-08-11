package utils;

import user.UserLogin;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {

    /**
     * Path to properties files
     */
    private String path = "";

    /**
     * Used to reading properties
     */
    private Properties properties = null;

    /**
     * Used to reading file
     */
    private InputStream inputStream = null;

    /**
     * Constructor
     *
     * @param path
     */
    public PropertiesReader(String path) {
        this.path = path;

        this.properties = new Properties();

        this.initial();
    }

    /**
     * Initialization
     */
    private void initial() {
        try {
            inputStream = this.getClass().getClassLoader().getResourceAsStream(path);

            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                System.out.println("File not found on path: " + this.path);

                this.close();
            }
        } catch (IOException e) {
            e.printStackTrace();

            this.close();
        }
    }

    /**
     * Read properties by key
     *
     * @param key
     * @return String
     */
    public String read(String key) {
        // Re-initial if was closed
        if(inputStream == null) this.initial();

        return properties.getProperty(key);
    }

    /**
     * Close stream
     */
    public void close() {
        try {
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
