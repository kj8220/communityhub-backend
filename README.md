# Reddit Clone - Backend 🚀

This repository contains the backend source code for a **Reddit Clone** project, built with **Spring Boot**. This service handles user authentication, posts, subreddits, comments, voting, and all core API functionalities.

***

## ✨ Architecture & Frontend Repository

This project uses a decoupled architecture. The frontend is developed and maintained in a separate repository.

➡️ **You can find the frontend repository here: https://github.com/kj8220/communityhub_frontend **

***

## 🔧 Tech Stack

* **Language:** Java
* **Framework:** Spring Boot, Spring Security, Spring Data JPA
* **Database:** MySQL
* **Build Tool:** Maven
* **Authentication:** JSON Web Tokens (JWT)

***

## 🛠️ Getting Started

Follow these instructions to get a local copy of the backend up and running for development.

### Prerequisites

You'll need the following software installed on your machine:
* [Java JDK (v17 or higher)](https://www.oracle.com/java/technologies/downloads/)
* [Apache Maven](https://maven.apache.org/download.cgi)
* [MySQL Server](https://dev.mysql.com/downloads/mysql/)
* [Git](https://git-scm.com/)

### Installation & Setup

1.  **Clone the repository:**
    ```sh
    git clone https://github.com/kj8220/communityhub-backend
    cd [repository-folder-name]
    ```

2.  **Setup the Database:**
    * Ensure your MySQL server is running.
    * Create a database for the project (e.g., `reddit_clone_db`).

3.  **Configure the Application:**
    * Open the `src/main/resources/application.properties` file.
    * Update the `spring.datasource` properties to match your local MySQL username, password, and database name.

    ```properties
    # src/main/resources/application.properties

    # MySQL Database Connection
    spring.datasource.url=jdbc:mysql://localhost:3306/reddit_clone_db
    spring.datasource.username=your_mysql_username
    spring.datasource.password=your_mysql_password

    # JPA / Hibernate Settings
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

    # JWT Secret Key
    jwt.secret.key=your-super-strong-and-secret-jwt-key
    ```

4.  **Build and Run the Application:**
    The included Maven wrapper (`mvnw`) will handle downloading dependencies.
    ```sh
    ./mvnw spring-boot:run
    ```
    The server should now be running, typically on `http://localhost:8080`.

***

## 📚 API Documentation

This project uses **Springdoc OpenAPI** to generate live API documentation. Once the server is running, you can view and test all the available API endpoints in your browser.

* **Swagger UI URL:** `http://localhost:8080/swagger-ui.html`

***

## 🤝 Contributing

Contributions are what make the open-source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

Please read our `CONTRIBUTING.md` file for details on our code of conduct and the process for submitting pull requests.

***

## 📜 License

This project is licensed under the MIT License. See the `LICENSE` file for more information.
