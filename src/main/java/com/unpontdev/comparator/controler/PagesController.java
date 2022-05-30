package com.unpontdev.comparator.controler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * taking care of app pages
 */
@Controller
public class PagesController {

    /**
     * Shows about page
     * @return - despre page
     */
    @GetMapping("/despre")
    public String showAboutPage(){
        return "despre";
    }

    /**
     * Shows terms page
     * @return - termeni page
     */
    @GetMapping("/termeni")
    public String showTermsPage(){
        return "termeni";
    }

    /**
     * Shows search/comparator page
     * @return - main-comparator page
     */
    @RequestMapping("/main-comparator")
    public String showComparatorMainPage(){
        return "main-comparator";
    }

    /**
     * Shows search results(table) page
     * @return - search page
     */
    @RequestMapping("/search")
    public String showSearchPage(){
        return "search";
    }

    /**
     * Shows product data sorted by name of product page
     * @return - orderbyname page
     */
    @RequestMapping("/searchlists/orderbyname")
    public String showSearchOrderByNamePage(){
        return "searchlists/orderbyname";
    }

    /**
     * Shows product data sorted by product sku page
     * @return - orderbysku page
     */
    @RequestMapping("/searchlists/orderbysku")
    public String showSearchOrderBySkuPage(){
        return "searchlists/orderbysku";
    }

    /**
     * Shows product data sorted by product brand page
     * @return - orderbybrand page
     */
    @RequestMapping("/searchlists/orderbybrand")
    public String showSearchOrderByBrandPage(){
        return "searchlists/orderbybrand";
    }

    /**
     * Shows product data sorted by product id page
     * @return - mainlist page
     */
    @RequestMapping("/searchlists/mainlist")
    public String showMainProductsListPage(){
        return "searchlists/mainlist";
    }

}
