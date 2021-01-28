/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import businessLogic.BusinessLogicException;
import businessLogic.VisitorManager;
import businessLogic.VisitorManagerFactory;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Boss;
import model.User;
import model.UserStatus;
import model.Visitor;
import securityClient.ClavePublicaCliente;

/**
 *
 * @author markel
 */
public class VisitorManagementController {

    /**
     * Logger object used to control activity from class
     * FXMLDocumentVisitorController.
     */
    private static final Logger LOGGER = Logger.getLogger("emex51.cliente.controlador.FXMLDocumentVisitorController");
    /**
     * The Window showing sector components.
     */
    private Stage stage;
    /**
     * The User that Manages the Window.
     */
    User user;
    /**
     * A visitor selected in te table.
     */
    private Visitor visitor;
    /**
     * Combo Box to list the visitors
     */
    @FXML
    private ComboBox cbxBuscar;
    /**
     * Text field to search visitors
     */
    @FXML
    private TextField txtBuscar;
    /**
     * Button to search
     */
    @FXML
    private Button btnBuscar;
    /**
     * Date Picker to search visitors
     */
    @FXML
    private DatePicker datePicker;
    /**
     * TableView containing visitors.
     */
    @FXML
    private TableView tblVisitors;
    /**
     * TableColumn contains visitor email.
     */
    @FXML
    private TableColumn<Visitor, String> colEmail;
    /**
     * TableColumn contains visitor dni.
     */
    @FXML
    private TableColumn<Visitor, String> colDni;
    /**
     * TableColumn contains visitor fullName.
     */
    @FXML
    private TableColumn<Visitor, String> colFullName;
    /**
     * TableColumn contains visitor last Acces
     */
    @FXML
    private TableColumn<Visitor, Date> colVisitDate;
    /**
     * TableColumn contains if the visitor visited
     */
    @FXML
    private TableColumn<Visitor, Boolean> colVisited;
    /**
     * TableColumn contains visitor Reply
     */
    private TableColumn<Visitor, Boolean> colVisitReply;
    /**
     * A {@link SectorManager} object
     */
    private VisitorManager visitorManager;
    /**
     * Label for user info.
     */
    @FXML
    private Label lblUsuario;
    /**
     * Button removing a visitor.
     */
    @FXML
    private Button btnBorrar;

    @FXML
    private AnchorPane visitorPane;
    @FXML
    private TableColumn<?, ?> colLastAccess;
    @FXML
    private Label lblVisitanteTitulo;

    /**
     * Sectors table data model.
     */
    private ObservableList<Visitor> visitorsData;
    @FXML
    private Menu menu;
    @FXML
    private MenuItem ItemSectores;
    @FXML
    private MenuItem ItemEmpleados;
    @FXML
    private MenuItem ItemVisitantes;
    @FXML
    private Menu MenuLogout;
    @FXML
    private MenuItem ItemExit;
    @FXML
    private MenuItem ItemLogout;

