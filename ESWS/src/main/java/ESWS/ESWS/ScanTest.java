package ESWS.ESWS;

import java.io.File;
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
		System.out.println("Clearing...");
		new ScanTest().clear();
		
		int toScan = inputCount();
		System.out.println("Number of files :: " + toScan);
		
		System.out.println("Scanning...");
		new ScanTest().scan();
		System.out.println("Counting...");
		int scanned = new ScanTest().docCount();
		
		if(scanned==toScan)
		{
			System.out.println("Scanned all " + toScan + " files. Success.");
		}
		else
		{
			System.out.println("Scanned " + scanned + " out of " + toScan + " files. Failed.");
		}
		
	}
	
	public void scan()
	{
		WebElement btnScan = driver.findElement(By.id("start-scanner"));
		btnScan.click();
	}
	
	public void clear()
	{
		
		List<WebElement> docs = new ArrayList<WebElement>();
		docs = driver.findElements(By.className("doc-number"));
		if(docs.isEmpty())
		{
			System.out.println("Cleaning not required.");
		}
		else
		{			
			WebElement btnSAll = driver.findElement(By.id("docSelectAllCheckbox"));
			btnSAll.click();
			WebElement btnDel = driver.findElement(By.id("delete-doc-button"));
			btnDel.click();
			WebElement btnOk = driver.findElement(By.id("dialog-confirmation-ok-c175"));
			btnOk.click();
			System.out.println("Cleaned");
		}
	}
	
	
	public static int inputCount()
	{
		int count = 0;
		File f = new File("C:\\TestESWS\\");
	    File[] files = f.listFiles();

	    if (files != null)
	    for (int i = 0; i < files.length; i++) {
	        count++;
	        File file = files[i];
	    }
		return count;
	}
	
	public int docCount()
	{
		int count = 0;
		int pageCount = 0;
		wait = new WebDriverWait(driver, 50);
		WebElement docWait = wait.until(ExpectedConditions.elementToBeClickable(By.id("start-scanner")));
		
		List<WebElement> docs = new ArrayList<WebElement>();
		docs = driver.findElements(By.className("doc-number"));
		System.out.println("Doc Size :: "+docs.size());
		
		List<WebElement> pages = new ArrayList<WebElement>();
		pages = driver.findElements(By.className("doc-info"));
		
				
		for (WebElement webElement : pages) {
			String temp = webElement.getText();
//			System.out.println(temp);
			pageCount += Integer.parseInt(temp.substring(temp.lastIndexOf(' ')+1, temp.length()));
		}
		
		System.out.println("Total Pages = " + pageCount);
//		System.out.println("Document and pages");
		return pageCount;
	}

}
