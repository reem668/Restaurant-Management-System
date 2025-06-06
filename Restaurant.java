
package restaurant;


import java.util.Scanner;
import java.util.ArrayList;
import java.util.*;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Date;
import java.util.List;
import java.io.Serializable;
import java.util.Iterator;

public class Restaurant {

 public static boolean authenticateUser(String username, String password) throws IOException {
    // Open the RandomAccessFile in read mode
    try (RandomAccessFile raf = new RandomAccessFile("Users.dat", "r")) {
        
     
        while (raf.getFilePointer() < raf.length()) {
            
            String role = raf.readUTF();
            String storedUsername = raf.readUTF();
            String storedPassword = raf.readUTF();
            String name = raf.readUTF();

            
            if (storedUsername.equals(username) && storedPassword.equals(password)) {
                
                return true;
            }
        }
    } catch (IOException e) {
        throw new IOException("Error loading users from file: " + e.getMessage());
    }

    // Return false if no matching user is found
    return false;
}



  public static void main(String[] args) throws IOException, ClassNotFoundException {
    Scanner cred = new Scanner(System.in);
    ArrayList<User> sharedUsers = new ArrayList<>();

   Menu m = new Menu();
    Admin manager = new Admin("admin","123"," ","admin"," ",m);
    
    sharedUsers.add(manager);
   // System.out.println("Admin details: " + manager.getUsername() + ", " + manager.getPassword() + ", " + manager.getName() + ", " + manager.getRole() + ", " + manager.getPhone() + "," + manager.getMenu());

        try {
     
        manager.saveUsers(sharedUsers,manager);
        //sharedUsers = manager.loadUsers();
       
    } catch (IOException e) {
        System.out.println("An error occurred while saving or loading the user: " + e.getMessage());
        e.printStackTrace(); }
  /*  } catch (ClassNotFoundException e) {
        System.out.println("Class not found: " + e.getMessage());
        e.printStackTrace();
    }
*/

    int attempts = 0;
     while (true) {
    System.out.println("-------RESTAURANT MANAGEMENT SYSTEM------");
    System.out.println("1.Admin");
    System.out.println("2.Guest");
    System.out.println("3.Receptionist");
    Scanner input = new Scanner(System.in);
    int u = input.nextInt();
    switch (u) {
    case 1: // Admin
        Scanner scanner = new Scanner(System.in);

        String action;
        int tableID;
        ArrayList<String> newCategory;
        int capacity;
        boolean reserved;

        while (attempts < 3) {
            System.out.println("Enter Username: ");
            String auser = cred.next();
            System.out.println("Enter Password: ");
            String apass = cred.next();

            boolean aU = authenticateUser(auser, apass);
            if (aU) {
                System.out.println("Login successful!");
                manager.displayOptions();
                break; // Exit the login loop if login is successful
            } else {
                System.out.println("Incorrect Username or Password. Please try again.");
            }
            attempts++;
        }

        if (attempts == 3) {
            System.out.println("Too many failed attempts. Please try again later.");
            break; // Exit this case after 3 failed login attempts
        }

        // Admin options menu
        System.out.println("Enter your choice: ");
        int data = scanner.nextInt(); 
        scanner.nextLine(); // Consume leftover newline

        switch (data) {
            case 1: // Manage Tables
                System.out.println("---- Manage Tables ----");

                ArrayList<String> availableCategories = Table.loadCategoriesFromFile();
                System.out.println("Available Categories:");
                manager.displayCategories(availableCategories);

                System.out.println("Enter action (add/edit/remove): ");
                action = scanner.nextLine();

                System.out.println("Enter Table ID: ");
                tableID = scanner.nextInt();
                scanner.nextLine(); // Consume leftover newline

                if (action.equalsIgnoreCase("add") || action.equalsIgnoreCase("edit")) {
                    newCategory = new ArrayList<>();
                    System.out.println("Enter table categories (comma-separated): ");
                    String[] categories = scanner.nextLine().split(",");
                    for (String category : categories) {
                        newCategory.add(category.trim());
                    }

                    System.out.println("Enter capacity: ");
                    capacity = scanner.nextInt();

                    System.out.println("Is the table reserved? (true/false): ");
                    reserved = scanner.nextBoolean();

                    manager.manageTables(action, tableID, newCategory, capacity, reserved);
                } else if (action.equalsIgnoreCase("remove")) {
                    manager.manageTables(action, tableID, null, 0, false);
                } else {
                    System.out.println("Invalid action for managing tables!");
                }
                break;

            case 2: // Manage Menus
                System.out.println("Enter action (add/edit/remove): ");
                action = scanner.nextLine();

                System.out.println("Enter meal name: ");
                String name = scanner.nextLine();

                System.out.println("Enter meal category (e.g., breakfast, lunch, dinner, drinks, appetizers): ");
                String category = scanner.nextLine();

                double price = 0;
                if (action.equalsIgnoreCase("add") || action.equalsIgnoreCase("edit")) {
                    System.out.println("Enter meal price: ");
                    price = scanner.nextDouble();
                }

                manager.manageMenu(action, name, price, category);
                break;

            case 3: // Manage Users
                System.out.println("Enter action (add/edit/remove): ");
                action = scanner.nextLine();

                System.out.println("Enter username: ");
                String newUsername = scanner.nextLine();

                System.out.println("Enter user role: ");
                String role = scanner.nextLine();

                System.out.println("Enter user password: ");
                String apass = scanner.nextLine();

                System.out.println("Enter user name: ");
                String newName = scanner.nextLine();

                manager.manageUsers(action, newUsername, role, apass, newName, "");
                break;

            case 4: // View Reports
                manager.viewReports();
                break;

            case 5: // View Data
                manager.viewData();
                break;

            default:
                System.out.println("Invalid number! Please enter a number between 1 and 5.");
                break;
        }
        break; // Ensure Admin case ends here

    default:
        System.out.println("Invalid option. Please enter a valid user type.");
        break;


     
    case 2: //Guest
      System.out.println("1.Login");
      System.out.println("2.Sign Up");
      int x = cred.nextInt();
      switch (x) {
      case 1: //Login
        attempts = 0;
        while (attempts < 3) {
          System.out.println("Enter Username: ");
          String guser = cred.next();
          System.out.println("Enter Password: ");
          String gpass = cred.next();

          boolean aU = authenticateUser(guser, gpass);
          if (aU) {
            System.out.println("Login successful!");
            try {
              sharedUsers = User.loadUsers();
              if (sharedUsers == null || sharedUsers.isEmpty()) {
                System.out.println("No users found in the system.");
                break;
              }

              for (User currentUser : sharedUsers) {
            
                if (currentUser instanceof Guest && currentUser.getUsername().equals(guser) && currentUser.getPassword().equals(gpass)) {
                  Guest g = (Guest) currentUser;

                  System.out.println("-----Options-----");
                  g.displayOptions();
                  System.out.println("What Would You Like To Do?");
                  
                  int opt = cred.nextInt();
                  switch (opt) {
                  case 1:
                    
                    m.displayMenu();
                  case 2:
                    g.viewReservations(g.getPhone());
                    break;
                  case 3:
                    System.out.println("Please Enter a Rating Between 1 & 5");
                    int r = cred.nextInt();
                    g.setRating(r);
                    break;
                  case 4:
                    break;
                  }

                }
              }
            } catch (IOException | ClassNotFoundException e) {
              System.out.println("Error loading users: " + e.getMessage());
            }
            break; // Exit the login loop if login is successful
          } else {
            System.out.println("Incorrect Username or Password. Please try again.");
          }

          attempts++;
        }

        if (attempts == 3) {
          System.out.println("Too many failed attempts. Please try again later.");
        }
        break;

    case 2:
       
     

          System.out.println("Username: ");
          String guser = cred.next();
          System.out.println("Password: ");
          String gpass = cred.next();
          System.out.println("Name: ");
          String name = cred.next();
          System.out.println("Phone Number: ");
          String phone = cred.next();

          
          Guest g = new Guest(guser, gpass, name, "Guest", phone);
          sharedUsers.add(g);
         try {
               g.saveUsers(sharedUsers,g); 
    System.out.println("Sign-up Successful!");
} catch (Exception e) {
    System.out.println("An error occurred while saving the user: " + e.getMessage());
    e.printStackTrace(); 
}
          System.out.println("Sign-up Successful!");
           System.out.println("-----Options-----");
          g.displayOptions();
          System.out.println("What Would You Like To Do?");
          Scanner in = new Scanner(System.in);
          int opt = in.nextInt();
          switch (opt) {
          case 1:
            Menu mn = new Menu();
            mn.displayMenu();
            break;
          case 2:
            g.viewReservations(g.getPhone());
            break;
          case 3:
            System.out.println("Please Enter a Rating Between 1 & 5");
            int r = in.nextInt();
            g.setRating(r);
            break;
          case 4:
            break;
          }
          
        break;
         
         

       
      default:
        System.out.println("Invalid option.");
        break;
      }


      

    case 3:

      boolean exit = false;
      Receptionist receptionist = new Receptionist();

      attempts = 0;
      while (attempts < 3) {
        System.out.println("Enter Username: ");
        String ruser = cred.next();
        System.out.println("Enter Password: ");
        String rpass = cred.next();

        boolean aU = authenticateUser(ruser, rpass);
        if (aU) {
          System.out.println("Login successful!");
          try {
            sharedUsers = User.loadUsers();
            if (sharedUsers == null || sharedUsers.isEmpty()) {
              System.out.println("No users found in the system.");
              break;
            }

            for (Iterator < User > it = sharedUsers.iterator(); it.hasNext();) {
              User currentUser = it.next();
              if (currentUser instanceof Receptionist && currentUser.getUsername().equals(ruser) && currentUser.getPassword().equals(rpass)) {
                Receptionist r = (Receptionist) currentUser;

                receptionist.displayOptions();
                System.out.println("Please choose an option");

                int choice = input.nextInt();
                input.nextLine();

                switch (choice) {
                case 1: {

                  System.out.print("Please enter Reservstion ID: ");
                  int rID = input.nextInt();
                  System.out.print("Please enter Table ID: ");
                  int tID = input.nextInt();
                  input.nextLine();
                  System.out.print("Please enter Guest Name: ");
                  String gName = input.nextLine();
                  System.out.print("Please enter Guest ID: ");
                  String phone = input.next();
                  Date date = new Date();
                  receptionist.createReservations(rID, date, tID, gName,phone );
                  System.out.println("Reservation created successfully");
                  break;

                }
                case 2: {
                  System.out.print("Enter Reservation Number: ");
                  int reservationNo = input.nextInt();
                  input.nextLine();
                  System.out.println("Available Meals:");
                  Menu menu = new Menu();
                  int index = 1;
                  System.out.println("Breakfast: ");
                  for (Meal meal: menu.getBreakfast()) {
                    System.out.println(index++ + "." + meal.getName() + " -$" + meal.getPrice());
                  }
                  System.out.println("Lunch: ");
                  for (Meal meal: menu.getBreakfast()) {
                    System.out.println(index++ + "." + meal.getName() + " -$" + meal.getPrice());
                  }

                  System.out.println("Dinner: ");
                  for (Meal meal: menu.getBreakfast()) {
                    System.out.println(index++ + "." + meal.getName() + " -$" + meal.getPrice());
                  }

                  System.out.println("Drinks: ");
                  for (Meal meal: menu.getBreakfast()) {
                    System.out.println(index++ + "." + meal.getName() + " -$" + meal.getPrice());
                  }
                  System.out.println("Appetizers: ");
                  for (Meal meal: menu.getBreakfast()) {
                    System.out.println(index++ + "." + meal.getName() + " -$" + meal.getPrice());
                  }

                  System.out.print("Enter the number corrosponding to the meal");
                  int mealchoice = input.nextInt();
                  Meal selected = null;
                  index = 1;
                  for (Meal meal: menu.getBreakfast()) {
                    if (index == mealchoice) {
                      selected = meal;
                      break;
                    }
                    index++;
                  }
                  if (selected == null) {
                    for (Meal meal: menu.getLunch()) {
                      if (index == mealchoice) {
                        selected = meal;
                        break;
                      }
                      index++;
                    }
                  }
                  if (selected == null) {
                    for (Meal meal: menu.getDinner()) {
                      if (index == mealchoice) {
                        selected = meal;
                        break;
                      }
                      index++;
                    }

                  }

                  if (selected == null) {
                    for (Meal meal: menu.getDrinks()) {
                      if (index == mealchoice) {
                        selected = meal;
                        break;

                      }
                      index++;
                    }
                  }

                  if (selected == null) {
                    for (Meal meal: menu.getAppetizers()) {
                      if (index == mealchoice) {
                        selected = meal;
                        break;
                      }
                      index++;
                    }
                  }
                  if (selected != null) {
                    receptionist.addMealToReservation(reservationNo, selected, selected.getPrice());
                    receptionist.saveReservations();
                    System.out.println("Added Meal: " + selected.getName() + "-$" + selected.getPrice());
                  } else {
                    System.out.println("Meal is not available");
                  }
                  break;
                }

                case 3: {
                  System.out.println("Enter Reservation Number to Cancel");
                  int reservationNo = input.nextInt();
                  receptionist.cancelReservation(reservationNo);
                  break;
                }
                case 4: {
                  receptionist.loadReservations();
                }
                case 5: {
                  receptionist.mostReservedTable();
                  break;
                }
                case 6: {
                  receptionist.mostOrderedMeal();
                  break;
                }
                case 7: {
                  exit = true;
                  System.out.println("Exiting the program");
                  break;
                }
                default: {
                  System.out.println("Please enter a valid choice.");
                }

                }
              }
            }

          } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading users: " + e.getMessage());
          }
          break; // Exit the login loop if login is successful
        } else {
          System.out.println("Incorrect Username or Password. Please try again.");
        }

        attempts++;
      }

      if (attempts == 3) {
        System.out.println("Too many failed attempts. Please try again later.");
      }

    }
  }
}
 }
