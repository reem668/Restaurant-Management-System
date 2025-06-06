/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant;


import java.util.Date;       // For Date object
import java.util.List;       // For List interface
import java.util.ArrayList;

public class Reservation {

    
    private int ResID;
    private Date dateTime;
    private int TableID;
    private String GuestName;
    private String Phone;
    private double totalPayment;
    private List<Meal> orderedMeals;

    public Reservation() {
    }

    public Reservation(int ResID, Date dateTime, int TableID, String GuestName, String Phone) {
        this.ResID = ResID;
        this.dateTime = dateTime;
        this.TableID = TableID;
        this.GuestName = GuestName;
        this.Phone = Phone;
        this.orderedMeals = new ArrayList<>();
    }

    public double CalculatePayment() {
        this.totalPayment = 0.0;
        for (Meal meal : orderedMeals) {
            totalPayment += meal.getPrice();
        }
        return totalPayment;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public int getTableID() {
        return TableID;
    }

    public String getPhone() {
        return Phone;
    }

    public double getPayment() {
        return totalPayment;
    }

    public int getResID() {
        return ResID;
    }

    public String getGuestName() {
        return GuestName;
    }

    public Date getdateTime() {
        return dateTime;
    }

    public List<Meal> getOrderedMeals() {
        return orderedMeals;
    }

    public void addMeal(Meal meal) {
        orderedMeals.add(meal);
        totalPayment += meal.getPrice();
    }

    public void removeMeal(Meal meal) {
        orderedMeals.remove(meal);
        totalPayment -= meal.getPrice();
    }
}
