package restaurant;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class User implements Serializable {
    protected String username;
    protected String password;
    protected String name;
    protected String role;
    protected String phone;
    protected static ArrayList<User> sharedUsers = new ArrayList<>();

    public User() {}

    public User(String username, String password, String name, String role, String phone) {
        if (username == null || password == null || name == null || role == null|| phone==null) {
            throw new IllegalArgumentException("Username, password, name, and role cannot be null");
        }
        this.username = username;
        this.password = password;
        this.name = name;
        this.role = role;
        this.phone=phone;
    }

    public abstract void displayOptions();

    // Static method to add user to the sharedUsers list after validation
    public static void addUserToSharedList(User user) throws IllegalArgumentException {
        if (user == null || user.getUsername() == null || user.getPassword() == null || user.getName() == null || user.getRole() == null ||user.getPhone()==null) {
            throw new IllegalArgumentException("User or one of its fields cannot be null");
        }
        sharedUsers.add(user); // Add to the shared list only after validation
    }

    // Other methods (saveUsers, loadUsers, write, read) remain the same
    public static void saveUsers(ArrayList<User> sharedUsers, User newUser) throws IOException {
        /*if (sharedUsers == null) {
            sharedUsers = new ArrayList<>();
        }

        if (newUser.getUsername() == null || newUser.getPassword() == null || newUser.getName() == null ||newUser.getPhone()==null) {
            throw new IOException("Cannot save user with null values.");
        }

        sharedUsers.add(newUser);*/

        try (RandomAccessFile raf = new RandomAccessFile("Users.dat", "rw")) {
            raf.setLength(0); // Clear the file before writing
            for (User user : sharedUsers) {
                raf.writeUTF(user.role);
                raf.writeUTF(user.username);
                raf.writeUTF(user.password);
                raf.writeUTF(user.name);
                raf.writeUTF(user.phone);
            }
        } catch (IOException e) {
            throw new IOException("Error saving users: " + e.getMessage());
        }
    }

    public static ArrayList<User> loadUsers() throws IOException, ClassNotFoundException {
        ArrayList<User> sharedUsers = new ArrayList<>();

        try (RandomAccessFile raf = new RandomAccessFile("Users.dat", "r")) {
            while (raf.getFilePointer() < raf.length()) {
                String role = raf.readUTF(); 

                
                User user = null;
                switch (role.toLowerCase()) {
                    case "admin":
                        user = new Admin();
                        break;
                    case "guest":
                        user = new Guest();
                        break;
                    case "receptionist":
                        user = new Receptionist();
                        break;
                    default:
                        throw new IOException("Unknown role type: " + role);
                }

                user.read(raf); // Read user data
                sharedUsers.add(user); // Add user to the list
            }
        } catch (IOException e) {
            throw new IOException("Error loading users: " + e.getMessage());
        }

        return sharedUsers;
    }

    public void write(RandomAccessFile raf) throws IOException {
        if (username == null || password == null || name == null || role == null) {
            throw new IOException("Cannot save user with null values.");
        }

        raf.writeUTF(getUsername());
        raf.writeUTF(getPassword());
        raf.writeUTF(getName());
        raf.writeUTF(getRole());

        if (this instanceof Admin) {
            raf.writeUTF("Admin");
        } else if (this instanceof Guest) {
            raf.writeUTF("Guest");
            Guest guest = (Guest) this;
            raf.writeUTF(guest.getPhone());
        } else if (this instanceof Receptionist) {
            raf.writeUTF("Receptionist");
        }
    }

    public static User read(RandomAccessFile raf) throws IOException {
       
        String username = raf.readUTF();
        String password = raf.readUTF();
        String name = raf.readUTF();
        String role = raf.readUTF();
        String phone=raf.readUTF();
      
        switch (role) {
            case "Admin":
                return new Admin(username, password, name, role,phone);
            case "Guest":
           
                return new Guest(username, password, name, role, phone);
            case "Receptionist":
                return new Receptionist(username, password, name, role,phone);
            default:
                throw new IOException("Unknown role type: " + role);
        }
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public static ArrayList<User> getSharedUsers() {
        return sharedUsers;
    }
}
