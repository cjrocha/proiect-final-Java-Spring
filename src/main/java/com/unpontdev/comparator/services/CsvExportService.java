package com.unpontdev.comparator.services;

import com.unpontdev.comparator.entities.Product;
import com.unpontdev.comparator.repositories.ProductRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * Handles the export of the data to csv format
 */
@Service
public class CsvExportService {
    private static Logger logger = LoggerFactory.getLogger(CsvExportService.class);
    private final ProductRepository productRepository;

    /**
     * Constructoer for export service
     * @param productRepository - the products data
     */
    public CsvExportService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * method to export searched data to csv
     * @param writer
     */
    public void writeProductsToCsv(Writer writer, String keyword) {
        List<Product> products = productRepository.searchProducts(keyword);
        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            for (Product product : products) {
                csvPrinter.printRecord(product.getProductId(), product.getProductImages(), product.getProductName(),
                        product.getProductSource(), product.getProductSku(), product.getOldPrice(), product.getPrice(),
                        product.getProductStock(), product.getProductBrand(), product.getAddedOn());
            }
        } catch (IOException e) {
            logger.error("Error While writing CSV ", e);
        }
    }
}
