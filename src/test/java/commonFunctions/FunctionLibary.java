package commonFunctions;

import java.io.FileInputStream;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class FunctionLibary {
	public static WebDriver driver;
	public static Properties conprop;
	public static String Expected="";
	public static String Actual ="";
	public static WebDriver startBrowser()throws Throwable
	{
		conprop = new Properties();
		conprop.load(new FileInputStream("./PropertyFile/Environment.properties"));
		if(conprop.getProperty("Browser").equalsIgnoreCase("chrome"))
		{
			driver = new ChromeDriver();
			driver.manage().window().maximize();
		}
		else if(conprop.getProperty("Browser").equalsIgnoreCase("firefox"))
		{
			driver = new FirefoxDriver();

		}
		else
		{
			System.out.println("Browser value is Not Matching");
		}
		return driver;
	}
	public static void openUrl(WebDriver driver)
	{
		driver.get(conprop.getProperty("Url"));
	}
	public static void waitForElement(WebDriver driver,String LocatorType,String LocatorValue,String waitTime)
	{
		WebDriverWait Mywait = new WebDriverWait(driver, Integer.parseInt(waitTime));
		if(LocatorType.equalsIgnoreCase("id"))
		{
			Mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(LocatorValue)));
		}
		else if(LocatorType.equalsIgnoreCase("name"))
		{
			Mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LocatorValue)));
		}
		else if(LocatorType.equalsIgnoreCase("xpath"))
		{
			Mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LocatorValue)));
		}
	}
	public static void typeAction(WebDriver driver,String LocatorType,String LocatorValue,String testData)
	{
		if(LocatorType.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(LocatorValue)).clear();
			driver.findElement(By.name(LocatorValue)).sendKeys(testData);
		}
		else if(LocatorType.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(LocatorValue)).clear();
			driver.findElement(By.id(LocatorValue)).sendKeys(testData);
		}
		else if(LocatorType.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(LocatorValue)).clear();
			driver.findElement(By.xpath(LocatorValue)).sendKeys(testData);
		}
	}
	public static void clickAction(WebDriver driver,String LocatorType,String Locatorvalue)
	{
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(Locatorvalue)).click();
		}
		else if(LocatorType.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(Locatorvalue)).click();
		}
		else if(LocatorType.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(Locatorvalue)).sendKeys(Keys.ENTER);
		}
	}
	public static void validateTitle(WebDriver driver,String ExpectedTitle)
	{
		String ActualTitle= driver.getTitle();
		try {
			Assert.assertEquals(ActualTitle, ExpectedTitle,"Title is Not matching");
		}catch(Throwable t)
		{
			System.out.println(t.getMessage());
		}
	}
	public static void closeBrowser(WebDriver driver)
	{
		driver.quit();
	}
	public static void mouseClick(WebDriver driver) throws Throwable
	{
		Actions ac = new Actions(driver);
		ac.moveToElement(driver.findElement(By.xpath("//a[text()='Stock Items ']"))).perform();
		Thread.sleep(2000);
		ac.moveToElement(driver.findElement(By.xpath("(//a[contains(.,'Stock Categories')])[2]"))).click().perform();
	}
	public static void stockTable(WebDriver driver,String ExpectedData) throws Throwable
	{
		if(!driver.findElement(By.xpath(conprop.getProperty("search-textbox"))).isDisplayed())
			driver.findElement(By.xpath(conprop.getProperty("search-panel"))).click();
		driver.findElement(By.xpath(conprop.getProperty("search-textbox"))).sendKeys(ExpectedData);
		Thread.sleep(2000);
		driver.findElement(By.xpath(conprop.getProperty("search-button"))).click();
		Thread.sleep(2000);
		String Actualdata =driver.findElement(By.xpath("//table[@id='tbl_a_stock_categorieslist']/tbody/tr[1]/td[4]/div/span/span")).getText();
		System.out.println(ExpectedData+"      "+Actualdata);
		Assert.assertEquals(ExpectedData, Actualdata,"Category name Not Found in Table");
	}
	public static void captureData(WebDriver driver,String LocatorType,String LocatorValue)
	{
		Expected = driver.findElement(By.name(LocatorValue)).getAttribute("value");
	}
	public static void supplierTable(WebDriver driver) throws Throwable
	{
		if(!driver.findElement(By.xpath(conprop.getProperty("search-textbox"))).isDisplayed())
			driver.findElement(By.xpath(conprop.getProperty("search-panel"))).click();
		driver.findElement(By.xpath(conprop.getProperty("search-textbox"))).sendKeys(Expected);
		Thread.sleep(2000);
		driver.findElement(By.xpath(conprop.getProperty("search-button"))).click();
		Thread.sleep(2000);
		Actual= driver.findElement(By.xpath("//table[@id='tbl_a_supplierslist']/tbody/tr[1]/td[6]/div/span/span")).getText();
		System.out.println(Expected+"     "+Actual);
		Assert.assertEquals(Expected, Actual, "Supplier number Not Found in Table");
	}
}






















