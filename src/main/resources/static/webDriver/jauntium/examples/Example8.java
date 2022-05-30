package com.jauntium.examples;
import org.openqa.selenium.chrome.ChromeDriver;
import com.jauntium.Browser;
import com.jauntium.Element;
import com.jauntium.Elements;

public class Example8 {	
  public static void main(String[] args){
    System.setProperty("webdriver.chrome.driver", "path/to/chromedriver.exe");
    
    Browser browser = new Browser(new ChromeDriver());
    browser.visit("https://amazon.com");  
      
    Elements tables = browser.doc.findEach("<table>");         //find non-nested tables   
    System.out.println("Found " + tables.size() + " tables:");
    for(Element table : tables){                               //iterate through results
      System.out.println(table.outerHTML() + "\n----\n");      //print each table
    }	                                                         
  }
}