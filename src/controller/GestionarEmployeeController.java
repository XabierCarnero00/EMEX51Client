/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import bussinesLogic.EmployeeFactory;
import bussinesLogic.EmployeeInterface;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Boss;
import model.Employee;
import model.UserStatus;
import securityClient.ClavePublicaCliente;

/**
 * FXML Controller class
 *
 * @author xabig
 */
public class GestionarEmployeeController {

    /**
     * Atributo Logger para rastrear los pasos de ejecución del programa.
     */
    private static final Logger LOGGER
            = Logger.getLogger("grupog5.signinsignupapplication.cliente.application");

    /**
     * Una ventana sobre la que se coloca una escena.
     */
    private Stage stage;
    /**
     * The email of the Employee to be changed.
     */
    private String savedEmail;

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
    private ComboBox comboBox;
    @FXML
    private TextField textfieldEmail;
    @FXML
    private TextField textfieldNombApell;
    @FXML
    private Button buttonAniadir;
    @FXML
    private Button buttonVolver;
    @FXML
    private TextField textfieldTrabajo;
    @FXML
    private TextField textfieldSalario;
    @FXML
    private TextField textfieldBuscar;
    @FXML
    private Button buttonBuscar;
    @FXML
    private Button buttonModificar;
    @FXML
    private Button buttonOk;
    @FXML
    private TableColumn tableLogin;
    @FXML
    private Button buttonBorrar;
    @FXML
    private TextField textfieldLogin;
    @FXML
    private TextField textfieldPassword;
    @FXML
    private Button buttonLimpiar;

    ObservableList employees;

    EmployeeInterface employeeInt = EmployeeFactory.getEmployeeImp();

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

        buttonAniadir.setDisable(true);
        buttonBorrar.setDisable(true);
        buttonBuscar.setDisable(true);
        buttonOk.setDisable(true);

        employees = FXCollections.observableArrayList(employeeInt.getAllEmpoyees());
        loadEmployees(employees);

        ObservableList<String> cbOptions = FXCollections.observableArrayList();
        cbOptions.addAll("Nombre", "Email", "Todos");
        comboBox.setItems(cbOptions);

        stage.show();

