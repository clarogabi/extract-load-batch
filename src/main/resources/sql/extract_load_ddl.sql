-- Extract and Load Database DDL
CREATE USER EXTRACT_LOAD_BATCH IDENTIFIED BY EXTRACT_LOAD_BATCH;
ALTER USER EXTRACT_LOAD_BATCH QUOTA 524M ON SYSTEM;
GRANT UNLIMITED TABLESPACE TO EXTRACT_LOAD_BATCH;

alter user EXTRACT_LOAD_BATCH default tablespace USERS
              quota unlimited on USERS;

alter user EXTRACT_LOAD_BATCH temporary tablespace TEMP;

grant create session,
create table,
create sequence,
create view,
create procedure
    to EXTRACT_LOAD_BATCH
    identified by "EXTRACT_LOAD_BATCH";

ALTER SESSION SET CURRENT_SCHEMA = EXTRACT_LOAD_BATCH;

CREATE SEQUENCE EXTRACT_LOAD_BATCH.EL_APP_TABLE_SEQ
    MINVALUE 1
    INCREMENT BY 1
    START WITH 1
    CACHE 20
    NOCYCLE;

CREATE SEQUENCE EXTRACT_LOAD_BATCH.EL_DATASOURCE_CONFIG_SEQ
    MINVALUE 1
    INCREMENT BY 1
    START WITH 1
    CACHE 20
    NOCYCLE;

CREATE SEQUENCE EXTRACT_LOAD_BATCH.EL_DATA_BUNDLE_SEQ
    MINVALUE 1
    INCREMENT BY 1
    START WITH 1
    CACHE 20
    NOCYCLE;

CREATE SEQUENCE EXTRACT_LOAD_BATCH.EL_BUNDLED_APP_TABLE_SEQ
    MINVALUE 1
    INCREMENT BY 1
    START WITH 1
    CACHE 20
    NOCYCLE;

CREATE TABLE EXTRACT_LOAD_BATCH.EL_APP_TABLE
(
    APP_TABLE_UID           NUMBER(20, 0) NOT NULL,
    APP_TABLE_PHYSICAL_NAME VARCHAR2(128) NOT NULL,
    CREATE_DATE_TIME        TIMESTAMP(6)  NOT NULL,
    UPDATE_DATE_TIME        TIMESTAMP(6)  NOT NULL
);

ALTER TABLE EXTRACT_LOAD_BATCH.EL_APP_TABLE
    ADD CONSTRAINT PK_EL_APP_TABLE PRIMARY KEY (APP_TABLE_UID);


CREATE TABLE EXTRACT_LOAD_BATCH.EL_DATASOURCE_CONFIGURATION
(
    DATASOURCE_UID        NUMBER(20, 0)  NOT NULL,
    DATABASE_NAME         VARCHAR2(128)  NOT NULL,
    DATABASE_HOST         VARCHAR2(63)   NOT NULL,
    DATABASE_NUMBER_PORT  NUMBER(5, 0)   NOT NULL,
    DATABASE_USER_NAME    VARCHAR2(128)  NOT NULL,
    DATABASE_PASSWORD     VARCHAR2(128)  NOT NULL,
    DATABASE_PRODUCT_NAME VARCHAR2(50)   NOT NULL,
    DELETED_INDICATOR     VARCHAR2(5)    NOT NULL,
    CREATE_DATE_TIME      TIMESTAMP(6)   NOT NULL,
    UPDATE_DATE_TIME      TIMESTAMP(6)   NOT NULL
);

ALTER TABLE EXTRACT_LOAD_BATCH.EL_DATASOURCE_CONFIGURATION
    ADD CONSTRAINT PK_EL_DATASOURCE_CONFIGURATION PRIMARY KEY (DATASOURCE_UID);


