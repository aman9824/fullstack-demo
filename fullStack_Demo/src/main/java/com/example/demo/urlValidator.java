package com.example.demo;
import java.util.regex.*;


public class urlValidator {
    public String url;

    public urlValidator () {
    }

    public urlValidator (String url) {
        this.url = url;
    }

    public boolean validate (String url) {
        Pattern regExp = Pattern.compile("https://www\\.linkedin\\.com/in/*");
        Matcher m = regExp.matcher(url);
        return m.lookingAt();
    }

    public String getError () {
        return "URL IS INVALID, PLEASE ENTER VALID URL AGAIN !!";
    }
}