    /**
     * Sets a stage.
     *
     * @param stage A JavaFx stage.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Gets a stage.
     *
     * @return A stage.
     */
    public Stage getStage() {
        return stage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * This method adds a scene in the window and initializes the components of
     * the scene.
     *
     * @param root A FXML node in graphic format.
     */
    public void initStage(Parent root) {
        try {
            LOGGER.log(Level.INFO, "Inicializando la ventana Visitantes.");
            //Creación de  una nueva escena
            Scene scene = new Scene(root);
            //Añadir escena a la ventana
            stage.setScene(scene);
            //Asignación de un título a la ventana
            stage.setTitle("Visitantes");
            //Asignar tamaño fijo a la ventana
            stage.setResizable(false);

            //Conseguimos un visitor managerimplementation
            visitorManager = VisitorManagerFactory.getVisitorManager();

            //Botones inhabilitados al arrancar la ventana. Todos menos el de volver.
            btnBorrar.setDisable(true);
            btnBuscar.setDisable(true);
            //datePicker inicialicing 
            datePicker.setEditable(false);
            //La tabla sera editable.
            tblVisitors.setEditable(true);
            datePicker.setEditable(false);

            //Añadir listener a la seleccion de la tabla 
            tblVisitors.getSelectionModel().selectedItemProperty().addListener(this::manejarSeleccionTabla);

            ObservableList<String> cbxOptions = FXCollections.observableArrayList();
            cbxOptions.addAll("Nombre", "Todos");
            cbxBuscar.setItems(cbxOptions);

            visitorsData = FXCollections.observableArrayList(visitorManager.getAllVisitors());
            if (visitorsData != null) {
                loadVisitorsTable(visitorsData);
            }

            tblVisitors.setItems(visitorsData);

            //Ejecutar método evento acción clickar botón
            btnBuscar.setOnAction(this::clickBuscar);
            btnBorrar.setOnAction(this::accionBotonBorrar);
            datePicker.setOnAction(this::changeDatePicker);

            //Label para el tipo de usuario
            if (user instanceof Boss) {
                lblUsuario.setText(user.getLogin() + "(BOSS)");
            } else {
                lblUsuario.setText(user.getLogin() + "(EMPLEADO)");
                ItemEmpleados.setDisable(true);
            }

            //Hace visible la pantalla
            stage.show();
        } catch (Exception ex) {
            Logger.getLogger(VisitorManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method handles the event when a row of the table is selected.
     *
     * @param observable Property being observed.
     * @param oldValue Old creature row selected.
     * @param newValue New creature row selected.
     */
    private void manejarSeleccionTabla(ObservableValue observable, Object oldValue, Object newValue) {
        LOGGER.info("Iniciando VisitorController::manejarSeleccionTabla");
        //If there is a row selected, move row data to corresponding fields in the
        //window and enable create, modify and delete buttons
        if (newValue != null) {
            visitor = (Visitor) newValue;
            btnBorrar.setDisable(false);
        } else {
            //No hay ningun elemento de la tabla seleccionado limpiar los textfields
            btnBorrar.setDisable(false);

        }
    }

    private void loadVisitorsTable(ObservableList<Visitor> visitors) {
        //Set factorias a las celdas de las columnas de la tabla.
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        colEmail.setCellFactory(TextFieldTableCell.forTableColumn());
        colEmail.setOnEditCommit((CellEditEvent<Visitor, String> t) -> {
            try {
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setEmail(t.getNewValue());
                //guardar en la base de datos donde cojo el sector
                visitor = t.getRowValue();
                visitorManager.updateVisitor(visitor);
            } catch (BusinessLogicException ex) {
                LOGGER.info("Error al updatear en la lambda del email edit");
            }
        });

        //Set factorias a las celdas de las columnas de la tabla.
        colDni.setCellValueFactory(new PropertyValueFactory<>("dni"));

        colDni.setCellFactory(TextFieldTableCell.forTableColumn());
        colDni.setOnEditCommit((CellEditEvent<Visitor, String> t) -> {
            try {
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setDni(t.getNewValue());
                //guardar en la base de datos donde cojo el sector
                visitor = t.getRowValue();
                visitorManager.updateVisitor(visitor);
            } catch (BusinessLogicException ex) {
                LOGGER.info("Error al updatear en la lambda del email edit");
            }
        });

        //Set factorias a las celdas de las columnas de la tabla.
        colFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));

        colFullName.setCellFactory(TextFieldTableCell.forTableColumn());
        colFullName.setOnEditCommit((CellEditEvent<Visitor, String> t) -> {
            try {
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setFullName(t.getNewValue());
                //guardar en la base de datos donde cojo el sector
                visitor = t.getRowValue();
                visitorManager.updateVisitor(visitor);
            } catch (BusinessLogicException ex) {
                LOGGER.info("Error al updatear en la lambda del email edit");
            }
        });

        colVisitDate.setCellValueFactory(new PropertyValueFactory<>("visitDate"));
        colVisited.setCellValueFactory(new PropertyValueFactory<>("visited"));
        //colVisitReply.setCellValueFactory(new PropertyValueFactory<>("visitReply"));

        tblVisitors.setItems(visitors);
    }

    /**
     * Action event handler for return button. Returns to the previews scene.
     *
     * @param event The ActionEvent object for the event.
     */
    private void accionBotonBorrar(ActionEvent event) {
        LOGGER.info("Iniciando VisitorController::accionBotonBorrar");
        Alert alert = null;
        try {
            //Mirar si el sector tiene contenido si tiene avisar
            //si no confirmamos 
            alert = new Alert(Alert.AlertType.CONFIRMATION, "Estas seguro de borrar un visitante?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                visitorManager.deleteVisitor(visitor);
                //tblVisitors.getItems().remove(visitor);
                visitorsData = FXCollections.observableArrayList(visitorManager.getAllVisitors());
                tblVisitors.setItems(visitorsData);
            }
        } catch (BusinessLogicException ex) {
            Logger.getLogger(VisitorManagementController.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            mostrarVentanaAlertError("Error al intentar borrar.");
        }
    }

    private void cbListener(ActionEvent event) {
        btnBuscar.setDisable(false);
        if (cbxBuscar.getValue().equals("Todos")) {
            txtBuscar.setText("");
            txtBuscar.setDisable(true);
        } else {
            txtBuscar.setDisable(false);
        }
    }

    private void clickBuscar(ActionEvent event) {
        if (cbxBuscar.getValue().equals("Todos")) {
            try {
                visitorsData = FXCollections.observableArrayList(visitorManager.getAllVisitors());

            } catch (BusinessLogicException ex) {
                Logger.getLogger(VisitorManagementController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        } else if (cbxBuscar.getValue().equals("Nombre")) {
            try {
                visitorsData = FXCollections.observableArrayList(visitorManager.getVisitorsByName(txtBuscar.getText().trim()));

            } catch (BusinessLogicException ex) {
                Logger.getLogger(VisitorManagementController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        tblVisitors.setItems(visitorsData);
    }

    private void changeDatePicker(ActionEvent event) {
        try {
            visitorsData = FXCollections.observableArrayList(visitorManager.getVisitorsByDate(Date.from(datePicker.getValue().atStartOfDay().toInstant(ZoneOffset.UTC))));
            tblVisitors.setItems(visitorsData);
            datePicker.setValue(null);

        } catch (BusinessLogicException ex) {
            Logger.getLogger(VisitorManagementController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This methos shows alert error messages.
     *
     * @param msg Message shown in the alert.
     */
    private static void mostrarVentanaAlertError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg,
                ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    @FXML
    private void accionComboBox(ActionEvent event) {
        btnBuscar.setDisable(false);
        if (cbxBuscar.getValue().equals("Todos")) {
            txtBuscar.setText("");
            txtBuscar.setDisable(true);
        } else {
            txtBuscar.setDisable(false);
        }
    }

    //HERE STARTS WHAT CONTROLS THE MENU
    @FXML
    private void openWindowLogout(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Seguro que quieres cerrar sesion?");
            alert.setTitle(null);
            alert.setHeaderText(null);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                //New FXMLLoader Añadir el fxml de MenuPrincipal que es la ventana principal
                FXMLLoader loader = new FXMLLoader(getClass().
                        getResource("/view/FXMLSignIn.fxml"));
                //Parent es una clase gráfica de nodos xml son nodos.
                Parent root = (Parent) loader.load();
                //Relacionamos el documento FXML con el controlador que le va a controlar.
                SignInController signInController = (SignInController) loader.getController();
                //Llamada al método setStage del controlador de la ventana SignIn. Pasa la ventana.
                signInController.setStage(getStage());
                //Llamada al método initStage del controlador de la ventana SignIn. Pasa el documento fxml en un nodo.
                signInController.initStage(root);
            }
        } catch (IOException ex) {
            LOGGER.log(Level.INFO, "Execepción abrir ventana Sector");
        }
    }

    @FXML
    private void openWindowEmployee(ActionEvent event) {
        try {
            //New FXMLLoader Añadir el fxml de logout que es la escena a la que se redirige si todo va bien
            FXMLLoader loader = new FXMLLoader(getClass().
                    getResource("/view/FXMLEmployeeManagement.fxml"));
            //Parent es una clase gráfica de nodos. xml son nodos.
            Parent root = (Parent) loader.load();
            //Relacionamos el documento FXML con el controlador que le va a controlar.
            EmployeeManagementController gestionarEmployeeController = (EmployeeManagementController) loader.getController();
            //Set the User
            gestionarEmployeeController.setUser(user);
            //Llamada al método setStage del controlador de la ventana signIn. Pasa la ventana.
            gestionarEmployeeController.setStage(stage);
            //Llamada al método initStage del controlador de la ventana LogOut. Pasa el documento fxml en un nodo.
            gestionarEmployeeController.initStage(root);
        } catch (IOException ex) {
            LOGGER.log(Level.INFO, "Execepción abrir ventana Empleado");
        }
    }

    @FXML
    private void openWindowVisitor(ActionEvent event) {
        try {
            //New FXMLLoader Añadir el fxml de logout que es la escena a la que se redirige si todo va bien
            FXMLLoader loader = new FXMLLoader(getClass().
                    getResource("/view/FXMLVisitorManagement.fxml"));
            //Parent es una clase gráfica de nodos. xml son nodos.
            Parent root = (Parent) loader.load();
            //Relacionamos el documento FXML con el controlador que le va a controlar.
            VisitorManagementController gestionarVisitorController = (VisitorManagementController) loader.getController();
            //Llamada al método setStage del controlador de la ventana signIn. Pasa la ventana.
            gestionarVisitorController.setStage(stage);
            //Set the User
            gestionarVisitorController.setUser(user);
            //Llamada al método initStage del controlador de la ventana LogOut. Pasa el documento fxml en un nodo.
            gestionarVisitorController.initStage(root);
        } catch (IOException ex) {
            LOGGER.log(Level.INFO, "Execepción abrir ventana Visitante");
        }
    }

    @FXML
    private void openWindowSector(ActionEvent event) {
        try {
            //New FXMLLoader Añadir el fxml de sector management que es la escena a la que se redirige si todo va bien
            FXMLLoader loader = new FXMLLoader(getClass().
                    getResource("/view/FXMLSectorManagement.fxml"));
            //Parent es una clase gráfica de nodos. xml son nodos.
            Parent root = (Parent) loader.load();
            //Relacionamos el documento FXML con el controlador que le va a controlar.
            SectorManagementController sectorManagementController = (SectorManagementController) loader.getController();
            //Set the User
            sectorManagementController.setUser(user);
            //Llamada al método setStage del controlador de la ventana signIn. Pasa la ventana.
            sectorManagementController.setStage(getStage());
            //Llamada al método initStage del controlador de la ventana LogOut. Pasa el documento fxml en un nodo.
            sectorManagementController.initStage(root);
        } catch (IOException ex) {
            LOGGER.log(Level.INFO, "Execepción abrir ventana Sector");
        }
    }

    @FXML
    private void openWindowExit(ActionEvent event) {
        LOGGER.info("Iniciando Controller::alertaCerrarPestaña");
        Alert alert;

        alert = new Alert(Alert.AlertType.CONFIRMATION, "Estás seguro que quieres salir?.");
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();
        event.consume();
        if (result.get() == ButtonType.OK) {
            //Cerrar ventana
            stage.close();
        }
    }

    /**
     * This method controls the event window closing when the window [x] is
     * clicked.
     *
     * @param event Window closing
     */
    private void manejarCierreVentana(WindowEvent event) {
        LOGGER.info("Iniciando CreatureController::manejarCierreVentana");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Estás seguro que quieres salir?", ButtonType.OK, ButtonType.CANCEL);
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            //ssi pulsa ok la ventana se cierra
            stage.close();
        } else {
            //pulsa cancelar se consume el evento. Es decir, no hace nada y por tanto, se queda en la ventana.
            event.consume();
        }
    }

}
