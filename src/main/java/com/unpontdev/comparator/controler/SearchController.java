package com.unpontdev.comparator.controler;

import com.unpontdev.comparator.entities.SearchTerms;
import com.unpontdev.comparator.repositories.SearchRepository;
import com.unpontdev.comparator.repositories.SearchResultRepository;
import com.unpontdev.comparator.services.AltexCategoryCrawler;
import com.unpontdev.comparator.services.EmagCategoryCrawler;
import com.unpontdev.comparator.services.FlancoCategoryCrawler;
import com.unpontdev.comparator.services.VivreCategoryCrawler;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import static com.unpontdev.comparator.util.SearchKeyGenerator.searchGenerator;

/**
 * Handles the data received from user and
 * builds threads for each scraper
 */
@Controller
public class SearchController {
    private static Logger logger = LoggerFactory.getLogger(SearchController.class);
    @Autowired
    private SearchRepository searchTerms;
    @Autowired
    private SearchResultRepository searchResults;

    /**
     * Handles the form page for
     * scraping information required;
     * Manages the threads that should run and redirects to
     * 'search' page that contains product data in DB
     * @param model - html handlers
     * @param keyword - the term that is being searched on external sites
     * @param altex - external site name - boolean value
     * @param flanco - external site name - boolean value
     * @param emag - external site name - boolean value
     * @param vivre - external site name - boolean value
     * @return - starts scrapers and redirect to products table page 'search'
     */
    @RequestMapping(value = "/main-comparator", method = RequestMethod.POST)
    public String searchForm(Model model,
                             @RequestParam("keyword") String keyword,
                             @RequestParam("altex") Boolean altex,
                             @RequestParam("flanco") Boolean flanco,
                             @RequestParam("emag") Boolean emag,
                             @RequestParam("vivre") Boolean vivre) {

        model.addAttribute("keyword", keyword);
        model.addAttribute("altex", altex);
        model.addAttribute("flanco", flanco);
        model.addAttribute("emag", emag);
        model.addAttribute("vivre", vivre);

        handlersForThreads(keyword, altex, flanco, emag, vivre);
        return "redirect:search";
    }

    /**
     * handles the start of each scraper thread base on user input
     * @param keyword - the term that is being searched on external sites
     * @param altex - external site name - boolean value
     * @param flanco - external site name - boolean value
     * @param emag - external site name - boolean value
     * @param vivre - external site name - boolean value
     * Logs scraper started and term searched
     */
    public void handlersForThreads(String keyword, Boolean altex, Boolean flanco, Boolean emag, Boolean vivre){
        if(altex == true){
            String mainUrl ="https://altex.ro/cauta/?q=";
            String urlToCrawl = searchGenerator(mainUrl, keyword);
            SearchTerms newSearchTerms = new SearchTerms();
            newSearchTerms.setTerm(keyword);
            newSearchTerms.setTermUrl(urlToCrawl);
            newSearchTerms.setSource("altex");
            searchTerms.save(newSearchTerms);
            AltexCategoryCrawler acc = new AltexCategoryCrawler(new ChromeDriver(), searchTerms, searchResults);
            Thread altexSearchThread = new Thread(acc);
            altexSearchThread.start();
            logger.info("Scraperul a pornit. Vizitam acum siteul altex.ro.\nSuntem in cautarea a: "+keyword);
        }
        if(flanco == true){
            String mainUrl ="https://www.flanco.ro/catalogsearch/result/?q=";
            String urlToCrawl = searchGenerator(mainUrl, keyword);
            SearchTerms newSearchTerms = new SearchTerms();
            newSearchTerms.setTerm(keyword);
            newSearchTerms.setTermUrl(urlToCrawl);
            newSearchTerms.setSource("flanco");
            searchTerms.save(newSearchTerms);
            FlancoCategoryCrawler fcc = new FlancoCategoryCrawler(new ChromeDriver(), searchTerms, searchResults);
            Thread flancoSearchThread = new Thread(fcc);
            flancoSearchThread.start();
            logger.info("Scraperul a pornit. Vizitam acum siteul flanco.ro.\nSuntem in cautarea a: "+keyword);
        }
        if(emag == true){
            String mainUrl ="https://www.emag.ro/search/";
            String urlToCrawl = searchGenerator(mainUrl, keyword);
            SearchTerms newSearchTerms = new SearchTerms();
            newSearchTerms.setTerm(keyword);
            newSearchTerms.setTermUrl(urlToCrawl);
            newSearchTerms.setSource("emag");
            searchTerms.save(newSearchTerms);
            EmagCategoryCrawler ecc = new EmagCategoryCrawler(new ChromeDriver(), searchTerms, searchResults);
            Thread emagSearchThread = new Thread(ecc);
            emagSearchThread.start();
            logger.info("Scraperul a pornit. Vizitam acum siteul emag.ro.\nSuntem in cautarea a: "+keyword);
        }
        if(vivre == true){
            String mainUrl ="https://www.vivre.ro/search?searchItem=";
            String urlToCrawl = searchGenerator(mainUrl, keyword);
            SearchTerms newSearchTerms = new SearchTerms();
            newSearchTerms.setTerm(keyword);
            newSearchTerms.setTermUrl(urlToCrawl);
            newSearchTerms.setSource("vivre");
            searchTerms.save(newSearchTerms);
            VivreCategoryCrawler vcc = new VivreCategoryCrawler(new ChromeDriver(), searchTerms, searchResults);
            Thread vivreSearchThread = new Thread(vcc);
            vivreSearchThread.start();
            logger.info("Scraperul a pornit. Vizitam acum siteul vivre.ro.\nSuntem in cautarea a: "+keyword);
        }
    }
}
