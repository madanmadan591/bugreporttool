package org.com.bugreport.Bugreport;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class Attoch {

	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.gecko.driver", "C:\\Users\\immadan\\Downloads\\geckodriver-v0.32.1-win64\\geckodriver.exe");	
        WebDriver driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
	    driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.get("https://atochadp.amazon.com");
		driver.findElement(By.xpath("//input[@id='user_name_field']")).sendKeys("immadan");
		driver.findElement(By.xpath("//input[@id = 'user_name_btn']")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("password_field")).sendKeys("Madan@18");
		driver.findElement(By.xpath("//input[@id='password_btn']")).click();	
		Thread.sleep(2000);
		driver.findElement(By.xpath("//a[@id='hrefAdvancedSearch']")).click();
		
		driver.findElement(By.xpath("//span[@id='span_attr_customerId']")).click();
		
		driver.findElement(By.xpath("//input[@id='input_customerId']")).sendKeys("AMQKAVQDRYU03");
		
	    driver.findElement(By.xpath("//span[@id='span_attr_more']")).click();
		driver.findElement(By.xpath("//span[@id='span_attr_event']")).click();
		driver.findElement(By.id("input_event")).sendKeys("AudioPlayer.PlaybackPaused");
		
		driver.findElement(By.id("input_timeWindowFrom")).sendKeys("2023/05/08 15:11:00");
		
		driver.findElement(By.id("submit")).click();
		
		//WebElement more = driver.findElement(By.id("span_attr_more"));        
        //Select select1 = new Select(more);
        //select1.selectByVisibleText("Event");
		

	}

}
