package clientes;
import java.util.ArrayList;
import java.util.List;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
public class CustomerManager {
    private static final String ARCHIVO_CLIENTES = "data/customers.txt";
    private ArrayList<Customer> customers;
    ByteArrayOutputStream byteArrayOutputStream;
    FileOutputStream fileOutputStream;
    public CustomerManager() {
        customers = new ArrayList<>();
        byteArrayOutputStream = new ByteArrayOutputStream();
        fileOutputStream = null;
    }
    static{
        File archivo = new File(ARCHIVO_CLIENTES);
        try {
            archivo.getParentFile().mkdirs();
            archivo.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void printAllCustomers() {
        for (Customer c : customers) {
            System.out.println("ID: " + c.getId() + ", Nombre: " + c.getName() + ", Alias: " + c.getAlias());
        }
    }
    public void addCustomer(int id, String name, String alias, String direction, int phone) {
        // Logic to add a customer
        customers.add(new Customer(id, name, alias, direction, phone));
        // Save customer to database or list
    }
    public Customer getCustomer(int id) {
        // Logic to retrieve a customer by ID
        for (Customer customer : customers) {
            if (customer.getId() == id) {
                return customer;
            }
        }
        return null; // Customer not found
    }
    public ArrayList<Customer> getAllCustomers() {
        // Logic to retrieve all customers
        return customers;
    }
    public void updateCustomer(int id, String name, String alias, String direction, int phone) {
        // Logic to update a customer by ID
        Customer customer = getCustomer(id);
        if (customer != null) {
            customer.setName(name);
            customer.setAlias(alias);
            customer.setDirection(direction);
            customer.setPhone(phone);
            // Save updated customer to database or list
        }
    }
    public void deleteCustomer(int id) {
        // Logic to delete a customer by ID
        Customer customer = getCustomer(id);
        if (customer != null) {
            deleteCustomer(customer);
            customers.remove(customer);
            // Delete customer from database or list
        }
    }
    public void deleteCustomer(String nameOrAlias) {
        // Iterar una sola vez sobre la lista
        for (Customer customer : customers) {
            if (customer.getName().equals(nameOrAlias) || customer.getAlias().equals(nameOrAlias)) {
                deleteCustomer(customer);  // Lógica adicional para eliminar al cliente
                customers.remove(customer); // Eliminar de la lista
                break; // Salir del bucle una vez encontrado y eliminado
            }
        }
    }
    public void deleteCustomer(Customer customer) {
        customer.setAlias(null); // Logic to delete a customer
        customer.setDirection(null); // Logic to delete a customer
        customer.setId(0); // Logic to delete a customer
        customer.setName(null); // Logic to delete a customer
        customer.setPhone(0); // Logic to delete a customer     
        customer = null; // Logic to delete a customer
    }
    
    public void WriteCustomerToFile(Customer customer) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_CLIENTES, true))){
            writer.write(customer.toArchive());
            writer.newLine();
            System.out.println("Cliente registrado con ID: " + customer.getId());
        }catch (IOException e){
            System.out.println("Error al guardar el cliente.");
            e.printStackTrace();
        }
    }
    public void ReadCustomerFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_CLIENTES))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    int id = Integer.parseInt(fromHex(parts[0]));
                    String name = fromHex(parts[1]);
                    String alias = fromHex(parts[2]);
                    String direction = fromHex(parts[3]);
                    int phone = Integer.parseInt(fromHex(parts[4]));
                    Customer customer = new Customer(id, name, alias, direction, phone);
                    customers.add(customer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String fromHex(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2)
        {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)+ Character.digit(hex.charAt(i+1), 16));
        }
        return new String(data, StandardCharsets.UTF_8);
    }
    public List<String> getCustomerReportLines() {
        List<String> lines = new ArrayList<>();
    for (Customer c : customers) {
        lines.add(c.getId() + " - " + c.getName());
    }
        return lines;
    }
}