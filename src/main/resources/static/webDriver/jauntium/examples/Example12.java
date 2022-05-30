package com.jauntium.examples;
import org.openqa.selenium.chrome.ChromeDriver;
import com.jauntium.Browser;
import com.jauntium.JauntiumException;

public class Example12 {
	
  public static void main(String[] args){
    System.setProperty("webdriver.chrome.driver", "path/to/chromedriver.exe"); 
    try{
      Browser browser = new Browser(new ChromeDriver());
      browser.visit("http://jauntium.com/examples/signup.htm");
		   
      browser.doc.apply(     //fill-out the form by applying a sequence of inputs
        "tom@mail.com",      //apply input to textfield 
        "advanced",          //select a menu item by label
        "no comment",        //apply input to textarea
        "no thanks"          //select radiobutton by label 
      ).submit("create trial account");           //click submit button specified by label
      System.out.println(browser.getLocation());  //print the current location (url)	
    }
    catch(JauntiumException e){
      System.out.println(e);			
    }
  }
}