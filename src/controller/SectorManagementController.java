/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import bussinesLogic.BusinessLogicException;
import bussinesLogic.SectorExistException;
import bussinesLogic.SectorManager;
import bussinesLogic.SectorManagerFactory;
import clientREST.SectorContentREST;
import java.io.IOException;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.ws.rs.core.GenericType;
import model.Sector;
import model.SectorContent;
import model.SectorType;
import model.User;
import model.UserPrivilege;

/**
 * FXML Controller class for <code>Sector</code> class.
 * @author Endika Ubierna Lopez.
 */
public class SectorManagementController{
    /**
     * Logger object used to control activity from class SectorManagementController.
     */
    private static final Logger LOGGER = Logger.getLogger("emex51.cliente.controlador.FXMLDocumentSectorController");
    /**
     * The Window showing sector components.
     */
    private Stage stage;
    /**
     * A {@link SectorManager} object 
     */
    private SectorManager sectorManager;
    /**
     * Max length allow in current stage textFields.
     */
    private static final Integer TEXTFIELD_MAX_LENGTH = 20;
    /**
     * Min length allow in current stage textFields.
     */
    private static final Integer TEXTFIELD_MIN_LENGTH = 4;
    /**
     * A sector selected in te table.
     */
    private Sector sector;
    /**
     * The user logged session.
     */
    private User user;
    /**
     * TableView containing sectors.
     */
    @FXML 
    private TableView tbSectores;
    /**
     * TableColumn contains sector name.
     */
    @FXML 
    private TableColumn <Sector,String> colNombre;
    /**
     * TableColumn contains sector type.
     */
    @FXML 
    private TableColumn <Sector,SectorType> colTipo;
    /**
     * TextField for sector name.
     */
    @FXML 
    private TextField txtFieldNombre;
    /**
     * Combo for different sector type.
     */
    @FXML 
    private ComboBox cbTipo; 
    /**
     * Button removing a sector.
     */
    @FXML 
    private Button btnBorrar;
    @FXML 
    private Label lblUsuario;
    /**
     * Button creating a sector.
     */
    @FXML 
    private Button btnAnadir;
    /**
     * Button redirecting to the sectorcontent scene.
     */
    @FXML 
    private Button btnIr;
    /**
     * Sectors table data model.
     */
    private ObservableList<Sector> sectorsData;    
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
     * 
     * @return 
     */
    public User getUser() {
        return user;
    }
    /**
     * 
     * @param user 
     */
    public void setUser(User user) {
        this.user = user;
    }
    /**
     * This method adds a scene in the window and initializes the components of the scene.
     * @param root A FXML node in graphic format.
     */
    public void initStage(Parent root) {
        try{
            LOGGER.log(Level.INFO,"Inicializando la ventana Sectors. ");
            //Creación de  una nueva escena
            Scene scene = new Scene(root);
            //Añadir escena a la ventana
            stage.setScene(scene);
            //Asignación de un título a la ventana
            stage.setTitle("Sectores");
            //Asignar tamaño fijo a la ventana
            stage.setResizable(false); 
            //Conseguimos un creature managerimplementation
            sectorManager = SectorManagerFactory.getSectorManager();
            //Asignar texto cuando el campo está desenfocado.        
            txtFieldNombre.setPromptText("Sector");

            //Botones inhabilitados al arrancar la ventana. Todos menos el de volver.
            btnIr.setDisable(true);
            btnAnadir.setDisable(true);
            btnBorrar.setDisable(true);
            
            if(user.getPrivilege()==UserPrivilege.BOSS)
                lblUsuario.setText(user.getLogin()+" (Boss)");
            else
               lblUsuario.setText(user.getLogin()+" (Employee)");
            //La tabla sera editable.
            tbSectores.setEditable(true);
            //Añadir listener a la seleccion de la tabla 
            tbSectores.getSelectionModel().selectedItemProperty().addListener(this::manejarSeleccionTabla);
            //Set factorias a las celdas de las columnas de la tabla.
            colNombre.setCellValueFactory(new PropertyValueFactory<>("name"));
            //Añadir factoria para hacerla editable. Le digo que colNombre es un textField
            colNombre.setCellFactory(TextFieldTableCell.<Sector>forTableColumn());
            //Con editcommit la hago que los cambios se guarden al pulsar enter. Es un metodo lanbda hace lo siguiente a ->
            colNombre.setOnEditCommit((CellEditEvent<Sector,String> t) -> {
                try {
                    //guardar en sector el sector seleccionado de la tabla
                    sector = (Sector)tbSectores.getSelectionModel().getSelectedItem();
                    //Actualiza el campo que se ha modificado
                    t.getTableView().getItems().get(t.getTablePosition().getRow()).setName(t.getNewValue());
                    //guardar en sector el valor nuevo
                    sector = t.getRowValue();
                    //guardar en la base de datos donde cojo el sector
                    sectorManager.updateSector(sector);
                } catch (BusinessLogicException ex) {
                    Logger.getLogger(SectorManagementController.class.getName()).log(Level.SEVERE, null, ex);
                    LOGGER.info("Error al updatear en la lambda del nombre edit");
                }
            });
            colTipo.setCellValueFactory(new PropertyValueFactory<>("type"));
            //Añadir las opciones de busqueda  a la combo. El tipo de la columna coltipo es una combo de SectorType 
            ObservableList<SectorType> options = FXCollections.observableArrayList((SectorType.values()));
            colTipo.setCellFactory(ComboBoxTableCell.forTableColumn(options));
            colTipo.setOnEditCommit((CellEditEvent<Sector,SectorType> t) -> {
                try {
                    SectorContentREST sc = new SectorContentREST();
                    List<SectorContent> contenido = sc.findSectorContentBySector(new GenericType<List<SectorContent>>() {
                    }, sector.getIdSector());
                    if (!contenido.isEmpty()) {
                        mostrarVentanaAlertError("El sector no está vacío. No se puede modificar el tipo.");
                    } else {
                        sector = (Sector) tbSectores.getSelectionModel().getSelectedItem();
                        t.getTableView().getItems().get(t.getTablePosition().getRow()).setType(t.getNewValue());
                        sector = t.getRowValue();
                        //guardar en la base de datos donde cojo el sector
                        sectorManager.updateSector(sector);
                    }
                } catch (BusinessLogicException ex) {
                    Logger.getLogger(SectorManagementController.class.getName()).log(Level.SEVERE, null, ex);
                    LOGGER.info("Error al updatear en la lambda del tipo edit");
                }
            });
            //Cargar la combo de añadir un nuevo sector.
            cbTipo.setItems(options);
            //Cargar la lista observable para añadir desde la base de datos a la tabla
            sectorsData = FXCollections.observableArrayList(sectorManager.getAllSectors());
            //Añadir la lista a la tabla
            tbSectores.setItems(sectorsData);
            //Ejecutar método handleWindowShowing cuando se produzca el evento setOnShowing
            //Este evento se lanza cuando la ventana esta a punto de aparecer. Este evento no se lanza al volver de otra escena porque no abrimos una stage cambiamos de scene
            stage.setOnShowing(this::manejarInicioVentana);
            //Ejecutar método evento acción clickar botón
            btnIr.setOnAction(this::accionBotonIr);
            btnAnadir.setOnAction(this::accionBotonAnadir);
            btnBorrar.setOnAction(this::accionBotonBorrar);
            //Ejecutar método cambioTexto cuando el texto de el campo  cambie.
            txtFieldNombre.textProperty().addListener(this::cambioTexto);
            //Si se pulsa la x de la ventana para salir
            stage.setOnCloseRequest(this::manejarCierreVentana);
            //Hace visible la pantalla
            stage.show();           
        }catch(BusinessLogicException e){
            mostrarVentanaAlertError("No se ha podido iniciar la ventana");
        }
    } 
    /**
     * Acciones que se realizan en el momento previo a que se muestre la ventana.
     * @param event Evento de ventana.
     */
    private void manejarInicioVentana(WindowEvent event){
        LOGGER.info("Iniciando CreatureController::handleWindowShowing.Metodo_ManejarInicioVentana");
        //Asignar texto cuando el campo está desenfocado.
        txtFieldNombre.setPromptText("Introduce el nombre.");
        txtFieldNombre.setEditable(false);
        //El boton está inhabilitado al arrancar la ventana.
        //Botones inhabilitados al arrancar la ventana. Todos menos el de volver.
        btnIr.setDisable(true);
        btnAnadir.setDisable(true);
        btnBorrar.setDisable(true);
        
        tbSectores.setItems(sectorsData);
    }
    /**
     * This method handles the event when a row of the table is selected.
     * @param observable Property being observed.
     * @param oldValue Old creature row selected.
     * @param newValue New creature row selected.
     */
    private void manejarSeleccionTabla(ObservableValue observable, Object oldValue, Object newValue) {
        LOGGER.info("Iniciando CreatureController::manejarSeleccionTabla");
        //If there is a row selected, move row data to corresponding fields in the
        //window and enable create, modify and delete buttons
        if(newValue!=null){                                   
            sector = (Sector) newValue;
            btnIr.setDisable(false);
            btnBorrar.setDisable(false);
        }else{
        //No hay ningun elemento de la tabla seleccionado limpiar los textfields
            btnIr.setDisable(false);
            btnBorrar.setDisable(false);
        }
    } 

