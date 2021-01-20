/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
import businessLogic.BusinessLogicException;
import businessLogic.VisitorManager;
import businessLogic.VisitorManagerFactory;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import model.Visitor;

/**
 *
 * @author markel
 */
public class GestionarVisitorController {
    
    /**
     * Logger object used to control activity from class FXMLDocumentVisitorController.
     */
    private static final Logger LOGGER = Logger.getLogger("emex51.cliente.controlador.FXMLDocumentVisitorController");
    /**
     * The Window showing sector components.
     */
    private Stage stage;
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
    private TableColumn <Visitor,String> colEmail;
    /**
     * TableColumn contains visitor dni.
     */
    @FXML 
    private TableColumn <Visitor,String> colDni;
    /**
     * TableColumn contains visitor fullName.
     */
    @FXML 
    private TableColumn <Visitor,String> colFullName;
    /**
     * TableColumn contains visitor last Acces
     */
    @FXML 
    private TableColumn <Visitor,Date> colLastAccess;
    /**
     * TableColumn contains if the visitor visited
     */
    @FXML 
    private TableColumn <Visitor,Boolean> colVisited;
    /**
     * TableColumn contains visitor Reply
     */
    @FXML 
    private TableColumn <Visitor,Boolean> colVisitReply;
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
     * Combo for different sector type.
     */
    @FXML 
    private ComboBox cbTipo; 
    /**
     * Button removing a visitor.
     */
    @FXML 
    private Button btnBorrar;
       
    /**
     * Sectors table data model.
     */
    private ObservableList<Visitor> visitorsData;  
    /**
     * Sets a stage.
     * @param stage A JavaFx stage.
     */
    public void setStage(Stage stage) {
        this.stage = stage;    
    }    
    /**
     * Gets a stage.
     * @return A stage.
     */
    public Stage getStage(){
        return stage;
    } 
    /**
     * This method adds a scene in the window and initializes the components of the scene.
     * @param root A FXML node in graphic format.
     */
    public void initStage(Parent root) {
        try{
            LOGGER.log(Level.INFO,"Inicializando la ventana Visitantes.");
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

            //La tabla sera editable.
            tblVisitors.setEditable(true);
            
            //Añadir listener a la seleccion de la tabla 
            tblVisitors.getSelectionModel().selectedItemProperty().addListener(this::manejarSeleccionTabla);
            
            ObservableList<String> cbxOptions = FXCollections.observableArrayList();
            cbxOptions.addAll("Nombre", "Todos");            
            cbxBuscar.setItems(cbxOptions);
            
            
            
            //Set factorias a las celdas de las columnas de la tabla.
            colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

            colEmail.setCellFactory(TextFieldTableCell.forTableColumn());
            colEmail.setOnEditCommit((CellEditEvent<Visitor,String> t) -> {
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
            colDni.setOnEditCommit((CellEditEvent<Visitor,String> t) -> {
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
            colFullName.setOnEditCommit((CellEditEvent<Visitor,String> t) -> {
                try {
                    t.getTableView().getItems().get(t.getTablePosition().getRow()).setFullName(t.getNewValue());
                    //guardar en la base de datos donde cojo el sector
                    visitor = t.getRowValue();
                    visitorManager.updateVisitor(visitor);
                } catch (BusinessLogicException ex) {
                    LOGGER.info("Error al updatear en la lambda del email edit");
                }
            });

            visitorsData = FXCollections.observableArrayList(visitorManager.getAllVisitors());
            tblVisitors.setItems(visitorsData);         
            
            //Ejecutar método evento acción clickar botón
            btnBorrar.setOnAction(this::accionBotonBorrar);
            
            
            //Hace visible la pantalla
            stage.show();           
        }catch(Exception ex){
            Logger.getLogger(GestionarVisitorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    /**
     * This method handles the event when a row of the table is selected.
     * @param observable Property being observed.
     * @param oldValue Old creature row selected.
     * @param newValue New creature row selected.
     */
    private void manejarSeleccionTabla(ObservableValue observable, Object oldValue, Object newValue) {
        LOGGER.info("Iniciando VisitorController::manejarSeleccionTabla");
        //If there is a row selected, move row data to corresponding fields in the
        //window and enable create, modify and delete buttons
        if(newValue!=null){
            visitor = (Visitor) newValue;
            btnBorrar.setDisable(false);
        }else{
        //No hay ningun elemento de la tabla seleccionado limpiar los textfields
            btnBorrar.setDisable(false);

        }
    } 
    /**
     * Action event handler for return button. Returns to the previews scene.
     * @param event The ActionEvent object for the event.
     */
    private void accionBotonBorrar(ActionEvent event){
        LOGGER.info("Iniciando VisitorController::accionBotonBorrar");
           Alert alert = null;
        try{
            //Mirar si el sector tiene contenido si tiene avisar
            //si no confirmamos 
            alert = new Alert (Alert.AlertType.CONFIRMATION, "Estas seguro de borrar un visitante?");
            Optional <ButtonType> result = alert.showAndWait();
            if (result.get()==ButtonType.OK)
                visitorManager.deleteVisitor(visitor);
        }catch(Exception ex){
            Logger.getLogger(GestionarVisitorController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }   
    
    private void clickBuscar(ActionEvent event) {
        if (cbxBuscar.getValue().equals("Todos")) {
            try {
                visitorsData = FXCollections.observableArrayList(visitorManager.getAllVisitors());
            } catch (BusinessLogicException ex) {
                Logger.getLogger(GestionarVisitorController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (cbxBuscar.getValue().equals("Nombre")) {
            try {
                visitorsData = FXCollections.observableArrayList(visitorManager.getVisitorsByName(txtBuscar.getText().trim()));
            } catch (BusinessLogicException ex) {
                Logger.getLogger(GestionarVisitorController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        tblVisitors.setItems(visitorsData);
    }
    /**
    @FXML
    private void changeDatePicker(ActionEvent event) {
        try {
            visitorsData = FXCollections.observableArrayList(visitorManager.getVisitorsByDate(Date.from(datePicker.getValue().atStartOfDay().toInstant(ZoneOffset.UTC))));
            tblVisitors.setItems(visitorsData);
        } catch (BusinessLogicException ex) {
            Logger.getLogger(GestionarVisitorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    **/
}
