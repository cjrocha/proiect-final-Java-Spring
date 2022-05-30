package com.unpontdev.comparator.services;

import com.jauntium.Browser;
import com.jauntium.Element;
import com.jauntium.Elements;
import com.jauntium.NotFound;
import com.unpontdev.comparator.entities.SearchResults;
import com.unpontdev.comparator.entities.SearchTerms;
import com.unpontdev.comparator.repositories.SearchRepository;
import com.unpontdev.comparator.repositories.SearchResultRepository;
import lombok.AllArgsConstructor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * External source category scraper
 * based on term provided by user.
 * It's implementing runnable class in order to
 * offer multi-threading capability.
 */
@Service
@AllArgsConstructor
public class AltexCategoryCrawler implements  Runnable{
    private static Logger logger = LoggerFactory.getLogger(AltexCategoryCrawler.class);

    private final ChromeDriver driver;
    @Autowired
    private SearchRepository searchTerms;
    @Autowired
    private SearchResultRepository searchResults;

    /**
     * Category crawler and grabber.
     * Uses chrome driver, selenium and jauntium libraries,
     * to visit the web pages and gather data needed.
     * Based on search term provided by user, builds the search url,
     * visits the page and grabs subcategory urls.
     * Data gathered is being pushed to DB.
     * Catches exceptions and handles them.
     * Logs exceptions and success operations.
     */
    public void AltcategoryScraper()  {
        int j = 1;
        String cName;
        String source = "altex";

        List<SearchTerms> searchIds = searchTerms.findAllByOrderByIdDesc();
        List<SearchTerms> filteredList = searchIds.stream()
                .filter(result -> source.equals(result.getSource())).toList();
        Long workingId = filteredList.get(0).getSearchID();
        String url = searchTerms.findById(workingId).getTermUrl();
        Browser altexBrowser = new Browser(driver);
        altexBrowser.visit(url);
        try {
            Element body = altexBrowser.doc.findFirst("<div id=catalog-filters-container>");
            Elements elements = body.getElement(0).getElement(3).findEach("<a>");
            SearchResults searchFilters = new SearchResults();
            searchFilters.setID(String.valueOf(workingId)+"-0");
            searchFilters.setResultName("Continua fara filtrare");
            searchFilters.setResultUrl(altexBrowser.getLocation());
            searchFilters.setSearchTerm(searchTerms.findById(workingId));
            searchResults.save(searchFilters);
            for (Element element : elements) {
                try{
                String cUrl = element.getAttribute("href");
                cName = element.getElement(1).getText().split("\\(")[0];
                searchFilters.setID(String.valueOf(workingId) +"-"+ j);
                searchFilters.setResultName(cName);
                searchFilters.setResultUrl(cUrl);
                searchFilters.setSearchTerm(searchTerms.findById(workingId));
                searchResults.save(searchFilters);
                j++;
                } catch (Exception e){
                    logger.error("Some elements were not found");
                }
            }
            logger.info("Rezultatul cautarii a fost salvat in DB");
            altexBrowser.close();
        }catch(NotFound env){
            logger.error("We are sorry but the search terms did not return any valid entry! Reason: " + Arrays.toString(env.getStackTrace()));
        }
    }

    /**
     * Override of run method to
     * handle category scraper
     */
    @Override
    public void run() {
        AltcategoryScraper();
    }
}
