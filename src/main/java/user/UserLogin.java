package user;

import org.apache.poi.ss.usermodel.Row;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.BaseUnit;
import utils.ExcelReader;
import utils.PropertiesReader;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class UserLogin extends BaseUnit {

    private static final String PROPERTIES_FILE = "UIMaps/UserLogin.properties";

    private PropertiesReader pathReader = null;
    private PropertiesReader prosReader = null;

    /**
     *
     */
    public UserLogin() {
        pathReader = new PropertiesReader(PATH_FILE);
        prosReader = new PropertiesReader(PROPERTIES_FILE);
    }

    /**
     * @param username
     * @param password
     */
    public void handle(String username, String password) {
        try {
            driver.manage().timeouts().implicitlyWait(11, TimeUnit.SECONDS);

            driver.findElement(By.cssSelector(prosReader.read("usernameField"))).sendKeys(username);
            driver.findElement(By.cssSelector(prosReader.read("passwordField"))).sendKeys(password);
            driver.findElement(By.cssSelector(prosReader.read("submitButton"))).click();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * @return
     */
    public String getMessage() {
        try {
            WebElement message = driver.findElement(By.cssSelector("div.flash-alert.mb-2 span"));

            if (message != null) {
                return message.getText();
            }
        } catch (Exception e) {
        }

        return "Fail without message";
    }

    /**
     *
     */
    @Test
    public void testLogin() {
        String path = "src/main/resources/Data/accounts.xlsx";

        ExcelReader reader = new ExcelReader(path);

        try {
            reader.open();

            while (reader.hasNext()) {
                Row row = reader.readRow();

                open(pathReader.read("UserLoginPath"));

                String username = row.getCell(0) != null ? row.getCell(0).toString() : "";
                String password = row.getCell(1) != null ? row.getCell(1).toString() : "";

                System.out.print("Start test: username: " + username + "\t\t\t" + "password: " + password);

                handle(username, password);

                if (pathReader.read("UserLoginPathPassed").equalsIgnoreCase(driver.getCurrentUrl())) {
                    System.out.println("\t\tstatus: Success");
                } else {


                    System.out.println("\t\tstatus: " + this.getMessage());
                }

                pathReader.close();

                quit();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());

            quit();
        }
    }
}
