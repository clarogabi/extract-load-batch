CREATE USER CO_APP_TARGET IDENTIFIED BY CO_APP;

ALTER USER CO_APP_TARGET QUOTA 524M ON SYSTEM;
GRANT UNLIMITED TABLESPACE TO CO_APP_TARGET;

alter user CO_APP_TARGET default tablespace USERS
             quota unlimited on USERS;

alter user CO_APP_TARGET temporary tablespace TEMP;

grant create session,
create table,
create sequence,
create view,
create procedure
   to CO_APP_TARGET
 identified by "CO_APP_TARGET";

create sequence CO_APP_TARGET.customers_seq
    minvalue 1
    increment by 1
    START with 1
    cache 20
    nocycle;

create sequence CO_APP_TARGET.stores_seq
    minvalue 1
    increment by 1
    START with 1
    cache 20
    nocycle;

create sequence CO_APP_TARGET.products_seq
    minvalue 1
    increment by 1
    START with 1
    cache 20
    nocycle;

create sequence CO_APP_TARGET.orders_seq
    minvalue 1
    increment by 1
    START with 1
    cache 20
    nocycle;

create sequence CO_APP_TARGET.shipments_seq
    minvalue 1
    increment by 1
    START with 1
    cache 20
    nocycle;

create sequence CO_APP_TARGET.order_items_seq
    minvalue 1
    increment by 1
    START with 1
    cache 20
    nocycle;

create sequence CO_APP_TARGET.inventory_seq
    minvalue 1
    increment by 1
    START with 1
    cache 20
    nocycle;

create table CO_APP_TARGET.customers (
                                         customer_id     	integer,
                                         email_address   	varchar2(255 char) not null,
                                         full_name       	varchar2(255 char) not null,
                                         create_date_time  timestamp(6) default sysdate not null,
                                         update_date_time  timestamp(6) default sysdate not null)

;

create table CO_APP_TARGET.stores (
                                      store_id          integer,
                                      store_name        varchar2(255 char) not null,
                                      web_address       varchar2(100 char),
                                      physical_address  varchar2(512 char),
                                      latitude          number,
                                      longitude         number,
                                      logo              blob,
                                      logo_mime_type    varchar2(512 char),
                                      logo_filename     varchar2(512 char),
                                      logo_charset      varchar2(512 char),
                                      logo_last_updated date,
                                      create_date_time  timestamp(6) default sysdate not null,
                                      update_date_time  timestamp(6) default sysdate not null)
;

create table CO_APP_TARGET.products (
                                        product_id         integer,
                                        product_name       varchar2(255 char) not null,
                                        unit_price         number(10,2),
                                        product_details    blob,
                                        product_image      blob,
                                        image_mime_type    varchar2(512 char),
                                        image_filename     varchar2(512 char),
                                        image_charset      varchar2(512 char),
                                        image_last_updated date,
                                        create_date_time  timestamp(6) default sysdate not null,
                                        update_date_time  timestamp(6) default sysdate not null)
;

create table CO_APP_TARGET.orders (
                                      order_id          integer,
                                      order_datetime    timestamp not null,
                                      customer_id       integer not null,
                                      order_status      varchar2(10 char) not null,
                                      store_id          integer not null,
                                      create_date_time  timestamp(6) default sysdate not null,
                                      update_date_time  timestamp(6) default sysdate not null)
;

create table CO_APP_TARGET.shipments (
                                         shipment_id          integer,
                                         store_id             integer not null,
                                         customer_id          integer not null,
                                         delivery_address     varchar2(512 char) not null,
                                         shipment_status      varchar2(100 char) not null,
                                         create_date_time  timestamp(6) default sysdate not null,
                                         update_date_time  timestamp(6) default sysdate not null)
;

create table CO_APP_TARGET.order_items (
                                           order_id        integer not null,
                                           line_item_id    integer not null,
                                           product_id      integer not null,
                                           unit_price      number(10,2) not null,
                                           quantity        integer not null,
                                           shipment_id     integer,
                                           create_date_time  timestamp(6) default sysdate not null,
                                           update_date_time  timestamp(6) default sysdate not null)
;

create table CO_APP_TARGET.inventory (
                                         inventory_id          integer,
                                         store_id              integer not null,
                                         product_id            integer not null,
                                         product_inventory     integer not null,
                                         create_date_time  timestamp(6) default sysdate not null,
                                         update_date_time  timestamp(6) default sysdate not null)
;

