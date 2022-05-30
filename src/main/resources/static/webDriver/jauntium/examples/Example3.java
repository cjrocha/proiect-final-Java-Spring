package com.jauntium.examples;
import org.openqa.selenium.chrome.ChromeDriver;
import com.jauntium.Browser;
import com.jauntium.Element;
import com.jauntium.JauntiumException;

public class Example3 {
	
  public static void main(String[] args){
    System.setProperty("webdriver.chrome.driver", "path/to/chromedriver.exe");
    try{
      Browser browser = new Browser(new ChromeDriver());      
      browser.openContent("<html><body>WebPage <div>Hobbies:<p>beer<p>skiing</div> Copyright 2018</body></html>");
      Element body = browser.doc.findFirst("<body>");
      Element div = body.findFirst("<div>");
	  
      System.out.println("body's child text: " + body.getChildText());//joins child text of body element
      System.out.println("-----------");
      System.out.println("body's text: " + body.getTextContent());   //joins all text within body element
      System.out.println("-----------");
      System.out.println("body's visible text: " + body.getText());  //joins all visible text within body element
      System.out.println("-----------");

      System.out.println("div's child text: " + div.getChildText()); //joins child text of div element
      System.out.println("-----------");
      System.out.println("div's text: " + div.getTextContent());     //joins all text within the div element
      System.out.println("-----------");
      System.out.println("div's visible text: " + div.getText());    //joins all visible text within div element
    }
    catch(JauntiumException e){
      System.err.println(e);
    }
  }
}