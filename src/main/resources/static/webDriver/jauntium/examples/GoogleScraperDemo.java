package com.jauntium.examples;
import org.openqa.selenium.chrome.ChromeDriver;
import com.jauntium.Browser;
import com.jauntium.Element;
import com.jauntium.Elements;
import com.jauntium.JauntiumException;

public class GoogleScraperDemo {
	
  public static void main(String[] args) throws JauntiumException{
    System.setProperty("webdriver.chrome.driver", "path/to/chromedriver.exe");  //setup
    Browser browser = new Browser(new ChromeDriver());      //create (Chrome) browser instance    
    browser.visit("https://google.com");                    //visit google.com
    browser.doc.apply("seashells").submit();                //apply search term and submit form

    Elements links = browser.doc.findEvery("<h3>").findEvery("<a>");      //find search result links
      for(Element link : links) System.out.println(link.getAt("href"));   //print results
    }
}
