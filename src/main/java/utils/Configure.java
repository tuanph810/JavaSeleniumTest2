package utils;

import java.io.File;

public class Configure {
    /**
     * Filenames
     */
    public static final String DRIVER_FILENAME = "Driver.properties";
    public static final String PATH_FILENAME = "Path.properties";

    /**
     * Maps
     */
    public static final String USER_LOGIN_MAP = "UserLogin.properties";

    /**
     * Define drivers
     */
    public enum DRIVERS {
        CHROMEDRIVER,
        GECKODRIVER
    }

    /**
     * Resource folders
     */
    public static final String CONFIG_FOLDER = "Configs";
    public static final String DATA_FOLDER = "src/main/resources/Data";
    public static final String DRIVER_FOLDER = "Drivers";
    public static final String UIMAP_FOLDER = "UIMaps";

    /**
     * Get path
     */
    public static String getFilePath(String folder, String filename) {
        return folder + File.separator + filename;
    }

    /**
     * Get path to Config file
     */
    public static String getConfigPath(String filename) {
        return getFilePath(CONFIG_FOLDER, filename);
    }

    /**
     * Get path to Data file
     */
    public static String getDataPath(String filename) {
        return getFilePath(DATA_FOLDER, filename);
    }

    /**
     * Get path to Driver file
     */
    public static String getDriverPath(String filename) {
        return getFilePath(DRIVER_FOLDER, filename);
    }

    /**
     * Get path to UIMap file
     */
    public static String getMapPath(String filename) {
        return getFilePath(UIMAP_FOLDER, filename);
    }
}
