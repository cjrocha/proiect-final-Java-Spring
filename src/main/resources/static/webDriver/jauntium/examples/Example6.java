package com.jauntium.examples;
import java.io.File;

import org.openqa.selenium.chrome.ChromeDriver;
import com.jauntium.Browser;
import com.jauntium.Element;
import com.jauntium.JauntiumException;

public class Example6 {
	
  public static void main(String[] args){
    System.setProperty("webdriver.chrome.driver", "path/to/chromedriver.exe");
    try{
      Browser browser = new Browser(new ChromeDriver());   
      browser.visit("http://northernbushcraft.com");                  //visit website
			
      Element imgElement = browser.doc.findFirst("<img src='.*jpg'>");//find first jpg image
      String url = imgElement.getAt("src");                           //extract image url (src attribute)
      System.out.print("downloading: " + url);                        //print image url
      browser.download(url, new File("result.jpg"));                  //download image
    }
    catch(JauntiumException e){                         
      System.err.println(e);         
    }
  }
}