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


@Service
@AllArgsConstructor
public class AltexCategoryCrawler implements  Runnable{
    private static Logger logger = LoggerFactory.getLogger(AltexCategoryCrawler.class);

    private final ChromeDriver driver;
    @Autowired
    private SearchRepository searchTerms;
    @Autowired
    private SearchResultRepository searchResults;

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
                    logger.error("Element not found!");

                }
            }
            altexBrowser.close();
        }catch(NotFound env){
            logger.error("Ne pare rau dar termenii cautarii nu aduc niciun rezultat! Motiv: " + Arrays.toString(env.getStackTrace()));

        };
    }

     @Override
    public void run() {
        AltcategoryScraper();
    }
}
