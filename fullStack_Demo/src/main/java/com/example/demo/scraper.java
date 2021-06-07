package com.example.demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class scraper {
    public scraper () {

    }

    public Map<String, String> getData(String url2) {
        Map<String, String> res = new HashMap<String, String>();
        WebElement Experience;
        WebElement Education;
        try {
            System.setProperty("webdriver.chrome.driver", "/Users/amanpatel/Desktop/AmanStudy/Project/fullStack_Demo/src/main/java/com/example/demo/chromedriver");
            WebDriver driver = new ChromeDriver();
            driver.get(url2);
            Pattern p = Pattern.compile("https://www\\.linkedin\\.com/authwall*");
            Matcher m = p.matcher(driver.getCurrentUrl());
            if(m.lookingAt()) {
                driver.manage().timeouts().implicitlyWait(300, TimeUnit.MILLISECONDS);
                WebElement button = driver.findElement(By.xpath("/html/body/main/div/div/form[2]/section/p/button"));
                button.click();
                driver.manage().timeouts().implicitlyWait(50, TimeUnit.MILLISECONDS);
                WebElement email = driver.findElement(By.xpath("//*[@id=\"login-email\"]"));
                WebElement password = driver.findElement(By.xpath("//*[@id=\"login-password\"]"));
                email.sendKeys("amanpatelamway@gmail.com");
                password.sendKeys("Rock@1234");
                WebElement signin = driver.findElement(By.xpath("//*[@id=\"login-submit\"]"));
                signin.click();
            }
            driver.manage().timeouts().implicitlyWait(1200, TimeUnit.MILLISECONDS);

            try{
                driver.manage().timeouts().implicitlyWait(400, TimeUnit.MILLISECONDS);
                Experience = driver.findElement(By.xpath("//*[@id=\"experience-section\"]"));
                res.put("Experience", Experience.getText());
            } catch (Exception e) {
                try {
                    Experience = driver.findElement(By.xpath("//*[@id=\"main-content\"]/section[1]/section/section[4]"));
                    res.put("Experience", Experience.getText());
                } catch (Exception error) {
                    if(!res.containsKey("Experience")) {
                        res.put("Experience", "User Has No Experience");
                    }
                }
            }

            try {
                driver.manage().timeouts().implicitlyWait(200, TimeUnit.MILLISECONDS);
                Education = driver.findElement(By.xpath("//*[@id=\"education-section\"]"));
                res.put("Education", Education.getText());
            } catch (Exception e) {
                try {
                    Education = driver.findElement(By.xpath("//*[@id=\"main-content\"]/section[1]/section/section[5]"));
                    res.put("Education", Education.getText());
                } catch (Exception error) {
                    if(!res.containsKey("Education")) {
                        res.put("Education", "User Has No Education");
                    }
                }
            }
            driver.close();
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            res.put("error", "Error While Scraping Web");
            return res;
        }
    }
}
