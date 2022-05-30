package com.jauntium.examples;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import com.jauntium.Browser;
import com.jauntium.JauntiumException;

public class Example2b {
	
  public static void main(String[] args){
    System.setProperty("webdriver.chrome.driver", "path/to/chromedriver.exe");    //setup
    try{
      ChromeOptions options = new ChromeOptions();                //create chrome options object
      options.addArguments("--headless");                         //specify headless mode (no GUI)
      
      Browser browser = new Browser(new ChromeDriver(options));   //create headless browser
      browser.visit("http://northernbushcraft.com");              //visit a url.
      System.out.println(browser.doc.findFirst("<meta>"));        //find & print first meta tag
    }
    catch(JauntiumException e){  
      System.err.println(e);          
    }
  }
}
