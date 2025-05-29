package pedidos;

import product.Product;
import product.ProductManager; // para buscar productos por id al cargar
import java.io.*;
import java.util.*;

public class OrderManager {
    private HashMap<Integer, HashMap<Product, Integer>> orders;
    private int nextOrderId;
    private final String FILE_PATH = "data/orders.txt";
    private ProductManager productManager; // Para referencia de productos existentes

    public OrderManager(ProductManager pm) {
        this.productManager = pm;
        orders = new HashMap<>();
        nextOrderId = 1;
        loadFromFile();
    }

    private void loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Error creando archivo de órdenes: " + e.getMessage());
            }
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    int orderId = Integer.parseInt(parts[0]);
                    HashMap<Product, Integer> productsInOrder = new HashMap<>();

                    String[] items = parts[1].split(",");
                    for (String item : items) {
                        String[] prodQty = item.split(":");
                        if (prodQty.length == 2) {
                            int productId = Integer.parseInt(prodQty[0]);
                            int quantity = Integer.parseInt(prodQty[1]);
                            Product product = productManager.getProductById(productId);
                            if (product != null) {
                                productsInOrder.put(product, quantity);
                            }
                        }
                    }
                    orders.put(orderId, productsInOrder);
                    if (orderId >= nextOrderId) {
                        nextOrderId = orderId + 1;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error leyendo archivo de órdenes: " + e.getMessage());
        }
    }

    private void saveToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Map.Entry<Integer, HashMap<Product, Integer>> entry : orders.entrySet()) {
                int orderId = entry.getKey();
                HashMap<Product, Integer> productsInOrder = entry.getValue();
                StringBuilder sb = new StringBuilder();
                sb.append(orderId).append(";");

                List<String> prodStrings = new ArrayList<>();
                for (Map.Entry<Product, Integer> pEntry : productsInOrder.entrySet()) {
                    prodStrings.add(pEntry.getKey().getId() + ":" + pEntry.getValue());
                }
                sb.append(String.join(",", prodStrings));
                pw.println(sb.toString());
            }
        } catch (IOException e) {
            System.out.println("Error guardando archivo de órdenes: " + e.getMessage());
        }
    }

    public void createOrder(HashMap<Product, Integer> productsToOrder) {
        for (Map.Entry<Product, Integer> entry : productsToOrder.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();

            if (product.getStock() < quantity) {
                System.out.println("No hay suficiente stock para el producto: " + product.getName());
                return;
            }
        }

        // Reducir stock
        for (Map.Entry<Product, Integer> entry : productsToOrder.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            product.setStock(product.getStock() - quantity);
        }

        // Registrar pedido
        orders.put(nextOrderId, productsToOrder);
        System.out.println("Pedido creado con ID: " + nextOrderId);
        nextOrderId++;
        saveToFile();
    }

    public void addOrder(int orderId, HashMap<Product, Integer> items) {
        orders.put(orderId, items);
        saveToFile();
    }

    public void getOrder(int orderId) {
        if (orders.containsKey(orderId)) {
            System.out.println("Pedido #" + orderId);
            HashMap<Product, Integer> order = orders.get(orderId);
            for (Map.Entry<Product, Integer> entry : order.entrySet()) {
                System.out.println("- " + entry.getKey().getName() + " x" + entry.getValue());
            }
        } else {
            System.out.println("No se encontró el pedido con ID: " + orderId);
        }
    }

    public Map<Integer, HashMap<Product, Integer>> getAllOrders() {
        return orders;
    }

    public void cancelOrder(int orderId) {
        if (!orders.containsKey(orderId)) {
            System.out.println("No se encontró el pedido con ID: " + orderId);
            return;
        }

        // Reponer stock
        HashMap<Product, Integer> order = orders.get(orderId);
        for (Map.Entry<Product, Integer> entry : order.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            product.setStock(product.getStock() + quantity);
        }

        // Eliminar pedido
        orders.remove(orderId);
        System.out.println("Pedido cancelado y stock repuesto para el ID: " + orderId);
        saveToFile();
    }
}
