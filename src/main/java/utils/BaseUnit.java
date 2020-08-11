package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;

public class BaseUnit {
    /**
     *
     */
    private static final String DRIVER_FILE = "Configs/Driver.properties";
    protected static final String PATH_FILE = "Configs/Path.properties";

    /**
     *
     */
    protected WebDriver driver = null;

    /**
     * Constructor
     *
     */
    protected BaseUnit() {

    }

    /**
     * Automatic called before each @Test
     *
     * Call manual without @BeforeMethod
     */
    public void open(String url) {
        PropertiesReader reader = new PropertiesReader(DRIVER_FILE);

        String path = reader.read("chromedriver");

        if(path != null) {
            System.setProperty("webdriver.chrome.driver", path);

            driver = new ChromeDriver();
            driver.get(url);
        } else {
            System.out.println("Driver not found!");
        }

        reader.close();
    }

    /**
     * Automatic called after each @Test
     */
    @AfterMethod
    public void quit() {
        driver.quit();
    }
}
