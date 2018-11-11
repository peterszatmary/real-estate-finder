package com.reality.finder.realityfinder;

import com.google.common.io.Files;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class RealEstateService implements RealEstateServiceApi {

    private final ApplicationProperties appProps;
    private final WebDriver webDriver;

    public List<String> search(final Integer filePostfix, final Integer from, final Integer to) {

        final List<String> links = getAllLinks(from, to);
        final List<String> result = new ArrayList<>();

        for (String link : links) {
            String p = getPageContent(link);
            String pp = justRelevantContent(p);
            if (isOK(pp)) {
                result.add(link);
            }
        }

        log.info("Links that looks promising = " + result.size());
        createHtmlPage(result, targetFileName(filePostfix), from, to);
        return result;
    }

    private List<String> getLinks(final Integer pageNumber) {
        final String pageContent = getPageContent(pageUrlWithLinks(pageNumber));
        final Document doc = Jsoup.parse(pageContent);
        final Elements els = doc.select(appProps.getLinksSelector());
        return els.stream().map(x -> x.attr("href")).collect(Collectors.toList());
    }

    private String pageUrlWithLinks(final Integer pageNumber) {
        return appProps.getBaseUrl() + appProps.getPageUrl() + pageNumber;
    }

    private String targetFileName(final Integer filePostfix) {
        return appProps.getFilePrefix() + filePostfix + ".html";
    }

    public List<String> getAllLinks(final Integer from, final Integer to) {

        final List<String> links = new ArrayList<>();

        for (int i = from; i <= to; i++) {
            List<String> lls = getLinks(i);

            if (lls.isEmpty()) {
                break;
            }
            links.addAll(lls);
        }

        log.info("Pages from = " + from + ", to = " + to);
        log.info("Real Estates count = " + links.size());
        return links;
    }

    private void createHtmlPage(final List<String> links, final String fileName, final Integer from, final Integer to) {

        final StringBuilder content = new StringBuilder()
                .append("<html>\n")
                .append("<body>\n")
                .append("<h1>Real Estates (")
                .append(new Date())
                .append(")\n")
                .append("</h1>\n")
                .append("<h3>Pages from - to :")
                .append(from)
                .append(" - ")
                .append(to)
                .append("</h3>\n")
                .append("<ul>\n");

        for (String link : links) {
            content
                    .append("<li>\n<a href=\"")
                    .append(link)
                    .append("\" target=\"_blank\">\n")
                    .append(link)
                    .append("</a>\n</li>\n");
        }

        content.append("</ul>\n");
        content.append("</body>\n").append("</html>\n");

        try {
            Files.write(content.toString().getBytes(StandardCharsets.UTF_8), new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private boolean isOK(String content) {
        final String lowerContent = content.toLowerCase();
        return !lowerContent.contains(appProps.getFilterWord2()) && !lowerContent.contains(appProps.getFilterWord1());
    }

    private String justRelevantContent(final String rawContent) {

        final Document doc = Jsoup.parse(rawContent);
        final Elements contact = doc.select(appProps.getContactSelector());
        final String wholeContactText = contact.text();

        return StringUtils.substringBefore(wholeContactText, "zobraziť číslo");
    }

    private String getPageContent(final String url) {
        webDriver.get(url);

        final String pageSource = webDriver.getPageSource();

        if (pageSource.length() <= 0) {
            log.error("Elements could not be parsed from " + url + " because page source is empty.");
        } else {
            log.info("Going to parse from " + url + ", pageSize = " + pageSource.length());
        }
        return pageSource;
    }
}