/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import businessLogic.BusinessLogicException;
import businessLogic.CreaturaExistException;
import businessLogic.CreatureInterface;
import businessLogic.CreatureFactory;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Creature;
import model.Sector;
import model.User;


/**
 * FXML Controller class for <code>Creature</code> class.
 * @author Endika Ubierna Lopez.
 */
public class CreatureManagementController {
    /**
     * Logger object used to control activity from class CreatureManagementController.
     */
    private static final Logger LOGGER = Logger.getLogger("emex51.cliente.controlador.FXMLDocumentCreatureController");
    /**
     * The Window showing creature components.
     */
    private Stage stage;
    /**
     * This window comes from a sector window.
     */
    private Sector sector;
    /**
     * The user logged in.
     */
    private User user;
    /**
     * Label informing the user logged in.
     */
    @FXML
    private Label lblUsuario;
    /**
     * A CreatureManager object 
     */
    private CreatureInterface creatureManager;
    /**
     * Max length allow in current stage textFields.
     */
    private static final Integer TEXTFIELD_MAX_LENGTH = 20;
    /**
     * Min length allow in current stage textFields.
     */
    private static final Integer TEXTFIELD_MIN_LENGTH = 4;
    /**
     * TextField for creature search.
     */
    @FXML 
    private TextField txtFieldBuscar;
    /**
     * Button confirming creature search.
     */
    @FXML 
    private Button btnBuscar;
    /**
     * Combo for different creature search.
     */
    @FXML 
    private ComboBox cmbBuscar;
    /**
     * TableView containing creatures.
     */
    @FXML 
    private TableView tbCreature;
    /**
     * TableColumn contains name of creatures.
     */
    @FXML 
    private TableColumn colNombre;
    /**
     * TableColumn contains specie of creatures.
     */
    @FXML 
    private TableColumn colEspecie;
    /**
     * TableColumn contains arrivalDate of creatures.
     */
    @FXML 
    private TableColumn colFecha;
    /**
     * TextField for creature name.
     */
    @FXML 
    private TextField txtFieldNombre;
    /**
     * TextField for creature specie.
     */
    @FXML 
    private TextField txtFieldEspecie;
    /**
     * DatePicker for creature arrivalDate.
     */
    @FXML 
    private DatePicker datePickerFechaLlegada;
    /**
     * Button confirming update action.
     */
    @FXML 
    private Button btnModificar;
    /**
     * Button confirming create action.
     */
    @FXML 
    private Button btnAnadir;
    /**
     * Button confirming return action.
     */
    @FXML 
    private Button btnVolver;
    /**
     * Button empties the informative textFields.
     */
    @FXML 
    private Button btnLimpiar;
    /**
     * Creatures table data model.
     */
    private ObservableList<Creature> creaturesData;
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
     * Gets the sector object.
     * @return A sector.
     */
    public Sector getSector() {
        return sector;
    }

