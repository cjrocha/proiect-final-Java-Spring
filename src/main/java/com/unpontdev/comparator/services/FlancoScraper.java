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
public class FlancoScraper implements  Runnable{
    private static Logger logger = LoggerFactory.getLogger(FlancoScraper.class);
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
     * visits the page and follows all pagination's, then moves to each paginated
     * page and follows products links to access and grab the product data.
     * Data gathered is being pushed to DB.
     * Catches exceptions and handles them.
     * Logs exceptions and success operations.
     */
    public void FlcScraper() {
        LocalDateTime now = LocalDateTime.now();
        Long termId = 0L;
        String termUrl = null;
        boolean test = true;
        List<SearchTerms> terms = searchTerms.findAllByOrderByIdDesc();

        //obtain the url to crawl and the term that was searched
        for(SearchTerms term : terms){
            while(test){
                if(term.getSource().equals("flanco")) {
                    termId = term.getSearchID();
                    termUrl = term.getTermUrl();
                    logger.info("search_id is: "+termId+" The term url is: "+termUrl);
                    test = false;
                }
            }
        }

        //visit url and add product urls to a list using follow pagination
        Browser pagesCatcherBrowser = new Browser(new ChromeDriver());
        pagesCatcherBrowser.visit(termUrl);
        List<String> prodDetUrl = new ArrayList<>();
        boolean check = true;
        try {
            while (check) {
                Elements products = pagesCatcherBrowser.doc.findEach("<div class=product-item-info>");
                for (Element product : products) {
                    String pUrl = product.findFirst("<a class=product-item-link>").getAttribute("href");
                    prodDetUrl.add(pUrl);
                }
                String nextPage = pagesCatcherBrowser.doc.findFirst("<a title=Următorul>").getAttribute("href");
                pagesCatcherBrowser.visit(nextPage);
            }
        }catch(JauntiumException e){
            check = false;
        }
        pagesCatcherBrowser.close();

        //visit each product page and get data required
        Product product = new Product();
        for (String prodUrl : prodDetUrl) {
            Browser detBrowser = new Browser(new ChromeDriver());
            detBrowser.visit(prodUrl);
            try {
                //handle data
                String stock, price, oldPrice;
                try {
                    oldPrice = detBrowser.doc.findFirst("<div class=pricesPrp>").getElement(0).getElement(0).getElement(1).getText();
                    oldPrice = oldPrice.replace(".", "").split(" ")[0].trim();
                    price = detBrowser.doc.findFirst("<div class=pricesPrp>").getElement(1).getElement(0).getText();
                    price = price.replace(".", "").split(" ")[0].trim();
                } catch (Exception e) {
                    price = detBrowser.doc.findFirst("<div class=price-box.price-final_price>").getElement(0).getText();
                    price = price.replace(".", "").split(" ")[0].trim();
                    oldPrice = "0";
                }
                String sstock = detBrowser.doc.findFirst("<div class=product-info-main.right-wrapp>").getElement(0)
                        .getElement(0).getElement(0).getElement(0).getElement(1).getText();
                if (sstock.equals("In stock") || sstock.equals("Stock limitat")) {
                    stock = "0";
                } else {
                    stock = "1";
                }
                String[] brands = detBrowser.doc.findFirst("<body id=html-body>").getElement(5).innerHTML().split(",");
                String brand = brands[4].replace("bc:\"", "").replace("\"\n", "").replace("};", "").trim();
                product.setpId(UUID.randomUUID().toString());
                product.setProductName(detBrowser.doc.findFirst("<h1 class=page-title>").getText());
                product.setProductUrl(detBrowser.getLocation());
                product.setProductId(detBrowser.doc.findFirst("<div class=product-info-stock-sku>").getElement(0).getElement(1).getText());
                product.setProductSku(detBrowser.doc.findFirst("<div class=product-info-stock-sku>").getElement(1).getElement(1).getText());
                product.setPrice(Double.parseDouble(price.replace(",", ".")));
                product.setOldPrice(Double.parseDouble(oldPrice.replace(",", ".")));
                try {
                    product.setProductDescription(detBrowser.doc.findFirst("<div class=contorsy-st>").getText());
                } catch(NotFound nfd){
                    product.setProductDescription(detBrowser.doc.findFirst("<h1 class=page-title>").getText());
                }
                product.setProductStock(stock);
                product.setProductMainImage(detBrowser.doc.findFirst("<figure class=slick-slide.slick-current.slick-active>").getElement(0).getAttribute("href"));
                product.setProductBrand(brand);
                product.setProductSource("flanco");
                product.setTermId(termId);
                product.setAddedOn(now);
                productRepository.save(product);
                detBrowser.close();
            } catch (NotFound nf) {
                logger.error(Arrays.toString(nf.getStackTrace()) + ". One or more elements were not found!");

            } catch (Exception e) {
                logger.error(Arrays.toString(e.getStackTrace()) +"One element was broken!");

            }
        }
        logger.info("Scraperul de produse Flanco a terminat treaba!");
    }

    /**
     * Override of run method to
     * handle product scraper
     */
    @Override
    public void run() {
        FlcScraper();
    }
}