create index CO_APP_TARGET.customers_name_i on CO_APP_TARGET.customers ( full_name );
create index CO_APP_TARGET.orders_customer_id_i on CO_APP_TARGET.orders ( customer_id );
create index CO_APP_TARGET.orders_store_id_i on CO_APP_TARGET.orders ( store_id );
create index CO_APP_TARGET.shipments_store_id_i on CO_APP_TARGET.shipments ( store_id );
create index CO_APP_TARGET.shipments_customer_id_i on CO_APP_TARGET.shipments ( customer_id );
create index CO_APP_TARGET.order_items_shipment_id_i on CO_APP_TARGET.order_items ( shipment_id );
create index CO_APP_TARGET.inventory_product_id_i on CO_APP_TARGET.inventory ( product_id );

alter table CO_APP_TARGET.customers add constraint customers_pk primary key (customer_id);

alter table CO_APP_TARGET.customers add constraint customers_email_u unique (email_address);

alter table CO_APP_TARGET.stores add constraint stores_pk primary key (store_id);

alter table CO_APP_TARGET.stores add constraint store_name_u unique (store_name);

alter table CO_APP_TARGET.stores add constraint store_at_least_one_address_c
    check (
            web_address is not null or physical_address is not null
        );

alter table CO_APP_TARGET.products add constraint products_pk primary key (product_id);

alter table CO_APP_TARGET.orders add constraint orders_pk primary key (order_id);

alter table CO_APP_TARGET.orders add constraint orders_customer_id_fk
    foreign key (customer_id) references CO_APP_TARGET.customers (customer_id);

alter table CO_APP_TARGET.orders add constraint  orders_status_c
    check ( order_status in
            ( 'CANCELLED','COMPLETE','OPEN','PAID','REFUNDED','SHIPPED'));

alter table CO_APP_TARGET.orders add constraint orders_store_id_fk foreign key (store_id)
    references CO_APP_TARGET.stores (store_id);

alter table CO_APP_TARGET.shipments add constraint shipments_pk primary key (shipment_id);

alter table CO_APP_TARGET.shipments add constraint shipments_store_id_fk
    foreign key (store_id) references CO_APP_TARGET.stores (store_id);

alter table CO_APP_TARGET.shipments add constraint shipments_customer_id_fk
    foreign key (customer_id) references CO_APP_TARGET.customers (customer_id);

alter table CO_APP_TARGET.shipments add constraint shipment_status_c
    check ( shipment_status in
            ( 'CREATED', 'SHIPPED', 'IN-TRANSIT', 'DELIVERED'));

alter table CO_APP_TARGET.order_items add constraint order_items_pk primary key ( order_id, line_item_id );

alter table CO_APP_TARGET.order_items add constraint order_items_order_id_fk
    foreign key (order_id) references CO_APP_TARGET.orders (order_id);

alter table CO_APP_TARGET.order_items add constraint order_items_shipment_id_fk
    foreign key (shipment_id) references CO_APP_TARGET.shipments (shipment_id);

alter table CO_APP_TARGET.order_items add constraint order_items_product_id_fk
    foreign key (product_id) references CO_APP_TARGET.products (product_id);

alter table CO_APP_TARGET.order_items add constraint order_items_product_u unique ( product_id, order_id );

alter table CO_APP_TARGET.inventory add constraint inventory_pk primary key (inventory_id);

alter table CO_APP_TARGET.inventory add constraint inventory_store_product_u unique (store_id, product_id);

alter table CO_APP_TARGET.inventory add constraint inventory_store_id_fk
    foreign key (store_id) references CO_APP_TARGET.stores (store_id);

alter table CO_APP_TARGET.inventory add constraint inventory_product_id_fk
    foreign key (product_id) references CO_APP_TARGET.products (product_id);

-- CONSUMERS TABLE

create sequence CO_APP_TARGET.consumers_seq
    minvalue 1
    increment by 1
    START with 1
    cache 20
    nocycle;

create table CO_APP_TARGET.consumers (
                                         customer_id     	integer,
                                         email_address   	varchar2(255 char) not null,
                                         full_name       	varchar2(255 char) not null,
                                         create_date_time  timestamp(6) default sysdate not null,
                                         update_date_time  timestamp(6) default sysdate not null)

;

create index CO_APP_TARGET.consumers_name_i on CO_APP_TARGET.consumers ( full_name );
alter table CO_APP_TARGET.consumers add constraint consumers_pk primary key (customer_id);
alter table CO_APP_TARGET.consumers add constraint consumers_email_u unique (email_address);
