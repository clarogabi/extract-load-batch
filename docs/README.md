# extract-load-batch-api

-----

### Data Extraction and Load with Spring Batch and Rest API
### Version 0.0.2

## Table of Contents

* [Installation Guide](#installation-guide)
    * [Requirements](#requirements)
    * [Environment Variables](#environment-variables)
    * [Running Application](#running-application)
    * [Checking Application Health](#checking-application-health)
* [Using Application](#using-application)
    * [Swagger UI Interface](#swagger-ui)
* [License](#license)

## Installation Guide

### Requirements

- [Java JDK 21](https://docs.oracle.com/en/java/javase/21/install/overview-jdk-installation.html#GUID-8677A77F-231A-40F7-98B9-1FD0B48C346A)
- Database Oracle Xe 11g
- Create database credentials, schema and tables from [DDL Scripts](https://github.com/clarogabi/extract-load-batch/tree/main/src/main/resources/sql)
- Download the latest application version `.jar` file from [GitHub Packages](https://github.com/clarogabi/extract-load-batch/packages/1976775)

### Environment Variables

At the installation machine, configure the following environment variables:

|       Variable       |     Description      |       Sample       |
|:--------------------:|:--------------------:|:------------------:|
|   `MS_ELB_DB_HOST`   | Database host or dns |     localhost      |
|   `MS_ELB_DB_PORT`   |    Database port     |        1521        |
|   `MS_ELB_DB_NAME`   |    Database name     | EXTRACT_LOAD_BATCH |
|  `MS_ELB_DB_LOGIN`   | Database user login  | EXTRACT_LOAD_BATCH |
| `MS_ELB_DB_PASSWORD` |  Database password   | EXTRACT_LOAD_BATCH |


### Running Application

From application installed directory, run `.jar` file using command line terminal like sample bellow:

```shell
  java -jar extract-load-batch-0.0.1-20231031.003204-3.jar
```

The initialization and executions Logs will appear at terminal.

### Checking Application Health

Use the URL [actuator/health](http://localhost:8080/actuator/health) to check application status. It should be `{"status":"UP"}`.

## Using Application

### Swagger UI Interface

In order to interact with API documents, features and endpoints, it is available Swagger UI interface.

| Installation |                     URL                      |
|:------------:|:--------------------------------------------:|
|  localhost   | http://localhost:8080/swagger-ui/index.html# |

## License

Project under Apache License Version 2.0 [https://www.apache.org/licenses/LICENSE-2.0](https://www.apache.org/licenses/LICENSE-2.0).
