package com.jauntium.examples;
import org.openqa.selenium.chrome.ChromeDriver;
import com.jauntium.Browser;
import com.jauntium.Element;
import com.jauntium.Elements;
import com.jauntium.JauntiumException;

public class Example17 {
	
  public static void main(String[] args){
    System.setProperty("webdriver.chrome.driver", "path/to/chromedriver.exe");
    try{
      Browser browser = new Browser(new ChromeDriver());
      browser.visit("http://jauntium.com/examples/stocks.htm");
      
      Element table = browser.doc.findFirst("<table class=stocks>");    //find table element
      Elements tds = table.findEach("<td|th>");                         //find non-nested td/th elements
      for(Element td: tds){                                             //iterate through td/th's
        System.out.println(td.outerHTML());                             //print each td/th element
      }
    }
    catch(JauntiumException e){
      System.out.println(e);
    }    
  }
}