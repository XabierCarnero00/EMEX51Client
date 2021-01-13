
package controller;

import java.io.IOException;
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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.User;

/**
 * FXML Controller class para la escena SignIn
 * @version 1
 * @since 23/10/2020
 * @author Eneko, Endika, Markel
 */
public class FXMLDocumentSignInController {
    /**
     * Logger para trazar los pasos del código.
     */
    private static final Logger LOGGER = Logger.getLogger("grupog5.signinsignupapplication.cliente.controlador.FXMLDocumentControllerSignIn");
    /**
     * Una ventana sobre la que se coloca una escena.
     */
    private Stage stage;
    /**
     * Longitud máxima de los campos de texto permitida.
     */
    private static final Integer TEXTFIELD_MAX_LENGTH = 20;
    /**
     * Longitud minima de los campos de texto permitida.
     */
    private static final Integer TEXTFIELD_MIN_LENGTH = 4;
    
    /**
     * Campo de texto Usuario
     */
    @FXML private TextField txtFieldUsuario;
    /**
     * Campo de texto contraseña 
     */
    @FXML private PasswordField pswFieldContrasena;
    /**
     * Botón Acceso a la aplicación
     */
    @FXML private Button btnEntrar;
    /**
     * Hyperlink de registro. Enlace a la escena SignUp.
     */
    @FXML private Hyperlink hplRegistrate;
    
    /**
     * Label Mensajes Error usuario,contraseña.
     */ 
    @FXML private Label lblErrorUsuarioContrasena;
    
    /**
     * LAbel Mensajes Error genéricos o ajenos a la aplicación.
     */
    @FXML private Label lblErrorExcepcion;
    


    /**
     * Asigna al atributo Stage de la clase una Stage recibida como parámetro.
     * @param stage Una ventana JavaFx.
     */
    public void setStage(Stage stage) {
        this.stage = stage;    
    }

    /**
     * Retorna una Stage (ventana).
     * @return Una Stage (ventana)
     */
    public Stage getStage(){
        return stage;
    }

    /**
     * Método que añade la escena en la ventana e inicializa los distintos componenetes de la escena.
     * @param root Un nodo FXML en formato gráfico.
     */
    public void initStage(Parent root) {
        LOGGER.log(Level.INFO,"Inicializando la ventana SignIn. ");
        //Creación de  una nueva escena
        Scene scene = new Scene(root);
        //Añadir escena a la ventana
        stage.setScene(scene);
        //Asignación de un título a la ventana
        stage.setTitle("Sign In");
        //Asignar tamaño fijo a la ventana
        stage.setResizable(false); 
        
        //Asignar un texto de fondo en el campo contraseña, se muestra cuando el campo está desenfocado.
        pswFieldContrasena.setPromptText("Introduce tu contraseña. ("+TEXTFIELD_MIN_LENGTH+ " a "+TEXTFIELD_MAX_LENGTH+" caracteres)");
        //Asignar texto cuando el campo está desenfocado.
        txtFieldUsuario.setPromptText("Introduce tu nombre de usuario. ("+TEXTFIELD_MIN_LENGTH+ " a "+TEXTFIELD_MAX_LENGTH+" caracteres)");
        //El boton está inhabilitado al arrancar la ventana.
        btnEntrar.setDisable(true);

        //Ejecutar método handleWindowShowing cuando se produzca el evento setOnShowing
        //Este evento se lanza cuando la ventana esta a punto de aparecer. Este evento no se lanza al volver de otra escena porque no abrimos una stage cambiamos de scene
        stage.setOnShowing(this::manejarInicioVentana);
        //Ejecutar método cambioTexto cuando el texto de el campo txtFieldUsuario cambie.
        txtFieldUsuario.textProperty().addListener(this::cambioTexto);
        //Ejecutar método cambioTexto cuando el texto de el campo pswFieldContraseña cambie.
        pswFieldContrasena.textProperty().addListener(this::cambioTexto);
        //Ejecutar método evento acción clickar botón
        btnEntrar.setOnAction(this::accionBoton);
        //Ejecutar método Hyperlink clickado
        hplRegistrate.setOnAction(this::hyperlinkClickado);
        //Si se pulsa la x de la ventana para salir
        stage.setOnCloseRequest(this::manejarCierreVentana);
        //Hace visible la pantalla
        stage.show();
    }

