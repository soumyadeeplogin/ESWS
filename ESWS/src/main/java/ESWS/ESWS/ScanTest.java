package ESWS.ESWS;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class ScanTest {
	
	static WebDriver driver;
	static WebElement element;
	static WebDriver wait;
	
	public static void main(String args[])
	{
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://www.google.co.in/");
	}

}
