/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import businessLogic.EmployeeFactory;
import businessLogic.EmployeeInterface;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Boss;
import model.Employee;
import model.User;
import model.UserStatus;
import utilMethods.MetodosUtiles;

/**
 * FXML Controller class
 *
 * @author xabig
 */
public class EmployeeManagementController {

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
    /**
     * The User that operates on the window.
     */
    User user;

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

    @FXML
    private Label labelError;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
        buttonModificar.setDisable(true);

        employees = FXCollections.observableArrayList(employeeInt.getAllEmpoyees());
        loadEmployeesTable(employees);

        ObservableList<String> cbOptions = FXCollections.observableArrayList();
        cbOptions.addAll("Nombre", "Email", "Todos");
        comboBox.setItems(cbOptions);

        stage.show();

        buttonBuscar.setOnAction(this::clickBuscar);
        textfieldEmail.textProperty().addListener(this::textfieldListener);
        textfieldNombApell.textProperty().addListener(this::textfieldListener);
        textfieldSalario.textProperty().addListener(this::textfieldListener);
        textfieldTrabajo.textProperty().addListener(this::textfieldListener);
        textfieldPassword.textProperty().addListener(this::textfieldListener);
        textfieldLogin.textProperty().addListener(this::textfieldListener);
        textfieldBuscar.textProperty().addListener(this::listenerBuscar);
        buttonAniadir.setOnAction(this::clickAniadir);
        buttonModificar.setOnAction(this::clickModificar);
        buttonLimpiar.setOnAction(this::limpiarListener);
        buttonBorrar.setOnAction(this::clickBorrar);
        table.getSelectionModel().selectedItemProperty().addListener(this::clickTabla);
    }

    private void loadEmployeesTable(ObservableList<Employee> employees) {
        tableEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tableNombApell.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        tableSalario.setCellValueFactory(new PropertyValueFactory<>("wage"));
        tableTrabajo.setCellValueFactory(new PropertyValueFactory<>("job"));
        tableLogin.setCellValueFactory(new PropertyValueFactory<>("login"));

        table.setItems(employees);
    }

    private void textfieldListener(ObservableValue observable, String oldValue, String newValue) {
        labelError.setText("");
        comprobarAniadir();
        comprobarModificar();
    }

    private void listenerBuscar(ObservableValue observable, String oldValue, String newValue) {
        labelError.setText("");
    }

    private void limpiarListener(ActionEvent event) {
        limpiarCampos();
    }

    private void comprobarAniadir() {
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

    private void comprobarModificar() {
        if (!textfieldEmail.getText().equals("")
                && !textfieldNombApell.getText().equals("")
                && !textfieldSalario.getText().equals("")
                && !textfieldTrabajo.getText().equals("")
                && !textfieldLogin.getText().equals("")
                && textfieldPassword.getText().equals("")) {
            buttonModificar.setDisable(false);
        } else {
            buttonModificar.setDisable(true);
        }
    }

    private void clickBuscar(ActionEvent event) {
        labelError.setText("");
        if (MetodosUtiles.maximoCaracteres(textfieldBuscar, 50)) {
            if (comboBox.getValue().equals("Nombre")) {
                String name = textfieldBuscar.getText().trim();
                employees = FXCollections.observableArrayList(employeeInt.getEmployeesByName(name));
            } else if (comboBox.getValue().equals("Email")) {
                String email = textfieldBuscar.getText().trim();
                employees = FXCollections.observableArrayList(employeeInt.getEmployeesByEmail(email));
            } else {
                employees = FXCollections.observableArrayList(employeeInt.getAllEmpoyees());
            }
            table.setItems(employees);
        } else {
            textfieldBuscar.setText("");
            labelError.setText("Demasiados caracteres en el campo Buscar");
            labelError.setTextFill(Color.web("#ff0000"));
        }
    }

    private void clickAniadir(ActionEvent event) {
        if (verifyEmail(textfieldEmail.getText().trim())
                && verifyFullName(textfieldNombApell.getText().trim())
                && verifyLogin(textfieldLogin.getText().trim())
                && verifySalario(textfieldSalario.getText().trim())
                && verifyTrabajo(textfieldTrabajo.getText().trim())
                && verifyPassword(textfieldPassword.getText().trim())) {
            Boolean confirmar = mostrarAlertConfirmation("Create", "Are you sure you want to create?");

            if (confirmar) {
                Employee employee = new Employee();
                employee.setJob(textfieldTrabajo.getText().trim());
                employee.setEmail(textfieldEmail.getText().trim());
                employee.setFullName(textfieldNombApell.getText().trim());
                employee.setWage(Float.parseFloat(textfieldSalario.getText().trim()));
                employee.setLogin(textfieldLogin.getText().trim());
                employee.setPassword(textfieldPassword.getText().trim());
                employee.setLastAccess(Date.from(LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC)));
                employee.setLastPasswordChange(Date.from(LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC)));
                employee.setBoss((Boss) user);
                employee.setStatus(UserStatus.ENABLED);

                employeeInt.createEmployee(employee);

                employees = FXCollections.observableArrayList(employeeInt.getAllEmpoyees());
                table.setItems(employees);

                limpiarCampos();
            }
        }
    }

    private void clickModificar(ActionEvent event) {
        if (verifyEmail(textfieldEmail.getText().trim())
                && verifyFullName(textfieldNombApell.getText().trim())
                && verifyLogin(textfieldLogin.getText().trim())
                && verifySalario(textfieldSalario.getText().trim())
                && verifyTrabajo(textfieldTrabajo.getText().trim())) {

            if (mostrarAlertConfirmation("Modify", "Are you sure you want to modify?")) {
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
    }

    private void clickBorrar(ActionEvent event) {
        if (mostrarAlertConfirmation("Delete", "Are you sure you want to delete?")) {
            Employee selectedEmp = ((Employee) table.getSelectionModel()
                    .getSelectedItem());
            employeeInt.deleteEmployee(selectedEmp.getId().toString());

            employees = FXCollections.observableArrayList(employeeInt.getAllEmpoyees());
            table.setItems(employees);

            buttonBorrar.setDisable(true);
            limpiarCampos();
        }
    }

    private void clickTabla(ObservableValue observable, Object oldValue, Object newValue) {
        buttonModificar.setDisable(false);
        buttonBorrar.setDisable(false);
        Employee selectedEmp = ((Employee) table.getSelectionModel()
                .getSelectedItem());

        textfieldEmail.setText(selectedEmp.getEmail());
        textfieldNombApell.setText(selectedEmp.getFullName());
        textfieldSalario.setText(Float.toString(selectedEmp.getWage()));
        textfieldTrabajo.setText(selectedEmp.getJob());
        textfieldLogin.setText(selectedEmp.getLogin());

        savedEmail = selectedEmp.getEmail();
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

    private Boolean verifyEmail(String comp) {
        if (MetodosUtiles.validateEmail(comp)) {
            return true;
        } else {
            labelError.setText("Introduzca un email en el campo Email");
            labelError.setTextFill(Color.web("#ff0000"));
            return false;
        }
    }

    private Boolean verifyFullName(String comp) {
        if (MetodosUtiles.contieneSoloLetras(comp)) {
            if (MetodosUtiles.maximoCaracteres(textfieldNombApell, 50)) {
                if (!MetodosUtiles.comprobarEspaciosBlancos(textfieldNombApell)) {
                    return true;
                } else {
                    labelError.setText("El campo Nombre y Apellidos debe de tener al menos un apellido");
                    labelError.setTextFill(Color.web("#ff0000"));
                    return false;
                }
            } else {
                labelError.setText("Demasiados caracteres en el campo Nombre y Apellidos");
                labelError.setTextFill(Color.web("#ff0000"));
                return false;
            }
        } else {
            labelError.setText("El campo Nombre y Apellidos solo debe de contener letras");
            labelError.setTextFill(Color.web("#ff0000"));
            return false;
        }
    }

    private Boolean verifyLogin(String comp) {
        if (MetodosUtiles.maximoCaracteres(textfieldLogin, 50)) {
            if (MetodosUtiles.minimoCaracteres(textfieldLogin, 4)) {
                return true;
            } else {
                labelError.setText("Debe haber mas caracteres en el campo Login");
                labelError.setTextFill(Color.web("#ff0000"));
                return false;
            }
        } else {
            labelError.setText("Demasiados caracteres en el campo Login");
            labelError.setTextFill(Color.web("#ff0000"));
            return false;
        }
    }

    private Boolean verifySalario(String comp) {
        if (MetodosUtiles.verifyFloat(comp)) {
            return true;
        } else {
            labelError.setText("El campo Salario debe ser un numero");
            labelError.setTextFill(Color.web("#ff0000"));
            return false;
        }
    }

    private Boolean verifyTrabajo(String comp) {
        if (MetodosUtiles.maximoCaracteres(textfieldTrabajo, 50)) {
            if (MetodosUtiles.contieneSoloLetras(comp)) {
                return true;
            } else {
                labelError.setText("El campo Trabajo solo debe de contener letras");
                labelError.setTextFill(Color.web("#ff0000"));
                return false;
            }
        } else {
            labelError.setText("Demasiados caracteres en el campo Trabajo");
            labelError.setTextFill(Color.web("#ff0000"));
            return false;
        }
    }

    private Boolean verifyPassword(String comp) {
        if (MetodosUtiles.maximoCaracteres(textfieldPassword, 50)) {
            if (MetodosUtiles.minimoCaracteres(textfieldPassword, 4)) {
                return true;
            } else {
                labelError.setText("Debe haber mas caracteres en el campo Password");
                labelError.setTextFill(Color.web("#ff0000"));
                return false;
            }
        } else {
            labelError.setText("Demasiados caracteres en el campo Password");
            labelError.setTextFill(Color.web("#ff0000"));
            return false;
        }
    }

    private void limpiarCampos() {
        textfieldEmail.setText("");
        textfieldNombApell.setText("");
        textfieldSalario.setText("");
        textfieldTrabajo.setText("");
        textfieldLogin.setText("");
        textfieldPassword.setText("");

    }

}
