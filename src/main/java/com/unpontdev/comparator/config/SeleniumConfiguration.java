package com.unpontdev.comparator.config;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Configuration for web driver.
 * Driver used is chromedriver, that resides in
 * webDriver folder
 */
@Configuration
public class SeleniumConfiguration{

    @PostConstruct
    void postConstruct(){
        System.setProperty("webdriver.chrome.driver", "D:/Proiect_final/proiect-final/src/main/resources/static/webDriver/chromedriver.exe");
    }

    /**
     * Driver for selenium library
     * using headless option.
     * @return - chromedriver for app
     */
    @Bean
    public ChromeDriver driver(){
        final ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless"); // does not work on altex category search
        return new ChromeDriver();
    }
}
