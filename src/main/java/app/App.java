package app;
import clientes.CustomerManager;
import pedidos.OrderManager;
import product.ProductManager;
import product.Product;
import ventas.SaleManager;
import java.io.Console;
import java.util.*;
import auth.LoginManager;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LoginManager loginManager = new LoginManager();
        System.out.println("=== INICIO DE SESIÓN ===");
        System.out.print("Usuario: ");
        String username = scanner.nextLine();
        System.out.print("Contraseña: ");
        String password = scanner.nextLine();

        if (!loginManager.authenticate(username, password)) {
            System.out.println("Credenciales inválidas. Saliendo...");
            return;
        }
        System.out.println("Inicio de sesión exitoso.\n");
        ProductManager productManager = new ProductManager();
        CustomerManager customerManager = new CustomerManager();
        OrderManager orderManager = new OrderManager(productManager);
        SaleManager saleManager = new SaleManager();

        HashMap<Product, Integer> carrito = new HashMap<>();

        int option;
        do {
            System.out.println("==== SISTEMA DE VENTAS ====");
            System.out.println("1. Agregar producto");
            System.out.println("2. Ver todos los productos");
            System.out.println("3. Agregar cliente");
            System.out.println("4. Ver clientes");
            System.out.println("5. Añadir al carrito");
            System.out.println("6. Vender");
            System.out.println("7. Generar reporte de ventas");
            System.out.println("8. Menú de pedidos");
            System.out.println("9. Gestión de usuarios");
            System.out.println("10. Salir");
            System.out.print("Selecciona una opción: ");
            option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 1 -> {
                    System.out.println("== Agregar Producto ==");
                    System.out.print("ID: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    System.out.print("Nombre: ");
                    String name = scanner.nextLine();
                    System.out.print("Descripción: ");
                    String desc = scanner.nextLine();
                    System.out.print("Precio: ");
                    double price = Double.parseDouble(scanner.nextLine());
                    System.out.print("Stock: ");
                    int stock = Integer.parseInt(scanner.nextLine());
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
                        System.out.println("- ID: " + p.getId() + " | " + p.getName() + " | $" + p.getPrice() + " | Stock: " + p.getStock());
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
                case 5 -> {
                    System.out.println("== Añadir al carrito ==");
                    System.out.print("ID del producto: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    Product selectedProduct = productManager.getProductById(id);
                    if (selectedProduct != null) {
                        System.out.print("Cantidad: ");
                        int qty = Integer.parseInt(scanner.nextLine());
                        if (qty <= selectedProduct.getStock()) {
                            carrito.put(selectedProduct, carrito.getOrDefault(selectedProduct, 0) + qty);
                            System.out.println("Producto añadido al carrito.");
                        } else {
                            System.out.println("Stock insuficiente.");
                        }
                    } else {
                        System.out.println("Producto no encontrado.");
                    }
                }
                case 6 -> {
                    System.out.println("== Realizar Venta ==");
                    if (carrito.isEmpty()) {
                        System.out.println("El carrito está vacío.");
                    } else {
                        saleManager.createSale(carrito);
                        // Descontar stock
                        for (Map.Entry<Product, Integer> entry : carrito.entrySet()) {
                            Product p = entry.getKey();
                            int qty = entry.getValue();
                            p.setStock(p.getStock() - qty);
                        }
                        carrito.clear();
                        System.out.println("Venta realizada con éxito.");
                    }
                }
                case 7 -> {
                    System.out.println("== Reporte de Ventas ==");
                    saleManager.generateSalesReport(); // Este método debe implementarse
                }
                case 8 -> {
                    int subOption;
                    do {
                        System.out.println("== MENÚ DE PEDIDOS ==");
                        System.out.println("1. Crear pedido");
                        System.out.println("2. Ver todos los pedidos");
                        System.out.println("3. Volver al menú principal");
                        System.out.print("Selecciona una opción: ");
                        subOption = Integer.parseInt(scanner.nextLine());

                        switch (subOption) {
                            case 1 -> {
                                System.out.println("== Crear Pedido ==");
                                System.out.print("ID del Pedido: ");
                                int orderId = Integer.parseInt(scanner.nextLine());
                                HashMap<Product, Integer> pedido = new HashMap<>();

                                String continuar;
                                do {
                                    System.out.print("ID del producto: ");
                                    int productId = Integer.parseInt(scanner.nextLine());
                                    Product producto = productManager.getProductById(productId);
                                    if (producto != null) {
                                        System.out.print("Cantidad: ");
                                        int quantity = Integer.parseInt(scanner.nextLine());
                                        pedido.put(producto, quantity);
                                    } else {
                                        System.out.println("Producto no encontrado.");
                                    }

                                    System.out.print("¿Agregar otro producto? (s/n): ");
                                    continuar = scanner.nextLine();
                                } while (continuar.equalsIgnoreCase("s"));

                                orderManager.addOrder(orderId, pedido);
                                System.out.println("Pedido creado.");
                            }
                            case 2 -> {
                                System.out.println("== Lista de Pedidos ==");
                                Map<Integer, HashMap<Product, Integer>> orders = orderManager.getAllOrders();
                                if (orders.isEmpty()) {
                                    System.out.println("No hay pedidos registrados.");
                                } else {
                                    for (Map.Entry<Integer, HashMap<Product, Integer>> entry : orders.entrySet()) {
                                        System.out.println("Pedido ID: " + entry.getKey());
                                        for (Map.Entry<Product, Integer> producto : entry.getValue().entrySet()) {
                                            System.out.println("- " + producto.getKey().getName() + " x" + producto.getValue());
                                        }
                                    }
                                }
                            }
                            case 3 -> System.out.println("Volviendo al menú principal...");
                            default -> System.out.println("Opción no válida.");
                        }
                    } while (subOption != 3);
                }

                case 9 -> {
                    System.out.println("== Gestión de usuarios ==");
                    System.out.println("1. Crear nuevo usuario");
                    System.out.println("2. Eliminar usuario");
                    System.out.println("3. Volver al menú principal");
                    System.out.print("Opción: ");
                    int userOption = Integer.parseInt(scanner.nextLine());
                    switch (userOption) {
                        case 1 -> {
                            System.out.print("Nuevo nombre de usuario: ");
                            String newUser = scanner.nextLine();
                            System.out.print("Nueva contraseña: ");
                            String newPass = new String(System.console().readPassword());

                            loginManager.createUser(newUser, newPass);
                            System.out.println("Usuario creado con éxito.");
                        }
                        case 2 -> {
                            System.out.print("Nombre de usuario a eliminar: ");
                            String userToDelete = scanner.nextLine();
                            loginManager.deleteUser(userToDelete);
                            System.out.println("Usuario eliminado si existía.");
                        }
                        case 3 -> System.out.println("Volviendo al menú principal...");
                        default -> System.out.println("Opción inválida.");
                    }
                }
                case 10 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción no válida.");

            }
        } while (option != 10);

        scanner.close();
    }
}
