package product;

import java.io.*;
import java.util.*;

public class ProductManager {
    private List<Product> products;
    private final String FILE_PATH = "data/productos.txt";

    public ProductManager() {
        this.products = new ArrayList<>();
        loadFromFile();
    }

    // Carga productos desde el archivo
    private void loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs(); // crea carpetas si no existen
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Error creando archivo de productos: " + e.getMessage());
            }
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 7) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String desc = parts[2];
                    double price = Double.parseDouble(parts[3]);
                    int stock = Integer.parseInt(parts[4]);
                    String category = parts[5];
                    String barcode = parts[6];
                    products.add(new Product(id, name, desc, price, stock, category, barcode));
                }
            }
        } catch (IOException e) {
            System.out.println("Error leyendo archivo de productos: " + e.getMessage());
        }
    }

    // Guarda productos al archivo (sobrescribe todo)
    private void saveToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Product p : products) {
                pw.println(String.join(",",
                    String.valueOf(p.getId()),
                    p.getName(),
                    p.getDescription(),
                    String.valueOf(p.getPrice()),
                    String.valueOf(p.getStock()),
                    p.getCategory(),
                    p.getBarcode()
                ));
            }
        } catch (IOException e) {
            System.out.println("Error guardando archivo de productos: " + e.getMessage());
        }
    }

    public void addProduct(Product product) {
        products.add(product);
        saveToFile();
    }

    public Product getProductById(int id) {
        for (Product p : products) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public boolean removeProductById(int id) {
        boolean removed = products.removeIf(p -> p.getId() == id);
        if (removed) saveToFile();
        return removed;
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    public boolean updateStock(int productId, int quantityChange) {
        Product p = getProductById(productId);
        if (p != null) {
            int newStock = p.getStock() + quantityChange;
            if (newStock >= 0) {
                p.setStock(newStock);
                saveToFile();
                return true;
            }
        }
        return false;
    }

    public List<Product> searchByName(String namePart) {
        List<Product> result = new ArrayList<>();
        for (Product p : products) {
            if (p.getName().toLowerCase().contains(namePart.toLowerCase())) {
                result.add(p);
            }
        }
        return result;
    }
}
