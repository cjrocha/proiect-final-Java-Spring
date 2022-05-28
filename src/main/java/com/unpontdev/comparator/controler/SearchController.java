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

@Controller
public class SearchController {
    private static Logger logger = LoggerFactory.getLogger(SearchController.class);
    @Autowired
    private SearchRepository searchTerms;
    @Autowired
    private SearchResultRepository searchResults;

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
        if(altex == true){
            String mainUrl ="https://altex.ro/cauta/?q=";
            String urlToCrawl = searchGenerator(mainUrl, keyword);
            SearchTerms newsearchTerms = new SearchTerms();
            newsearchTerms.setTerm(keyword);
            newsearchTerms.setTermUrl(urlToCrawl);
            newsearchTerms.setSource("altex");
            searchTerms.save(newsearchTerms);
            AltexCategoryCrawler acc = new AltexCategoryCrawler(new ChromeDriver(), searchTerms, searchResults);
            Thread altexSearchThread = new Thread(acc);
            altexSearchThread.start();
            logger.info("termenii de cautare pe site-ul altex.ro au fost salvati in DB");
        }
        if(flanco == true){
            String mainUrl ="https://www.flanco.ro/catalogsearch/result/?q=";
            String urlToCrawl = searchGenerator(mainUrl, keyword);
            SearchTerms newsearchTerms = new SearchTerms();
            newsearchTerms.setTerm(keyword);
            newsearchTerms.setTermUrl(urlToCrawl);
            newsearchTerms.setSource("flanco");
            searchTerms.save(newsearchTerms);
            FlancoCategoryCrawler fcc = new FlancoCategoryCrawler(new ChromeDriver(), searchTerms, searchResults);
            Thread flancoSearchThread = new Thread(fcc);
            flancoSearchThread.start();
            logger.info("termenii de cautare pe site-ul flanco.ro au fost salvati in DB");
        }
        if(emag == true){
            String mainUrl ="https://www.emag.ro/search/";
            String urlToCrawl = searchGenerator(mainUrl, keyword);
            SearchTerms newsearchTerms = new SearchTerms();
            newsearchTerms.setTerm(keyword);
            newsearchTerms.setTermUrl(urlToCrawl);
            newsearchTerms.setSource("emag");
            searchTerms.save(newsearchTerms);
            EmagCategoryCrawler ecc = new EmagCategoryCrawler(new ChromeDriver(), searchTerms, searchResults);
            Thread emagSearchThread = new Thread(ecc);
            emagSearchThread.start();
            logger.info("termenii de cautare pe site-ul emag.ro au fost salvati in DB");
        }
        if(vivre == true){
            String mainUrl ="https://www.vivre.ro/search?searchItem=";
            String urlToCrawl = searchGenerator(mainUrl, keyword);
            SearchTerms newsearchTerms = new SearchTerms();
            newsearchTerms.setTerm(keyword);
            newsearchTerms.setTermUrl(urlToCrawl);
            newsearchTerms.setSource("vivre");
            searchTerms.save(newsearchTerms);
            VivreCategoryCrawler vcc = new VivreCategoryCrawler(new ChromeDriver(), searchTerms, searchResults);
            Thread vivreSearchThread = new Thread(vcc);
            vivreSearchThread.start();
            logger.info("termenii de cautare pe site-ul vivre.ro au fost salvati in DB");
        }
        return "redirect:search";
    }
}
