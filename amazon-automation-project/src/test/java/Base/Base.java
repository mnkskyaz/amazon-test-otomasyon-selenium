package Base;

import org.openqa.selenium.WebDriver;


public class Base {
    private WebDriver driver;

    public WebDriver getWebDriver() {
        return driver;
        
    }

    public void setWebDriver(WebDriver webDriver) {
        this.driver = driver;
    }
}
