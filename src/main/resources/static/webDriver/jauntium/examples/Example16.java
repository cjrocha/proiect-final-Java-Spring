package com.jauntium.examples;
import java.io.File;
import java.util.List;

import org.openqa.selenium.chrome.ChromeDriver;
import com.jauntium.Browser;
import com.jauntium.JauntiumException;
import com.jauntium.Node;

public class Example16 {
  public static void main(String[] args){
	System.setProperty("webdriver.chrome.driver", "path/to/chromedriver.exe");
	try{
	  Browser browser = new Browser(new ChromeDriver());
	  browser.visit("http://jauntium.com/examples/goodbye.htm"); 
	  
	  Node bodyNode = browser.doc.findFirst("<body>").toNode();   //get body node
	  List<Node> childNodes = bodyNode.getChildNodes();     //get list of child nodes
      for(Node node : childNodes){							//print each child node
    	  System.out.println("NODE:");
    	  System.out.println(node.toString());
    	  System.out.println("---");
      }
	}
	catch(JauntiumException e){
	  System.out.println(e);
	}
  }
}