    /**
     * Sets the sector object.
     * @param sector
     */
    public void setSector(Sector sector) {
        this.sector = sector;
    }
    /**
     * Gets the user logged in.
     * @return An user.
     */
    public User getUser() {
        return user;
    }
    /**
     * Sets the user logged in.
     * @param user An user.
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
            LOGGER.log(Level.INFO,"Inicializando la ventana Creatures. ");
            //Creación de  una nueva escena
            Scene scene = new Scene(root);
            //Añadir escena a la ventana
            stage.setScene(scene);
            //Asignación de un título a la ventana
            stage.setTitle("Creatures");
            //Asignar tamaño fijo a la ventana
            stage.setResizable(false); 
            //Conseguimos un creature managerimplementation
            creatureManager = CreatureFactory.getCreatureInterface();

            txtFieldBuscar.setPromptText("Buscar");
            //Asignar texto cuando el campo está desenfocado.
            txtFieldNombre.setPromptText("Nombre");
            txtFieldEspecie.setPromptText("Especie");
            //El boton está inhabilitado al arrancar la ventana.
            btnModificar.setDisable(true);
            btnAnadir.setDisable(true);
            datePickerFechaLlegada.setEditable(false);
            //Añadir listener a la seleccion de la tabla 
            tbCreature.getSelectionModel().selectedItemProperty().addListener(this::manejarSeleccionTabla);
            
            //Formatear la fecha para que se vea formateada.
            colFecha.setCellFactory(column -> {
                TableCell<Creature, Date> cell = new TableCell<Creature, Date>() {
            private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);
                if(empty) {
                    setText(null);
                }
                else {
                    this.setText(format.format(item));

                }
            }
        };

        return cell;
    });
            //Set factorias a las celdas de las columnas de la tabla.
            colNombre.setCellValueFactory(new PropertyValueFactory<>("name"));
            colEspecie.setCellValueFactory(new PropertyValueFactory<>("species"));
            colFecha.setCellValueFactory(new PropertyValueFactory<>("arrivalDate"));

            //Añadir las opciones de busqueda  a la combo
            ObservableList<String> options = FXCollections.observableArrayList("Todos","Nombre","Especie");
            cmbBuscar.setItems(options);
           
            //Ejecutar método handleWindowShowing cuando se produzca el evento setOnShowing
            //Ejecutar método cambioTexto cuando el texto de el campo  cambie.
            txtFieldBuscar.textProperty().addListener(this::cambioTexto);
            txtFieldNombre.textProperty().addListener(this::cambioTexto);
            txtFieldEspecie.textProperty().addListener(this::cambioTexto);
            //Ejecutar método evento acción clickar botón
            btnBuscar.setOnAction(this::accionBotonBuscar);
            btnAnadir.setOnAction(this::accionBotonAnadir);
            btnModificar.setOnAction(this::accionBotonModificar);
            btnVolver.setOnAction(this::accionBotonVolver);
            btnLimpiar.setOnAction(this::accionBotonLimpiar);

            //Si se pulsa la x de la ventana para salir
            stage.setOnCloseRequest(this::manejarCierreVentana);
            
            creaturesData = FXCollections.observableArrayList(creatureManager.getCreatureBySector(sector.getIdSector().toString()));
            tbCreature.setItems(creaturesData);
            //Este evento se lanza cuando la ventana esta a punto de aparecer. Este evento no se lanza al volver de otra escena porque no abrimos una stage cambiamos de scene
            stage.setOnShowing(this::manejarInicioVentana);
            //Hace visible la pantalla
            stage.show();            
        }catch(BusinessLogicException ex){
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
        txtFieldEspecie.setPromptText("Introduce la especie.");
        //El boton está inhabilitado al arrancar la ventana.
        btnModificar.setDisable(true);
        btnAnadir.setDisable(true);
        //Al iniciar la ventana el foco en el textfield de arriba.
        txtFieldBuscar.requestFocus();
        //El combo de busqueda se inicia sin seleccion.
        cmbBuscar.getSelectionModel().select(-1);
        //Cargar la tabla con todos los datos
        //cargarTabla();
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
            Creature creature = (Creature) newValue;
            txtFieldNombre.setText(creature.getName());
            txtFieldEspecie.setText(creature.getSpecies());
            datePickerFechaLlegada.setValue(Instant.ofEpochMilli(creature.getArrivalDate().getTime()).
                    atZone(ZoneId.systemDefault()).toLocalDate());
            btnAnadir.setDisable(false);
            btnModificar.setDisable(false);
        }else{
        //No hay ningun elemento de la tabla seleccionado limpiar los textfields
            txtFieldNombre.setText("");
            txtFieldEspecie.setText("");
            btnAnadir.setDisable(true);
            btnModificar.setDisable(true);
        }
        //Focus textfield nombre
        txtFieldNombre.requestFocus();
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
        }else if(this.txtFieldEspecie.getText().trim().length()>=TEXTFIELD_MAX_LENGTH){
            txtFieldEspecie.setText(txtFieldEspecie.getText().trim().substring(0, (TEXTFIELD_MAX_LENGTH)));
        }else if(this.txtFieldBuscar.getText().trim().length()>=TEXTFIELD_MAX_LENGTH){
            txtFieldBuscar.setText(txtFieldBuscar.getText().trim().substring(0, (TEXTFIELD_MAX_LENGTH)));
        }
        //Si uno de los dos textfield está vacío botones deshabilitados Modificar y añadir.
        if(this.txtFieldNombre.getText().equals("")||
                this.txtFieldEspecie.getText().equals("")){
            //Deshabilitar botón
            btnModificar.setDisable(true);
            btnAnadir.setDisable(true);
        }else{//Los dos campos están informados.
            //Habilitar botón 
            btnAnadir.setDisable(false);
            btnModificar.setDisable(false);
            //Añadir tooltip al botón.
            btnAnadir.setTooltip(new Tooltip("Pulsa para registrar una criatura"));
            btnModificar.setTooltip(new Tooltip("Pulsa para modificar la criatura"));
            //Habilitar el uso del teclado para moverse por la ventana.
            btnAnadir.setMnemonicParsing(true);
        }
    }
    /**
     * Action event handler for search button.
     * @param event The ActionEvent object for the event.
     */
    private void accionBotonBuscar(ActionEvent event){
        LOGGER.info("Iniciando CreatureController::handleWindowShowing.Metodo_accionBotonBuscar");
        try{
            if(cmbBuscar.getSelectionModel().getSelectedItem()==null){
                mostrarVentanaAlertError("Elige una opcion de busqueda");
            }else if(cmbBuscar.getSelectionModel().getSelectedIndex()==0){
               creaturesData = FXCollections.observableArrayList(creatureManager.       
                       getCreatureBySector(sector.getIdSector().toString()));; 
                txtFieldBuscar.setText("");
            }else if(cmbBuscar.getSelectionModel().getSelectedIndex()==1){
               creaturesData = FXCollections.observableArrayList(creatureManager.getCreatureByName(txtFieldBuscar.getText().trim(),sector));
            }else{
               creaturesData = FXCollections.observableArrayList(creatureManager.getCreatureByEspecie(txtFieldBuscar.getText().trim(),sector));
            }
            if(creaturesData.isEmpty())
                tbCreature.getItems().removeAll(creaturesData);
            else
                tbCreature.setItems(creaturesData);            
        }catch(BusinessLogicException e){
            Logger.getLogger(CreatureManagementController.class.getName()).log(Level.SEVERE, null, e);
            tbCreature.getItems().removeAll(creaturesData);
        } 
    }
    /**
     * Action event handler for search button.
     * @param event The ActionEvent object for the event.
     */
    private void accionBotonLimpiar(ActionEvent event){
        LOGGER.info("Iniciando CreatureController::handleWindowShowing.Metodo_accionBotonBuscar");
        txtFieldNombre.setText("");
        txtFieldEspecie.setText("");
        datePickerFechaLlegada.setValue(null);
    }
    /**
     * Action event handler for add button. Adds a creature in the table.
     * @param event The ActionEvent object for the event.
     */
    private void accionBotonAnadir(ActionEvent event){
        LOGGER.info("Iniciando CreatureController::accionBotonAnadir");
        Alert alert;
        Creature creature = null;
        try{
            if (validarCampoNombre(txtFieldNombre) && validarCampoNombre(txtFieldEspecie)) {
                //Crear nueva criatura y añadir los datos de los textfiel y datepicker 
                creature = new Creature();
                creature.setSector(sector);
                creature.setName(txtFieldNombre.getText().trim());
                creature.setSpecies(txtFieldEspecie.getText().trim());
                if (datePickerFechaLlegada.getValue() != null) {
                    //De localdate a date. Datepicker devuelve un localdate
                    creature.setArrivalDate(Date.from(datePickerFechaLlegada.getValue()
                            .atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    alert = new Alert(Alert.AlertType.CONFIRMATION, "Confirmar registro?.");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        //Comprobar que no existe el nombre
                        creatureManager.creatureNameIsRegistered(creature.getName());
                        //llamar al metodo de creatureimplementation
                        creatureManager.createCreature(creature);
                        //Añadir en la tabla el nuevo registro
                        tbCreature.getItems().add(creature);
                        //creo que se puede hacer asi directamente y la tabla reconoce el cambio en la lista observable
                        //creaturesData.add(creature);
                        //Vaciar textfields y datepicker
                        txtFieldNombre.setText("");
                        txtFieldEspecie.setText("");
                        datePickerFechaLlegada.getEditor().clear();
                    }              
                }else
                    mostrarVentanaAlertError("Tienes que elegir una fecha");
            }else{
               mostrarVentanaAlertError("Los campos nombre y especie deben contener solo letras."); 
            }
        }catch(BusinessLogicException e){
            Logger.getLogger(CreatureManagementController.class.getName()).log(Level.SEVERE, null, e); 
            mostrarVentanaAlertError("Error al añadir criatura");
        } catch (CreaturaExistException ex) {
            Logger.getLogger(CreatureManagementController.class.getName()).log(Level.SEVERE, null, ex);
            mostrarVentanaAlertError("La criatura "+creature.getName()+" ya existe. Elije otro nombre");
            txtFieldNombre.requestFocus();
        }catch(Exception e){
            Logger.getLogger(CreatureManagementController.class.getName()).log(Level.SEVERE, null, e); 
            mostrarVentanaAlertError("Error al añadir criatura");
        }
    }
    /**
     * Action event handler for print button. Modifies an existing creature from the table.
     * @param event The ActionEvent object for the event.
     */
    private void accionBotonModificar(ActionEvent event){
        LOGGER.info("Iniciando CreatureController::accionBotonModificar");
        Alert alert;
        Creature creature = null;
        try{
            //Se guarda en criatura la seleccionada en la tabla
            creature = ((Creature)tbCreature.getSelectionModel().getSelectedItem());
            if(!creature.getName().equals(txtFieldNombre.getText().trim())){
                //Mirar si el nombre existe. Si se ha cambiado
                creature.setName(txtFieldNombre.getText().trim());
                creatureManager.creatureNameIsRegistered(txtFieldNombre.getText().trim());
            }
            creature.setName(txtFieldNombre.getText().trim());
            creature.setSpecies(txtFieldEspecie.getText().trim());
            if (validarCampoNombre(txtFieldNombre) && validarCampoNombre(txtFieldEspecie)) {
                if (datePickerFechaLlegada.getValue() != null) {
                    //De localdate a date. Datepicker devuelve un localdate
                    creature.setArrivalDate(Date.from(datePickerFechaLlegada.getValue()
                            .atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    alert = new Alert(Alert.AlertType.CONFIRMATION, "Confirmar modificacion?.");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        creatureManager.updateCreature(creature);
                        //Limpiar los datos de la tabla
                        tbCreature.getSelectionModel().clearSelection();
                        //Refrescamos la tabla para que muestre los nuevos datos
                        tbCreature.refresh();
                        //Vaciar los textfield y datepicker
                        txtFieldNombre.setText("");
                        txtFieldEspecie.setText("");
                        datePickerFechaLlegada.getEditor().clear();
                    }
                } else {
                    mostrarVentanaAlertError("Tienes que elegir una fecha");
                }               
            }else
                mostrarVentanaAlertError("Los campos nombre y especie deben contener solo letras.");
        }catch(BusinessLogicException e){
            Logger.getLogger(CreatureManagementController.class.getName()).log(Level.SEVERE, null, e); 
            mostrarVentanaAlertError("Error al modificar criaturas");
        } catch (CreaturaExistException ex) {
            Logger.getLogger(CreatureManagementController.class.getName()).log(Level.SEVERE, null, ex);
            mostrarVentanaAlertError("La criatura "+creature.getName()+" ya existe. Elije otro nombre");
            txtFieldNombre.requestFocus();
        }catch (Exception e){
            Logger.getLogger(CreatureManagementController.class.getName()).log(Level.SEVERE, null, e); 
            mostrarVentanaAlertError("Error al modificar criaturas");            
        }
    }
    /**
     * Action event handler for return button. Returns to the previews scene.
     * @param event The ActionEvent object for the event.
     */
    private void accionBotonVolver(ActionEvent event){
        LOGGER.info("Iniciando CreatureController::accionBotonVolver");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Estás seguro que quieres salir?");
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {       
            //Abrir ventana sector
        try{
            //New FXMLLoader Añadir el fxml de sector que es la ventana a la que se redirige si todo va bien
            FXMLLoader loader = new FXMLLoader(getClass().
                getResource("/view/FXMLSectorManagement.fxml"));
            //Parent es una clase gráfica de nodos xml son nodos.
            Parent root = (Parent)loader.load();
            //Relacionamos el documento FXML con el controlador que le va a controlar.
            SectorManagementController controladorSector = (SectorManagementController)loader.getController();
            //Pasar la ventana al controlador de la ventana signIn.
            controladorSector.setStage(stage);
            controladorSector.setUser(user);
            //Llamada al método initStage del controlador de la ventana signIn. Pasa el documento fxml en un nodo.
            controladorSector.initStage(root);
            
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
        /**
     * Comprueba que el contenido del campo de texto Nombre cumple los requisitos
     * @return Un Booleano. True si el campo cumple los requisitos, false si no lo hace. 
     */
    private Boolean validarCampoNombre(TextField field) {
        //Vamos a ver primero si el texto son solo letras y numeros.
        Boolean ok = true;
        String textoConCaracteres = field.getText().trim();
        //ForEach de character. Recorremos letra a letra
        for(Character t:textoConCaracteres.toCharArray()){
            LOGGER.info("Recorrer el texto para buscar espacios. ControllerSignIn.ComprobaEspaciosBlancos");
            //Si el caracter no es ni número ni letra retorna false;
            if(!Character.isLetter(t)&& t !=' '){
                System.out.println(t);
                ok = false;
                break;
            }
        }//El texto está compuesto por letras y números
        return ok;
    }

}
