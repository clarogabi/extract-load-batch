-- SQL Server DDL script for the sample_app schema
-- Based on samples: https://github.com/oracle-samples/db-sample-schemas/blob/main/customer_orders/

-- drop schema sample_app;

create schema sample_app;

--drop sequence sample_app.customers_seq;

create sequence sample_app.customers_seq
    minvalue 1
    increment by 1
    start with 1
    no cycle cache 20;

--drop sequence sample_app.stores_seq;

create sequence sample_app.stores_seq
    minvalue 1
    increment by 1
    start with 1
    no cycle cache 20;

--drop sequence sample_app.products_seq;

create sequence sample_app.products_seq
    minvalue 1
    increment by 1
    start with 1
    no cycle cache 20;

--drop sequence sample_app.orders_seq;

create sequence sample_app.orders_seq
    minvalue 1
    increment by 1
    start with 1
    no cycle cache 20;

--drop sequence sample_app.shipments_seq;

create sequence sample_app.shipments_seq
    minvalue 1
    increment by 1
    start with 1
    no cycle cache 20;

--drop sequence sample_app.order_items_seq;

create sequence sample_app.order_items_seq
    minvalue 1
    increment by 1
    start with 1
    no cycle cache 20;

--drop sequence sample_app.inventory_seq;

create sequence sample_app.inventory_seq
    minvalue 1
    increment by 1
    start with 1
    no cycle cache 20;

-- master.sample_app.customers definition

-- drop table

-- drop table master.sample_app.customers;

create table master.sample_app.customers
(
    customer_id      int      not null,
    email_address    varchar(255) collate sql_latin1_general_cp1_ci_as null,
    full_name        varchar(255) collate sql_latin1_general_cp1_ci_as null,
    create_date_time datetime not null,
    update_date_time datetime not null,
    constraint customers_pk primary key (customer_id)
);

create nonclustered index customers_email_u on sample_app.customers (  email_address asc  )
	 with (  pad_index = off ,fillfactor = 100  ,sort_in_tempdb = off , ignore_dup_key = off , statistics_norecompute = off , online = off , allow_row_locks = on , allow_page_locks = on  )
	 on [primary ] ;
create nonclustered index customers_name_i on sample_app.customers (  full_name asc  )
	 with (  pad_index = off ,fillfactor = 100  ,sort_in_tempdb = off , ignore_dup_key = off , statistics_norecompute = off , online = off , allow_row_locks = on , allow_page_locks = on  )
	 on [primary ] ;


-- master.sample_app.products definition

-- drop table

-- drop table master.sample_app.products;

create table master.sample_app.products
(
    product_id         int      not null,
    product_name       varchar(255) collate sql_latin1_general_cp1_ci_as null,
    unit_price         float null,
    product_details    varbinary(4000) null,
    product_image      varbinary(4000) null,
    image_mime_type    varchar(512) collate sql_latin1_general_cp1_ci_as null,
    image_filename     varchar(512) collate sql_latin1_general_cp1_ci_as null,
    image_charset      varchar(512) collate sql_latin1_general_cp1_ci_as null,
    image_last_updated date null,
    create_date_time   datetime not null,
    update_date_time   datetime not null,
    constraint products_pk primary key (product_id)
);


-- master.sample_app.stores definition

-- drop table

-- drop table master.sample_app.stores;

create table master.sample_app.stores
(
    store_id          int      not null,
    store_name        varchar(255) collate sql_latin1_general_cp1_ci_as null,
    web_address       varchar(100) collate sql_latin1_general_cp1_ci_as null,
    physical_address  varchar(512) collate sql_latin1_general_cp1_ci_as null,
    latitude          int null,
    longitude         int null,
    logo              varbinary(4000) null,
    logo_mime_type    varchar(512) collate sql_latin1_general_cp1_ci_as null,
    logo_filename     varchar(512) collate sql_latin1_general_cp1_ci_as null,
    logo_charset      varchar(512) collate sql_latin1_general_cp1_ci_as null,
    logo_last_updated date null,
    create_date_time  datetime not null,
    update_date_time  datetime not null,
    constraint stores_pk primary key (store_id),
    constraint store_at_least_one_address_c check (web_address is not null or physical_address is not null)
);

create unique nonclustered index store_name_u on sample_app.stores (  store_name asc  )
	 with (  pad_index = off ,fillfactor = 100  ,sort_in_tempdb = off , ignore_dup_key = off , statistics_norecompute = off , online = off , allow_row_locks = on , allow_page_locks = on  )
	 on [primary ] ;

-- master.sample_app.inventory definition

-- drop table

-- drop table master.sample_app.inventory;

