package com.jauntium.examples;
import org.openqa.selenium.chrome.ChromeDriver;
import com.jauntium.Browser;
import com.jauntium.JauntiumException;

public class Example20 {
	
  public static void main(String[] args){
    System.setProperty("webdriver.chrome.driver", "path/to/chromedriver.exe");  //setup
    try{
      Browser browser = new Browser(new ChromeDriver());    
      browser.visit("https://google.com");              //visit google.com
      browser.doc.apply("seashells").submit();          //apply search term and submit form

      String nextPageUrl = browser.doc.nextPageUrl();   //extract link to next page of results
      browser.visit(nextPageUrl);                       //visit next page(p 2).
    }  
    catch(JauntiumException e){
    	System.err.println(e);
    }
  }
}
 