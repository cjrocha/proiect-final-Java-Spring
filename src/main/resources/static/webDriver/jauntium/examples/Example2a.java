package com.jauntium.examples;
import org.openqa.selenium.chrome.ChromeDriver;
import com.jauntium.Browser;
import com.jauntium.JauntiumException;

public class Example2a {
	
  public static void main(String[] args){
    System.setProperty("webdriver.chrome.driver", "path/to/chromedriver.exe");    //setup
    try{
      Browser browser = new Browser(new ChromeDriver());               //create chrome browser
      
      browser.visit("https://heroku.com");                             //visit a url.
      String title = browser.doc.findFirst("<title>").getChildText();  //get child text of title element.
      System.out.println("Heroku's website title: " + title);          //print the title 

      browser.visit("https://reddit.com");                             //visit another url.
      title = browser.doc.findFirst("<title>").getChildText();         //get child text of title element.
      System.out.println("Reddit's website title: " + title);          //print the title  
    }
    catch(JauntiumException e){   //if title element isn't found, handle JauntiumException.
      System.err.println(e);          
    }
  }
}
