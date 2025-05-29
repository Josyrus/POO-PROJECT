package ventas;
import java.util.HashMap;
import java.util.Map;
import product.Product;

public class SaleManager {
    private Map<Integer, HashMap<Product, Integer>> sales;
    private int nextSaleId;

    public SaleManager() {
        sales = new HashMap<>();
        nextSaleId = 1;
    }

    public void createSale(HashMap<Product, Integer> saleItems) {
        sales.put(nextSaleId, saleItems);
        System.out.println("Venta registrada con ID: " + nextSaleId);
        nextSaleId++;
    }

    public HashMap<Product, Integer> getSale(int saleId) {
        HashMap<Product, Integer> sale = sales.get(saleId);
        if (sale != null) {
            System.out.println("Venta encontrada: ID " + saleId);
            return sale;
        } else {
            System.out.println("No se encontró la venta con ID: " + saleId);
            return null;
        }
    }

    public void deleteSale(int saleId) {
        if (sales.containsKey(saleId)) {
            sales.remove(saleId);
            System.out.println("Venta eliminada: ID " + saleId);
        } else {
            System.out.println("No se encontró la venta con ID: " + saleId);
        }
    }

    public void generateSalesReport() {
        System.out.println("=== Reporte de Ventas ===");
        if (sales.isEmpty()) {
            System.out.println("No hay ventas registradas.");
            return;
        }

        for (Map.Entry<Integer, HashMap<Product, Integer>> entry : sales.entrySet()) {
            int saleId = entry.getKey();
            System.out.println("Venta ID: " + saleId);
            HashMap<Product, Integer> saleItems = entry.getValue();

            for (Map.Entry<Product, Integer> item : saleItems.entrySet()) {
                Product product = item.getKey();
                int quantity = item.getValue();
                System.out.println(" - " + product.getName() + " x" + quantity + " | $" + product.getPrice());
            }
        }
    }

    public Map<Integer, HashMap<Product, Integer>> getAllSales() {
        return sales;
    }
}
