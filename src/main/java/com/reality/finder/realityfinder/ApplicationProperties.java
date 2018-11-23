package com.reality.finder.realityfinder;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class ApplicationProperties {

    @Value("${base.url}")
    private String baseUrl;

    @Value("${page.url}")
    private String pageUrl;

    @Value("${file.prefix}")
    private String filePrefix;

    @Value("${contact.selector}")
    private String contactSelector;

    @Value("${links.selector}")
    private String linksSelector;

    @Value("${filter.word.1}")
    private String filterWord1;

    @Value("${filter.word.2}")
    private String filterWord2;
}