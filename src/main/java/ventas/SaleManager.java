package ventas;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import product.Product;

public class SaleManager {
    private HashMap<Integer, ArrayList<Product>> sales;
    private int nextSaleId;

    public SaleManager() {
        sales = new HashMap<>();
        nextSaleId = 1;
    }

    public void createSale(ArrayList<Product> products) {
        sales.put(nextSaleId, products);
        System.out.println("Venta creada con ID: " + nextSaleId);
        nextSaleId++;
    }

    public ArrayList<Product> getSale(int saleId) {
        ArrayList<Product> sale = sales.get(saleId);
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
    public Map<Integer, ArrayList<Product>> getAllSales() {
        return sales;
    }

}
