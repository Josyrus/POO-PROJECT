package reportes;

import ventas.SaleManager;
import pedidos.OrderManager;
import clientes.CustomerManager;
import product.Product;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReportManager {
    private SaleManager saleManager;
    private OrderManager orderManager;
    private CustomerManager customerManger;

    public ReportManager(SaleManager saleManager, OrderManager orderManager, CustomerManager customerManger) {
        this.saleManager = saleManager;
        this.orderManager = orderManager;
        this.customerManger = customerManger;
    }

    public void generateSalesReport() {
        System.out.println("=== Reporte de Ventas ===");
        try (PrintWriter writer = new PrintWriter(new FileWriter("reporte_ventas.txt"))) {
            Map<Integer, ArrayList<Product>> sales = saleManager.getAllSales();
            for (Map.Entry<Integer, ArrayList<Product>> entry : sales.entrySet()) {
                int saleId = entry.getKey();
                String saleInfo = "Venta ID: " + saleId;
                System.out.println(saleInfo);
                writer.println(saleInfo);

                for (Product p : entry.getValue()) {
                    String productLine = " - " + p.getName() + " | Precio: $" + p.getPrice();
                    System.out.println(productLine);
                    writer.println(productLine);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al escribir el reporte de ventas: " + e.getMessage());
        }
    }

    public void generateCustomerReport() {
        System.out.println("=== Reporte de Clientes ===");
        try (PrintWriter writer = new PrintWriter(new FileWriter("reporte_clientes.txt"))) {
            // Suponiendo que tienes un método que devuelve los clientes como texto:
            for (String cliente : customerManger.getCustomerReportLines()) {
                System.out.println(cliente);
                writer.println(cliente);
            }
        } catch (IOException e) {
            System.out.println("Error al escribir el reporte de clientes: " + e.getMessage());
        }
    }

    public void generateOrderReport() {
        System.out.println("=== Reporte de Pedidos ===");
        try (PrintWriter writer = new PrintWriter(new FileWriter("reporte_pedidos.txt"))) {
            Map<Integer, HashMap<Product, Integer>> orders = orderManager.getAllOrders();
            for (Map.Entry<Integer, HashMap<Product, Integer>> entry : orders.entrySet()) {
                int orderId = entry.getKey();
                String orderInfo = "Pedido ID: " + orderId;
                System.out.println(orderInfo);
                writer.println(orderInfo);

                for (Map.Entry<Product, Integer> productEntry : entry.getValue().entrySet()) {
                    Product p = productEntry.getKey();
                    int qty = productEntry.getValue();
                    String productLine = " - " + p.getName() + " x" + qty;
                    System.out.println(productLine);
                    writer.println(productLine);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al escribir el reporte de pedidos: " + e.getMessage());
        }
    }
}