create table master.sample_app.inventory
(
    inventory_id      int      not null,
    store_id          int      not null,
    product_id        int      not null,
    product_inventory int      not null,
    create_date_time  datetime not null,
    update_date_time  datetime not null,
    constraint inventory_pk primary key (inventory_id),
    constraint inventory_product_id_fk foreign key (product_id) references master.sample_app.products (product_id),
    constraint inventory_store_id_fk foreign key (store_id) references master.sample_app.stores (store_id)
);

create unique nonclustered index inventory_store_product_u on sample_app.inventory (  store_id asc  , product_id asc  )
	 with (  pad_index = off ,fillfactor = 100  ,sort_in_tempdb = off , ignore_dup_key = off , statistics_norecompute = off , online = off , allow_row_locks = on , allow_page_locks = on  )
	 on [primary ] ;


-- master.sample_app.orders definition

-- drop table

-- drop table master.sample_app.orders;

create table master.sample_app.orders
(
    order_id         int      not null,
    order_datetime   datetime not null,
    customer_id      int      not null,
    order_status     varchar(10) collate sql_latin1_general_cp1_ci_as null,
    store_id         int      not null,
    create_date_time datetime not null,
    update_date_time datetime not null,
    constraint orders_pk primary key (order_id),
    constraint orders_customer_id_fk foreign key (customer_id) references master.sample_app.customers (customer_id),
    constraint orders_store_id_fk foreign key (store_id) references master.sample_app.stores (store_id),
    constraint orders_status_c check (order_status in ('cancelled', 'complete', 'open', 'paid', 'refunded', 'shipped'))
);

-- master.sample_app.shipments definition

-- drop table

-- drop table master.sample_app.shipments;

create table master.sample_app.shipments
(
    shipment_id      int      not null,
    store_id         int      not null,
    customer_id      int      not null,
    delivery_address varchar(512) collate sql_latin1_general_cp1_ci_as null,
    shipment_status  varchar(100) collate sql_latin1_general_cp1_ci_as null,
    create_date_time datetime not null,
    update_date_time datetime not null,
    constraint shipments_pk primary key (shipment_id),
    constraint shipments_customer_id_fk foreign key (customer_id) references master.sample_app.customers (customer_id),
    constraint shipments_store_id_fk foreign key (store_id) references master.sample_app.stores (store_id),
    constraint shipment_status_c check (shipment_status in ('created', 'shipped', 'in-transit', 'delivered'))
);
create nonclustered index shipments_customer_id_i on sample_app.shipments (  customer_id asc  )
	 with (  pad_index = off ,fillfactor = 100  ,sort_in_tempdb = off , ignore_dup_key = off , statistics_norecompute = off , online = off , allow_row_locks = on , allow_page_locks = on  )
	 on [primary ] ;
create nonclustered index shipments_store_id_i on sample_app.shipments (  store_id asc  )
	 with (  pad_index = off ,fillfactor = 100  ,sort_in_tempdb = off , ignore_dup_key = off , statistics_norecompute = off , online = off , allow_row_locks = on , allow_page_locks = on  )
	 on [primary ] ;

-- master.sample_app.order_items definition

-- drop table

-- drop table master.sample_app.order_items;

create table master.sample_app.order_items
(
    order_item_id    int      not null,
    order_id         int      not null,
    line_item_id     int      not null,
    product_id       int      not null,
    unit_price       float    not null,
    quantity         int      not null,
    shipment_id      int null,
    create_date_time datetime not null,
    update_date_time datetime not null,
    constraint order_items_pk primary key (order_item_id),
    constraint order_items_order_id_fk foreign key (order_id) references master.sample_app.orders (order_id),
    constraint order_items_product_id_fk foreign key (product_id) references master.sample_app.products (product_id),
    constraint order_items_shipment_id_fk foreign key (shipment_id) references master.sample_app.shipments (shipment_id)
);

create unique nonclustered index order_items_product_u on sample_app.order_items (  order_id asc  , product_id asc  )
	 with (  pad_index = off ,fillfactor = 100  ,sort_in_tempdb = off , ignore_dup_key = off , statistics_norecompute = off , online = off , allow_row_locks = on , allow_page_locks = on  )
	 on [primary ] ;
create  unique nonclustered index order_items_order_u on sample_app.order_items (  order_id asc  , line_item_id asc  )
	 with (  pad_index = off ,fillfactor = 100  ,sort_in_tempdb = off , ignore_dup_key = off , statistics_norecompute = off , online = off , allow_row_locks = on , allow_page_locks = on  )
	 on [primary ] ;
create nonclustered index order_items_shipment_id_i on sample_app.order_items (  shipment_id asc  )
	 with (  pad_index = off ,fillfactor = 100  ,sort_in_tempdb = off , ignore_dup_key = off , statistics_norecompute = off , online = off , allow_row_locks = on , allow_page_locks = on  )
	 on [primary ] ;