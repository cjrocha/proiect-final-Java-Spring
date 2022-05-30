package com.jauntium.examples;
import org.openqa.selenium.chrome.ChromeDriver;
import com.jauntium.Browser;
import com.jauntium.Form;
import com.jauntium.JauntiumException;

public class Example13 {
	
  public static void main(String[] args){
    System.setProperty("webdriver.chrome.driver", "path/to/chromedriver.exe");
    try{
      Browser browser = new Browser(new ChromeDriver());
      browser.visit("http://jauntium.com/examples/login.htm");
      Form form = browser.doc.getForm(0);
      
      form.filloutField("Username:", "tom");       //fill out the field labelled 'Username:' with "tom"
      form.filloutField("Password:", "secret");    //fill out the field labelled 'Password:' with "secret"
      form.chooseCheckBox("Remember me");           //choose the checkbox right-labelled 'Remember me'.
      form.submit();                                //submit the form
      System.out.println(browser.getLocation());    //print the current location (url)
    }
    catch(JauntiumException e){
      System.out.println(e);			
    }
  }
}