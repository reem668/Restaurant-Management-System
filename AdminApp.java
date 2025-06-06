package javaapplication15;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;

public class AdminApp extends Application {
    private ObservableList<Admin> adminList = FXCollections.observableArrayList();
    private TextField idField, nameField, emailField;
    private ListView<String> displayListView;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Set the window (Stage) title
        primaryStage.setTitle("Admin Management");

        // Create a TabPane to switch between forms
        TabPane tabPane = new TabPane();

        // Create tabs for different forms
        Tab registrationTab = new Tab("Registration", createRegistrationForm());
        Tab editTab = new Tab("Edit", createEditForm());
        Tab deleteTab = new Tab("Delete", createDeleteForm());
        Tab displayTab = new Tab("Display", createDisplayForm());

        // Add tabs to TabPane
        tabPane.getTabs().addAll(registrationTab, editTab, deleteTab, displayTab);

        // Create the scene
        Scene scene = new Scene(tabPane, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // 1. Registration Form
    private VBox createRegistrationForm() {
        Label idLabel = new Label("Admin ID:");
        idField = new TextField();

        Label nameLabel = new Label("Admin Name:");
        nameField = new TextField();

        Label emailLabel = new Label("Admin Email:");
        emailField = new TextField();

        Button addButton = new Button("Add Admin");
        addButton.setOnAction(e -> addAdmin());

        Button eraseButton = new Button("Erase All");
        eraseButton.setOnAction(e -> eraseAll());

        VBox vbox = new VBox(10, idLabel, idField, nameLabel, nameField, emailLabel, emailField, addButton, eraseButton);
        vbox.setStyle("-fx-padding: 20px;");
        return vbox;
    }

    private void addAdmin() {
        String id = idField.getText();
        String name = nameField.getText();
        String email = emailField.getText();

        if (id.isEmpty() || name.isEmpty() || email.isEmpty()) {
            showAlert("All fields must be filled!");
            return;
        }

        adminList.add(new Admin(id, name, email));
        idField.clear();
        nameField.clear();
        emailField.clear();
    }

    private void eraseAll() {
        adminList.clear();
    }

    // 2. Edit Form
    private VBox createEditForm() {
        Label idLabel = new Label("Admin ID:");
        TextField editIdField = new TextField();

        Label nameLabel = new Label("Admin Name:");
        TextField editNameField = new TextField();

        Label emailLabel = new Label("Admin Email:");
        TextField editEmailField = new TextField();

        Button saveButton = new Button("Save Data");
        saveButton.setOnAction(e -> saveData(editIdField.getText(), editNameField.getText(), editEmailField.getText()));

        VBox vbox = new VBox(10, idLabel, editIdField, nameLabel, editNameField, emailLabel, editEmailField, saveButton);
        vbox.setStyle("-fx-padding: 20px;");
        return vbox;
    }

    private void saveData(String id, String name, String email) {
        for (Admin admin : adminList) {
            if (admin.getId().equals(id)) {
                admin.setName(name);
                admin.setEmail(email);
                return;
            }
        }
        showAlert("Admin not found!");
    }

    // 3. Delete Form
    private VBox createDeleteForm() {
        Label idLabel = new Label("Admin ID to delete:");
        TextField deleteIdField = new TextField();

        Button deleteButton = new Button("Delete Admin");
        deleteButton.setOnAction(e -> deleteAdmin(deleteIdField.getText()));

        VBox vbox = new VBox(10, idLabel, deleteIdField, deleteButton);
        vbox.setStyle("-fx-padding: 20px;");
        return vbox;
    }

    private void deleteAdmin(String id) {
        adminList.removeIf(admin -> admin.getId().equals(id));
    }

    // 4. Display Form
    private VBox createDisplayForm() {
        displayListView = new ListView<>();
        updateDisplay();

        Button refreshButton = new Button("Refresh");
        refreshButton.setOnAction(e -> updateDisplay());

        VBox vbox = new VBox(10, displayListView, refreshButton);
        vbox.setStyle("-fx-padding: 20px;");
        return vbox;
    }

    private void updateDisplay() {
        ArrayList<String> displayData = new ArrayList<>();
        for (Admin admin : adminList) {
            displayData.add("ID: " + admin.getId() + ", Name: " + admin.getName() + ", Email: " + admin.getEmail());
        }
        displayListView.setItems(FXCollections.observableArrayList(displayData));
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
