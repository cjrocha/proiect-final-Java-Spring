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

@Service
@AllArgsConstructor
public class VivreScraper implements Runnable{
    private static Logger logger = LoggerFactory.getLogger(EmagScraper.class);
    private final ChromeDriver driver;

    @Autowired
    private SearchRepository searchTerms;
    @Autowired
    private ProductRepository productRepository;

    public void VrvScraper() {
        LocalDateTime now = LocalDateTime.now();
        Long vTermId = 0L;
        String vTermUrl = null;
        boolean test = true;
        List<SearchTerms> terms = searchTerms.findAllByOrderByIdDesc();
        //obtain the url to crawl and the term that was searched
        for (SearchTerms term : terms) {
            while (test) {
                if (term.getSource().equals("vivre")) {
                    vTermId = term.getSearchID();
                    vTermUrl = term.getTermUrl();
                    logger.info("search_id is: " + vTermId + " The term url is: " + vTermUrl);
                    test = false;
                }
            }
        }
        //visit url and add product urls to a list using follow pagination
        Browser newBrowser = new Browser(new ChromeDriver());
        newBrowser.visit(vTermUrl);
        List<String> prodDetUrl = new ArrayList<>();
        boolean check = true;
        try {
            while (check) {
                Elements products = newBrowser.doc.findEach("<div class=item-product>");
                for (Element product : products) {
                    String pUrl = product.findFirst("<h2 class=title>").getElement(0).getAttribute("href");
                    prodDetUrl.add(pUrl);
                }
                String nextPage = newBrowser.doc.findFirst("<a aria-label=Go.to.next.page>").getAttribute("href");
                newBrowser.visit(nextPage);
            }
        } catch (JauntiumException e) {
            check = false;
        }
        newBrowser.close();
        //visit each product page and get data required
        Product product = new Product();

        for (String prodUrl : prodDetUrl) {
            Browser detBrowser = new Browser(new ChromeDriver());
            detBrowser.visit(prodUrl);
            try {
                String price, oldPrice;
                try {
                    oldPrice = detBrowser.doc.findFirst("<span class=price.price-ref>").getElement(0).getText().replace("\n", ".");
                    price = detBrowser.doc.findFirst("<span class=price.price-product>").getElement(0).getText().replace("\n", ".");
                } catch (NotFound e) {
                    price = detBrowser.doc.findFirst("<span class=price.price-product>").getElement(0).getText().replace("\n", ".");
                    oldPrice = "0";
                } catch(ArrayIndexOutOfBoundsException ar){
                    oldPrice = "0";
                    price = price = detBrowser.doc.findFirst("<span class=price.price-product>").getElement(0).getText().replace("\n", ".");
                }
                product.setpId(UUID.randomUUID().toString());
                product.setProductStock("1");
                product.setProductName(detBrowser.doc.findFirst("<h1>").getText());
                product.setProductUrl(detBrowser.getLocation());
                product.setProductId(detBrowser.getLocation().replace("/", ",").split(",")[3].replace("p-", ""));
                product.setProductSource("vivre");
                product.setProductSku(detBrowser.doc.findFirst("<span class=sku-label.mb-3>").getText().replace("SKU: ", ""));
                product.setPrice(Double.parseDouble(price.replace(",", ".")));
                product.setOldPrice(Double.parseDouble(oldPrice.replace(",", ".")));
                try {
                    product.setProductDescription(detBrowser.doc.findFirst("<div class=product-sections>").getElement(0).getElement(1).getText());
                } catch (ArrayIndexOutOfBoundsException ar){
                    product.setProductDescription(detBrowser.doc.findFirst("<div class=product-sections>").getElement(5).getElement(1).getText());
                }
                product.setProductMainImage(detBrowser.doc.findFirst("<img loading=eager>").getAttribute("src"));
                product.setProductBrand(detBrowser.doc.findFirst("<li class=list-inline-item>").getElement(1).getText());
                product.setAddedOn(now);
                product.setTermId(vTermId);
                productRepository.save(product);
                detBrowser.close();
            } catch (NotFound nf){

                logger.error(Arrays.toString(nf.getStackTrace()) +". Some elemnts were not found.");
            }
        }
        logger.info("Vivre scraper has ended");
    }

    @Override
    public void run() {
        VrvScraper();
    }
}
