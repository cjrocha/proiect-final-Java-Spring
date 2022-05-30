package com.jauntium.examples;
import org.openqa.selenium.chrome.ChromeDriver;
import com.jauntium.Browser;
import com.jauntium.Form;
import com.jauntium.JauntiumException;

public class Example14 {
  public static void main(String[] args){
    System.setProperty("webdriver.chrome.driver", "path/to/chromedriver.exe");
    try{
      Browser browser = new Browser(new ChromeDriver());
      browser.visit("http://jauntium.com/examples/signup.htm");
			             
      Form form = browser.doc.getForm(0);  
      form.filloutField("E-mail:", "tom@mail.com");    //fill out the textfield labelled "E-mail:"
      form.chooseMenuItem("Account Type:", "advanced"); //choose "advanced" from the menu labelled "Account Type:"
      form.filloutField("Comments:", "no comment");    //fill out the textarea labelled "Comments:"
      form.chooseRadioButton("No thanks");              //choose the radiobutton labelled "No thanks"
      form.submit("create trial account");              //click the submit button labelled 'create trial account'
      System.out.println(browser.getLocation());        //print the current location (url)
    }
    catch(JauntiumException e){
      System.out.println(e);			
    }
  }
}