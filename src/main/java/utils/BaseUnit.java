package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;

public class BaseUnit {
    /**
     *
     */
    protected WebDriver driver = null;

    /**
     * Constructor
     */
    protected BaseUnit() {

    }

    /**
     * Automatic called before each @Test
     *
     * Call manual without @BeforeMethod
     *
     * @param url
     * @param driver
     */
    public void open(String url, Configure.DRIVERS driver) {
        PropertiesReader reader = new PropertiesReader(Configure.getConfigPath(Configure.DRIVER_FILENAME));

        switch (driver) {
            case GECKODRIVER:
                openGeckoDriver(url, reader);
                break;
            default:
                openChromeDriver(url, reader);
        }

        reader.close();
    }

    /**
     *
     */
    public void open(String url) {
        this.open(url, Configure.DRIVERS.CHROMEDRIVER);
    }

    /**
     * Open chrome driver
     *
     * @param url
     * @param reader
     */
    private void openChromeDriver(String url, PropertiesReader reader) {
        String path = reader.read("chromedriver");

        if (path != null) {
            System.setProperty("webdriver.chrome.driver", path);

            driver = new ChromeDriver();
            driver.get(url);
        } else {
            System.out.println("Driver not found!");
        }
    }

    /**
     * Open gecko driver (firefox)
     *
     * @param url
     * @param reader
     */
    private void openGeckoDriver(String url, PropertiesReader reader) {
        String path = reader.read("geckodriver");

        if (path != null) {
            System.setProperty("webdriver.gecko.driver", path);

            driver = new FirefoxDriver();
            driver.get(url);
        } else {
            System.out.println("Driver not found!");
        }
    }

    /**
     * @return
     */
    protected String getMessage(String selector) {
        try {
            WebElement message = driver.findElement(By.cssSelector(selector));

            if (message != null) return message.getText();
        } catch (Exception e) {
        }

        return "Fail without message";
    }

    /**
     * Automatic called after each @Test
     */
    @AfterMethod
    public void quit() {
        driver.quit();
    }
}
