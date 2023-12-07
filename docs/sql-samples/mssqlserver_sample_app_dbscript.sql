-- MSSql Server DDL script for the origin_app schema

-- DROP SCHEMA origin_app;

CREATE SCHEMA origin_app;

CREATE SEQUENCE ORIGIN_APP.CUSTOMERS_SEQ
    MINVALUE 1
    INCREMENT BY 1
    START WITH 1
    NO CYCLE
    CACHE 20;

CREATE SEQUENCE ORIGIN_APP.STORES_SEQ
    MINVALUE 1
    INCREMENT BY 1
    START WITH 1
    NO CYCLE
    CACHE 20;

CREATE SEQUENCE ORIGIN_APP.PRODUCTS_SEQ
    MINVALUE 1
    INCREMENT BY 1
    START WITH 1
    NO CYCLE
    CACHE 20;

CREATE SEQUENCE ORIGIN_APP.ORDERS_SEQ
    MINVALUE 1
    INCREMENT BY 1
    START WITH 1
    NO CYCLE
    CACHE 20;

CREATE SEQUENCE ORIGIN_APP.SHIPMENTS_SEQ
    MINVALUE 1
    INCREMENT BY 1
    START WITH 1
    NO CYCLE
    CACHE 20;

CREATE SEQUENCE ORIGIN_APP.ORDER_ITEMS_SEQ
    MINVALUE 1
    INCREMENT BY 1
    START WITH 1
    NO CYCLE
    CACHE 20;

CREATE SEQUENCE ORIGIN_APP.INVENTORY_SEQ
    MINVALUE 1
    INCREMENT BY 1
    START WITH 1
    NO CYCLE
    CACHE 20;

-- master.origin_app.CUSTOMERS definition

-- Drop table

-- DROP TABLE master.origin_app.CUSTOMERS;

CREATE TABLE master.origin_app.CUSTOMERS (
                                      CUSTOMER_ID int NOT NULL,
                                      EMAIL_ADDRESS varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
                                      FULL_NAME varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
                                      CREATE_DATE_TIME datetime NOT NULL,
                                      UPDATE_DATE_TIME datetime NOT NULL,
                                      CONSTRAINT CUSTOMERS_PK PRIMARY KEY (CUSTOMER_ID)
);
CREATE NONCLUSTERED INDEX CUSTOMERS_EMAIL_U ON origin_app.CUSTOMERS (  EMAIL_ADDRESS ASC  )
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX CUSTOMERS_NAME_I ON origin_app.CUSTOMERS (  FULL_NAME ASC  )
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- master.origin_app.PRODUCTS definition

-- Drop table

-- DROP TABLE master.origin_app.PRODUCTS;

CREATE TABLE master.origin_app.PRODUCTS (
                                     PRODUCT_ID int NOT NULL,
                                     PRODUCT_NAME varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
                                     UNIT_PRICE float NULL,
                                     PRODUCT_DETAILS varbinary(4000) NULL,
                                     PRODUCT_IMAGE varbinary(4000) NULL,
                                     IMAGE_MIME_TYPE varchar(512) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
                                     IMAGE_FILENAME varchar(512) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
                                     IMAGE_CHARSET varchar(512) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
                                     IMAGE_LAST_UPDATED date NULL,
                                     CREATE_DATE_TIME datetime NOT NULL,
                                     UPDATE_DATE_TIME datetime NOT NULL,
                                     CONSTRAINT PRODUCTS_PK PRIMARY KEY (PRODUCT_ID)
);


-- master.origin_app.STORES definition

-- Drop table

-- DROP TABLE master.origin_app.STORES;

CREATE TABLE master.origin_app.STORES (
                                   STORE_ID int NOT NULL,
                                   STORE_NAME varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
                                   WEB_ADDRESS varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
                                   PHYSICAL_ADDRESS varchar(512) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
                                   LATITUDE int NULL,
                                   LONGITUDE int NULL,
                                   LOGO varbinary(4000) NULL,
                                   LOGO_MIME_TYPE varchar(512) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
                                   LOGO_FILENAME varchar(512) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
                                   LOGO_CHARSET varchar(512) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
                                   LOGO_LAST_UPDATED date NULL,
                                   CREATE_DATE_TIME datetime NOT NULL,
                                   UPDATE_DATE_TIME datetime NOT NULL,
                                   CONSTRAINT STORES_PK PRIMARY KEY (STORE_ID),
                                   CONSTRAINT STORE_AT_LEAST_ONE_ADDRESS_C CHECK (WEB_ADDRESS IS NOT NULL OR PHYSICAL_ADDRESS IS NOT NULL)
);
CREATE  UNIQUE NONCLUSTERED INDEX STORE_NAME_U ON origin_app.STORES (  STORE_NAME ASC  )
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;

-- master.origin_app.INVENTORY definition

-- Drop table

-- DROP TABLE master.origin_app.INVENTORY;

