package clientes;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
public class Customer implements Serializable{
    // Attributes
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private String alias;
    private String direction;
    private int phone;
    // Constructor
    protected Customer(int id, String name, String alias, String direction, int phone) {
        this.id = id;
        this.name = name;
        this.alias = alias;
        this.direction = direction;
        this.phone = phone;
    }
    // Getters and Setters
    protected int getId() {
        return id;
    }
    protected void setId(int id) {
        this.id = id;
    }
    protected String getName() {
        return name;
    }
    protected void setName(String name) {
        this.name = name;
    }
    protected String getAlias() {
        return alias;
    }
    protected void setAlias(String alias) {
        this.alias = alias;
    }
    protected String getDirection() {
        return direction;
    }
    protected void setDirection(String direction) {
        this.direction = direction;
    }
    protected int getPhone() {
        return phone;
    }
    protected void setPhone(int phone) {
        this.phone = phone;
    }
    public String toString() {
        return "Customer [id=" + id + ", name=" + name + ", alias=" + alias + ", direction=" + direction + ", phone=" + phone + "]";
    }
    public String toArchive() {
        return toHex(Integer.toString(id)) + "," + toHex(name) + "," + toHex(alias) + "," + toHex(direction) + "," + toHex(Integer.toString(phone));
    }
    private String toHex(String text) {
        StringBuilder sb = new StringBuilder();
        for (byte b : text.getBytes(StandardCharsets.UTF_8)) {
            sb.append(String.format("%02X", b));
    }
        return sb.toString();
    }
}