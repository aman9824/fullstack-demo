package com.example.demo;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping(value = "/api")
public class controller {

    @PostMapping(value = "/link",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> profiles (@RequestBody Map<String, String> url){
        Map<String, String> res = new HashMap<String, String>();
        model mo = new model();
        try{
            System.out.print(url.values());
            String url2 = url.get("url");
            urlValidator u = new urlValidator(url2);
            if(u.validate(url2)) {
                /*WebClient client = new WebClient();
                client.getOptions().setJavaScriptEnabled(false);
                client.getOptions().setCssEnabled(false);
                client.getOptions().setUseInsecureSSL(true);*/
                try {
                    /*HtmlPage page = client.getPage(url);
                    Connection con = Jsoup.connect(url);
                    Document doc = con.get();
                    if(con.response().statusCode() == 200) {
                        res.put("Server Connected", "I fucking did it");
                    }
                    res.put("Connection", "Failed");*/
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
                        mo.Experience = driver.findElement(By.xpath("//*[@id=\"experience-section\"]"));
                        res.put("Experience", mo.Experience.getText());
                    } catch (Exception e) {
                        try {
                            mo.Experience = driver.findElement(By.xpath("//*[@id=\"main-content\"]/section[1]/section/section[4]"));
                            res.put("Experience", mo.Experience.getText());
                        } catch (Exception error) {
                            if(!res.containsKey("Experience")) {
                                res.put("Experience", "User Has No Experience");
                            }
                        }
                    }

                    try {
                        mo.Education = driver.findElement(By.xpath("//*[@id=\"education-section\"]"));
                        res.put("Education", mo.Education.getText());
                    } catch (Exception e) {
                        try {
                            mo.Education = driver.findElement(By.xpath("//*[@id=\"main-content\"]/section[1]/section/section[5]"));
                            res.put("Education", mo.Education.getText());
                        } catch (Exception error) {
                            if(!res.containsKey("Education")) {
                                res.put("Education", "User Has No Education");
                            }
                        }
                    }

                    try {
                        mo.Name = driver.findElement(By.xpath("//*[@id=\"ember41\"]/div[2]/div[2]/div/div[1]/h1"));
                        res.put("Name", mo.Name.getText());
                    } catch (Exception e) {
                        res.put("Name", "Error");
                    }

                    driver.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    res.put("error", "Error While Scraping Web");
                    return res;
                }
            } else {
                res.put("error", u.getError());
                return res;
            }
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            if(!res.containsKey("error") && (!res.containsKey("Education") || !res.containsKey("Experience"))) {
                res.put("error", "Unusual Error Occurred");
            }
            return res;
        }
    }
}
