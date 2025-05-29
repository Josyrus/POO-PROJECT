package app;

import clientes.CustomerManager;
import pedidos.OrderManager;
import product.ProductManager;
import product.Product;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CustomerManager customerManager = new CustomerManager();
        OrderManager orderManager = new OrderManager();
        ProductManager productManager = new ProductManager();

        int option;
        do {
            System.out.println("==== SISTEMA DE VENTAS ====");
            System.out.println("1. Agregar producto");
            System.out.println("2. Ver todos los productos");
            System.out.println("3. Agregar cliente");
            System.out.println("4. Ver clientes");
            System.out.println("5. Salir");
            System.out.print("Selecciona una opción: ");
            option = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (option) {
                case 1 -> {
                    System.out.println("== Agregar Producto ==");
                    System.out.print("ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // limpiar buffer
                    System.out.print("Nombre: ");
                    String name = scanner.nextLine();
                    System.out.print("Descripción: ");
                    String desc = scanner.nextLine();
                    System.out.print("Precio: ");
                    double price = scanner.nextDouble();
                    System.out.print("Stock: ");
                    int stock = scanner.nextInt();
                    scanner.nextLine(); // limpiar
                    System.out.print("Categoría: ");
                    String category = scanner.nextLine();
                    System.out.print("Código de barras: ");
                    String barcode = scanner.nextLine();

                    Product newProduct = new Product(id, name, desc, price, stock, category, barcode); 
                    productManager.addProduct(newProduct);
                    System.out.println("Producto agregado.");
                }
                case 2 -> {
                    System.out.println("== Lista de Productos ==");
                    for (Product p : productManager.getAllProducts()) {
                        System.out.println("- " + p.getName() + " | $" + p.getPrice() + " | Stock: " + p.getStock());
                    }
                }
                case 3 -> {
                System.out.println("== Agregar Cliente ==");
                System.out.print("ID: ");
                int id = Integer.parseInt(scanner.nextLine());
                System.out.print("Nombre: ");
                String name = scanner.nextLine();
                System.out.print("Alias: ");
                String alias = scanner.nextLine();
                System.out.print("Dirección: ");
                String direction = scanner.nextLine();
                System.out.print("Teléfono: ");
                int phone = Integer.parseInt(scanner.nextLine());
                customerManager.addCustomer(id, name, alias, direction, phone);
                System.out.println("Cliente agregado.");
                }
                case 4 -> {
                    System.out.println("== Lista de Clientes ==");
                    customerManager.printAllCustomers();
                }
                case 5 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción no válida.");
            }
        } while (option != 5);

        scanner.close();
    }
}
