# My POO Workspace

## Project Overview
This project is a Java application that manages sales, customers, orders, and reports. It is structured to facilitate easy management of these entities through dedicated classes.
## Project Members
Garcia Guerra Andres Caleb
Flores Dominguez Beatriz Monica
Arcos Garduño Ayax
## Project Structure
```
my-java-workspace
├── src
│   └── main
│       └── java
│           ├── app
│           │   └── App.java
│           ├── ventas
│           │   └── SaleManager.java
│           ├── clientes
│           │   └── CustomerMangar.java
│           ├── pedidos
│           │   └── OrderManager.java
│           └── reportes
│               └── ReportManager.java
├── .vscode
│   └── settings.json
├── pom.xml
└── README.md
```

## Setup Instructions
1. **Clone the repository**:
   ```
   git clone <repository-url>
   cd my-java-workspace
   ```

2. **Build the project**:
   Use Maven to build the project:
   ```
   mvn clean install
   ```

3. **Run the application**:
   Execute the main class:
   ```
   java -cp target/my-java-workspace-1.0-SNAPSHOT.jar app.App
   ```

## Usage
- **Sales Management**: Use the `SaleManager` class to create, retrieve, and delete sales.
- **Customer Management**: Use the `CustomerManager` class to add, retrieve, and remove customers.
- **Order Processing**: Use the `OrderManager` class to create, retrieve, and cancel orders.
- **Reporting**: Use the `ReportManager` class to generate various reports related to sales, customers, and orders.

## Dependencies
This project uses Maven for dependency management. Please refer to the `pom.xml` file for the list of dependencies.

## Contributing
Contributions are welcome! Please submit a pull request or open an issue for any enhancements or bug fixes.

## License
This project is licensed under the MIT License. See the LICENSE file for details.