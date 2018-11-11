package com.reality.finder.realityfinder;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Beans {

    @Bean
    public WebDriver webDriver() {
        return new PhantomJSDriver();
    }

}
