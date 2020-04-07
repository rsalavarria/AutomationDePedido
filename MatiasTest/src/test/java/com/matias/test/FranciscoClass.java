package com.matias.test;


import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FranciscoClass {
	
	public static void portabilidad (WebDriver driver) throws InterruptedException {
		
		Thread.sleep(2000);
		
		WebDriverWait ewait = new WebDriverWait(driver, 30);
		
		ewait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("slds-spinner_container")));
		List<WebElement> cantIFrames = driver.findElements(By.tagName("iFrame"));
		int size = cantIFrames.size();
		driver.switchTo().frame(size - 2);
		
		
		driver.findElement(By.xpath("//button[contains(@class,'neutral')][3])")). click();
		
		
	}

}