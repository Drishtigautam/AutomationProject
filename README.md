# AutomationProject
Selenium Hybrid Framework 

# Selenium Hybrid Automation Framework with API & Data-Driven Testing

This project showcases a **Hybrid Test Automation Framework** developed using **Java**, **Selenium WebDriver**, and **TestNG**, following the **Page Object Model (POM)** design pattern. It supports automated testing of both **web UI and REST APIs**, and uses **custom DataProvider classes** for **data-driven testing**, without relying on Excel or CSV files.

---

## Key Highlights

- **Hybrid Framework**: Merges modular, keyword-based, and data-driven methodologies.
- **Page Object Model (POM)**: Each page in the application is represented by a separate class containing element locators and interaction methods.
- **Custom Data-Driven Testing**: Test data is supplied via TestNG `@DataProvider` methods written in standalone Java classes.
- **API Test Integration**: REST API endpoints are validated using **REST-assured**.
- **Centralized Configuration**: Uses a `config.properties` file for managing environment-specific settings.
- **Reusable Utility Classes**: Common tasks like browser setup, driver management, configuration loading, and screenshot capture are abstracted into utility classes.
- **TestNG Support**: Enables flexible test execution, including parallel runs, test grouping, prioritization, and parameterization.

---

## Folder Overview

### `1. utilities/`

Houses shared functionality and helpers:
- `Browser.java` – Manages browser-specific configurations
- `ConfigReader.java` – Loads and provides access to configuration values
- `ScreenshotUtils.java` – Captures screenshots for reporting or failure analysis
- `WebDriverFactory.java` – Instantiates browser drivers dynamically

### `2. exceptions/`

Contains user-defined exceptions to ensure meaningful messages and better failure handling.

### `3. pages/`

Implements POM where:
- Each web page is encapsulated as a Java class
- Locators are declared using Selenium's `By`
- Interactions are handled through well-named methods

### `4. testclasses/`

Test scripts based on TestNG:
- UI tests interact with page classes
- API tests directly validate REST responses

### `5. base/BaseTest.java`

Includes:
- WebDriver initialization and cleanup
- Configuration loading
- Pre/post-test logic setup

### `6. apitests/`

API tests built with **REST-assured**:
- Covers all CRUD operations
- Includes status code checks, response body validation, and header assertions

### `7. dataproviders/`

Java classes that provide input data via `@DataProvider`:
- code-based 
---

## Tools & Technologies

- Java
- Selenium WebDriver
- TestNG
- Maven
- REST-assured
- Log4j or ExtentReports (optional for logging/reporting)

---

## Multiple TestNG Suites

The framework supports multiple `testng.xml` files, each targeting different categories of tests.

### Available Suite Files:

- `testng.xml` – UI test suite with parallel execution.
- `testng-api.xml` – API test suite
- `testng-Smokesuite.xml` – Smoke tests
- `testngdatadriven.xml` – Data Driven Testing.

### Running a Suite:

- mvn clean test "-DsuiteXMLFile=testng.xml" → works in PowerShell

- mvn clean test -DsuiteXMLFile=testng.xml → works in CMD


  
