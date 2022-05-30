package com.jauntium.examples;
import org.openqa.selenium.chrome.ChromeDriver;
import com.jauntium.Browser;
import com.jauntium.Element;
import com.jauntium.JauntiumException;

public class Example4 {
	
  public static void main(String[] args){

    System.setProperty("webdriver.chrome.driver", "path/to/chromedriver.exe");
    try{
      Browser browser = new Browser(new ChromeDriver());  
      browser.visit("http://intel.com");                       //visit intel.com
   
      Element anchor = browser.doc.findFirst("<a href>");      //find 1st anchor element with href attribute
      System.out.println("anchor element: " + anchor);                         //print anchor element
      System.out.println("anchor's tagname: " + anchor.getTagName());          //print anchor's tagname
      System.out.println("anchor's href attribute: " + anchor.getAt("href"));  //print anchor's href attribute
      System.out.println("anchor's parent Element: " + anchor.getParent());    //print anchor's parent element
                
      Element meta = browser.doc.findFirst("<head>").findFirst("<meta>");      //find 1st meta element in head
      System.out.println("meta element: " + meta);                             //print meta element
      System.out.println("meta's tagname: " + meta.getTagName());              //print meta's tagname
      System.out.println("meta's parent Element: " + meta.getParent());        //print meta's parent element   
    }
    catch(JauntiumException e){             
      System.err.println(e);         
    }
  }
}