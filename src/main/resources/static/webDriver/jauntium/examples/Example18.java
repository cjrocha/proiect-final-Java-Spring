package com.jauntium.examples;

import java.io.File;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.chrome.ChromeDriver;
import com.jauntium.Browser;
import com.jauntium.Element;
import com.jauntium.JauntiumException;
import com.jauntium.Table;

public class Example18 {
  public static void main(String[] args){
    System.setProperty("webdriver.chrome.driver", "path/to/chromedriver.exe");
    try{
      Browser browser = new Browser(new ChromeDriver());
      browser.visit("http://jauntium.com/examples/schedule.htm");
      Element tableElement = browser.doc.findFirst("<table class=schedule>");   //find table Element
      Table table = new Table(tableElement);                   //create table component
    
      System.out.println("\nText of first column:");                            
      List<String> results = table.getTextFromColumn(0);       //get text from first column
      for(String text : results) System.out.println(text);     //iterate through results & print     
      
      System.out.println("\nText of column containing 'Mon':");
      results = table.getTextFromColumn("Mon");                //get text from column containing 'Mon'
      for(String text : results) System.out.println(text);     //iterate through results & print 
      		   
      System.out.println("\nText of first row:");
      results = table.getTextFromRow(0);                       //get text from first row
      for(String text : results) System.out.println(text);     //iterate through results & print 
      
      System.out.println("\nText of row containing '2:00pm':");
      results = table.getTextFromRow("2:00pm");                //get text from row containing '2:00pm'
      for(String text : results) System.out.println(text);     //iterate through results & print
      
      System.out.println("\nCreate Map of text from first two columns:");  
      Map<String, String> map = table.getTextFromColumns(0, 1); //create map containing text from cols 0 and 1
      for(String key : map.keySet()){                           //print keys (from col 0) and values (from col 1) 
        System.out.println(key + ":" + map.get(key));           
      }
    }
    catch(JauntiumException e){
      System.out.println(e);
    }
  }
}