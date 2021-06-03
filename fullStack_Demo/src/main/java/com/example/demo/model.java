package com.example.demo;

import org.openqa.selenium.WebElement;


public class model {
    public String img_url;
    public WebElement Name;
    public WebElement Experience;
    public WebElement Education;

    public model () {

    }

    public model (String img_url, WebElement Name, WebElement Experience, WebElement Education) {
        this.img_url = img_url;
        this.Name = Name;
        this.Experience = Experience;
        this.Education = Education;
    }

    public String getImg_url () {
        return this.img_url;
    }

    public WebElement getName () {
        return this.Name;
    }

    public WebElement getExperience () {
        return this.Experience;
    }

    public WebElement getEducation () {
        return this.Education;
    }
}
