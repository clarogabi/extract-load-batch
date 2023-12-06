-- PostgreSQL DDL script for the target_app schema

-- DROP SCHEMA target_app;

CREATE SCHEMA target_app AUTHORIZATION postgres;

-- DROP SEQUENCE target_app.customers_seq;

CREATE SEQUENCE target_app.customers_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
	CACHE 20
	NO CYCLE;

-- Permissions

ALTER SEQUENCE target_app.customers_seq OWNER TO postgres;
GRANT ALL ON SEQUENCE target_app.customers_seq TO postgres;

-- DROP SEQUENCE target_app.inventory_seq;

CREATE SEQUENCE target_app.inventory_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
	CACHE 20
	NO CYCLE;

-- Permissions

ALTER SEQUENCE target_app.inventory_seq OWNER TO postgres;
GRANT ALL ON SEQUENCE target_app.inventory_seq TO postgres;

-- DROP SEQUENCE target_app.order_items_seq;

CREATE SEQUENCE target_app.order_items_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
	CACHE 20
	NO CYCLE;

-- Permissions

ALTER SEQUENCE target_app.order_items_seq OWNER TO postgres;
GRANT ALL ON SEQUENCE target_app.order_items_seq TO postgres;

-- DROP SEQUENCE target_app.orders_seq;

CREATE SEQUENCE target_app.orders_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
	CACHE 20
	NO CYCLE;

-- Permissions

ALTER SEQUENCE target_app.orders_seq OWNER TO postgres;
GRANT ALL ON SEQUENCE target_app.orders_seq TO postgres;

-- DROP SEQUENCE target_app.products_seq;

CREATE SEQUENCE target_app.products_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
	CACHE 20
	NO CYCLE;

-- Permissions

ALTER SEQUENCE target_app.products_seq OWNER TO postgres;
GRANT ALL ON SEQUENCE target_app.products_seq TO postgres;

-- DROP SEQUENCE target_app.shipments_seq;

CREATE SEQUENCE target_app.shipments_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
	CACHE 20
	NO CYCLE;

-- Permissions

ALTER SEQUENCE target_app.shipments_seq OWNER TO postgres;
GRANT ALL ON SEQUENCE target_app.shipments_seq TO postgres;

-- DROP SEQUENCE target_app.stores_seq;

CREATE SEQUENCE target_app.stores_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
	CACHE 20
	NO CYCLE;

-- Permissions

ALTER SEQUENCE target_app.stores_seq OWNER TO postgres;
GRANT ALL ON SEQUENCE target_app.stores_seq TO postgres;
-- target_app.customers definition

-- Drop table

-- DROP TABLE target_app.customers;

CREATE TABLE target_app.customers (
                                      customer_id numeric NOT NULL,
                                      email_address varchar(255) NULL,
                                      full_name varchar(255) NULL,
                                      create_date_time timestamp NOT NULL,
                                      update_date_time timestamp NOT NULL,
                                      CONSTRAINT customers_email_u UNIQUE (email_address),
                                      CONSTRAINT customers_pk PRIMARY KEY (customer_id)
);
CREATE INDEX customers_name_i ON target_app.customers USING btree (full_name);

-- Permissions

ALTER TABLE target_app.customers OWNER TO postgres;
GRANT ALL ON TABLE target_app.customers TO postgres;


-- target_app.products definition

-- Drop table

-- DROP TABLE target_app.products;

CREATE TABLE target_app.products (
                                     product_id numeric NOT NULL,
                                     product_name varchar(255) NULL,
                                     unit_price numeric(10, 2) NULL,
                                     product_details bytea NULL,
                                     product_image bytea NULL,
                                     image_mime_type varchar(512) NULL,
                                     image_filename varchar(512) NULL,
                                     image_charset varchar(512) NULL,
                                     image_last_updated date NULL,
                                     create_date_time timestamp NOT NULL,
                                     update_date_time timestamp NOT NULL,
                                     CONSTRAINT products_pk PRIMARY KEY (product_id)
);

-- Permissions

ALTER TABLE target_app.products OWNER TO postgres;
GRANT ALL ON TABLE target_app.products TO postgres;


-- target_app.stores definition

-- Drop table

-- DROP TABLE target_app.stores;

CREATE TABLE target_app.stores (
                                   store_id numeric NOT NULL,
                                   store_name varchar(255) NULL,
                                   web_address varchar(100) NULL,
                                   physical_address varchar(512) NULL,
                                   latitude numeric NULL,
                                   longitude numeric NULL,
                                   logo bytea NULL,
                                   logo_mime_type varchar(512) NULL,
                                   logo_filename varchar(512) NULL,
                                   logo_charset varchar(512) NULL,
                                   logo_last_updated date NULL,
                                   create_date_time timestamp NOT NULL,
                                   update_date_time timestamp NOT NULL,
                                   CONSTRAINT store_at_least_one_address_c CHECK (((web_address IS NOT NULL) OR (physical_address IS NOT NULL))),
                                   CONSTRAINT store_name_u UNIQUE (store_name),
                                   CONSTRAINT stores_pk PRIMARY KEY (store_id)
);

-- Permissions

ALTER TABLE target_app.stores OWNER TO postgres;
GRANT ALL ON TABLE target_app.stores TO postgres;


-- target_app.inventory definition

-- Drop table

