ALTER USER EXTRACTLOADBATCH QUOTA 524M ON SYSTEM;
GRANT UNLIMITED TABLESPACE TO EXTRACTLOADBATCH;

alter user EXTRACTLOADBATCH default tablespace USERS
              quota unlimited on USERS;

alter user EXTRACTLOADBATCH temporary tablespace TEMP;

grant create session,
    create table,
    create sequence,
    create view,
    create procedure
    to EXTRACTLOADBATCH
    identified by "EXTRACTLOADBATCH";

--ALTER SESSION SET CURRENT_SCHEMA=EXTRACTLOADBATCH

CREATE SEQUENCE EXTRACTLOADBATCH.EL_APP_TABLE_SEQ
    MINVALUE 1
    INCREMENT BY 1
    START WITH 1
    CACHE 20
    NOCYCLE;

CREATE SEQUENCE EXTRACTLOADBATCH.EL_DATASOURCE_CONFIG_SEQ
    MINVALUE 1
    INCREMENT BY 1
    START WITH 1
    CACHE 20
    NOCYCLE;

CREATE SEQUENCE EXTRACTLOADBATCH.EL_DATA_BUNDLE_SEQ
    MINVALUE 1
    INCREMENT BY 1
    START WITH 1
    CACHE 20
    NOCYCLE;

CREATE SEQUENCE EXTRACTLOADBATCH.EL_BUNDLED_APP_TABLE_SEQ
    MINVALUE 1
    INCREMENT BY 1
    START WITH 1
    CACHE 20
    NOCYCLE;

CREATE TABLE EXTRACTLOADBATCH.EL_APP_TABLE
(
    APP_TABLE_UID           NUMBER(20, 0) NOT NULL,
    APP_TABLE_PHYSICAL_NAME VARCHAR2(255) NOT NULL,
    CREATE_DATE_TIME        TIMESTAMP(6)  NOT NULL,
    UPDATE_DATE_TIME        TIMESTAMP(6)  NOT NULL
);

ALTER TABLE EXTRACTLOADBATCH.EL_APP_TABLE
    ADD CONSTRAINT PK_EL_APP_TABLE PRIMARY KEY (APP_TABLE_UID);


CREATE TABLE EXTRACTLOADBATCH.EL_DATASOURCE_CONFIGURATION
(
    DATASOURCE_CONFIGURATION_UID NUMBER(20, 0) NOT NULL,
    DATABASE_NAME                VARCHAR2(50)  NOT NULL,
    DATABASE_PROVIDER            VARCHAR2(50)  NOT NULL,
    DATABASE_PLATFORM            VARCHAR2(255) NOT NULL,
    DATABASE_CONNECTION_URL      VARCHAR2(255) NOT NULL,
    DATABASE_USER_NAME           VARCHAR2(255) NOT NULL,
    DATABASE_PASSWORD            VARCHAR2(255) NOT NULL,
    DRIVER_CLASS_NAME            VARCHAR2(255) NOT NULL,
    DATABASE_DIALECT             VARCHAR2(255) NOT NULL,
    DATABASE_SCHEMA              VARCHAR2(255) NOT NULL,
    CREATE_DATE_TIME             TIMESTAMP(6)  NOT NULL,
    UPDATE_DATE_TIME             TIMESTAMP(6)  NOT NULL
);

ALTER TABLE EXTRACTLOADBATCH.EL_DATASOURCE_CONFIGURATION
    ADD CONSTRAINT PK_EL_DATASOURCE_CONFIGURATION PRIMARY KEY (DATASOURCE_CONFIGURATION_UID);


CREATE TABLE EXTRACTLOADBATCH.EL_DATA_BUNDLE
(
    DATA_BUNDLE_UID              NUMBER(20, 0) NOT NULL,
    SOURCE_DATASOURCE_CONFIG_UID NUMBER(20, 0) NOT NULL,
    TARGET_DATASOURCE_CONFIG_UID NUMBER(20, 0) NOT NULL,
    DATA_BUNDLE_NAME             VARCHAR2(255) NOT NULL,
    CREATE_DATE_TIME             TIMESTAMP(6)  NOT NULL,
    UPDATE_DATE_TIME             TIMESTAMP(6)  NOT NULL
);

ALTER TABLE EXTRACTLOADBATCH.EL_DATA_BUNDLE
    ADD CONSTRAINT PK_EL_DATA_BUNDLE PRIMARY KEY (DATA_BUNDLE_UID);

ALTER TABLE EXTRACTLOADBATCH.EL_DATA_BUNDLE
    ADD (CONSTRAINT FK_SOURCE_DATASOURCE FOREIGN KEY (SOURCE_DATASOURCE_CONFIG_UID)
        REFERENCES EXTRACTLOADBATCH.EL_DATASOURCE_CONFIGURATION (DATASOURCE_CONFIGURATION_UID));

ALTER TABLE EXTRACTLOADBATCH.EL_DATA_BUNDLE
    ADD (CONSTRAINT FK_TARGET_DATASOURCE FOREIGN KEY (TARGET_DATASOURCE_CONFIG_UID)
        REFERENCES EXTRACTLOADBATCH.EL_DATASOURCE_CONFIGURATION (DATASOURCE_CONFIGURATION_UID));

