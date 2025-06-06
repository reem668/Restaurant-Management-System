/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant;

import java.io.*;
import java.util.ArrayList;

public class Table {

    


    private int tableID;
    protected ArrayList<String> category;
    private int capacity;
    private boolean Reserved;

    public Table() {
        this.category = new ArrayList<>();
    }

    public Table(int tableID, ArrayList<String> category, int capacity, boolean Reserved) {
        this.tableID = tableID;
        this.category = category;
        this.capacity = capacity;
        this.Reserved = Reserved;
    }

    public int getTableId() {
        return tableID;
    }

    public void setTableId(int tableID) {
        this.tableID = tableID;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        if (capacity < 1) {
            System.out.println("Capacity must be at least 1.");
            return;
        }
        this.capacity = capacity;
    }

    public ArrayList<String> getCategory() {
        return category;
    }

    public void setCategory(ArrayList<String> category) {
        if (category == null) {
            System.out.println("Category cannot be empty.");
            return;
        }
        this.category = category;
    }

    public boolean isReserved() {
        return Reserved;
    }

    public void setReserved(boolean Reserved) {
        this.Reserved = Reserved;
    }

    private void initializeDefaultCategories() {
        category.add("Outdoor");
        category.add("Indoor");
        category.add("VIP");
    }

    public static void saveCategoriesToFile(ArrayList<String> categories) {
        try (RandomAccessFile file = new RandomAccessFile("categories.dat", "rw")) {
            file.setLength(0);  
            file.writeInt(categories.size());  
            for (String cat : categories) {
                file.writeUTF(cat);  
            }
            System.out.println("Categories saved to file successfully!");
        } catch (IOException e) {
            System.err.println("Error saving categories to file: " + e.getMessage());
        }
    }
    public static ArrayList<String> loadCategoriesFromFile() {
    ArrayList<String> categories = new ArrayList<>();
    File file = new File("categories.dat");

    try {
        // Check if the file exists
        if (!file.exists()) {
            System.out.println("Categories file not found. Creating a new one...");
            try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
                // Write default categories
                categories.add("Outdoor");
                categories.add("Indoor");
                categories.add("VIP");

                raf.writeInt(categories.size()); // Write the size of the list
                for (String category : categories) {
                    raf.writeUTF(category); // Write each category
                }
                System.out.println("Default categories created and saved.");
            }
        } else {
            // Load existing categories from the file
            try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
                int size = raf.readInt(); // Read the number of categories
                for (int i = 0; i < size; i++) {
                    categories.add(raf.readUTF()); // Read each category
                }
                System.out.println("Categories loaded from file successfully!");
            }
        }
    } catch (IOException e) {
        System.err.println("Error loading categories from file: " + e.getMessage());
    }

    return categories;
}


/*public static void saveTablesToFile(ArrayList<Table> tableList) {
    try (RandomAccessFile file = new RandomAccessFile("tables.dat", "rw")) {
        file.setLength(0);  // Clear the file before writing
        file.writeInt(tableList.size());  // Write the number of tables
        
        for (Table table : tableList) {
            file.writeInt(table.getTableId());  // Write table ID
            file.writeInt(table.getCapacity());  // Write table capacity
            file.writeBoolean(table.isReserved());  // Write reservation status

            // Write the categories associated with the table
            ArrayList<String> categories = table.getCategory();
            file.writeInt(categories.size());  // Write the number of categories
            for (String cat : categories) {
                file.writeUTF(cat);  // Write each category
            }
        }
        System.out.println("Tables saved to file successfully!");
    } catch (IOException e) {
        System.err.println("Error saving tables to file: " + e.getMessage());
    }
}
*/
    public static void saveTablesToFile(ArrayList<Table> tableList) {
    if (tableList == null || tableList.isEmpty()) {
        System.out.println("Table list is empty or null, nothing to save.");
        return;  // No need to save an empty list
    }

    try (RandomAccessFile file = new RandomAccessFile("tables.dat", "rw")) {
        file.setLength(0);  // Clear the file before writing
        file.writeInt(tableList.size());  // Write the number of tables
        
        for (Table table : tableList) {
            if (table == null) {
                System.out.println("Skipping null table.");
                continue;  // Skip null tables
            }
            
            // Ensure table fields are not null
            if (table.getCategory() == null) {
                table.setCategory(new ArrayList<>());  // Initialize if null
            }

            file.writeInt(table.getTableId());  // Write table ID
            file.writeInt(table.getCapacity());  // Write table capacity
            file.writeBoolean(table.isReserved());  // Write reservation status

            // Write the categories associated with the table
            ArrayList<String> categories = table.getCategory();
            file.writeInt(categories.size());  // Write the number of categories
            for (String cat : categories) {
                if (cat == null) {
                    file.writeUTF("");  // Handle null category
                } else {
                    file.writeUTF(cat);  // Write each category
                }
            }
        }
        System.out.println("Tables saved to file successfully!");
    } catch (IOException e) {
        System.err.println("Error saving tables to file: " + e.getMessage());
        e.printStackTrace();  // Print stack trace for detailed error info
    }
}

   
    public static ArrayList<Table> loadTablesFromFile() {
        ArrayList<Table> tableList = new ArrayList<>();
        try (RandomAccessFile file = new RandomAccessFile("tables.dat", "r")) {
            int size = file.readInt();  
            for (int i = 0; i < size; i++) {
                int tableID = file.readInt();  
                int capacity = file.readInt(); 
                boolean Reserved = file.readBoolean();  
                int categorySize = file.readInt();
                ArrayList<String> categories = new ArrayList<>();
                for (int j = 0; j < categorySize; j++) {
                    categories.add(file.readUTF());  
                }

                Table table = new Table(tableID, categories, capacity, Reserved);
                tableList.add(table);  
            }
            System.out.println("Tables loaded from file successfully!");
        } catch (IOException e) {
            System.err.println("Error loading tables from file: " + e.getMessage());
        }
        return tableList;
    }

    
}
