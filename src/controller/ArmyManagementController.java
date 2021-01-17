/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import bussinesLogic.ArmyFactory;
import bussinesLogic.ArmyInterface;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Army;
import model.User;

/**
 *
 * @author xabig
 */
public class ArmyManagementController {

    /**
     * Logger para trazar los pasos del código.
     */
    private static final Logger LOGGER = Logger.getLogger("grupog5.signinsignupapplication.cliente.controlador.FXMLDocumentControllerSignIn");

    /**
     * Una ventana sobre la que se coloca una escena.
     */
    private Stage stage;

    User user;

    ArmyInterface armyInt = ArmyFactory.getArmyImp();

    ObservableList armys;

    @FXML
    private TextField textfieldBuscar;

    @FXML
    private Button buttonBuscar;

    @FXML
    private ComboBox comboBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TableView tableView;

    @FXML
    private TableColumn tableColumnNombre;

    @FXML
    private TableColumn tableColumnMunicion;

    @FXML
    private TableColumn tableColumnFechaLlegada;

    @FXML
    private TextField textfieldNombre;

    @FXML
    private TextField textfieldMunicion;

    @FXML
    private Button buttonAniadir;

    @FXML
    private Button buttonBorrar;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initStage(Parent root) {
        Scene scene = new Scene(root);

        stage.setScene(scene);

        stage.setTitle("Army Management");
        stage.setResizable(false);

        buttonAniadir.setDisable(true);
        buttonBorrar.setDisable(true);
        buttonBuscar.setDisable(true);

        armys = FXCollections.observableArrayList(armyInt.getAllArmys());
        loadArmysTable(armys);

        stage.show();
    }

    private void loadArmysTable(ObservableList<Army> armys) {
        tableColumnNombre.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnMunicion.setCellValueFactory(new PropertyValueFactory<>("ammunition"));
        tableColumnFechaLlegada.setCellValueFactory(new PropertyValueFactory<>("arrivalDate"));
        
        tableView.setItems(armys);
    }

}
