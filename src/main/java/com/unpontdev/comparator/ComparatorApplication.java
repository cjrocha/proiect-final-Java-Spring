package com.unpontdev.comparator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Comparator web application with beautifull UI
 * that allows users to:
 * - compare products from app DB
 * - search the web for products to compare
 * - sort product data easily
 *
 * @author - Chirila Andrei (cjrocha.webs@gmail.com)
 * Date: 2022 - 05 - 30
 */
@SpringBootApplication
public class ComparatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComparatorApplication.class, args);
	}

}