CREATE TABLE EXTRACT_LOAD_BATCH.EL_DATA_BUNDLE
(
    DATA_BUNDLE_UID              NUMBER(20, 0) NOT NULL,
    SOURCE_DATASOURCE_CONFIG_UID NUMBER(20, 0) NOT NULL,
    TARGET_DATASOURCE_CONFIG_UID NUMBER(20, 0) NOT NULL,
    DATA_BUNDLE_NAME             VARCHAR2(128) NOT NULL,
    CREATE_DATE_TIME             TIMESTAMP(6)  NOT NULL,
    UPDATE_DATE_TIME             TIMESTAMP(6)  NOT NULL
);

ALTER TABLE EXTRACT_LOAD_BATCH.EL_DATA_BUNDLE
    ADD CONSTRAINT PK_EL_DATA_BUNDLE PRIMARY KEY (DATA_BUNDLE_UID);

ALTER TABLE EXTRACT_LOAD_BATCH.EL_DATA_BUNDLE
    ADD (CONSTRAINT FK_SOURCE_DATASOURCE FOREIGN KEY (SOURCE_DATASOURCE_CONFIG_UID)
        REFERENCES EXTRACT_LOAD_BATCH.EL_DATASOURCE_CONFIGURATION (DATASOURCE_UID));

ALTER TABLE EXTRACT_LOAD_BATCH.EL_DATA_BUNDLE
    ADD (CONSTRAINT FK_TARGET_DATASOURCE FOREIGN KEY (TARGET_DATASOURCE_CONFIG_UID)
        REFERENCES EXTRACT_LOAD_BATCH.EL_DATASOURCE_CONFIGURATION (DATASOURCE_UID));

CREATE TABLE EXTRACT_LOAD_BATCH.EL_BUNDLED_APP_TABLE
(
    BUNDLE_APP_TABLE_UID       NUMBER(20, 0)  NOT NULL,
    DATA_BUNDLE_UID            NUMBER(20, 0)  NOT NULL,
    SOURCE_APP_TABLE_UID       NUMBER(20, 0)  NOT NULL,
    TARGET_APP_TABLE_UID       NUMBER(20, 0)  NOT NULL,
    RELATIONAL_ORDERING_NUMBER NUMBER(20, 0)  NOT NULL,
    EXTRACT_CUSTOM_QUERY       VARCHAR2(4000),
    LOAD_CUSTOM_INSERT_QUERY   VARCHAR2(4000),
    LOAD_CUSTOM_UPDATE_QUERY   VARCHAR2(4000),
    CREATE_DATE_TIME           TIMESTAMP(6)   NOT NULL,
    UPDATE_DATE_TIME           TIMESTAMP(6)   NOT NULL
);

ALTER TABLE EXTRACT_LOAD_BATCH.EL_BUNDLED_APP_TABLE
    ADD CONSTRAINT PK_EL_BUNDLED_APP_TABLE PRIMARY KEY (BUNDLE_APP_TABLE_UID);

ALTER TABLE EXTRACT_LOAD_BATCH.EL_BUNDLED_APP_TABLE
    ADD (CONSTRAINT FK_DATA_BUNDLE FOREIGN KEY (DATA_BUNDLE_UID)
        REFERENCES EXTRACT_LOAD_BATCH.EL_DATA_BUNDLE (DATA_BUNDLE_UID));

ALTER TABLE EXTRACT_LOAD_BATCH.EL_BUNDLED_APP_TABLE
    ADD (CONSTRAINT FK_SOURCE_APP_TABLE FOREIGN KEY (SOURCE_APP_TABLE_UID)
        REFERENCES EXTRACT_LOAD_BATCH.EL_APP_TABLE (APP_TABLE_UID));

ALTER TABLE EXTRACT_LOAD_BATCH.EL_BUNDLED_APP_TABLE
    ADD (CONSTRAINT FK_TARGET_APP_TABLE FOREIGN KEY (TARGET_APP_TABLE_UID)
        REFERENCES EXTRACT_LOAD_BATCH.EL_APP_TABLE (APP_TABLE_UID));

CREATE UNIQUE INDEX EXTRACT_LOAD_BATCH.UIX_TARGET_TABLE_ORDERING
    ON EXTRACT_LOAD_BATCH.EL_BUNDLED_APP_TABLE (TARGET_APP_TABLE_UID ASC, RELATIONAL_ORDERING_NUMBER ASC);