-- DROP TABLE target_app.inventory;

CREATE TABLE target_app.inventory (
                                      inventory_id numeric NOT NULL,
                                      store_id numeric NOT NULL,
                                      product_id numeric NOT NULL,
                                      product_inventory numeric NOT NULL,
                                      create_date_time timestamp NOT NULL,
                                      update_date_time timestamp NOT NULL,
                                      CONSTRAINT inventory_pk PRIMARY KEY (inventory_id),
                                      CONSTRAINT inventory_store_product_u UNIQUE (store_id, product_id),
                                      CONSTRAINT inventory_product_id_fk FOREIGN KEY (product_id) REFERENCES target_app.products(product_id),
                                      CONSTRAINT inventory_store_id_fk FOREIGN KEY (store_id) REFERENCES target_app.stores(store_id)
);
CREATE INDEX inventory_product_id_i ON target_app.inventory USING btree (product_id);

-- Permissions

ALTER TABLE target_app.inventory OWNER TO postgres;
GRANT ALL ON TABLE target_app.inventory TO postgres;


-- target_app.orders definition

-- Drop table

-- DROP TABLE target_app.orders;

CREATE TABLE target_app.orders (
                                   order_id numeric NOT NULL,
                                   order_datetime timestamp NOT NULL,
                                   customer_id numeric NOT NULL,
                                   order_status varchar(10) NULL,
                                   store_id numeric NOT NULL,
                                   create_date_time timestamp NOT NULL,
                                   update_date_time timestamp NOT NULL,
                                   CONSTRAINT orders_pk PRIMARY KEY (order_id),
                                   CONSTRAINT orders_status_c CHECK (((order_status)::text = ANY ((ARRAY['CANCELLED'::character varying, 'COMPLETE'::character varying, 'OPEN'::character varying, 'PAID'::character varying, 'REFUNDED'::character varying, 'SHIPPED'::character varying])::text[]))),
	CONSTRAINT orders_customer_id_fk FOREIGN KEY (customer_id) REFERENCES target_app.customers(customer_id),
	CONSTRAINT orders_store_id_fk FOREIGN KEY (store_id) REFERENCES target_app.stores(store_id)
);
CREATE INDEX orders_customer_id_i ON target_app.orders USING btree (customer_id);
CREATE INDEX orders_store_id_i ON target_app.orders USING btree (store_id);

-- Permissions

ALTER TABLE target_app.orders OWNER TO postgres;
GRANT ALL ON TABLE target_app.orders TO postgres;


-- target_app.shipments definition

-- Drop table

-- DROP TABLE target_app.shipments;

CREATE TABLE target_app.shipments (
                                      shipment_id numeric NOT NULL,
                                      store_id numeric NOT NULL,
                                      customer_id numeric NOT NULL,
                                      delivery_address varchar(512) NULL,
                                      shipment_status varchar(100) NULL,
                                      create_date_time timestamp NOT NULL,
                                      update_date_time timestamp NOT NULL,
                                      CONSTRAINT shipment_status_c CHECK (((shipment_status)::text = ANY ((ARRAY['CREATED'::character varying, 'SHIPPED'::character varying, 'IN-TRANSIT'::character varying, 'DELIVERED'::character varying])::text[]))),
	CONSTRAINT shipments_pk PRIMARY KEY (shipment_id),
	CONSTRAINT shipments_customer_id_fk FOREIGN KEY (customer_id) REFERENCES target_app.customers(customer_id),
	CONSTRAINT shipments_store_id_fk FOREIGN KEY (store_id) REFERENCES target_app.stores(store_id)
);
CREATE INDEX shipments_customer_id_i ON target_app.shipments USING btree (customer_id);
CREATE INDEX shipments_store_id_i ON target_app.shipments USING btree (store_id);

-- Permissions

ALTER TABLE target_app.shipments OWNER TO postgres;
GRANT ALL ON TABLE target_app.shipments TO postgres;


-- target_app.order_items definition

-- Drop table

-- DROP TABLE target_app.order_items;

CREATE TABLE target_app.order_items (
                                        order_id numeric NOT NULL,
                                        line_item_id numeric NOT NULL,
                                        product_id numeric NOT NULL,
                                        unit_price numeric(10, 2) NULL,
                                        quantity numeric NOT NULL,
                                        shipment_id numeric NULL,
                                        create_date_time timestamp NOT NULL,
                                        update_date_time timestamp NOT NULL,
                                        CONSTRAINT order_items_pk PRIMARY KEY (order_id, line_item_id),
                                        CONSTRAINT order_items_product_u UNIQUE (product_id, order_id),
                                        CONSTRAINT order_items_order_id_fk FOREIGN KEY (order_id) REFERENCES target_app.orders(order_id),
                                        CONSTRAINT order_items_product_id_fk FOREIGN KEY (product_id) REFERENCES target_app.products(product_id),
                                        CONSTRAINT order_items_shipment_id_fk FOREIGN KEY (shipment_id) REFERENCES target_app.shipments(shipment_id)
);
CREATE INDEX order_items_shipment_id_i ON target_app.order_items USING btree (shipment_id);

-- Permissions

ALTER TABLE target_app.order_items OWNER TO postgres;
GRANT ALL ON TABLE target_app.order_items TO postgres;


-- Permissions

GRANT ALL ON SCHEMA target_app TO postgres;
