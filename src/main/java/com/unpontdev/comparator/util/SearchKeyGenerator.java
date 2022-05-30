package com.unpontdev.comparator.util;

import org.springframework.stereotype.Component;

/**
 * Component used to generate correctly the
 * key search terms based on user input
 */
@Component
public class SearchKeyGenerator {

    /**
     * Generates the urls to be crawled based on
     * user input.
     * @param mainUrl - url sequence for external source
     * @param keyword - keyword provided by user
     * @return - the full and correct url
     */
    public static String searchGenerator(String mainUrl, String keyword) {
        if (mainUrl.contains("altex") || mainUrl.contains("emag")) {
            if (keyword.contains(" ")) keyword = keyword.replaceAll(" ", "%20");
        }
        if (mainUrl.contains("vivre") || mainUrl.contains("flanco")) {
            if (keyword.contains(" ")) keyword = keyword.replaceAll(" ", "+");
        }
        return mainUrl + keyword;
    }
}
