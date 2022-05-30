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

import java.util.List;

@Service
@AllArgsConstructor
public class VivreCategoryCrawler implements Runnable{
    private static Logger logger = LoggerFactory.getLogger(EmagCategoryCrawler.class);

    private final ChromeDriver driver;
    @Autowired
    private SearchRepository searchTerms;
    @Autowired
    private SearchResultRepository searchResults;

    public void VrvCategoryScraper()  {
        int j = 1;
        String cName;
        String source = "vivre";

        List<SearchTerms> searchIds = searchTerms.findAllByOrderByIdDesc();
        List<SearchTerms> filteredList = searchIds.stream()
                .filter(result -> source.equals(result.getSource())).toList();
        Long workingId = filteredList.get(0).getSearchID();
        String url = searchTerms.findById(workingId).getTermUrl();
        Browser vivreBrowser = new Browser(driver);
        vivreBrowser.visit(url);
        try {
            Element body = vivreBrowser.doc.findFirst("<div class=filters-body.scroll.scroll-y>").getElement(1).getElement(1);
            Elements elements = body.findEach("<a>");
            SearchResults searchFilters = new SearchResults();
            searchFilters.setID(String.valueOf(workingId)+"-0");
            searchFilters.setResultName("Continua fara filtrare");
            searchFilters.setResultUrl(vivreBrowser.getLocation());
            searchFilters.setSearchTerm(searchTerms.findById(workingId));
            searchResults.save(searchFilters);
            for (Element element : elements) {
                try {
                    String cUrl = element.getAttribute("href");
                    cName = element.getText();
                    searchFilters.setID(String.valueOf(workingId) + "-" + j);
                    searchFilters.setResultName(cName);
                    searchFilters.setResultUrl(cUrl);
                    searchFilters.setSearchTerm(searchTerms.findById(workingId));
                    searchResults.save(searchFilters);
                    j++;
                } catch (Exception e) {
                    logger.error("Element not found!");

                }
            }
            vivreBrowser.close();
        } catch (NotFound e) {
            logger.error("Element not found!");

        }
    }

    @Override
    public void run() {
        VrvCategoryScraper();
    }
}
