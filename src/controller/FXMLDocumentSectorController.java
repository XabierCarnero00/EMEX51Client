/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import bussinesLogic.BusinessLogicException;
import bussinesLogic.CreaturaExistException;
import bussinesLogic.SectorExistException;
import bussinesLogic.SectorManager;
import bussinesLogic.SectorManagerFactory;
import java.io.IOException;
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
import model.Sector;
import model.SectorType;

/**
 * FXML Controller class for <code>Sector</code> class.
 * @author Endika Ubierna Lopez.
 */
public class FXMLDocumentSectorController{
    /**
     * Logger object used to control activity from class FXMLDocumentSectorController.
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
     * A sector selected in te table.
     */
    private Sector sector;
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
     * Label for user info.
     */
    @FXML 
    private Label lblUsuario;
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
    /**
     * Button updating a sector.
     */
    @FXML 
    private Button btnModificar;
    /**
     * Button creating a sector.
     */
    @FXML 
    private Button btnAnadir;
    /**
     * Button returning to the previous scene.
     */
    @FXML 
    private Button btnVolver;
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
            btnModificar.setDisable(true);

            //La tabla sera editable.
            tbSectores.setEditable(true);
            //Añadir listener a la seleccion de la tabla 
            tbSectores.getSelectionModel().selectedItemProperty().addListener(this::manejarSeleccionTabla);
            //Set factorias a las celdas de las columnas de la tabla.
            colNombre.setCellValueFactory(new PropertyValueFactory<>("name"));

            colNombre.setCellFactory(TextFieldTableCell.forTableColumn());
            colNombre.setOnEditCommit((CellEditEvent<Sector,String> t) -> {
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setName(t.getNewValue());
            });
            colTipo.setCellValueFactory(new PropertyValueFactory<>("type"));
                //Añadir las opciones de busqueda  a la combo
            ObservableList<SectorType> options = FXCollections.observableArrayList((SectorType.values()));
            colTipo.setCellFactory(ComboBoxTableCell.forTableColumn(options));
            colTipo.setOnEditCommit((CellEditEvent<Sector,SectorType> t) -> {
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setType(t.getNewValue());
            });

