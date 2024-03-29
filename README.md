# Banking
Banking service handles customers, accounts and transactions

# Requirements and Assumptions

**Requirement 1 : Assignment needs to create API for opening a new current account of already existing customers.**

Assumption No 1 : Customers needs to be there (CustomerController is needed)

Assumption No 2 : An account should automatically get created when creating a customer (AccountController is needed)

**Requirement 2 : API will expose an endpoint which accepts the user information (customerID and initialCredit)**

Assumption No 3 : This might be part of Account controller for the user to optionally create a current account

**Requirement 3 : Once the above endpoint is called, a new account will be opened connected to the user whose ID is customerID**

**Requirement 4 : Also, if initialCredit is not 0, a transaction will be sent to the new account.**

**Requirement 5 : Another Endpoint will output the user information showing Name, Surname, balance, and transactions of the accounts**

Assumption 4: Sounds like a dashboard api, but "transactions of the accounts" does not match a dashboard api.

Assumption 5: "and transactions of the accounts" showing all transactions instead of last ten, since it is a requirement.

**Bonus Requirement 6: For storing the information, the data can be saved in memory and not actually persisted to an external database, so that we can test the solution easier.**

Assumption 6: Using a in memory DB, since it is clearly mentioned in the Bonus section

# Author
- Name: Padmaprasad Ganesan
- Email: padmaprasad1729@gmail.com
- Created : 28 Mar 2024

# Tech stack
- Java 17
- Spring Boot
- Maven
- H2

# Build project
- Move to the project path
- ./mvnw clean install

# Run by Maven
- Pre-requisite : configure in properties
- Move to the project path
- ./mvnw spring-boot:run

# Run by Docker
- Pre-requisite : Docker Desktop
- Move to the project path
- docker compose up
-
# Swagger
- http://localhost:8081/swagger-ui/index.html

# Open Api Doc
- http://localhost:8081/v3/api-docs