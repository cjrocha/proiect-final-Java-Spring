package com.jauntium.examples;
import org.openqa.selenium.chrome.ChromeDriver;
import com.jauntium.Browser;

public class Example1 {
	
  public static void main(String[] args){
    //Add system property for chromedriver 
    System.setProperty("webdriver.chrome.driver", "path/to/chromedriver.exe");
		 
    Browser browser = new Browser(new ChromeDriver());   //create new browser window
    browser.visit("https://gmail.com");                  //visit a url
    System.out.println(browser.doc.outerHTML());         //print the HTML 
    browser.quit();                                      //terminate the browser
  }
}