package src.main.java.clientes;
import java.util.ArrayList;
import src.main.java.clientes.Customer;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
public class CustomerManger {
    private ArrayList<Customer> customers;
    ByteArrayOutputStream byteArrayOutputStream;
    FileOutputStream fileOutputStream;
    public CustomerManger() {
        customers = new ArrayList<>();
        byteArrayOutputStream = new ByteArrayOutputStream();
        fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream("customers.txt");
        } catch (IOException e) {
            e.printStackTrace();
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
        // Logic to write customer to a file
    }
    public void ReadCustomerFromFile() {
        // Logic to read customer from a file
    }
}