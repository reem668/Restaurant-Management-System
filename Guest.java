/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package restaurant;

import java.util.ArrayList;
import java.io.*;
public class Guest extends User {
 
    public Guest(){}
    public Guest(String username, String password, String name, String role,String phone) {
        super(username, password, name, role,phone);
       
    }
      public void viewReservations(String phone) {
    ArrayList<Reservation> guestReservations = new ArrayList<>();

    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Reservations.dat"))) {
        ArrayList<Reservation> allReservations = (ArrayList<Reservation>) ois.readObject();

        // Filter reservations by guest ID
        for (Reservation reservation : allReservations) {
            if (reservation.getPhone() == phone) {
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setRating(int rating) {
    if (rating < 1 || rating > 5) {
        System.out.println("Invalid rating . Please provide a rating between 1 and 5.");
        return;
    }
    else {
    System.out.println("Rating updated successfully to: " + rating);
    }
    }
    @Override
    public void displayOptions() {
        System.out.println("1. View Menu");
        System.out.println("2. View History of Reservations");
        System.out.println("3. Rate a Reservation");
        System.out.println("4. Exit");
    }

}

