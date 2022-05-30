package com.unpontdev.comparator.services;

import com.jauntium.*;
import com.unpontdev.comparator.entities.Product;
import com.unpontdev.comparator.entities.SearchTerms;
import com.unpontdev.comparator.repositories.ProductRepository;
import com.unpontdev.comparator.repositories.SearchRepository;
import lombok.AllArgsConstructor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * External source product scraper
 * based on start url provided by category crawler.
 * It's implementing runnable class in order to
 * offer multi-threading capability.
 */
@Service
@AllArgsConstructor
public class AltexScraper implements  Runnable {
    private static Logger logger = LoggerFactory.getLogger(AltexScraper.class);
    private final ChromeDriver driver;

    @Autowired
    private SearchRepository searchTerms;
    @Autowired
    private ProductRepository productRepository;

    /**
     * Category pages crawler and grabber of the number of listing pages.
     * Crawler of listing pages and grabber of products data.
     * Uses chrome driver, selenium and jauntium libraries,
     * to visit the web pages and gather data needed.
     * Based on search term provided by user, builds the search url,
     * visits the page and grabs all pagination's, then moves to each paginated
     * page and follows products links to access and grab the product data.
     * Data gathered is being pushed to DB.
     * Catches exceptions and handles them.
     * Logs exceptions and success operations.
     */
    public void AltScraper() {
        LocalDateTime now = LocalDateTime.now();
        Long termId = 0L;
        String termUrl = null;
        boolean test = true;
        List<SearchTerms> terms = searchTerms.findAllByOrderByIdDesc();

        //obtain the url to crawl and the term that was searched
        for (SearchTerms term : terms) {
            while (test) {
                if (term.getSource().equals("altex")) {
                    termId = term.getSearchID();
                    termUrl = term.getTermUrl();
                    logger.info("Id-ul de cautare este: " + termId + " si url-ul generat de acest id este: " + termUrl);
                    test = false;
                }
            }
        }

        //visit url and get number of pages
        Browser pagesCatcherBrowser = new Browser(driver);
        pagesCatcherBrowser.visit(termUrl);
        String entries;
        int nentries = 0;
        try {
            entries = pagesCatcherBrowser.doc.findFirst("<div class=text-sm.font-medium.text-center.md:mt-2.py-2>").getText();
            nentries = Integer.parseInt(entries.split(" ")[2]);
        } catch (NotFound e) {
            logger.error(Arrays.toString(e.getStackTrace()) +". Error when getting pages number");
        }
        pagesCatcherBrowser.close();
        int pages = nentries / 24 + 1;
        String url;
        if (pages > 2) {
            url = termUrl + "?page=2";//cause of limited resources
        } else {
            url = termUrl;
        }

        //visit url and put product urls into a list
        List<String> prodDetUrl = new ArrayList<>();
        Browser pagesNewCatcherBrowser = new Browser(new ChromeDriver());
        pagesNewCatcherBrowser.visit(url);
        try {
            Element block = pagesNewCatcherBrowser.doc.findFirst("<div class=lg:w-4/5 ");
            Elements products = block.findEach("<li>");
            for (Element product : products) {
                String pUrl = product.findFirst("<a>").getAttribute("href");
                prodDetUrl.add(pUrl);
            }
        } catch (JauntiumException e) {
            logger.error("We have an exception from scraper library");
        }
        pagesNewCatcherBrowser.close();

        //gather products data
        Product product = new Product();
        for (String pUrl : prodDetUrl) {
            Browser productCatcherBrowser = new Browser(new ChromeDriver());
            productCatcherBrowser.visit(pUrl);
            try {
                String stock = "";
                try {
                    stock = productCatcherBrowser.doc.findFirst("<div class=flex.items-center.text-green.text-13px.leading-tight.-tracking-0.39.mb-6>").getText();
                } catch (NotFound e) {
                    stock = "stock epuizat";
                }
                Element forPrice = productCatcherBrowser.doc.findFirst("<div class=my-2>").getElement(0);
                String oldPrice, price;
                try {
                    price = forPrice.getElement(1).getText().replace(".", "").split(" ")[0].trim();;
                    oldPrice = forPrice.getElement(0).getElement(0).getText().replace(".", "").split(" ")[0].trim();;
                } catch (ArrayIndexOutOfBoundsException e) {
                    price = forPrice.getElement(0).getElement(0).getText().replace(".", "").split(" ")[0].trim();;
                    oldPrice = "0";
                }
                product.setpId(UUID.randomUUID().toString());
                product.setProductName(productCatcherBrowser.doc.findFirst("<div class=mb-1>").getElement(0).getText());
                product.setProductSource("altex");
                product.setProductBrand(productCatcherBrowser.doc.findFirst("<main class=flow-root>").getElement(0).getAttribute("innerHTML").split("Thing\",\"name\":\"")[1].split("\"},\"image")[0]);
                product.setProductUrl(productCatcherBrowser.getLocation());
                product.setProductId(productCatcherBrowser.getLocation().split("/")[5]);
                product.setProductSku(productCatcherBrowser.doc.findFirst("<div class=inline-block.p-1.text-xs.md:text-sm.font-semibold.rounded-sm.bg-gray-300>").getText().replace("Cod produs: ", ""));
                product.setProductMainImage(productCatcherBrowser.doc.findFirst("<div class=swiper-wrapper>").getElement(0).getElement(0).getElement(0).getAttribute("src"));
                product.setProductStock(stock);
                product.setOldPrice(Double.parseDouble(oldPrice.replace(",", ".")));
                product.setPrice(Double.parseDouble(price.replace(",", ".")));
                product.setProductDescription(productCatcherBrowser.doc.findFirst("<li id=description>").getElement(1).getText());
                product.setAddedOn(now);
                product.setTermId(termId);
                productRepository.save(product);
                productCatcherBrowser.close();
            } catch (NotFound nf) {
                logger.error(Arrays.toString(nf.getStackTrace()) + ". One or more elements were not found!");
            }
        }
        logger.info("Scraperul de produse de la Altex a terminat!");
    }

    /**
     * Override of run method to
     * handle product scraper
     */
    @Override
    public void run() {AltScraper();}
}
