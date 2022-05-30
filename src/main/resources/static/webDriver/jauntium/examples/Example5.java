package com.jauntium.examples;
import java.io.File;
import org.openqa.selenium.chrome.ChromeDriver;

import com.jauntium.Browser;
import com.jauntium.Element;
import com.jauntium.JauntiumException;

public class Example5 {
	
  public static void main(String[] args){
    System.setProperty("webdriver.chrome.driver", "path/to/chromedriver.exe");
    try{
      Browser browser = new Browser(new ChromeDriver());  
      browser.open(new File("path/to/colors.htm"));  //open local file (edit this path)
    	    
      Element div = browser.doc.findFirst("<div class=colors>");      //find div who's class matches 'colors' 
      System.out.println("div's outerHTML():\n" + div.outerHTML());   //no extra indenting
      System.out.println("-------------");
      System.out.println("div's outerHTML(2):\n" + div.outerHTML(2)); //two extra spaces used per indent
      System.out.println("-------------");            
      System.out.println("div's innerHTML():\n" + div.innerHTML());   //no extra indenting
      System.out.println("-------------");
      System.out.println("div's innerHTML(3):\n" + div.innerHTML(3)); //three extra spaces used per indent     
      System.out.println("-------------");
  
      //make some changes
      div.innerHTML("<h1>Presto!</h1>");       //replace div's content with different elements.
      System.out.println("Altered document:\n" + browser.doc.innerHTML());  //print the altered document.
    }
    catch(JauntiumException e){
      System.err.println(e);
    }
  }
}