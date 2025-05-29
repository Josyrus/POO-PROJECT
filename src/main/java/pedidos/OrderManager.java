package pedidos;
import product.Product;
import java.util.*;

public class OrderManager {
    private HashMap<Integer, HashMap<Product, Integer>> orders;
    private int nextOrderId;
    
    public OrderManager() {
        orders = new HashMap<>();
        nextOrderId = 1;
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

        // Si hay suficiente stock, reducir el stock de cada producto
        for (Map.Entry<Product, Integer> entry : productsToOrder.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            product.setStock(product.getStock() - quantity);
        }

        // Registrar el pedido
        orders.put(nextOrderId, productsToOrder);
        System.out.println("Pedido creado con ID: " + nextOrderId);
        nextOrderId++;
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

        // Reponer el stock
        HashMap<Product, Integer> order = orders.get(orderId);
        for (Map.Entry<Product, Integer> entry : order.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            product.setStock(product.getStock() + quantity);
        }


        // Eliminar el pedido
        orders.remove(orderId);
        System.out.println("Pedido cancelado y stock repuesto para el ID: " + orderId);
    }
}
