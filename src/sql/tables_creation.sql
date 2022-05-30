-- Table: public.member

-- DROP TABLE IF EXISTS public.member;

CREATE TABLE IF NOT EXISTS public.member
(
    email character varying(255) COLLATE pg_catalog."default" NOT NULL,
    first_name character varying(255) COLLATE pg_catalog."default",
    last_name character varying(255) COLLATE pg_catalog."default",
    password character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT member_pkey PRIMARY KEY (email)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.member
    OWNER to postgres;


-- Table: public.member_roles

-- DROP TABLE IF EXISTS public.member_roles;

CREATE TABLE IF NOT EXISTS public.member_roles
(
    member_email character varying(255) COLLATE pg_catalog."default" NOT NULL,
    role_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT fkie23dqh98ukgf70y7qhefocjm FOREIGN KEY (role_name)
        REFERENCES public.role (name) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fko7jhtlokmix2wn87bj1qmi1qx FOREIGN KEY (member_email)
        REFERENCES public.member (email) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.member_roles
    OWNER to postgres;

-- Table: public.role

-- DROP TABLE IF EXISTS public.role;

CREATE TABLE IF NOT EXISTS public.role
(
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT role_pkey PRIMARY KEY (name)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.role
    OWNER to postgres;

-- Table: public.search_terms

-- DROP TABLE IF EXISTS public.search_terms;

CREATE TABLE IF NOT EXISTS public.search_terms
(
    search_id bigint NOT NULL,
    term_source character varying(255) COLLATE pg_catalog."default",
    term character varying(255) COLLATE pg_catalog."default",
    term_url character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT search_terms_pkey PRIMARY KEY (search_id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.search_terms
    OWNER to postgres;

-- Table: public.search_results

-- DROP TABLE IF EXISTS public.search_results;

CREATE TABLE IF NOT EXISTS public.search_results
(
    result_id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    search_result_name character varying(255) COLLATE pg_catalog."default",
    search_result_url character varying(255) COLLATE pg_catalog."default",
    search_term_search_id bigint,
    CONSTRAINT search_results_pkey PRIMARY KEY (result_id),
    CONSTRAINT fki7xvq2l8g2wggnt8jgq0v1syh FOREIGN KEY (search_term_search_id)
        REFERENCES public.search_terms (search_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.search_results
    OWNER to postgres;

-- Table: public.product

-- DROP TABLE IF EXISTS public.product;

CREATE TABLE IF NOT EXISTS public.product
(
    prod_id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    added_on timestamp without time zone,
    old_price double precision,
    price double precision,
    product_brand character varying(255) COLLATE pg_catalog."default",
    product_description text COLLATE pg_catalog."default",
    product_id character varying(100) COLLATE pg_catalog."default",
    product_main_image character varying(500) COLLATE pg_catalog."default",
    product_name character varying(500) COLLATE pg_catalog."default",
    product_sku character varying(150) COLLATE pg_catalog."default",
    product_source character varying(100) COLLATE pg_catalog."default",
    product_stock character varying(255) COLLATE pg_catalog."default",
    product_url character varying(500) COLLATE pg_catalog."default",
    term_id bigint,
    CONSTRAINT product_pkey PRIMARY KEY (prod_id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.product
    OWNER to postgres;

-- Table: public.product_images

-- DROP TABLE IF EXISTS public.product_images;

CREATE TABLE IF NOT EXISTS public.product_images
(
    id bigint NOT NULL,
    image_url character varying(255) COLLATE pg_catalog."default",
    products_prod_id character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT product_images_pkey PRIMARY KEY (id),
    CONSTRAINT fkstsbx7v4kw9cpa7iugxv0nnk FOREIGN KEY (products_prod_id)
        REFERENCES public.product (prod_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.product_images
    OWNER to postgres;