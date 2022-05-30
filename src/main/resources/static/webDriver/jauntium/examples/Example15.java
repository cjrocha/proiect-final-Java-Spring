package com.jauntium.examples;

import java.io.File;

import org.openqa.selenium.chrome.ChromeDriver;
import com.jauntium.Browser;
import com.jauntium.Document;
import com.jauntium.Element;
import com.jauntium.Elements;
import com.jauntium.Form;
import com.jauntium.JauntiumException;
import com.jauntium.NotFound;

public class Example15 {
  public static void main(String[] args){
    System.setProperty("webdriver.chrome.driver", "path/to/chromedriver.exe");
    try{
      Browser browser = new Browser(new ChromeDriver());
      Document doc = browser.visit("http://jauntium.com/examples/signup2.htm");
	             
      doc.findFirst("<input name=email>").setAttribute("value", "tom@mail.com");
      doc.findFirst("<input name=pw>").setAttribute("value", "abc123");
      doc.findFirst("<input name=remember>").setAttribute("checked", "true");
      doc.findFirst("<option>advanced").setAttribute("selected", "true");
      doc.findFirst("<textarea name=comment").innerHTML("no comment at this time");
      doc.findFirst("<input name=inform value=no>").click();
      doc.findFirst("<input type=submit value='create trial account'>").click();
      System.out.println(browser.getLocation());//print the current location (url)
    }
    catch(JauntiumException e){
      System.out.println(e);           
    }
  }
}