package com.jauntium.examples;

import java.io.File;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.chrome.ChromeDriver;
import com.jauntium.Browser;
import com.jauntium.Element;
import com.jauntium.JauntiumException;
import com.jauntium.Table;

public class Example19 {
  public static void main(String[] args){
    System.setProperty("webdriver.chrome.driver", "path/to/chromedriver.exe");
    try{
      Browser browser = new Browser(new ChromeDriver());
      browser.visit("http://jauntium.com/examples/schedule.htm");
      Element tableElement = browser.doc.findFirst("<table class=schedule>");   //find table Element
      Table table = new Table(tableElement);                   //create table component
    
      System.out.println("\nCell at position 3,3:");
      Element element = table.getCell(3,3);                    //get element at col index 3, row index 3
      System.out.println(element.outerHTML());                 //print element         
      
      System.out.println("\nCell for Fri at 10:00am:"); 
      element = table.getCell("Fri", "10:00am");               //get element at intersection of 'fri' and '10:00am'
      System.out.println(element.outerHTML());                 //print element
    }
    catch(JauntiumException e){
      System.out.println(e);
    }
  }
}