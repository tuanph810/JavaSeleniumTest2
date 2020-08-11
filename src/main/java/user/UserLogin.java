package user;

import org.apache.poi.ss.usermodel.Row;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class UserLogin extends BaseUnit {
    /**
     *
     */
    private PropertiesReader pathReader = null;
    private PropertiesReader prosReader = null;

    /**
     *
     */
    public UserLogin() {
        pathReader = new PropertiesReader(Configure.getConfigPath(Configure.PATH_FILENAME));
        prosReader = new PropertiesReader(Configure.getMapPath(Configure.USER_LOGIN_MAP));
    }

    /**
     * @param username
     * @param password
     */
    private void handle(String username, String password) {
        try {
            driver.manage().timeouts().implicitlyWait(11, TimeUnit.SECONDS);

            WebElement usernameEl = driver.findElement(By.cssSelector(prosReader.read("usernameField")));
            WebElement passwordEl = driver.findElement(By.cssSelector(prosReader.read("passwordField")));
            WebElement loginEl = driver.findElement(By.cssSelector(prosReader.read("submitButton")));

            usernameEl.sendKeys(username);
            passwordEl.sendKeys(password);

            loginEl.click();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void executeTest(ExcelReader reader, ExcelWriter writer) {
        int i = 1;

        while (reader.hasNext()) {
            Row row = reader.readRow();

            open(pathReader.read("UserLoginPath"));

            String username = reader.getCellData(row, 0);
            String password = reader.getCellData(row, 1);

            handle(username, password);

            if (pathReader.read("UserLoginPathPassed").equalsIgnoreCase(driver.getCurrentUrl())) {
                writer.write(new String[]{String.valueOf(i++), username, password, "Success", "Login success"});
            } else {
                writer.write(new String[]{
                        String.valueOf(i++),
                        username,
                        password,
                        "Fail",
                        this.getMessage("div.flash-alert.mb-2 span")
                });
            }

            pathReader.close();

            quit();
        }
    }

    /**
     *
     */
    @Test
    public void testLogin() {
        String path = Configure.getDataPath("accounts.xlsx");

        ExcelReader reader = new ExcelReader(path);
        ExcelWriter writer = new ExcelWriter(new String[]{"No", "Username", "Password", "Status", "Log"});

        try {
            reader.open();

            executeTest(reader, writer);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());

            quit();
        }

        System.out.println("File was saved: " + writer.save());
    }
}