            cbTipo.setItems(options);
            sectorsData = FXCollections.observableArrayList(sectorManager.getAllSectors());
            tbSectores.setItems(sectorsData);
            //Ejecutar método handleWindowShowing cuando se produzca el evento setOnShowing
            //Este evento se lanza cuando la ventana esta a punto de aparecer. Este evento no se lanza al volver de otra escena porque no abrimos una stage cambiamos de scene
            stage.setOnShowing(this::manejarInicioVentana);
            //Ejecutar método evento acción clickar botón
            btnIr.setOnAction(this::accionBotonIr);
            btnAnadir.setOnAction(this::accionBotonAnadir);
            btnBorrar.setOnAction(this::accionBotonBorrar);
            btnVolver.setOnAction(this::accionBotonVolver);
            btnModificar.setOnAction(this::accionBotonModificar);
            //Ejecutar método cambioTexto cuando el texto de el campo  cambie.
            txtFieldNombre.textProperty().addListener(this::cambioTexto);
            //Si se pulsa la x de la ventana para salir
            stage.setOnCloseRequest(this::manejarCierreVentana);
            //Hace visible la pantalla
            stage.show();           
        }catch(BusinessLogicException e){
            mostrarVentanaAlertError("No se ha podido abrir la ventana.");
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
            btnAnadir.setDisable(false);
            btnIr.setDisable(false);
            btnBorrar.setDisable(false);
        }else{
        //No hay ningun elemento de la tabla seleccionado limpiar los textfields
            btnAnadir.setDisable(true);
            btnIr.setDisable(false);
            btnBorrar.setDisable(false);
        }
        //Focus textfield nombre
        txtFieldNombre.requestFocus();
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
        if(this.txtFieldNombre.getText().equals(""))
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
     * Action event handler for return button. Returns to the previews scene.
     * @param event The ActionEvent object for the event.
     */
    private void accionBotonBorrar(ActionEvent event){
        LOGGER.info("Iniciando SectorController::accionBotonBorrar");
        mostrarVentanaAlertError("Estás seguro que quieres salir?.");
            //Abrir ventana sector
        try{
            //Mirar si el sector tiene contenido si tiene avisar
            //si no9 co9nfirmacio9n bo9rrado9
        }catch(Exception e){
            mostrarVentanaAlertError("Error al intentar borrar.");
        } 
    }             

    /**
     * Action event handler for return button. Returns to the previews scene.
     * @param event The ActionEvent object for the event.
     */
    private void accionBotonAnadir(ActionEvent event){
       LOGGER.info("Iniciando SectorController::accionBotonAnadir");
       Alert alert = null;
       try{
           Sector newSector = new Sector();
           newSector.setName(txtFieldNombre.getText().trim());
           if(cbTipo.getSelectionModel().getSelectedItem()==null)
               mostrarVentanaAlertError("Elige un tipo de sector");
           else{
              newSector.setType((SectorType)cbTipo.getSelectionModel().getSelectedItem());
              sectorManager.sectorNameIsRegistered(newSector.getName());
              alert = new Alert(Alert.AlertType.CONFIRMATION,"Confirmar registro?.");
              Optional<ButtonType> result = alert.showAndWait();
              if (result.get() == ButtonType.OK) {
                    //llamar al metodo de creatureimplementation
                    sectorManager.createSector(sector);
                    //Añadir en la tabla el nuevo registro
                    tbSectores.getItems().add(newSector);
                    //creo que se puede hacer asi directamente y la tabla reconoce el cambio en la lista observable
                    //creaturesData.add(creature);
                    //Vaciar textfields y datepicker
                    txtFieldNombre.setText("");
                    cbTipo.setValue(null);
                }
           }
       }catch(BusinessLogicException e){
            Logger.getLogger(FXMLDocumentCreatureController.class.getName()).log(Level.SEVERE, null, e); 
            mostrarVentanaAlertError("Error al añadir sector");
       } catch (SectorExistException ex) {
            Logger.getLogger(FXMLDocumentSectorController.class.getName()).log(Level.SEVERE, null, ex);
            mostrarVentanaAlertError(ex.getMessage());
            txtFieldNombre.requestFocus();
            txtFieldNombre.setStyle("-fx-text-inner-color: red;");
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
               FXMLDocumentCreatureController controladorCritauras = (FXMLDocumentCreatureController)loader.getController();
               //Pasar la ventana al controlador de la ventana signIn.
               controladorCritauras.setStage(stage);
               //Llamada al método initStage del controlador de la ventana signIn. Pasa el documento fxml en un nodo.
               controladorCritauras.initStage(root);               
            }else{
  
            }
        //Error al cargar la nueva escenamostrar mensaje.
        }catch(IOException e){
            mostrarVentanaAlertError("Error al intentar salir, espera unos segundos.");
        } 
    } 
    /**
     * Action event handler for update button. Updates a sector.
     * @param event The ActionEvent object for the event.
     */
    private void accionBotonModificar(ActionEvent event){
 /*       LOGGER.info("Iniciando CreatureController::accionBotonVolver");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Estás seguro que quieres salir?.");
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {       
            //Abrir ventana sector
        try{
            //New FXMLLoader Añadir el fxml de sector que es la ventana a la que se redirige si todo va bien
            FXMLLoader loader = new FXMLLoader(getClass().
                getResource("/view/FXMLDocumentSector.fxml"));
            //Parent es una clase gráfica de nodos xml son nodos.
            Parent root = (Parent)loader.load();
            //Relacionamos el documento FXML con el controlador que le va a controlar.
            FXMLDocumentSectorController controladorSector = (FXMLDocumentSectorController)loader.getController();
            //Pasar la ventana al controlador de la ventana signIn.
            controladorSector.setStage(stage);
            //Llamada al método initStage del controlador de la ventana signIn. Pasa el documento fxml en un nodo.
            controladorSector.initStage(root);
        //Error al cargar la nueva escenamostrar mensaje.
        }catch(IOException e){
            alert = new Alert(Alert.AlertType.ERROR,"Error al intentar salir, espera unos segundos."
                    ,ButtonType.OK);
            alert.showAndWait();
        } 
      }             
 */     }
    /**
     * Action event handler for return button. Returns to the previews scene.
     * @param event The ActionEvent object for the event.
     */
    private void accionBotonVolver(ActionEvent event){
        LOGGER.info("Iniciando MenuPrincipalController::accionBotonVolver");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Estás seguro que quieres salir?.");
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {       
            //Abrir ventana principal
        try{
            //New FXMLLoader Añadir el fxml de sector que es la ventana a la que se redirige si todo va bien
            FXMLLoader loader = new FXMLLoader(getClass().
                getResource("/view/MenuPrincipal.fxml"));
            //Parent es una clase gráfica de nodos xml son nodos.
            Parent root = (Parent)loader.load();
            //Relacionamos el documento FXML con el controlador que le va a controlar.
            MenuPrincipalController controladorMenuPrincipal = (MenuPrincipalController)loader.getController();
            //Pasar la ventana al controlador de la ventana signIn.
            controladorMenuPrincipal.setStage(stage);
            //Llamada al método initStage del controlador de la ventana signIn. Pasa el documento fxml en un nodo.
            controladorMenuPrincipal.initStage(root);
        //Error al cargar la nueva escenamostrar mensaje.
        }catch(IOException e){
            mostrarVentanaAlertError("Error al intentar salir, espera unos segundos.");
        } 
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
}
