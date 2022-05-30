package com.jauntium.examples;
import java.io.File;

import org.openqa.selenium.chrome.ChromeDriver;
import com.jauntium.Browser;
import com.jauntium.Element;
import com.jauntium.JauntiumException;

public class Example7 {
	
  public static void main(String[] args){
    System.setProperty("webdriver.chrome.driver", "path/to/chromedriver.exe");
    try{
      Browser browser = new Browser(new ChromeDriver()); 
      browser.visit("http://jauntium.com/examples/links.htm");    
    	   
      Element link_A = browser.doc.findFirst("<a>visit intelligent"); //returns first link
      System.out.println("link_A: " + link_A.outerHTML()); 
    	   
      Element link_B = browser.doc.findFirst("<a>visit intel");       //returns first link
      System.out.println("link_B: " + link_B.outerHTML());   
    	   
      Element link_C = browser.doc.findFirst("<a>^visit intel$");     //returns second link
      System.out.println("link_C: " + link_C.outerHTML()); 
    	 
      link_C.click();                                             //click link
      System.out.println("location: " + browser.getLocation());   //print browser location
    }
    catch(JauntiumException e){                        
      System.err.println(e);        
    }
  }
}