CREATE TABLE EXTRACTLOADBATCH.EL_BUNDLED_APP_TABLE
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

ALTER TABLE EXTRACTLOADBATCH.EL_BUNDLED_APP_TABLE
    ADD CONSTRAINT PK_EL_BUNDLED_APP_TABLE PRIMARY KEY (BUNDLE_APP_TABLE_UID);

ALTER TABLE EXTRACTLOADBATCH.EL_BUNDLED_APP_TABLE
    ADD (CONSTRAINT FK_DATA_BUNDLE FOREIGN KEY (DATA_BUNDLE_UID)
        REFERENCES EXTRACTLOADBATCH.EL_DATA_BUNDLE (DATA_BUNDLE_UID));

ALTER TABLE EXTRACTLOADBATCH.EL_BUNDLED_APP_TABLE
    ADD (CONSTRAINT FK_SOURCE_APP_TABLE FOREIGN KEY (SOURCE_APP_TABLE_UID)
        REFERENCES EXTRACTLOADBATCH.EL_APP_TABLE (APP_TABLE_UID));

ALTER TABLE EXTRACTLOADBATCH.EL_BUNDLED_APP_TABLE
    ADD (CONSTRAINT FK_TARGET_APP_TABLE FOREIGN KEY (TARGET_APP_TABLE_UID)
        REFERENCES EXTRACTLOADBATCH.EL_APP_TABLE (APP_TABLE_UID));

CREATE UNIQUE INDEX EXTRACTLOADBATCH.UIX_TARGET_TABLE_ORDERING
    ON EXTRACTLOADBATCH.EL_BUNDLED_APP_TABLE (TARGET_APP_TABLE_UID ASC, RELATIONAL_ORDERING_NUMBER ASC);

INSERT INTO EXTRACTLOADBATCH.EL_DATASOURCE_CONFIGURATION (DATASOURCE_CONFIGURATION_UID, DATABASE_NAME,DATABASE_PROVIDER,DATABASE_PLATFORM,DATABASE_CONNECTION_URL,DATABASE_USER_NAME,DATABASE_PASSWORD,DRIVER_CLASS_NAME,DATABASE_DIALECT,DATABASE_SCHEMA,CREATE_DATE_TIME,UPDATE_DATE_TIME)
VALUES (EXTRACTLOADBATCH.EL_DATASOURCE_CONFIG_SEQ.nextval, 'DUMMY_SOURCE','ORACLE','dummy','dummy','dummy','dummy','dummy','dummy','dummy',TIMESTAMP '2022-09-18 13:44:07.388000',TIMESTAMP '2022-09-18 13:44:11.435000');

INSERT INTO EXTRACTLOADBATCH.EL_DATASOURCE_CONFIGURATION (DATASOURCE_CONFIGURATION_UID, DATABASE_NAME,DATABASE_PROVIDER,DATABASE_PLATFORM,DATABASE_CONNECTION_URL,DATABASE_USER_NAME,DATABASE_PASSWORD,DRIVER_CLASS_NAME,DATABASE_DIALECT,DATABASE_SCHEMA,CREATE_DATE_TIME,UPDATE_DATE_TIME)
VALUES (EXTRACTLOADBATCH.EL_DATASOURCE_CONFIG_SEQ.nextval, 'DUMMY_TARGET','ORACLE','dummy','dummy','dummy','dummy','dummy','dummy','dummy',TIMESTAMP '2022-09-18 13:44:07.388000',TIMESTAMP '2022-09-18 13:44:11.435000');

INSERT INTO EXTRACTLOADBATCH.EL_DATA_BUNDLE (DATA_BUNDLE_UID,SOURCE_DATASOURCE_CONFIG_UID,TARGET_DATASOURCE_CONFIG_UID,DATA_BUNDLE_NAME,CREATE_DATE_TIME,UPDATE_DATE_TIME)
VALUES (EXTRACTLOADBATCH.EL_DATA_BUNDLE_SEQ.nextval,1,2,'APP',TIMESTAMP '2022-09-18 13:49:25.974000',TIMESTAMP '2022-09-18 13:49:29.709000');

INSERT INTO EXTRACTLOADBATCH.EL_APP_TABLE (APP_TABLE_UID,APP_TABLE_PHYSICAL_NAME,CREATE_DATE_TIME,UPDATE_DATE_TIME)
VALUES (EXTRACTLOADBATCH.EL_APP_TABLE_SEQ.nextval,'APP_USER',TIMESTAMP '2022-09-18 13:52:01.275000',TIMESTAMP '2022-09-18 13:52:06.587000');

INSERT INTO EXTRACTLOADBATCH.EL_BUNDLED_APP_TABLE (BUNDLE_APP_TABLE_UID,DATA_BUNDLE_UID,SOURCE_APP_TABLE_UID,TARGET_APP_TABLE_UID,RELATIONAL_ORDERING_NUMBER,CREATE_DATE_TIME,UPDATE_DATE_TIME)
VALUES (EXTRACTLOADBATCH.EL_BUNDLED_APP_TABLE_SEQ.nextval,1,1,1,1,TIMESTAMP '2022-09-18 13:53:39.324000',TIMESTAMP '2022-09-18 13:53:42.918000');
