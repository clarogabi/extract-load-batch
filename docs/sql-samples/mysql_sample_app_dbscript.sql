-- MySql DDL script for the ORIGIN_APP schema

-- DROP SCHEMA ORIGIN_APP;

CREATE DATABASE `ORIGIN_APP`;

-- `ORIGIN_APP`.CUSTOMERS definition

CREATE TABLE `ORIGIN_APP`.`CUSTOMERS` (
                                          `CUSTOMER_ID` bigint NOT NULL AUTO_INCREMENT,
                                          `EMAIL_ADDRESS` varchar(255) DEFAULT NULL,
                                          `FULL_NAME` varchar(255) DEFAULT NULL,
                                          `CREATE_DATE_TIME` timestamp NOT NULL,
                                          `UPDATE_DATE_TIME` timestamp NOT NULL,
                                          PRIMARY KEY (`CUSTOMER_ID`),
                                          UNIQUE KEY `CUSTOMERS_EMAIL_U` (`EMAIL_ADDRESS`),
                                          FULLTEXT KEY `CUSTOMERS_NAME_I` (`FULL_NAME`)
) ENGINE=InnoDB;


-- `ORIGIN_APP`.PRODUCTS definition

CREATE TABLE `ORIGIN_APP`.`PRODUCTS` (
                                         `PRODUCT_ID` bigint NOT NULL AUTO_INCREMENT,
                                         `PRODUCT_NAME` varchar(255) DEFAULT NULL,
                                         `UNIT_PRICE` float DEFAULT NULL,
                                         `PRODUCT_DETAILS` blob,
                                         `PRODUCT_IMAGE` blob,
                                         `IMAGE_MIME_TYPE` varchar(512) DEFAULT NULL,
                                         `IMAGE_FILENAME` varchar(512) DEFAULT NULL,
                                         `IMAGE_CHARSET` varchar(512) DEFAULT NULL,
                                         `IMAGE_LAST_UPDATED` date DEFAULT NULL,
                                         `CREATE_DATE_TIME` timestamp NOT NULL,
                                         `UPDATE_DATE_TIME` timestamp NOT NULL,
                                         PRIMARY KEY (`PRODUCT_ID`)
) ENGINE=InnoDB;


-- `ORIGIN_APP`.STORES definition

CREATE TABLE `ORIGIN_APP`.`STORES` (
                                       `STORE_ID` bigint NOT NULL AUTO_INCREMENT,
                                       `STORE_NAME` varchar(255) DEFAULT NULL,
                                       `WEB_ADDRESS` varchar(100) DEFAULT NULL,
                                       `PHYSICAL_ADDRESS` varchar(512) DEFAULT NULL,
                                       `LATITUDE` bigint DEFAULT NULL,
                                       `LONGITUDE` bigint DEFAULT NULL,
                                       `LOGO` blob,
                                       `LOGO_MIME_TYPE` varchar(512) DEFAULT NULL,
                                       `LOGO_FILENAME` varchar(512) DEFAULT NULL,
                                       `LOGO_CHARSET` varchar(512) DEFAULT NULL,
                                       `LOGO_LAST_UPDATED` date DEFAULT NULL,
                                       `CREATE_DATE_TIME` timestamp NOT NULL,
                                       `UPDATE_DATE_TIME` timestamp NOT NULL,
                                       PRIMARY KEY (`STORE_ID`),
                                       UNIQUE KEY `STORE_NAME_U` (`STORE_NAME`),
                                       CONSTRAINT `STORES_CHECK` CHECK (((`WEB_ADDRESS` is not null) or (`PHYSICAL_ADDRESS` is not null)))
) ENGINE=InnoDB;


-- `ORIGIN_APP`.INVENTORY definition

CREATE TABLE `ORIGIN_APP`.`INVENTORY` (
                                          `INVENTORY_ID` bigint NOT NULL AUTO_INCREMENT,
                                          `STORE_ID` bigint NOT NULL,
                                          `PRODUCT_ID` bigint NOT NULL,
                                          `PRODUCT_INVENTORY` bigint NOT NULL,
                                          `CREATE_DATE_TIME` timestamp NOT NULL,
                                          `UPDATE_DATE_TIME` timestamp NOT NULL,
                                          PRIMARY KEY (`INVENTORY_ID`),
                                          UNIQUE KEY `INVENTORY_STORE_PRODUCT_U` (`STORE_ID`,`PRODUCT_ID`),
                                          KEY `INVENTORY_PRODUCT_ID_FK` (`PRODUCT_ID`),
                                          CONSTRAINT `INVENTORY_PRODUCT_ID_FK` FOREIGN KEY (`PRODUCT_ID`) REFERENCES `PRODUCTS` (`PRODUCT_ID`),
                                          CONSTRAINT `INVENTORY_STORE_ID_FK` FOREIGN KEY (`STORE_ID`) REFERENCES `STORES` (`STORE_ID`)
) ENGINE=InnoDB;


-- `ORIGIN_APP`.ORDERS definition

