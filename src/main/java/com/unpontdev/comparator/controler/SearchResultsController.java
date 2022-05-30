package com.unpontdev.comparator.controler;

import com.unpontdev.comparator.entities.Product;
import com.unpontdev.comparator.repositories.ProductRepository;
import com.unpontdev.comparator.services.CsvExportService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Takes care of building product data
 * list, based on a search keyword
 */
@Controller
@AllArgsConstructor
public class SearchResultsController {
    private static Logger logger = LoggerFactory.getLogger(SearchResultsController.class);

    @Autowired
    private CsvExportService csvExportService;
    @Autowired
    private ProductRepository productRepository;

     /**
     * Search by keyword page
     * @param model  - html handler
     * @param keyword - term to search for
     * @return - search-results page
     */
    @RequestMapping(value = "/search-results", method = RequestMethod.GET)
    public String showOrderByName(Model model, @RequestParam("search-keyword") String keyword) {
        List<Product> productsSearched = productRepository.searchProducts(keyword);
        model.addAttribute("productsSearched", productsSearched);
        model.addAttribute("searchkey", keyword);
        return "search-results";
    }


    /**
     * Csv exporter
     * @param servletResponse - the type of response
     * @param keyword - term that is searched
     * @param model - html handler
     */
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void getProductInCsv(HttpServletResponse servletResponse, @RequestParam("searchkey") String keyword, Model model) {
        servletResponse.setContentType("text/csv");
        servletResponse.addHeader("Content-Disposition","attachment; filename=\"searched_products.csv\"");
        model.addAttribute("keyword", keyword);
        try {
            csvExportService.writeProductsToCsv(servletResponse.getWriter(), keyword);
        } catch (IOException e) {
            logger.error("Something is wrong with the export! See: "+ Arrays.toString(e.getStackTrace()));
        }
    }
}

