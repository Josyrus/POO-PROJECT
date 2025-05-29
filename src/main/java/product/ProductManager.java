package product;
import java.util.ArrayList;
import java.util.List;

public class ProductManager {
    private List<Product> products;

    public ProductManager() {
        this.products = new ArrayList<>();
    }

    // Agregar producto
    public void addProduct(Product product) {
        products.add(product);
    }

    // Buscar producto por ID
    public Product getProductById(int id) {
        for (Product p : products) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null; // no encontrado
    }

    // Eliminar producto por ID
    public boolean removeProductById(int id) {
        return products.removeIf(p -> p.getId() == id);
    }

    // Listar todos los productos
    public List<Product> getAllProducts() {
        return new ArrayList<>(products); // se devuelve una copia para evitar modificaciones externas
    }

    // Actualizar stock (ejemplo: compra o venta)
    public boolean updateStock(int productId, int quantityChange) {
        Product p = getProductById(productId);
        if (p != null) {
            int newStock = p.getStock() + quantityChange;
            if (newStock >= 0) {
                p.setStock(newStock);
                return true;
            }
        }
        return false;
    }

    // Buscar por nombre (opcional)
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