    /**
     * Acciones que se realizan en el momento previo a que se muestre la ventana.
     * @param event Evento de ventana.
     */
    private void manejarInicioVentana(WindowEvent event){
        LOGGER.info("Iniciando ControllerSignIn::handleWindowShowing.Metodo_ManejarInicioVentana");
            //Asignar un texto de fondo en el campo contraseña, se muestra cuando el campo está desenfocado.
        pswFieldContrasena.setPromptText("Introduce tu contraseña. ("+TEXTFIELD_MIN_LENGTH+ " a "+TEXTFIELD_MAX_LENGTH+" caracteres)");
        //Asignar texto cuando el campo está desenfocado.
        txtFieldUsuario.setPromptText("Introduce tu nombre de usuario. ("+TEXTFIELD_MIN_LENGTH+ " a "+TEXTFIELD_MAX_LENGTH+" caracteres)");
        //El boton está inhabilitado al arrancar la ventana.
        btnEntrar.setDisable(true); 
    }
    /**
     * Controla el evento de orden de cerrar la ventana al pulsar el usuario la x de salir.
     * @param event El evento de cierre de ventana
     */
    private void manejarCierreVentana(WindowEvent event){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Estás seguro que quieres salir?.",ButtonType.OK,ButtonType.CANCEL);
            alert.setHeaderText(null);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                stage.close();
            }else{
                event.consume();
            }
    }
    
     /**
     * Evento del Hyperlink clickado. Redirige a la escena SignUp.
     * @param event Un evento del Hyperlink.
     */
    public void hyperlinkClickado(ActionEvent event){
        LOGGER.log(Level.INFO,"Evento del Hyperlink clickado. ");
        try{
            //Llamada a método abrirVentanaSignUp
          // abrirVentanaSignUp(); 
        }catch(Exception e){
            //Escribir en el label.
            lblErrorExcepcion.setText("Intentalo mas tarde. Fallo la conexión");
        }       
    }
    
    /**
     * Registra los cambios de texto en los textField o passwordField.
     * @param observable 
     * @param oldValue Valor anterior al lanzamiento del evento.
     * @param newValue Nuevo valor tras el cambio.
     */
    private void cambioTexto(ObservableValue observable,String oldValue,String newValue){
        LOGGER.info("Iniciando ControllerSignIn::cambioTexto");
        //Vaciar label de error tras escribir en cualquiera de los campos.
        lblErrorUsuarioContrasena.setText("");
        //Vaciar label de error tras escribir en cualquiera de los campos.
        lblErrorExcepcion.setText("");
        //Si llevo 20 caracteres metidos ya no dejar meter mas.
        if(this.txtFieldUsuario.getText().trim().length()>=TEXTFIELD_MAX_LENGTH){
            txtFieldUsuario.setText(txtFieldUsuario.getText().substring(0, (TEXTFIELD_MAX_LENGTH)));
        }else if(this.pswFieldContrasena.getText().trim().length()>=TEXTFIELD_MAX_LENGTH){
            pswFieldContrasena.setText(pswFieldContrasena.getText().trim().substring(0, (TEXTFIELD_MAX_LENGTH)));
        }
        //Condición.Si tiene menos de 4 caracteres cualquiera de los dos campos. Trim Quita espacios delante y detrás del texto del campo.
        if(this.txtFieldUsuario.getText().trim().length()<TEXTFIELD_MIN_LENGTH||
                this.pswFieldContrasena.getText().trim().length()<TEXTFIELD_MIN_LENGTH){
            //Deshabilitar botón
            btnEntrar.setDisable(true);  
        }else{//Los dos campos están informados.
            //Habilitar botón 
            btnEntrar.setDisable(false);
            //Añadir tooltip al botón.
            btnEntrar.setTooltip(new Tooltip("Pulsa para acceder"));
            //Habilitar el uso del teclado para moverse por la ventana.
            btnEntrar.setMnemonicParsing(true);
        }
    }
    /**
     * Tratar el click del botón
     * @param event Un evento del botón
     */
    private void accionBoton(ActionEvent event){
 /*       LOGGER.info("Iniciando ControllerSignIn.accionBoton");
        //Si cumplen las condiciones enviar datos.
        //Almacenar en el objeto de la clase User los datos recogidos de los campos de la ventana.
        User myUser = new User (txtFieldUsuario.getText().trim(),pswFieldContrasena.getText().trim());
        try {
            //Ejecutar método signIn de la clase SignImplemetation a traves de la instancia que tiene la propia clase como atributo.
            signable.signIn(myUser);
            //Llamada al método abrirVentanaLogOut para redireccionar aplicación a la siguiente escena.
            abrirVentanaLogOut(myUser);
            //Si se produce un error mirar los distintos throws del método signIn de la clase SignImplementation.
        } catch (ExcepcionUserNoExiste ex1) {
            //Vaciar campos de texto
            txtFieldUsuario.setText("");
            pswFieldContrasena.setText("");
            //Colocar el texto de la excepción en el label
            lblErrorUsuarioContrasena.setText(ex1.getMessage());
            //Cambia de color el texto del label, en este caso a rojo
            lblErrorUsuarioContrasena.setTextFill(Color.web("#ff0000"));
        } catch (ExcepcionPasswdIncorrecta ex2) {
            //Vaciar campos de texto
            pswFieldContrasena.setText("");
            pswFieldContrasena.requestFocus();
            //Colocar el texto de la excepción en el label
            lblErrorUsuarioContrasena.setText(ex2.getMessage());
            //Cambia de color el texto del label, en este caso a rojo
            lblErrorUsuarioContrasena.setTextFill(Color.web("#ff0000"));
        } catch (Exception ex3){
            //Vaciar campos de texto
            txtFieldUsuario.setText("");
            pswFieldContrasena.setText("");
            //Colocar el texto de la excepción en el label
            lblErrorExcepcion.setText("Se ha producido un error. Lo sentimos. Inténtalo mas tarde");
            //Cambia de color el texto del label, en este caso a rojo
            lblErrorExcepcion.setTextFill(Color.web("#ff0000"));
        }    
 */   }


}
