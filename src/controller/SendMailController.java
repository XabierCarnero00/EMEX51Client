/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import businessLogic.BusinessLogicException;
import businessLogic.UserFactory;
import businessLogic.UserInterface;
import exceptions.ExcepcionEmailNoExiste;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.User;

/**
 * FXML Controller class for sending mail scene. This scene is visible when an user forgot the password
 * and asks for recover. 
 * @author Endika
 */
public class SendMailController {
    /**
     * Logger object used to control activity from class CreatureManagementController.
     */
    private static final Logger LOGGER = Logger.getLogger("emex51.cliente.controlador.FXMLDocumentSendMailController");
    /**
     * The Window showing creature components.
     */
    private Stage stage;
    /**
     * User manager instance
     */
    private UserInterface userManager;
    /**
     * Max length allow in current stage textFields.
     */
    private static final Integer TEXTFIELD_MAX_LENGTH = 40; 
    /**
     * TextField for creature search.
     */
    @FXML 
    private TextField txtFieldEmail;
    /**
     * Button confirming creature search.
     */
    @FXML 
    private Button btnEnviar;
    /**
     * Button confirming creature search.
     */
    @FXML 
    private Button btnVolver;
    /**
     * Label shown when an error is produced. Can be the email is not found or a general error.
     */
    @FXML
    private Label lblError;
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
     * Método que añade la escena en la ventana e inicializa los distintos
     * componenetes de la escena.
     *
     * @param root Un nodo FXML en formato gráfico.
     */
    public void initStage(Parent root) {
        LOGGER.log(Level.INFO, "Inicializando la ventana SendEmail. ");
        //Creación de  una nueva escena
        Scene scene = new Scene(root);
        //Añadir escena a la ventana
        stage.setScene(scene);
        //Asignación de un título a la ventana
        stage.setTitle("Send Email");
        //Asignar tamaño fijo a la ventana
        stage.setResizable(false);
        
        //Vaciar label de error tras escribir en cualquiera de los campos.
        lblError.setText("");

        //Asignar texto cuando el campo está desenfocado.
        //txtFieldEmail.setPromptText("Introduce tu correo electrónico.");
        //El boton está inhabilitado al arrancar la ventana.
        btnEnviar.setDisable(true);

        //Ejecutar método handleWindowShowing cuando se produzca el evento setOnShowing
        //Este evento se lanza cuando la ventana esta a punto de aparecer. Este evento no se lanza al volver de otra escena porque no abrimos una stage cambiamos de scene
        stage.setOnShowing(this::manejarInicioVentana);
        //Ejecutar método cambioTexto cuando el texto de el campo txtFieldUsuario cambie.
        txtFieldEmail.textProperty().addListener(this::cambioTexto);
        //Ejecutar método evento acción clickar botón
        btnEnviar.setOnAction(this::accionBotonEnviar);
        btnVolver.setOnAction(this::accionBotonVolver);
        
                    //Si se pulsa la x de la ventana para salir
        stage.setOnCloseRequest(this::manejarCierreVentana);
        
        //Hace visible la pantalla
        stage.show();
    }
    /**
     * Acciones que se realizan en el momento previo a que se muestre la
     * ventana.
     *
     * @param event Evento de ventana.
     */
    private void manejarInicioVentana(WindowEvent event) {
        LOGGER.info("Iniciando ControllerSendMail::handleWindowShowing.Metodo_ManejarInicioVentana");
        //El boton está inhabilitado al arrancar la ventana.
        btnEnviar.setDisable(true);
    }
    /**
     * This method controls the event window closing when the window [x] is clicked.
     * @param event Window closing
     */
    private void manejarCierreVentana(WindowEvent event){
        LOGGER.info("Iniciando SendMailController::manejarCierreVentana");
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
     * Registra los cambios de texto en los textField o passwordField.
     *
     * @param observable
     * @param oldValue Valor anterior al lanzamiento del evento.
     * @param newValue Nuevo valor tras el cambio.
     */
    private void cambioTexto(ObservableValue observable, String oldValue, String newValue) {
        LOGGER.info("Iniciando ControllerSignIn::cambioTexto");

        //Vaciar label de error tras escribir en cualquiera de los campos.
        lblError.setText("");
        if(this.txtFieldEmail.getText().trim().length()>=TEXTFIELD_MAX_LENGTH)
            txtFieldEmail.setText(txtFieldEmail.getText().substring(0, (TEXTFIELD_MAX_LENGTH)));
        //Condición.Si está vacío cualquiera de los dos campos. Trim Quita espacios delante y detrás del texto del campo.
        if (this.txtFieldEmail.getText().trim().equals("")) {
            //Deshabilitar botón
            btnEnviar.setDisable(true);
        } else {//Los dos campos están informados.
            //Habilitar botón 
            btnEnviar.setDisable(false);
            //Añadir tooltip al botón.
            btnEnviar.setTooltip(new Tooltip("Pulsa para enviar"));
            //Habilitar el uso del teclado para moverse por la ventana.
            btnEnviar.setMnemonicParsing(true);
        }
    }
    /**
     * Tratar el click del botón
     *
     * @param event Un evento del botón
     */
    private void accionBotonEnviar(ActionEvent event) {
        LOGGER.info("Iniciando ControllerSignIn.accionBotonEnviar");
        lblError.setText("");
        try{
            //Si cumplen las condiciones enviar datos.
            //El campo usuario tiene una longitud que no está entre 4 y 20 caracteres y no tiene espacios.
            if (!emailCorrecto(txtFieldEmail.getText().trim()) ){
                //El foco lo pone en el campo usuario
                txtFieldEmail.requestFocus();
                //Selecciona el texto para borrar.
                txtFieldEmail.selectAll();
                //Mostrar un mensaje de error en el label.
                lblError.setText("Formato Email incorrecto.");
                //Cambia de color el texto del label, en este caso a rojo
                lblError.setTextFill(Color.web("#ff0000"));
                //El campo contraseña tiene una longitud que no está entre 4 y 20 caracteres y no tiene espacios.
            } else{
                User user = new User();
                user.setEmail(txtFieldEmail.getText().trim());
                LOGGER.info("Enviamos el email");
                userManager = UserFactory.getUserImp();
                userManager.sendPassword(user);
                //Mostrar mensaje de confirmacion de cambio de contraseña
                Alert alert = new Alert(Alert.AlertType.INFORMATION,"Recibiras tu nueva contraseña en unos segundos.");
                alert.setHeaderText(null);
                alert.showAndWait();
            //ABRIR  VENTANA PASSWORDS
                //New FXMLLoader Añadir el fxml de logout que es la ventana a la que se redirige si todo va bien
                FXMLLoader loader = new FXMLLoader(getClass().
                    getResource("/view/FXMLEditPassword.fxml"));
                //Parent es una clase gráfica de nodos xml son nodos.
                Parent root = (Parent)loader.load();
                //Relacionamos el documento FXML con el controlador que le va a controlar.
                EditPasswordController controladorPasswords = (EditPasswordController)loader.getController();
                //Llamada al método setSignable del controlador de la escena signIn. Pasa instancia SignImplementation.
                controladorPasswords.setStage(stage);
                //Pasa el usuario para saber el email que le a llevado allí.
                controladorPasswords.setUser(user);
                //Llamada al método initStage del controlador de la ventana signIn. Pasa el documento fxml en un nodo.
                controladorPasswords.initStage(root);
                
            }            
        }catch(BusinessLogicException e){
            lblError.setText(e.getMessage());
        }catch(ExcepcionEmailNoExiste e){
            Alert alert = new Alert(Alert.AlertType.ERROR,e.getMessage(), ButtonType.CLOSE);
            alert.setHeaderText(null);
            alert.showAndWait();
        }catch(Exception e){
            lblError.setText(e.getMessage());
        }
    }
    /**
     * Tratar el click del botón
     *
     * @param event Un evento del botón
     */
    private void accionBotonVolver(ActionEvent event) {
        LOGGER.info("Iniciando ControllerSignIn.accionBotonVolver");
        try{
            //New FXMLLoader Añadir el fxml de logout que es la ventana a la que se redirige si todo va bien
            FXMLLoader loader = new FXMLLoader(getClass().
                getResource("/view/FXMLSignIn.fxml"));
            //Parent es una clase gráfica de nodos xml son nodos.
            Parent root = (Parent)loader.load();
            //Relacionamos el documento FXML con el controlador que le va a controlar.
            SignInController controladorSignIn = (SignInController)loader.getController();
            //Llamada al método setSignable del controlador de la escena signIn. Pasa instancia SignImplementation.
            controladorSignIn.setStage(stage);
            //Llamada al método initStage del controlador de la ventana signIn. Pasa el documento fxml en un nodo.
            controladorSignIn.initStage(root);
        //Error al cargar la nueva escenamostrar mensaje.
        }catch(IOException e){
            lblError.setText("Se ha producido un error. Lo sentimos. Inténtalo mas tarde");
            //Cambia de color el texto del label, en este caso a rojo
            lblError.setTextFill(Color.web("#ff0000"));
        }
    }
 /**
     * Comprueba que el String recibido como parámetro cumple las condiciones de un email.
     * @param email El email del campo contraseña.
     * @return Un booleano.  True si la contraseña es correcta.
     */
    public static boolean emailCorrecto(String email) {
        //un patrón de email válido.
        String regex =  "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        //Comprueba que el pattern del patron coincide con el email introducido.
        Matcher matcher = pattern.matcher(email);
        //Devuelve un booleano el metodo matches de la clase matcher
        return  matcher.matches();
    }
}
