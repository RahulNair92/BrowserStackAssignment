# El Pais Web Scraper

This project is a **web scraping tool** designed to extract and process data from the **El Pais** website. It is developed using **Java** and utilizes various dependencies such as **Selenium WebDriver** for browser automation and **REST Assured** for API interactions.

---

## Features

- **Automated Web Scraping**: Uses Selenium WebDriver for efficient data extraction.
- **API Integration**: Supports API requests using REST Assured.
- **Multi-Platform Testing**: Includes BrowserStack integration for cross-platform browser testing.
- **Data Handling**: Manages file operations with Apache Commons IO.
- **Enhanced Debugging**: Provides detailed console and network logs.

---

## Dependencies

The project uses the following dependencies:

- **Selenium WebDriver** (`4.16.1`) for web automation
- **REST Assured** (`5.3.0`) for API testing
- **TestNG** (`7.4.0`) for testing framework
- **Apache Commons IO** (`2.11.0`) for file handling
- **Jackson Databind** (`2.18.3`) for JSON parsing
- **BrowserStack Java SDK** for cloud-based testing

To ensure compatibility, the project is built with **Java 11**.

---

## Setup and Installation

1. Clone the repository:
   ```bash
   git clone <repository-url>
 Navigate to the project directory:
 
2. Build the project using Maven:
mvn clean install

4. Update your BrowserStack credentials in the browserstack.yml file:
userName: <your-username>
accessKey: <your-access-key>

5. Running Tests
Use the following Maven command to execute tests:
mvn test
The maven-surefire-plugin is configured to enable BrowserStack testing.

Configuration
BrowserStack Configuration: The browserstack.yml file includes settings for platforms, browsers, and debugging options. Examples include:
Platforms: Windows, macOS, Android, iOS
Browsers: Chrome, Safari, Edge
Debugging: Enabled console and network logs

Update as necessary to suit your testing requirements.
