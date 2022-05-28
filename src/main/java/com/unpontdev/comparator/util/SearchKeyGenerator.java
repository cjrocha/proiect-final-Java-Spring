package com.unpontdev.comparator.util;

import org.springframework.stereotype.Component;

@Component
public class SearchKeyGenerator {

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
