package com.jauntium.examples;
import java.io.File;
import org.openqa.selenium.chrome.ChromeDriver;
import com.jauntium.Browser;
import com.jauntium.Element;
import com.jauntium.Elements;
import com.jauntium.JauntiumException;

public class Example10 {
	
  public static void main(String[] args){
	System.setProperty("webdriver.chrome.driver", "path/to/chromedriver.exe");
	try{ 
	  Browser browser = new Browser(new ChromeDriver());
      browser.visit(("http://jauntium.com/examples/food.htm")); 
	    
	  Element body = browser.doc.findFirst("<body>");                      //find body element
	  Element element = body.getElement(2);                                //retrieve 3rd child element within the body.      
	  System.out.println("result1: " + element);                           //print the element
	     
	  String text = body.getElement(3).getElement(0).getChildText();       //get text of 1st child of 4th child of body
	  System.out.println("result2: " + text);                              //print the text
	     
	  element = body.findFirst("<div class=meat>").getElement(1);          //retrieve 2nd child element of div
	  System.out.println("result3: " + element.outerHTML());               //print the element and its content
	     
	  Elements elements = body.getEach("<div>");                           //get body's child divs
	  System.out.println("result4 has " + elements.size() + " divs:\n");   //print the search results
	  System.out.println(elements.innerHTML(2));                           //print elements, indenting by 2
	}
	catch(JauntiumException e){
	  System.err.println(e);
	}
  }
}