/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import bussinesLogic.EmployeeFactory;
import bussinesLogic.EmployeeInterface;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Employee;

/**
 * FXML Controller class
 *
 * @author xabig
 */
public class GestionarEmployeeController {

    /**
     * Una ventana sobre la que se coloca una escena.
     */
    private Stage stage;
    @FXML
    private TableView table;
    @FXML
    private TableColumn tableEmail;
    @FXML
    private TableColumn tableNombApell;
    @FXML
    private TableColumn tableSalario;
    @FXML
    private TableColumn tableTrabajo;
    @FXML
    private SplitMenuButton comboBox;
    @FXML
    private MenuItem comboNombre;
    @FXML
    private MenuItem comboTrabajo;
    @FXML
    private TextField textfieldEmail;
    @FXML
    private TextField textfieldNombApell;
    @FXML
    private Button buttonAniadir;
    @FXML
    private Button buttonVolver;
    @FXML
    private Button buttonDesactivar;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Initializes the controller class.
     *
     * @param root
     */
    public void initStage(Parent root) {
        Scene scene = new Scene(root);

        stage.setScene(scene);

        stage.setTitle("Gestionar Employee");

        stage.setResizable(false);
        
        EmployeeInterface employeeInt = EmployeeFactory.getEmployeeImp();
        
        ObservableList employees = FXCollections.observableArrayList(employeeInt.getAllEmpoyees());
        
        loadEmployees(employees);
        

        stage.show();
        
        
    }
    
    public void loadEmployees(ObservableList<Employee> employees){
        tableEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tableNombApell.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        tableSalario.setCellValueFactory(new PropertyValueFactory<>("wage"));
        tableTrabajo.setCellValueFactory(new PropertyValueFactory<>("job"));
        
        table.setItems(employees);
    }

}
