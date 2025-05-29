package auth;
import java.io.*;
import java.util.*;

public class LoginManager {
    private final String FILE_PATH = "data/usuarios.txt";

    public LoginManager() {
        ensureFileExists();
    }
    private String hashPassword(String password) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error al generar hash: " + e.getMessage());
        }
    }

    private void ensureFileExists() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                writer.println("admin,admin");
                System.out.println("Archivo de usuarios creado con usuario por defecto: admin / admin");
            } catch (IOException e) {
                System.out.println("Error creando archivo de usuarios: " + e.getMessage());
            }
        }
    }

    public boolean authenticate(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                //System.out.println("Leyendo línea: '" + line + "'");
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    //System.out.println("Comparando con usuario: '" + parts[0] + "', contraseña: '" + parts[1] + "'");
                    if (parts[0].trim().equals(username) && parts[1].trim().equals(password)) {
                        //System.out.println("Usuario autenticado correctamente.");
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error leyendo archivo de usuarios: " + e.getMessage());
        }
        return false;
    }


    public boolean createUser(String username, String password) {
        if (userExists(username)) return false;
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH, true))) {
            writer.println(username + "," + hashPassword(password));
            return true;
        } catch (IOException e) {
            System.out.println("Error al crear usuario: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteUser(String username) {
        List<String> lines = new ArrayList<>();
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith(username + ",")) {
                    lines.add(line);
                } else {
                    found = true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error leyendo usuarios: " + e.getMessage());
            return false;
        }

        if (!found) return false;

        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (String l : lines) writer.println(l);
            return true;
        } catch (IOException e) {
            System.out.println("Error guardando cambios al eliminar usuario: " + e.getMessage());
            return false;
        }
    }

    private boolean userExists(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(username + ",")) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error verificando existencia de usuario: " + e.getMessage());
        }
        return false;
    }
}