CREATE TABLE master.origin_app.INVENTORY (
                                      INVENTORY_ID int NOT NULL,
                                      STORE_ID int NOT NULL,
                                      PRODUCT_ID int NOT NULL,
                                      PRODUCT_INVENTORY int NOT NULL,
                                      CREATE_DATE_TIME datetime NOT NULL,
                                      UPDATE_DATE_TIME datetime NOT NULL,
                                      CONSTRAINT INVENTORY_PK PRIMARY KEY (INVENTORY_ID),
                                      CONSTRAINT INVENTORY_PRODUCT_ID_FK FOREIGN KEY (PRODUCT_ID) REFERENCES master.origin_app.PRODUCTS(PRODUCT_ID),
                                      CONSTRAINT INVENTORY_STORE_ID_FK FOREIGN KEY (STORE_ID) REFERENCES master.origin_app.STORES(STORE_ID)
);
CREATE  UNIQUE NONCLUSTERED INDEX INVENTORY_STORE_PRODUCT_U ON origin_app.INVENTORY (  STORE_ID ASC  , PRODUCT_ID ASC  )
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- master.origin_app.ORDERS definition

-- Drop table

-- DROP TABLE master.origin_app.ORDERS;

CREATE TABLE master.origin_app.ORDERS (
                                   ORDER_ID int NOT NULL,
                                   ORDER_DATETIME datetime NOT NULL,
                                   CUSTOMER_ID int NOT NULL,
                                   ORDER_STATUS varchar(10) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
                                   STORE_ID int NOT NULL,
                                   CREATE_DATE_TIME datetime NOT NULL,
                                   UPDATE_DATE_TIME datetime NOT NULL,
                                   CONSTRAINT ORDERS_PK PRIMARY KEY (ORDER_ID),
                                   CONSTRAINT ORDERS_CUSTOMER_ID_FK FOREIGN KEY (CUSTOMER_ID) REFERENCES master.origin_app.CUSTOMERS(CUSTOMER_ID),
                                   CONSTRAINT ORDERS_STORE_ID_FK FOREIGN KEY (STORE_ID) REFERENCES master.origin_app.STORES(STORE_ID),
                                   CONSTRAINT ORDERS_STATUS_C CHECK (ORDER_STATUS IN ( 'CANCELLED','COMPLETE','OPEN','PAID','REFUNDED','SHIPPED'))
);

-- master.origin_app.SHIPMENTS definition

-- Drop table

-- DROP TABLE master.origin_app.SHIPMENTS;

CREATE TABLE master.origin_app.SHIPMENTS (
                                      SHIPMENT_ID int NOT NULL,
                                      STORE_ID int NOT NULL,
                                      CUSTOMER_ID int NOT NULL,
                                      DELIVERY_ADDRESS varchar(512) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
                                      SHIPMENT_STATUS varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
                                      CREATE_DATE_TIME datetime NOT NULL,
                                      UPDATE_DATE_TIME datetime NOT NULL,
                                      CONSTRAINT SHIPMENTS_PK PRIMARY KEY (SHIPMENT_ID),
                                      CONSTRAINT SHIPMENTS_CUSTOMER_ID_FK FOREIGN KEY (CUSTOMER_ID) REFERENCES master.origin_app.CUSTOMERS(CUSTOMER_ID),
                                      CONSTRAINT SHIPMENTS_STORE_ID_FK FOREIGN KEY (STORE_ID) REFERENCES master.origin_app.STORES(STORE_ID),
                                      CONSTRAINT SHIPMENT_STATUS_C CHECK (SHIPMENT_STATUS IN ('CREATED', 'SHIPPED', 'IN-TRANSIT', 'DELIVERED'))
);
CREATE NONCLUSTERED INDEX SHIPMENTS_CUSTOMER_ID_I ON origin_app.SHIPMENTS (  CUSTOMER_ID ASC  )
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX SHIPMENTS_STORE_ID_I ON origin_app.SHIPMENTS (  STORE_ID ASC  )
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;

-- master.origin_app.ORDER_ITEMS definition

-- Drop table

-- DROP TABLE master.origin_app.ORDER_ITEMS;

CREATE TABLE master.origin_app.ORDER_ITEMS (
                                        ORDER_ID int NOT NULL,
                                        LINE_ITEM_ID int NOT NULL,
                                        PRODUCT_ID int NOT NULL,
                                        UNIT_PRICE float NOT NULL,
                                        QUANTITY int NOT NULL,
                                        SHIPMENT_ID int NULL,
                                        CREATE_DATE_TIME datetime NOT NULL,
                                        UPDATE_DATE_TIME datetime NOT NULL,
                                        CONSTRAINT ORDER_ITEMS_PK PRIMARY KEY (ORDER_ID,LINE_ITEM_ID),
                                        CONSTRAINT ORDER_ITEMS_ORDER_ID_FK FOREIGN KEY (ORDER_ID) REFERENCES master.origin_app.ORDERS(ORDER_ID),
                                        CONSTRAINT ORDER_ITEMS_PRODUCT_ID_FK FOREIGN KEY (PRODUCT_ID) REFERENCES master.origin_app.PRODUCTS(PRODUCT_ID),
                                        CONSTRAINT ORDER_ITEMS_SHIPMENT_ID_FK FOREIGN KEY (SHIPMENT_ID) REFERENCES master.origin_app.SHIPMENTS(SHIPMENT_ID)
);
CREATE  UNIQUE NONCLUSTERED INDEX ORDER_ITEMS_PRODUCT_U ON origin_app.ORDER_ITEMS (  ORDER_ID ASC  , PRODUCT_ID ASC  )
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX ORDER_ITEMS_SHIPMENT_ID_I ON origin_app.ORDER_ITEMS (  SHIPMENT_ID ASC  )
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;