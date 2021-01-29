/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import businessLogic.ArmyFactory;
import businessLogic.ArmyInterface;
import businessLogic.BusinessLogicException;
import businessLogic.SectorFactory;
import businessLogic.SectorInterface;
import exceptions.ExceptionArmyExiste;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import model.Army;
import model.Sector;
import model.User;
import utilMethods.MetodosUtiles;

/**
 * FXML Controller class for <code>Army</code> class.
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
    /**
     * The User that operates on the Window.
     */
    User user;
    /**
     * A {@link ArmyInterface} object
     */
    ArmyInterface armyInt = ArmyFactory.getArmyImp();
    /**
     * A {@link SectorInterface} object
     */
    SectorInterface sectorInt = SectorFactory.getSector();
    /**
     * List of Armys for inserting in the TableView.
     */
    ObservableList armys;
    /**
     * The Sector where the Army is ubicated.
     */
    Sector sector;
    /**
     * Textfield for searching Armys.
     */
    @FXML
    private TextField textfieldBuscar;
    /**
     * Button for searching Armys.
     */
    @FXML
    private Button buttonBuscar;
    /**
     * Combo for serching Armys by different types.
     */
    @FXML
    private ComboBox comboBox;
    /**
     * DataPicker for serching Armys by different types.
     */
    @FXML
    private DatePicker datePicker;
    /**
     * TableView containing Armys.
     */
    @FXML
    private TableView tableView;
    /**
     * TableColumn contains Armys Name.
     */
    @FXML
    private TableColumn<Army, String> tableColumnNombre;
    /**
     * TableColumn contains Armys ammunition.
     */
    @FXML
    private TableColumn<Army, Integer> tableColumnMunicion;
    /**
     * TableColumn contains Armys Arrival Date.
     */
    @FXML
    private TableColumn<Army, Date> tableColumnFechaLlegada;
    /**
     * TableColumn contains Armys Sector.
     */
    @FXML
    private TableColumn<Sector, String> tableColumnSector;
    /**
     * Textfield for Armys Name.
     */
    @FXML
    private TextField textfieldNombre;
    /**
     * Textfield for Armys ammunition.
     */
    @FXML
    private TextField textfieldMunicion;
    /**
     * Button for adding a new Army.
     */
    @FXML
    private Button buttonAniadir;
    /**
     * Button for removing Army.
     */
    @FXML
    private Button buttonBorrar;
    /**
     * DataPicker for Adding Army.
     */
    @FXML
    private DatePicker datePickerAniadir;
    /**
     * Label with the information of the User.
     */
    @FXML
    private Label labelError;

    /**
     * The Stage where the Window is shown.
     *
     * @return the Stage
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Sets the Stage for the Window.
     *
     * @param stage The Stage set.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Returns the User that operates in the Window.
     *
     * @return the User
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the User that operates in the Window.
     *
     * @param user the User to be set.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Get the Sector where is the Army
     *
     * @return the Sector
     */
    public Sector getSector() {
        return sector;
    }

    /**
     * Sets the Sector where is the Army
     *
     * @param sector the Sector to be set
     */
    public void setSector(Sector sector) {
        this.sector = sector;
    }

    /**
     * Initializes the controller class.
     *
     * @param root
     */
    public void initStage(Parent root) {
        try {
            Scene scene = new Scene(root);

            stage.setScene(scene);

            stage.setTitle("Army Management");
            stage.setResizable(false);

            buttonAniadir.setDisable(true);
            buttonBorrar.setDisable(true);
            buttonBuscar.setDisable(true);
            datePicker.setEditable(false);
            datePickerAniadir.setEditable(false);

            ObservableList<String> cbOptions = FXCollections.observableArrayList();
            cbOptions.addAll("Nombre", "Municion", "Todos");
            comboBox.setItems(cbOptions);

            armys = FXCollections.observableArrayList(armyInt.getArmysBySector(sector));
            if (armys != null) {
                loadArmysTable(armys);
            }
            makeTableEditable();

            //Formatear la fecha para que se vea formateada.
            tableColumnFechaLlegada.setCellFactory(column -> {
                TableCell<Army, Date> cell = new TableCell<Army, Date>() {
                    private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

                    @Override
                    protected void updateItem(Date item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            this.setText(format.format(item));

                        }
                    }
                };

                return cell;
            });

            stage.show();

            textfieldMunicion.textProperty().addListener(this::municionListener);
            textfieldNombre.textProperty().addListener(this::nombreListener);
            textfieldBuscar.textProperty().addListener(this::buscarListener);
            buttonBuscar.setOnAction(this::clickBuscar);
            buttonAniadir.setOnAction(this::clickAniadir);
            buttonBorrar.setOnAction(this::clickBorrar);
            tableView.getSelectionModel().selectedItemProperty().addListener(this::clickTabla);
        } catch (BusinessLogicException ex) {
            Logger.getLogger(ArmyManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * This method makes the Table be editable.
     */
    private void makeTableEditable() {
        tableColumnNombre.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumnNombre.setOnEditCommit((CellEditEvent<Army, String> t) -> {
            labelError.setText("");
            boolean existe = false;
            try {
                if (verifyNombre(t.getNewValue())) {
                    List<Army> armys = armyInt.getAllArmys();
                    for (Army a : armys) {
                        if (a.getName().equals(t.getNewValue())) {
                            throw new ExceptionArmyExiste();
                        }
                    }
                    t.getTableView().getItems().get(t.getTablePosition().getRow()).setName(t.getNewValue());
                    Army army = t.getRowValue();
                    armyInt.editArmy(army);
                }
            } catch (BusinessLogicException ex) {
                Logger.getLogger(ArmyManagementController.class.getName()).log(Level.SEVERE, null, ex);
                labelError.setText("Error when trying to Update Armys, try again later: " + ex.getMessage());
                labelError.setTextFill(Color.web("#ff0000"));
            } catch (ExceptionArmyExiste ex) {
                Logger.getLogger(ArmyManagementController.class.getName()).log(Level.SEVERE, null, ex);
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setName(t.getOldValue());
                tableView.refresh();
                labelError.setText("That Name alredy exists");
                labelError.setTextFill(Color.web("#ff0000"));
            }
        });
        tableColumnMunicion.setCellFactory(TextFieldTableCell.<Army, Integer>forTableColumn(new IntegerStringConverter()));
        tableColumnMunicion.setOnEditCommit((CellEditEvent<Army, Integer> t) -> {
            labelError.setText("");
            try {
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setAmmunition(t.getNewValue());
                Army army = t.getRowValue();
                armyInt.editArmy(army);
            } catch (BusinessLogicException ex) {
                Logger.getLogger(ArmyManagementController.class.getName()).log(Level.SEVERE, null, ex);
                labelError.setText("Error when trying to Update Armys, try again later: " + ex.getMessage());
                labelError.setTextFill(Color.web("#ff0000"));
            }
        });

        tableView.setEditable(true);
    }

    /**
     * This method loads the Armys in the TableView.
     *
     * @param armys
     */
    private void loadArmysTable(ObservableList<Army> armys) {
        tableColumnNombre.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnMunicion.setCellValueFactory(new PropertyValueFactory<>("ammunition"));
        tableColumnFechaLlegada.setCellValueFactory(new PropertyValueFactory<>("arrivalDate"));
        tableColumnSector.setCellValueFactory(new PropertyValueFactory<>("sector"));

        tableView.setItems(armys);
    }

    /**
     * Starts when the Button Buscar is pressed.
     *
     * @param event
     */
    private void clickBuscar(ActionEvent event) {
        labelError.setText("");
        if (MetodosUtiles.maximoCaracteres(textfieldBuscar, 50)) {
            try {
                if (comboBox.getValue().equals("Todos")) {
                    armys = FXCollections.observableArrayList(armyInt.getAllArmys());
                } else if (comboBox.getValue().equals("Nombre")) {
                    armys = FXCollections.observableArrayList(armyInt.getArmysByName(textfieldBuscar.getText().trim()));
                } else {
                    if (MetodosUtiles.verifyFloat(textfieldBuscar.getText().trim())) {
                        armys = FXCollections.observableArrayList(armyInt.getArmysByAmmunition(Integer.parseInt(textfieldBuscar.getText().trim())));
                    } else {
                        labelError.setText("Introduce numeros en el campo Buscar");
                        labelError.setTextFill(Color.web("#ff0000"));
                    }
                }
                tableView.setItems(armys);
            } catch (BusinessLogicException ex) {
                Logger.getLogger(ArmyManagementController.class.getName()).log(Level.SEVERE, null, ex);
                labelError.setText("Error when trying to Find Armys, try again later: " + ex.getMessage());
                labelError.setTextFill(Color.web("#ff0000"));
            }
        } else {
            textfieldBuscar.setText("");
            labelError.setText("Demasiados caracteres en el campo Buscar");
            labelError.setTextFill(Color.web("#ff0000"));
        }
    }

    /**
     * Starts when the Button Añadir is pressed.
     *
     * @param event
     */
    private void clickAniadir(ActionEvent event) {
        try {
            labelError.setText("");
            if (verifyNombre(textfieldNombre.getText().trim())
                    && verifyMunicion(textfieldMunicion.getText().trim())) {
                if (mostrarAlertConfirmation("Añadir", "Estas seguro que quieres añadir?")) {
                    try {
                        Army army = new Army();
                        army.setSector(sector);
                        army.setName(textfieldNombre.getText().trim());
                        army.setAmmunition(Integer.parseInt(textfieldMunicion.getText().trim()));
                        army.setArrivalDate(Date.from(datePickerAniadir.getValue().atStartOfDay().toInstant(ZoneOffset.UTC)));

                        armyInt.createArmy(army);

                        //Actualizar lista
                        textfieldNombre.setText("");
                        textfieldMunicion.setText("");
                        datePickerAniadir.setValue(null);

                        armys = FXCollections.observableArrayList(armyInt.getArmysBySector(sector));
                        tableView.setItems(armys);
                    } catch (BusinessLogicException ex) {
                        Logger.getLogger(ArmyManagementController.class.getName()).log(Level.SEVERE, null, ex);
                        labelError.setText("Error when trying to Create an Army, try again later: " + ex.getMessage());
                        labelError.setTextFill(Color.web("#ff0000"));
                    } catch (ExceptionArmyExiste ex) {
                        Logger.getLogger(ArmyManagementController.class.getName()).log(Level.SEVERE, null, ex);
                        labelError.setText("The Army you want to Create alredy exists");
                        labelError.setTextFill(Color.web("#ff0000"));
                    }
                }
            }
        } catch (NullPointerException ex) {
            labelError.setText("Introduce una fecha para poder añadir");
            labelError.setTextFill(Color.web("#ff0000"));
        }
    }

    /**
     * Starts when the Button Borrar is pressed.
     *
     * @param event
     */
    private void clickBorrar(ActionEvent event) {
        if (mostrarAlertConfirmation("Delete", "Are you sure you want to delete?")) {
            try {
                Army selectedArmy = ((Army) tableView.getSelectionModel()
                        .getSelectedItem());
                armyInt.deleteArmy(selectedArmy);

                armys = FXCollections.observableArrayList(armyInt.getArmysBySector(sector));
                tableView.setItems(armys);

                buttonBorrar.setDisable(true);
            } catch (BusinessLogicException ex) {
                Logger.getLogger(ArmyManagementController.class.getName()).log(Level.SEVERE, null, ex);
                labelError.setText("Error when trying to Delete an Army, try again later: " + ex.getMessage());
                labelError.setTextFill(Color.web("#ff0000"));
            }
        }
    }

    /**
     * Starts when yo make a click in the TableView.
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    private void clickTabla(ObservableValue observable, Object oldValue, Object newValue) {
        labelError.setText("");
        if (newValue != null) {
            buttonBorrar.setDisable(false);
        } else {
            buttonBorrar.setDisable(true);
        }
    }

    /**
     * Starts when is a change in the ComboBox.
     *
     * @param event
     */
    @FXML
    private void cbListener(ActionEvent event) {
        buttonBuscar.setDisable(false);
        labelError.setText("");
        if (comboBox.getValue().equals("Todos")) {
            textfieldBuscar.setText("");
            textfieldBuscar.setDisable(true);
        } else {
            textfieldBuscar.setDisable(false);
        }
    }

    /**
     * Starts when is a change in the DatePicker.
     *
     * @param event
     */
    @FXML
    private void changeDatePicker(ActionEvent event) {
        try {
            armys = FXCollections.observableArrayList(armyInt.getArmysByDate(Date.from(datePicker.getValue().atStartOfDay().toInstant(ZoneOffset.UTC))));
            tableView.setItems(armys);
            datePicker.setValue(null);
        } catch (BusinessLogicException ex) {
            Logger.getLogger(ArmyManagementController.class.getName()).log(Level.SEVERE, null, ex);
            labelError.setText("Error when trying to Find an Army, try again later: " + ex.getMessage());
            labelError.setTextFill(Color.web("#ff0000"));
        } catch (NullPointerException ex) {

        }
    }

    /**
     * Listens the TextField Buscar
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    private void buscarListener(ObservableValue observable, String oldValue, String newValue) {
        labelError.setText("");
    }

    /**
     * Listens the ammunition TextField.
     * 
     * @param observable
     * @param oldValue
     * @param newValue 
     */
    private void municionListener(ObservableValue observable, String oldValue, String newValue) {
        labelError.setText("");
        if (textfieldMunicion.getText().compareToIgnoreCase("") != 0
                && textfieldNombre.getText().compareToIgnoreCase("") != 0) {
            buttonAniadir.setDisable(false);
        } else {
            buttonAniadir.setDisable(true);
        }
    }

    /**
     * Listens the name TextField
     * @param observable
     * @param oldValue
     * @param newValue 
     */
    private void nombreListener(ObservableValue observable, String oldValue, String newValue) {
        labelError.setText("");
        if (textfieldMunicion.getText().compareToIgnoreCase("") != 0
                && textfieldNombre.getText().compareToIgnoreCase("") != 0) {
            buttonAniadir.setDisable(false);
        } else {
            buttonAniadir.setDisable(true);
        }
    }

    /**
     * Este metodo verifica que el el TextField Nombre este rellenado
     * adecuadamente
     *
     * @param comp El textfield a comprobar
     * @return un Booleano
     */
    private Boolean verifyNombre(String comp) {
        if (MetodosUtiles.maximoCaracteres(textfieldNombre, 50)) {
            return true;
        } else {
            labelError.setText("El campo Nombre debe tener menos caracteres");
            labelError.setTextFill(Color.web("#ff0000"));
            return false;
        }
    }

    /**
     * Este metodo verifica que el el TextField Municion este rellenado
     * adecuadamente
     *
     * @param comp El textfield a comprobar
     * @return un Booleano
     */
    private Boolean verifyMunicion(String comp) {
        if (MetodosUtiles.verifyFloat(comp)) {
            return true;
        } else {
            labelError.setText("El campo Municion debe tener contener numeros");
            labelError.setTextFill(Color.web("#ff0000"));
            return false;
        }
    }

    /**
     * Metodo para volver a la ventana Sectors.
     *
     * @param event el evento
     */
    @FXML
    private void clickVolver(ActionEvent event) {
        try {
            LOGGER.log(Level.INFO, "Método clickVolver de la clase ArmyManagementController");
            FXMLLoader loader = new FXMLLoader(getClass().
                    getResource("/view/FXMLSectorManagement.fxml"));
            Parent root = (Parent) loader.load();
            SectorManagementController sectorManagementController = (SectorManagementController) loader.getController();
            sectorManagementController.setUser(user);
            sectorManagementController.setStage(stage);
            sectorManagementController.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(ArmyManagementController.class.getName()).log(Level.SEVERE, null, ex);
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
}
