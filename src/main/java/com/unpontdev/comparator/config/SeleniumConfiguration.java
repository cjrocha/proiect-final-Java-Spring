package com.unpontdev.comparator.config;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class SeleniumConfiguration{

    @PostConstruct
    void postConstruct(){
        System.setProperty("webdriver.chrome.driver", "D:/Proiect_final/proiect-final/src/main/resources/static/webDriver/chromedriver.exe");
    }

    @Bean
    public ChromeDriver driver(){
        final ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless"); // does not work on altex category search
        return new ChromeDriver();
    }
}