        buttonBuscar.setOnAction(this::clickBuscar);
        textfieldEmail.textProperty().addListener(this::emailListener);
        textfieldNombApell.textProperty().addListener(this::fullNameListener);
        textfieldSalario.textProperty().addListener(this::salarioListener);
        textfieldTrabajo.textProperty().addListener(this::trabajoListener);
        textfieldPassword.textProperty().addListener(this::passwordListener);
        textfieldLogin.textProperty().addListener(this::loginListener);
        buttonAniadir.setOnAction(this::clickAniadir);
        buttonModificar.setOnAction(this::clickModificar);
        buttonOk.setOnAction(this::clickOk);
        buttonVolver.setOnAction(this::clickVolver);
        buttonLimpiar.setOnAction(this::limpiarListener);
        //comboBox.valueProperty().addListener(this::listenerCb);
    }

    public void loadEmployees(ObservableList<Employee> employees) {
        tableEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tableNombApell.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        tableSalario.setCellValueFactory(new PropertyValueFactory<>("wage"));
        tableTrabajo.setCellValueFactory(new PropertyValueFactory<>("job"));
        tableLogin.setCellValueFactory(new PropertyValueFactory<>("login"));

        table.setItems(employees);
    }

    public void clickBuscar(ActionEvent event) {
        if (comboBox.getValue().equals("Nombre")) {
            String name = textfieldBuscar.getText();
            employees = FXCollections.observableArrayList(employeeInt.getEmployeesByName(name));
        } else if (comboBox.getValue().equals("Email")) {
            String email = textfieldBuscar.getText();
            employees = FXCollections.observableArrayList(employeeInt.getEmployeesByEmail(email));
        } else {
            employees = FXCollections.observableArrayList(employeeInt.getAllEmpoyees());
        }
        table.setItems(employees);
    }

    public void emailListener(ObservableValue observable, String oldValue, String newValue) {
        comprobarAniadir();
    }

    public void fullNameListener(ObservableValue observable, String oldValue, String newValue) {
        comprobarAniadir();
    }

    public void salarioListener(ObservableValue observable, String oldValue, String newValue) {
        comprobarAniadir();
    }

    public void trabajoListener(ObservableValue observable, String oldValue, String newValue) {
        comprobarAniadir();
    }

    public void loginListener(ObservableValue observable, String oldValue, String newValue) {
        comprobarAniadir();
    }

    public void passwordListener(ObservableValue observable, String oldValue, String newValue) {
        comprobarAniadir();
    }

    public void limpiarListener(ActionEvent event) {
        limpiarCampos();
    }

    public void comprobarAniadir() {
        if (textfieldEmail.getText().compareToIgnoreCase("") != 0
                && textfieldNombApell.getText().compareToIgnoreCase("") != 0
                && textfieldSalario.getText().compareToIgnoreCase("") != 0
                && textfieldTrabajo.getText().compareToIgnoreCase("") != 0
                && textfieldPassword.getText().compareToIgnoreCase("") != 0
                && textfieldLogin.getText().compareToIgnoreCase("") != 0) {
            buttonAniadir.setDisable(false);
        } else {
            buttonAniadir.setDisable(true);
        }
    }

    public void clickAniadir(ActionEvent event) {

        Boss boss = new Boss();
        boss.setId(1);

        Employee employee = new Employee();
        employee.setJob(textfieldTrabajo.getText().trim());
        employee.setEmail(textfieldEmail.getText().trim());
        employee.setFullName(textfieldNombApell.getText().trim());
        employee.setWage(Float.parseFloat(textfieldSalario.getText().trim()));
        employee.setLogin(textfieldLogin.getText().trim());
        employee.setPassword(ClavePublicaCliente.cifrarTexto(textfieldPassword.getText().trim()));
        employee.setLastAccess(Date.from(LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC)));
        employee.setLastPasswordChange(Date.from(LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC)));
        employee.setBoss(boss);
        employee.setStatus(UserStatus.ENABLED);

        employeeInt.createEmployee(employee);

        employees = FXCollections.observableArrayList(employeeInt.getAllEmpoyees());
        table.setItems(employees);

        limpiarCampos();
    }

    @FXML
    private void cbListener(ActionEvent event) {
        buttonBuscar.setDisable(false);
        if (comboBox.getValue().equals("Todos")) {
            textfieldBuscar.setText("");
            textfieldBuscar.setDisable(true);
        } else {
            textfieldBuscar.setDisable(false);
        }
    }

    public void clickModificar(ActionEvent event) {
        buttonOk.setDisable(false);
        Employee selectedEmp = ((Employee) table.getSelectionModel()
                .getSelectedItem());

        textfieldEmail.setText(selectedEmp.getEmail());
        textfieldNombApell.setText(selectedEmp.getFullName());
        textfieldSalario.setText(Float.toString(selectedEmp.getWage()));
        textfieldTrabajo.setText(selectedEmp.getJob());
        textfieldLogin.setText(selectedEmp.getLogin());

        savedEmail = selectedEmp.getEmail();
    }

    public void clickOk(ActionEvent event) {

        Boolean confirmar = mostrarAlertConfirmation("Modify", "Are you sure you want to modify?");

        if (confirmar) {
            Employee employee = new Employee();
            employee = employeeInt.getSingleEmployeeByEmail(savedEmail);

            employee.setEmail(textfieldEmail.getText());
            employee.setFullName(textfieldNombApell.getText());
            employee.setJob(textfieldTrabajo.getText());
            employee.setWage(Float.parseFloat(textfieldSalario.getText()));
            employee.setLogin(textfieldLogin.getText());

            employeeInt.updateEmployee(employee);

            employees = FXCollections.observableArrayList(employeeInt.getAllEmpoyees());
            table.setItems(employees);

            limpiarCampos();
        }
    }

    public void clickVolver(ActionEvent event) {
        try {
            //Mensaje Logger al acceder al método
            LOGGER.log(Level.INFO, "Método start de la aplicación");
            //New FXMLLoader Añadir el fxml de MenuPrincipal que es la ventana principal
            FXMLLoader loader = new FXMLLoader(getClass().
                    getResource("/view/MenuPrincipal.fxml"));
            //Parent es una clase gráfica de nodos xml son nodos.
            Parent root = (Parent) loader.load();
            //Relacionamos el documento FXML con el controlador que le va a controlar.
            MenuPrincipalController menuPrincipalController = (MenuPrincipalController) loader.getController();
            //Llamada al método setStage del controlador de la ventana Menu principal. Pasa la ventana.
            menuPrincipalController.setStage(stage);
            //Llamada al método initStage del controlador de la ventana Menu principal. Pasa el documento fxml en un nodo.
            menuPrincipalController.initStage(root);
            //Llamada al método inicializarComponenentesVentana del controlador de la ventana signIn.
        } catch (IOException ex) {
            Logger.getLogger(GestionarEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Ventana de alerta que sale para confirmar o negar el cambio de pantalla
     *
     * @param event Contiene la accion realizada con el boton.
     * @return Devuelve el resultado de la eleccion.
     */
    private boolean mostrarAlertConfirmation(String title, String contextText) {
        boolean confirm = false;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.setContentText(contextText);
        Optional<ButtonType> resp = alert.showAndWait();

        if (resp.get() == ButtonType.OK) {
            confirm = true;
        }

        return confirm;
    }

    public void limpiarCampos() {
        textfieldEmail.setText("");
        textfieldNombApell.setText("");
        textfieldSalario.setText("");
        textfieldTrabajo.setText("");
        textfieldLogin.setText("");
        textfieldPassword.setText("");

    }
}