    /**
     * This method controls the event window closing when the window [x] is clicked.
     * @param event Window closing
     */
    private void manejarCierreVentana(WindowEvent event){
        LOGGER.info("Iniciando CreatureController::manejarCierreVentana");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Estás seguro que quieres salir?.",ButtonType.OK,ButtonType.CANCEL);
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            //ssi pulsa ok la ventana se cierra
            stage.close();
        }else{
            //pulsa cancelar se consume el evento. Es decir, no hace nada y por tanto, se queda en la ventana.
            event.consume();
        }
    }
    /**
     * Registers textFields changes.
     * @param observable 
     * @param oldValue Textfield value prior to the event thrown.
     * @param newValue TextField new value after the event thrown.
     */
    private void cambioTexto(ObservableValue observable,String oldValue,String newValue){
        LOGGER.info("Iniciando CreatureController::cambioTexto");
        //Si llevo 20 caracteres metidos ya no dejar meter mas.
        if(this.txtFieldNombre.getText().trim().length()>=TEXTFIELD_MAX_LENGTH){
            txtFieldNombre.setText(txtFieldNombre.getText().substring(0, (TEXTFIELD_MAX_LENGTH)));
        }
        //Si uno de los dos textfield está vacío botones deshabilitados Modificar y añadir.
        if(this.txtFieldNombre.getText().length()<TEXTFIELD_MIN_LENGTH)
            btnAnadir.setDisable(true);
        else{//Los dos campos están informados.
            //Habilitar botón 
            btnAnadir.setDisable(false);
            //Añadir tooltip al botón.
            btnAnadir.setTooltip(new Tooltip("Pulsa para registrar una sector"));
            //Habilitar el uso del teclado para moverse por la ventana.
            btnAnadir.setMnemonicParsing(true);
        }
    }
    /**
     * Action event handler for delete button.
     * @param event The ActionEvent object for the event.
     */
    private void accionBotonBorrar(ActionEvent event) {
        LOGGER.info("Iniciando SectorController::accionBotonBorrar");
        Alert alert = null;
        try {
            //Antes de borrar mirar si el sector no está vacío.
            SectorContentREST sc = new SectorContentREST();
            List <SectorContent> contenido = sc.findSectorContentBySector(new GenericType<List<SectorContent>>(){},sector.getIdSector());
            if (contenido.isEmpty()) {
                alert = new Alert(Alert.AlertType.CONFIRMATION, "Estas seguro de borrar el sector?.");
                alert.setHeaderText(null);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    sectorManager.deleteSector(sector);
                    //Borrar en la tabla el nuevo registro
                    tbSectores.getItems().remove(sector);
                }
            }else
                mostrarVentanaAlertError("El sector no está vacío. Mueve su contenido antes de borrarlo.");
        } catch (BusinessLogicException ex) {
            Logger.getLogger(SectorManagementController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            mostrarVentanaAlertError("Error al intentar borrar.");
        }
    }
    /**
     * Action event handler for return button. Returns to the previews scene.
     * @param event The ActionEvent object for the event.
     */
    private void accionBotonAnadir(ActionEvent event) {
        LOGGER.info("Iniciando SectorController::accionBotonAnadir");
        Alert alert = null;
        Sector newSector = null;
        try {
            newSector = new Sector();
            newSector.setName(txtFieldNombre.getText().trim());
            if (cbTipo.getSelectionModel().getSelectedItem() == null) {
                mostrarVentanaAlertError("Elige un tipo de sector");
            } else {
                newSector.setType((SectorType) cbTipo.getSelectionModel().getSelectedItem());
                //Mirar si el nombre de sector elegido existe
                sectorManager.sectorNameIsRegistered(newSector.getName());
                alert = new Alert(Alert.AlertType.CONFIRMATION, "Confirmar registro?.");
                alert.setTitle(null);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    //mirar que el nombre de sector sean letras
                    if (comprobarNombreCorrecto(newSector.getName())) {
                        //llamar al metodo de creatureimplementation
                        sectorManager.createSector(newSector);
                        //Añadir en la tabla el nuevo registro
                        tbSectores.getItems().add(newSector);
                        //creo que se puede hacer asi directamente y la tabla reconoce el cambio en la lista observable
                        //creaturesData.add(creature);
                        //Vaciar textfields y datepicker
                        txtFieldNombre.setText("");
                        cbTipo.setValue(null);
                    } else {
                        mostrarVentanaAlertError("El nombre del sector debe contener solo letras");
                        txtFieldNombre.requestFocus();
                    }
                }
            }
        } catch (BusinessLogicException e) {
            Logger.getLogger(CreatureManagementController.class.getName()).log(Level.SEVERE, null, e);
            mostrarVentanaAlertError("Error al añadir sector");
        } catch (SectorExistException ex) {
            Logger.getLogger(SectorManagementController.class.getName()).log(Level.SEVERE, null, ex);
            mostrarVentanaAlertError("El sector " + newSector.getName() + " ya existe");
            txtFieldNombre.requestFocus();
        }
    }
    /**
     * Action event handler for return button. Returns to the previews scene.
     * @param event The ActionEvent object for the event.
     */
    private void accionBotonIr(ActionEvent event){
        LOGGER.info("Iniciando SectorController::accionBotonIr");
        try{
            if(sector.getType().equals(SectorType.CREATURE)){
                //New FXMLLoader Añadir el fxml de sector que es la ventana a la que se redirige si todo va bien
               FXMLLoader loader = new FXMLLoader(getClass().
                   getResource("/view/FXMLDocumentCreature.fxml"));
               //Parent es una clase gráfica de nodos xml son nodos.
               Parent root = (Parent)loader.load();
               //Relacionamos el documento FXML con el controlador que le va a controlar.
               CreatureManagementController controladorCriaturas = (CreatureManagementController)loader.getController();
               //Pasar la ventana al controlador de la ventana signIn.
               controladorCriaturas.setStage(stage);
               //Le pasamos el sector
               controladorCriaturas.setSector(sector);
               //Le pasamos el usuario a la nueva escena
               controladorCriaturas.setUser(user);
               //Llamada al método initStage del controlador de la ventana signIn. Pasa el documento fxml en un nodo.
               controladorCriaturas.initStage(root);               
            }else{
  //Redireccinado a la ventana army
  //Falta añadir el controller de armys
                mostrarVentanaAlertError("No hay ventana armas");
            }
        //Error al cargar la nueva escenamostrar mensaje.
        }catch(IOException e){
            mostrarVentanaAlertError("Error al intentar salir, espera unos segundos.");
        } 
    } 
    /**
     * This methos shows alert error messages.
     * @param msg Message shown in the alert.
     */
    private static void mostrarVentanaAlertError(String msg){
        Alert alert = new Alert(Alert.AlertType.ERROR,msg
                    ,ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();       
    }
    /**
     * This method searches if a String has only letters.
     * @param field
     * @return 
     */
    private Boolean comprobarNombreCorrecto(String texto) {
        //Guardamos valos textField en string sin espacios delante ni detras.
        String textoSinEspacios = texto;
        //VAriable de retorno. Inicializar a false
        Boolean textoCorrecto = true;
        //ForEach de character. Recorremos letra a letra
        for(Character t:textoSinEspacios.toCharArray()){
            //Condición de igualdad. Propiedad equals de Character. Si el caracter actual igual a espacio
            if(!Character.isLetter(t)){
                textoCorrecto = false;
                break;
            }
        }
        return textoCorrecto;
    }
}
