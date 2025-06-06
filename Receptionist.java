/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant;
import java.util.ArrayList;
import java.util.Date;
import java.io.*;
import java.util.HashMap;

public class Receptionist extends User implements Serializable {
    private ArrayList<Reservation> reservations;
    private ArrayList<Table> tables;
    private ArrayList<Meal> menuItems;
    private static HashMap<Integer, Integer> tableReservation = new HashMap<>();
    private static int mostReservedTable = 0;
    private static HashMap<String, Integer> mealOrder = new HashMap<>();
    private static String mostOrderedMeal = null;
   

    public Receptionist() {
        reservations = new ArrayList<>();
        tables = new ArrayList<>();
        menuItems = new ArrayList<>();
    }

    public Receptionist(String username, String password, String name, String role,String phone) {
        super(username, password ,name, "Receptionist",phone);
    }

    public void createReservations(int rID, Date dT, int TID, String GName, String phone) {
        reservations.add(new Reservation(rID, dT, TID, GName, phone));
        addReservation(TID); // Track table reservations
    }

    public void addMealToReservation(int reservationNo, Meal meal, double mealPrice) {
        if (reservationNo >= 0 && reservationNo < reservations.size()) {
            reservations.get(reservationNo).addMeal(meal);
            reservations.get(reservationNo).CalculatePayment();
            addMealOrder(meal); // Track meal orders
        } else {
            System.out.println("Invalid Reservation number. Please re-enter the reservation number.");
        }
    }

    public void cancelReservation(int reservationNo) {
        if (reservationNo >= 0 && reservationNo < reservations.size()) {
            reservations.remove(reservationNo);
            System.out.println("Reservation cancelled.");
        } else {
            System.out.println("Invalid reservation number!");
        }
    }

    public void viewReservations() {
        if (reservations.isEmpty()) {
            System.out.println("No reservations available.");
        } else {
            for (int i = 0; i < reservations.size(); i++) {
                System.out.println("Reservation number " + (i + 1) + ": " + reservations.get(i));
            }
        }
    }

    public void saveReservations() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Reservations.dat"))) {
            oos.writeObject(reservations);
            System.out.println("Reservations have been saved to a file.");
        } catch (IOException e) {
            System.err.println("Error saving reservations to file: " + e.getMessage());
        }
    }

    public void loadReservations() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Reservations.dat"))) {
            reservations = (ArrayList<Reservation>) ois.readObject();
            System.out.println("Reservations have been loaded from the file 'Reservations.dat'.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading reservations from file: " + e.getMessage());
        }
    }

    public void loadReservations(String phone) {
        ArrayList<Reservation> guestReservations = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Reservations.dat"))) {
            ArrayList<Reservation> allReservations = (ArrayList<Reservation>) ois.readObject();

            for (Reservation reservation : allReservations) {
                if (reservation.getPhone().equals(phone)) {
                    guestReservations.add(reservation);
                }
            }

            if (guestReservations.isEmpty()) {
                System.out.println("No reservations found for Guest ID: " + phone);
            } else {
                System.out.println("Reservations for Guest ID " + phone + ":");
                for (Reservation res : guestReservations) {
                    System.out.println(res);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading reservations from file: " + e.getMessage());
        }
    }

    @Override
    public void displayOptions() {
        System.out.println("1. Create Reservation");
        System.out.println("2. Add meal to Reservation");
        System.out.println("3. Cancel Reservation");
        System.out.println("4. View Reservations");
        System.out.println("5. View Most Reserved Table");
        System.out.println("6. View Most Ordered Meal");
        System.out.println("7. Exit");
        
    }

    public ArrayList<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(ArrayList<Reservation> reservations) {
        this.reservations = reservations;
    }

    public ArrayList<Table> getTables() {
        return tables;
    }

    public void setTables(ArrayList<Table> tables) {
        this.tables = tables;
    }

    public ArrayList<Meal> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(ArrayList<Meal> menuItems) {
        this.menuItems = menuItems;
    }

    private static void addReservation(int tableID) {
        tableReservation.put(tableID, tableReservation.getOrDefault(tableID, 0) + 1);
        if (mostReservedTable == 0 || tableReservation.get(tableID) > tableReservation.get(mostReservedTable)) {
            mostReservedTable = tableID;
        }
    }

    private static void addMealOrder(Meal meal) {
        mealOrder.put(meal.getName(), mealOrder.getOrDefault(meal.getName(), 0) + 1);
        if (mostOrderedMeal == null || mealOrder.get(meal.getName()) > mealOrder.get(mostOrderedMeal)) {
            mostOrderedMeal = meal.getName();
        }
    }

    public static void mostReservedTable() {
        if (mostReservedTable != 0) {
            System.out.println("Most Reserved Table: Table " + mostReservedTable);
        } else {
            System.out.println("No reservations yet.");
        }
    }

    public static void mostOrderedMeal() {
        if (mostOrderedMeal != null) {
            System.out.println("Most Ordered Meal: " + mostOrderedMeal);
        } else {
            System.out.println("No meals ordered yet.");
            
        }
    }
 

}
