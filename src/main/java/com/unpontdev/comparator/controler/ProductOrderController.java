package com.unpontdev.comparator.controler;

import com.unpontdev.comparator.entities.Product;
import com.unpontdev.comparator.repositories.ProductRepository;
import com.unpontdev.comparator.repositories.SearchRepository;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class ProductOrderController {
    private static Logger logger = LoggerFactory.getLogger(ProductOrderController.class);
    private ChromeDriver driver;

    @Autowired
    private SearchRepository searchTerms;
    @Autowired
    private ProductRepository productRepository;

    @RequestMapping(value = "/searchlists/orderbyname", method = RequestMethod.GET)
    public String showOrderByName(Model model) {
        List<Product> productsOrderByName = productRepository.findAll(Sort.by(Sort.Direction.ASC, "productName"));
        model.addAttribute("productsOrderByName", productsOrderByName);
        return "searchlists/orderbyname";
    }

    @RequestMapping(value = "/searchlists/orderbysku", method = RequestMethod.GET)
    public String showOrderBySku(Model model) {
        List<Product> productsOrderBySku = productRepository.findAll(Sort.by(Sort.Direction.ASC, "productSku"));
        model.addAttribute("productsOrderBySku", productsOrderBySku);
        return "searchlists/orderbysku";
    }

    @RequestMapping(value = "/searchlists/orderbybrand", method = RequestMethod.GET)
    public String showOrderByBrand(Model model) {
        List<Product> productsOrderByBrand = productRepository.findAll(Sort.by(Sort.Direction.DESC, "productBrand"));
        model.addAttribute("productsOrderByBrand", productsOrderByBrand);
        return "searchlists/orderbybrand";
    }

    @RequestMapping(value = "/searchlists/mainlist", method = RequestMethod.GET)
    public String showProductsMainList(Model model) {
        List<Product> productsMainList = productRepository.findAll();
        model.addAttribute("productsMainList", productsMainList);
        return "searchlists/mainlist";
    }
}
