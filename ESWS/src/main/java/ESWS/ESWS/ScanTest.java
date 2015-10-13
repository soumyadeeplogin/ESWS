package ESWS.ESWS;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ScanTest {
	
	static WebDriver driver;
	static WebElement element;
	static WebDriverWait wait;
	
	public static void main(String args[])
	{
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream("config.properties");
			prop.load(input);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String appURL = "http://" +prop.getProperty("host")+":"+prop.getProperty("port")+"/"+prop.getProperty("app");
		System.out.println(appURL);
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(appURL);
		
		
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new ScanTest().scan();

		
	}
	
	public void scan()
	{
		WebElement btnScan = driver.findElement(By.id("start-scanner"));
		btnScan.click();
		
		wait = new WebDriverWait(driver, 50);
		WebElement docWait = wait.until(ExpectedConditions.elementToBeClickable(By.id("start-scanner")));
		List<WebElement> docs = new ArrayList<WebElement>();
		docs = driver.findElements(By.className("doc-number"));
		
		if(docs.isEmpty())
		{
			System.out.println("Scan failed");
		}
		else
		{
			System.out.println("Scan passed");
		}
		
	}

}
