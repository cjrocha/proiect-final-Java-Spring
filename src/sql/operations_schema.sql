--- Insert data into product table
insert into product (added_on, old_price, price, product_brand, product_description, product_id, product_main_image, product_name, product_sku, product_source, product_stock, product_url, term_id, prod_id) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)

--- Insert data into images table
insert into product_images (image_url, products_prod_id, id) values (?, ?, ?)

