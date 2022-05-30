package com.unpontdev.comparator.controler;

import com.unpontdev.comparator.entities.Product;
import com.unpontdev.comparator.entities.SearchTerms;
import com.unpontdev.comparator.repositories.ProductRepository;
import com.unpontdev.comparator.repositories.SearchRepository;
import com.unpontdev.comparator.services.AltexScraper;
import com.unpontdev.comparator.services.EmagScraper;
import com.unpontdev.comparator.services.FlancoScraper;
import com.unpontdev.comparator.services.VivreScraper;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class ProductsController {
    private static Logger logger = LoggerFactory.getLogger(ProductsController.class);
    private ChromeDriver driver;

    @Autowired
    private SearchRepository searchTerms;
    @Autowired
    private ProductRepository productRepository;

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String showSearch(Model model) {
        List<SearchTerms> terms = searchTerms.findAllByOrderByIdDesc();
        model.addAttribute("keyword", terms.get(0).getTerm());
        logger.info("Search term is: "+terms.get(0).getTerm());
        boolean a = false,f = false,e = false,v = false;
        for(int i=0; i<4; i++){
            if (terms.get(i).getSource().equals("altex")) a = true;
            if (terms.get(i).getSource().equals("flanco")) f = true;
            if (terms.get(i).getSource().equals("emag")) e = true;
            if (terms.get(i).getSource().equals("vivre")) v = true;
        }
        if (f) {
            FlancoScraper flancoScraper = new FlancoScraper(new ChromeDriver(), searchTerms, productRepository);
            Thread fc = new Thread(flancoScraper);
            fc.start();
        }
        if (a) {
            AltexScraper altexScraper = new AltexScraper(new ChromeDriver(), searchTerms, productRepository);
            Thread ac = new Thread(altexScraper);
            ac.start();
        }
        if (e) {
            EmagScraper emagScraper = new EmagScraper(new ChromeDriver(), searchTerms, productRepository);
            Thread ec = new Thread(emagScraper);
            ec.start();
        }
        if (v) {
            VivreScraper vivreScraper = new VivreScraper(new ChromeDriver(), searchTerms, productRepository);
            Thread vc = new Thread(vivreScraper);
            vc.start();
        }
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        //List<Product> productsOrderByBrand = productRepository.findAll(Sort.by(Sort.Direction.DESC, "productBrand"));
        //List<Product> productsOrderBySku = productRepository.findAll(Sort.by(Sort.Direction.ASC, "productSku"));
        //List<Product> productsOrderByName = productRepository.findAll(Sort.by(Sort.Direction.ASC, "productName"));

        //model.addAttribute("productsOrderByBrand", productsOrderByBrand);
        //model.addAttribute("productsOrderBySku", productsOrderBySku);
        //model.addAttribute("productsOrderByName", productsOrderByName);
//        for(Product product : products){
//            model.addAttribute("productName", product.getProductName());
//            model.addAttribute("mainImage", product.getProductMainImage());
//            model.addAttribute("productSKU", product.getProductSku());
//            model.addAttribute("prodUrl", product.getProductUrl());
//            model.addAttribute("productSource", product.getProductSource());
//            model.addAttribute("productDate", product.getAddedOn());
//            model.addAttribute("productOldPrice", product.getOldPrice());
//            model.addAttribute("productPrice", product.getPrice());
//            model.addAttribute("productBrand", product.getProductBrand());
//            model.addAttribute("productStock", product.getProductStock());
//        }
        return "search";
    }
}
