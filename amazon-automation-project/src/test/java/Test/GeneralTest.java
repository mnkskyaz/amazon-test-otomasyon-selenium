package Test;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import Base.Base;

import Util.PropertyLoader;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.concurrent.TimeUnit;

/**
 * GeneralTest includes login, search and search result validation tests..
 */
public class GeneralTest extends Base {

	static PropertyLoader propertyLoader;
	private static String browserType = "Chrome";
	private static String OS = System.getProperty("os.name").toUpperCase();
	static WebDriver driver;
	JavascriptExecutor executor = (JavascriptExecutor) driver;


	@BeforeClass
	public static void setUp() {
		propertyLoader = new PropertyLoader();

		if (browserType.equals("Chrome")) {

			if (OS.contains("MAC OS X")) {
				System.setProperty("webdriver.chrome.driver", propertyLoader.getProperty("MacOSChromeDriverPath"));
			} else if (OS.contains("WİNDOWS 10") || OS.contains("WINDOWS 10")) {
				System.setProperty("webdriver.chrome.driver", propertyLoader.getProperty("WindowsChromeDriverPath"));
			}

			driver = new ChromeDriver();

		} else if (browserType.equals("Firefox")) {

			if (OS.contains("MAC OS X")) {
				System.setProperty("webdriver.gecko.driver", propertyLoader.getProperty("MacOSFirefoxDriverPath"));
			} else if (OS.contains("WİNDOWS 10") || OS.contains("WINDOWS 10")) {
				System.setProperty("webdriver.gecko.driver", propertyLoader.getProperty("WindowsFirefoxDriverPath"));
			}

			driver = new FirefoxDriver();

		}

		String baseUrl = propertyLoader.getProperty("baseUrl");
		driver.get(baseUrl);

	}

	public boolean isElementExist(By by) {
		try {
			return driver.findElement(by).isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public void clickByJavascript(WebElement element){

		executor.executeScript("arguments[0].click();", element);
	}

	@Test
	public void MainTest() throws InterruptedException{

		String expectedTitle = "Amazon";
		String actualTitle = "";
		actualTitle = driver.getTitle();


		Assert.assertTrue("Ana Sayfa acilmadi", actualTitle.contains(expectedTitle));
		System.out.println("Anasayfa acildi");

		// 2

		driver.findElement(By.id("nav-link-accountList")).click();
		driver.findElement(By.id("ap_email")).sendKeys("mnkskyaz@windowslive.com");

		driver.findElement(By.id("continue")).click();

		driver.findElement(By.id("ap_password")).sendKeys("mnks27kyaz");
		driver.findElement(By.id("signInSubmit")).click();

		// 3-4

		String searchText = "Samsung";

		driver.findElement(By.id("twotabsearchtextbox")).sendKeys(searchText);

		driver.findElement(By.className("nav-input")).click();


		Assert.assertTrue("Aranan bulunamadi", driver.findElement(By.cssSelector("#s-result-count span span")).getText().contains(searchText));

		System.out.println("Aranan bulundu");

		// 5

		driver.findElement(By.cssSelector(".pagnLink a")).click();

		String expectedPage = "17";


		Assert.assertTrue("Sayfa Acilmadi", driver.findElement(By.id("s-result-count")).getText().contains(expectedPage));
		System.out.println("2. sayfadayiz");

		// 6

		String productName = driver.findElements(By.cssSelector(".s-access-detail-page h2")).get(2)
				.getAttribute("data-attribute");

		System.out.println("Product name " + productName);

		clickByJavascript(driver.findElements(By.className("s-access-detail-page")).get(2));
		System.out.println("Clicked");

		if (isElementExist(By.id("add-to-wishlist-button-submit"))) {
		clickByJavascript(driver.findElement(By.id("add-to-wishlist-button-submit")));
			System.out.println("First :");
		}

		if (isElementExist(By.className("masrw-button-input"))) {
			clickByJavascript(driver.findElement(By.className("masrw-button-input")));
			System.out.println("Second :");
		}

		System.out.println("Clicked");

		// 7

		WebElement list = driver.findElement(By.id("nav-link-accountList"));


		if (isElementExist(By.className("a-icon-close"))){
			driver.findElement(By.className("a-icon-close")).click();
		}



		// Hover to element with javascript

		String javaScript = "var evObj = document.createEvent('MouseEvents');" +
				"evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);" +
				"arguments[0].dispatchEvent(evObj);";

		((JavascriptExecutor)driver).executeScript(javaScript, list);

		Thread.sleep(3000);

		WebElement wish = driver.findElement(By.cssSelector(".nav-link .nav-text"));
		wish.click();


		// 8

		String Compare = driver.findElement(By.cssSelector(".a-size-base a")).getText();

		Assert.assertTrue("Ayni urun degil", productName.contains(Compare));
		System.out.println("Ayni urun");

		// 9


		driver.findElement(By.xpath("//input[@name='submit.deleteItem']")).click();
		System.out.println("Urun silindi");

		// 10

		Thread.sleep(5000);

		Assert.assertTrue("Urunn silinemedi." , driver.findElement(By.cssSelector(".a-list-item .a-alert-content")).getText().equals("Deleted"));


	}

	@After
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
}