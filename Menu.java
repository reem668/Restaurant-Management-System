/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant;

import java.io.IOException;
import java.util.*;
import java.io.RandomAccessFile;
    
public class Menu {
    
    ArrayList<Meal> breakfast = new ArrayList<>();
    ArrayList<Meal> appetizers = new ArrayList<>();
    ArrayList<Meal> lunch = new ArrayList<>();
    ArrayList<Meal> dinner = new ArrayList<>();
    ArrayList<Meal> drinks = new ArrayList<>();
    
    
     public Menu() {
        // Add meals to each category
        breakfast.add(new Meal("Pancakes", "Breakfast", 5.99));
        breakfast.add(new Meal("Omelette", "Breakfast", 7.99));
        breakfast.add(new Meal("Waffles", "Breakfast", 9.03));
        breakfast.add(new Meal("Croissant", "Breakfast", 8.56));
        breakfast.add(new Meal("French Toast", "Breakfast", 11.32));

        lunch.add(new Meal("Grilled Cheese Sandwich", "Lunch", 6.50));
        lunch.add(new Meal("Caesar Salad", "Lunch", 8.00));
        lunch.add(new Meal("Club Sandwich", "Lunch", 9.00));
        lunch.add(new Meal("Classic Burger", "Lunch", 11.00));
        lunch.add(new Meal("Chicken Wings", "Lunch", 8.50));

        dinner.add(new Meal("Steak", "Dinner", 15.99));
        dinner.add(new Meal("Spaghetti", "Dinner", 12.50));
        dinner.add(new Meal("Grilled Chicken", "Dinner", 14.50));
        dinner.add(new Meal("Chicken Friend Rice", "Dinner", 13.50));
        dinner.add(new Meal("Mac and Cheese", "Dinner", 11.50));

        drinks.add(new Meal("Coffee", "Drink", 2.50));
        drinks.add(new Meal("Orange Juice", "Drink", 3.00));
        drinks.add(new Meal("Mojito", "Drink", 5.00));
        drinks.add(new Meal("Water", "Drink", 1.50));
        drinks.add(new Meal("Redbull", "Drink", 6.70));

        appetizers.add(new Meal("Garlic Bread", "Appetizer", 4.00));
        appetizers.add(new Meal("Spring Rolls", "Appetizer", 5.50));
        appetizers.add(new Meal("French Fries", "Appetizer", 3.99));
        appetizers.add(new Meal("Onion Rings", "Appetizer", 5.20));
        appetizers.add(new Meal("Mozzarella Sticks", "Appetizer", 6.00));   
        
     }
     

    public void writeToRandomAccessFile(String fileName, ArrayList<Meal> menuList) {
        try (RandomAccessFile raf = new RandomAccessFile(fileName, "rw")) {
            // Write each menu item to the file
            for (Meal meal : menuList) {
                raf.writeBytes(meal.toString() + "\n");
            }
            System.out.println("Random Access File written: " + fileName);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + fileName);
            e.printStackTrace();
        }
    }

    // Method to read from a Random Access File
    public void readFromRandomAccessFile(String fileName) {
        try (RandomAccessFile raf = new RandomAccessFile(fileName, "r")) {
            String line;
            System.out.println("Reading from file: " + fileName);
            while ((line = raf.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading from file: " + fileName);
            e.printStackTrace();
        }
    }

    // Save and read all menus as random access files
    public void saveAndReadAllFiles() {
        writeToRandomAccessFile("breakfast.dat", breakfast);
        writeToRandomAccessFile("lunch.dat", lunch);
        writeToRandomAccessFile("dinner.dat", dinner);
        writeToRandomAccessFile("drinks.dat", drinks);
        writeToRandomAccessFile("appetizers.dat", appetizers);

        System.out.println();

        readFromRandomAccessFile("breakfast.dat");
        readFromRandomAccessFile("lunch.dat");
        readFromRandomAccessFile("dinner.dat");
        readFromRandomAccessFile("drinks.dat");
        readFromRandomAccessFile("appetizers.dat");
    }
        public void displayMenu() {
            
        System.out.println("\nAppetizers:");
        for (Meal meal : appetizers) {
            meal.display();
        }
        System.out.println("Breakfast:");
        for (Meal meal : breakfast) {
            meal.display();
        }

        System.out.println("\nLunch:");
        for (Meal meal : lunch) {
            meal.display();
        }

        System.out.println("\nDinner:");
        for (Meal meal : dinner) {
            meal.display();
        }

        System.out.println("\nDrinks:");
        for (Meal meal : drinks) {
            meal.display();
        }
      }
        public ArrayList<Meal> getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(ArrayList<Meal> breakfast) {
        this.breakfast = breakfast;
    }

    public ArrayList<Meal> getAppetizers() {
        return appetizers;
    }

    public void setAppetizers(ArrayList<Meal> appetizers) {
        this.appetizers = appetizers;
    }

    public ArrayList<Meal> getLunch() {
        return lunch;
    }

    public void setLunch(ArrayList<Meal> lunch) {
        this.lunch = lunch;
    }

    public ArrayList<Meal> getDinner() {
        return dinner;
    }

    public void setDinner(ArrayList<Meal> dinner) {
        this.dinner = dinner;
    }

    public ArrayList<Meal> getDrinks() {
        return drinks;
    }

    public void setDrinks(ArrayList<Meal> drinks) {
        this.drinks = drinks;
    }
}




