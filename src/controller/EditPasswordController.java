/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import businessLogic.UserFactory;
import businessLogic.UserInterface;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import securityClient.ClavePublicaCliente;

/**
 * FXML Controller class for user password change scene. 
 * @author Endika
 */
public class EditPasswordController {
    /**
     * Logger object used to control activity from class CreatureManagementController.
     */
    private static final Logger LOGGER = Logger.getLogger("emex51.cliente.controlador.FXMLDocumentPasswordChangeController");
    /**
     * The Window showing creature components.
     */
    private Stage stage;
    /**
     * Contains the email of the user
     */
    private User user;
    /**
     * User manager instance
     */
    private UserInterface userManager;
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
    private TextField txtFieldOldPass;
    /**
     * TextField for creature search.
     */
    @FXML 
    private TextField txtFieldNewPass;
    /**
     * TextField for creature search.
     */
    @FXML 
    private TextField txtFieldConfirmNewPass;
    @FXML
    private Label lblError;
    /**
     * Button confirming creature search.
     */
    @FXML 
    private Button btnEnviar; 
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
     * Gets the user with the email.
     * @return 
     */
    public User getUser() {
        return user;
    }
    /**
     * Sets the user with the email.
     * @param user 
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Método que añade la escena en la ventana e inicializa los distintos
     * componenetes de la escena.
     *
     * @param root Un nodo FXML en formato gráfico.
     */
    public void initStage(Parent root) {
        LOGGER.log(Level.INFO, "Inicializando la ventana EditPassword. ");
        //Creación de  una nueva escena
        Scene scene = new Scene(root);
        //Añadir escena a la ventana
        stage.setScene(scene);
        //Asignación de un título a la ventana
        stage.setTitle("Edit Password");
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
        txtFieldOldPass.textProperty().addListener(this::cambioTexto);
        txtFieldNewPass.textProperty().addListener(this::cambioTexto);
        txtFieldConfirmNewPass.textProperty().addListener(this::cambioTexto);
        //Ejecutar método evento acción clickar botón
        btnEnviar.setOnAction(this::accionBotonEnviar);
        
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
        LOGGER.info("Iniciando ControllerEditPassword::handleWindowShowing.Metodo_ManejarInicioVentana");
        //El boton está inhabilitado al arrancar la ventana.
        btnEnviar.setDisable(true);
    }
    /**
     * This method controls the event window closing when the window [x] is clicked.
     * @param event Window closing
     */
    private void manejarCierreVentana(WindowEvent event){
        LOGGER.info("Iniciando EditPasswordController::manejarCierreVentana");
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
        LOGGER.info("Iniciando ControllerEditPassword::cambioTexto");
        //Vaciar label de error tras escribir en cualquiera de los campos.
        lblError.setText("");
        //Si llevo 20 caracteres metidos ya no dejar meter mas.
        if(this.txtFieldOldPass.getText().trim().length()>=TEXTFIELD_MAX_LENGTH){
            txtFieldOldPass.setText(txtFieldOldPass.getText().substring(0, (TEXTFIELD_MAX_LENGTH)));
        }else if(this.txtFieldNewPass.getText().trim().length()>=TEXTFIELD_MAX_LENGTH){
            txtFieldNewPass.setText(txtFieldNewPass.getText().trim().substring(0, (TEXTFIELD_MAX_LENGTH)));
        }else if(this.txtFieldConfirmNewPass.getText().trim().length()>=TEXTFIELD_MAX_LENGTH){
            txtFieldConfirmNewPass.setText(txtFieldConfirmNewPass.getText().trim().substring(0, (TEXTFIELD_MAX_LENGTH)));
        }
        //Condición.Si tiene menos de 4 caracteres cualquiera de los dos campos. Trim Quita espacios delante y detrás del texto del campo.
        if(txtFieldOldPass.getText().trim().length()<TEXTFIELD_MIN_LENGTH ||
                txtFieldNewPass.getText().trim().length()<TEXTFIELD_MIN_LENGTH||
                txtFieldConfirmNewPass.getText().trim().length()<TEXTFIELD_MIN_LENGTH){
            //Deshabilitar botón
            btnEnviar.setDisable(true);  
        }else{//Los dos campos están informados.
            //Habilitar botón 
            btnEnviar.setDisable(false);
            //Añadir tooltip al botón.
            btnEnviar.setTooltip(new Tooltip("Pulsa para acceder"));
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
        LOGGER.info("Iniciando ControllerEditPassword.accionBotonEnviar");
        try{
            //Si cumplen las condiciones enviar datos.
            if(comprobarEspaciosBlancos(txtFieldOldPass)&&comprobarEspaciosBlancos(txtFieldNewPass)
                    &&comprobarEspaciosBlancos(txtFieldConfirmNewPass)&&
                    txtFieldNewPass.getText().trim().equalsIgnoreCase(txtFieldConfirmNewPass.getText().trim())){
                LOGGER.info("Todo bien enviamos password para ser editada");
                userManager = UserFactory.getUserImp();
                userManager.editChangePassword
                    (user,ClavePublicaCliente.cifrarTexto(txtFieldOldPass.getText().trim()),
                            ClavePublicaCliente.cifrarTexto(txtFieldNewPass.getText().trim()));
                //Mostrar mensaje de confirmacion de cambio de contraseña
                Alert alert = new Alert(Alert.AlertType.INFORMATION,"Contraseña actualizadada. Te llegará un correo de confirmación.");
                alert.setHeaderText(null);
                alert.showAndWait();
                //Abrir ventana signin
                FXMLLoader loader = new FXMLLoader(getClass().
                        getResource("/view/FXMLSignIn.fxml"));
                //Parent es una clase gráfica de nodos xml son nodos.
                Parent root = (Parent)loader.load();
                //Relacionamos el documento FXML con el controlador que le va a controlar.
                SignInController controladorSignIn = (SignInController)loader.getController();
                //Llamada al método setStage del controlador de la ventana signIn. Pasa la ventana.
                controladorSignIn.setStage(stage);
                //Llamada al método initStage del controlador de la ventana signIn. Pasa el documento fxml en un nodo.
                controladorSignIn.initStage(root);
                //Llamada al método inicializarComponenentesVentana del controlador de la ventana signIn.                

            } else{
                //El foco lo pone en el campo usuario
                txtFieldNewPass.requestFocus();
                //Selecciona el texto para borrar.
                txtFieldNewPass.selectAll();
                txtFieldConfirmNewPass.setText("");
                //Mostrar un mensaje de error en el label.
                lblError.setText("Password incorrectas.");
                //Cambia de color el texto del label, en este caso a rojo
                lblError.setTextFill(Color.web("#ff0000"));
                //El campo contraseña tiene una longitud que no está entre 4 y 20 caracteres y no tiene espacios.
            }            
        }catch(Exception e){
            lblError.setText("No se ha podido actualizar la contraseña");
        }
    }
    /**
     * Comprueba que el texto de un campo no tenga espacios intermedios.
     * @param field Un campo de texto.
     * @return Un boolean true si hay espacios en blanco en el texto, false si por el contrario no los hay.
     */
    public static Boolean comprobarEspaciosBlancos(TextField field) {
        //Guardamos valos textField en string sin espacios delante ni detras.
        String textoSinEspacios = field.getText().trim();
        //VAriable de retorno. Inicializar a false
        Boolean textoCorrecto = true;
        //ForEach de character. Recorremos letra a letra
        for(Character t:textoSinEspacios.toCharArray()){
            //Condición de igualdad. Propiedad equals de Character. Si el caracter actual igual a espacio.
            if(t.equals(' '))
                textoCorrecto = false;
        }
        return textoCorrecto;
    }
}