CREATE TABLE `ORIGIN_APP`.`ORDERS` (
                                       `ORDER_ID` bigint NOT NULL AUTO_INCREMENT,
                                       `ORDER_DATETIME` timestamp NOT NULL,
                                       `CUSTOMER_ID` bigint NOT NULL,
                                       `ORDER_STATUS` varchar(10) DEFAULT NULL,
                                       `STORE_ID` bigint NOT NULL,
                                       `CREATE_DATE_TIME` timestamp NOT NULL,
                                       `UPDATE_DATE_TIME` timestamp NOT NULL,
                                       PRIMARY KEY (`ORDER_ID`),
                                       KEY `ORDERS_STORE_ID_FK` (`STORE_ID`),
                                       KEY `ORDERS_CUSTOMER_ID_FK` (`CUSTOMER_ID`),
                                       CONSTRAINT `ORDERS_CUSTOMER_ID_FK` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `CUSTOMERS` (`CUSTOMER_ID`),
                                       CONSTRAINT `ORDERS_STORE_ID_FK` FOREIGN KEY (`STORE_ID`) REFERENCES `STORES` (`STORE_ID`),
                                       CONSTRAINT `ORDERS_STATUS_C` CHECK ((`ORDER_STATUS` in (_utf8mb4'CANCELLED',_utf8mb4'COMPLETE',_utf8mb4'OPEN',_utf8mb4'PAID',_utf8mb4'REFUNDED',_utf8mb4'SHIPPED')))
) ENGINE=InnoDB;


-- `ORIGIN_APP`.SHIPMENTS definition

CREATE TABLE `ORIGIN_APP`.`SHIPMENTS` (
                                          `SHIPMENT_ID` bigint NOT NULL AUTO_INCREMENT,
                                          `STORE_ID` bigint NOT NULL,
                                          `CUSTOMER_ID` bigint NOT NULL,
                                          `DELIVERY_ADDRESS` varchar(512) DEFAULT NULL,
                                          `SHIPMENT_STATUS` varchar(100) DEFAULT NULL,
                                          `CREATE_DATE_TIME` timestamp NOT NULL,
                                          `UPDATE_DATE_TIME` timestamp NOT NULL,
                                          PRIMARY KEY (`SHIPMENT_ID`),
                                          KEY `SHIPMENTS_STORE_ID_FK` (`STORE_ID`),
                                          KEY `SHIPMENTS_CUSTOMER_ID_FK` (`CUSTOMER_ID`),
                                          CONSTRAINT `SHIPMENTS_CUSTOMER_ID_FK` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `CUSTOMERS` (`CUSTOMER_ID`),
                                          CONSTRAINT `SHIPMENTS_STORE_ID_FK` FOREIGN KEY (`STORE_ID`) REFERENCES `STORES` (`STORE_ID`),
                                          CONSTRAINT `SHIPMENTS_STATUS_C` CHECK ((`SHIPMENT_STATUS` in (_utf8mb4'CREATED',_utf8mb4'SHIPPED',_utf8mb4'IN-TRANSIT',_utf8mb4'DELIVERED')))
) ENGINE=InnoDB;


-- `ORIGIN_APP`.ORDER_ITEMS definition

CREATE TABLE `ORIGIN_APP`.`ORDER_ITEMS` (
                                            `ORDER_ID` bigint NOT NULL AUTO_INCREMENT,
                                            `LINE_ITEM_ID` bigint NOT NULL,
                                            `PRODUCT_ID` bigint NOT NULL,
                                            `UNIT_PRICE` float NOT NULL,
                                            `QUANTITY` bigint NOT NULL,
                                            `SHIPMENT_ID` bigint DEFAULT NULL,
                                            `CREATE_DATE_TIME` timestamp NOT NULL,
                                            `UPDATE_DATE_TIME` timestamp NOT NULL,
                                            PRIMARY KEY (`ORDER_ID`,`LINE_ITEM_ID`),
                                            UNIQUE KEY `ORDER_ITEMS_PRODUCT_U` (`ORDER_ID`,`PRODUCT_ID`),
                                            KEY `ORDER_ITEMS_SHIPMENT_ID_FK` (`SHIPMENT_ID`),
                                            KEY `ORDER_ITEMS_PRODUCT_ID_FK` (`PRODUCT_ID`),
                                            CONSTRAINT `ORDER_ITEMS_ORDER_ID_FK` FOREIGN KEY (`ORDER_ID`) REFERENCES `ORDERS` (`ORDER_ID`),
                                            CONSTRAINT `ORDER_ITEMS_PRODUCT_ID_FK` FOREIGN KEY (`PRODUCT_ID`) REFERENCES `PRODUCTS` (`PRODUCT_ID`),
                                            CONSTRAINT `ORDER_ITEMS_SHIPMENT_ID_FK` FOREIGN KEY (`SHIPMENT_ID`) REFERENCES `SHIPMENTS` (`SHIPMENT_ID`)
) ENGINE=InnoDB;