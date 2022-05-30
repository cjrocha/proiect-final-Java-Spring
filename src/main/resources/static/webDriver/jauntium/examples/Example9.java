package com.jauntium.examples;
import java.io.File;
import org.openqa.selenium.chrome.ChromeDriver;
import com.jauntium.Browser;
import com.jauntium.Elements;
import com.jauntium.JauntiumException;


public class Example9 {
	
  public static void main(String[] args){
    System.setProperty("webdriver.chrome.driver", "path/to/chromedriver.exe");
    try{
      Browser browser = new Browser(new ChromeDriver());
      browser.visit(("http://jauntium.com/examples/food.htm")); 

      Elements elements = browser.doc.findEvery("<div>");                   //find all divs in the document
      System.out.println("Every div: " + elements.size() + " results");     //report number of search results.

      elements = browser.doc.findEach("<div>");                             //find all non-nested divs
      System.out.println("Each div: " + elements.size() + " results");      //report number of search results.
		                                                                    
      elements = browser.doc.findFirst("<div class=meat>").findEach("<div>"); //find non-nested divs within <p class='meat'>
      System.out.println("Meat search: " + elements.size() + " results");   //report number of search results.
    }
    catch(JauntiumException e){
       System.err.println(e);
    }
  }
}