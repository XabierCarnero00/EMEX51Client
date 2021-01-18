package controller;

import bussinesLogic.BusinessLogicException;
import bussinesLogic.UserFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.User;
import securityClient.ClavePublicaCliente;
import bussinesLogic.UserManager;
import java.io.IOException;
import java.util.Optional;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * FXML Controller class para la escena SignIn
 *
 * @version 1
 * @since 23/10/2020
 * @author Eneko, Endika, Markel
 */
public class SignInController {

    /**
     * Logger para trazar los pasos del código.
     */
    private static final Logger LOGGER = Logger.getLogger("grupog5.signinsignupapplication.cliente.controlador.FXMLDocumentControllerSignIn");
    /**
     * Una ventana sobre la que se coloca una escena.
     */
    private Stage stage;
    /**
     * The interface 
     */
    UserManager userImp = UserFactory.getUserImp();
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
    @FXML
    private TextField txtFieldUsuario;
    /**
     * Campo de texto contraseña
     */
    @FXML
    private PasswordField pswFieldContrasena;
    /**
     * Botón Acceso a la aplicación
     */
    @FXML
    private Button btnEntrar;
    /**
     * Hyperlink de registro. Enlace a la escena SignUp.
     */
    @FXML
    private Hyperlink hplRegistrate;
    /**
     * Hyperlink de recuperar contraseña. Enlace a la escena SendMail.
     */
    @FXML
    private Hyperlink hplContrasenia;
    /**
     * Label Mensajes Error usuario,contraseña.
     */
    @FXML
    private Label lblErrorUsuarioContrasena;

    /**
     * LAbel Mensajes Error genéricos o ajenos a la aplicación.
     */
    @FXML
    private Label lblErrorExcepcion;

    /**
     * Asigna al atributo Stage de la clase una Stage recibida como parámetro.
     *
     * @param stage Una ventana JavaFx.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Retorna una Stage (ventana).
     *
     * @return Una Stage (ventana)
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Método que añade la escena en la ventana e inicializa los distintos
     * componenetes de la escena.
     *
     * @param root Un nodo FXML en formato gráfico.
     */
    public void initStage(Parent root) {
        LOGGER.log(Level.INFO, "Inicializando la ventana SignIn. ");
        //Creación de  una nueva escena
        Scene scene = new Scene(root);
        //Añadir escena a la ventana
        stage.setScene(scene);
        //Asignación de un título a la ventana
        stage.setTitle("Sign In");
        //Asignar tamaño fijo a la ventana
        stage.setResizable(false);

        //Asignar un texto de fondo en el campo contraseña, se muestra cuando el campo está desenfocado.
        pswFieldContrasena.setPromptText("Introduce tu contraseña. (" + TEXTFIELD_MIN_LENGTH + " a " + TEXTFIELD_MAX_LENGTH + " caracteres)");
        //Asignar texto cuando el campo está desenfocado.
        txtFieldUsuario.setPromptText("Introduce tu nombre de usuario. (" + TEXTFIELD_MIN_LENGTH + " a " + TEXTFIELD_MAX_LENGTH + " caracteres)");
        //El boton está inhabilitado al arrancar la ventana.
        btnEntrar.setDisable(true);

        //Ejecutar método handleWindowShowing cuando se produzca el evento setOnShowing
        //Este evento se lanza cuando la ventana esta a punto de aparecer. Este evento no se lanza al volver de otra escena porque no abrimos una stage cambiamos de scene
        stage.setOnShowing(this::manejarInicioVentana);
        //Si se pulsa la x de la ventana para salir
        stage.setOnCloseRequest(this::manejarCierreVentana);
        //Ejecutar método cambioTexto cuando el texto de el campo txtFieldUsuario cambie.
        txtFieldUsuario.textProperty().addListener(this::cambioTexto);
        //Ejecutar método cambioTexto cuando el texto de el campo pswFieldContraseña cambie.
        pswFieldContrasena.textProperty().addListener(this::cambioTexto);
        //Ejecutar método evento acción clickar botón
        btnEntrar.setOnAction(this::accionBoton);
        //Ejecutar método Hyperlink clickado
        hplRegistrate.setOnAction(this::hyperlinkClickadoRegistro);
        hplContrasenia.setOnAction(this::hyperlinkClickadoContrasenia);
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
        LOGGER.info("Iniciando ControllerSignIn::handleWindowShowing.Metodo_ManejarInicioVentana");
        //Asignar un texto de fondo en el campo contraseña, se muestra cuando el campo está desenfocado.
        pswFieldContrasena.setPromptText("Introduce tu contraseña. (" + TEXTFIELD_MIN_LENGTH + " a " + TEXTFIELD_MAX_LENGTH + " caracteres)");
        //Asignar texto cuando el campo está desenfocado.
        txtFieldUsuario.setPromptText("Introduce tu nombre de usuario. (" + TEXTFIELD_MIN_LENGTH + " a " + TEXTFIELD_MAX_LENGTH + " caracteres)");
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
            //ssi pulsa ok la ventana se cierra
            stage.close();
        }else{
            //pulsa cancelar se consume el evento. Es decir, no hace nada y por tanto, se queda en la ventana.
            event.consume();
        }         
    }
    /**
     * Evento del Hyperlink clickado. Redirige a la escena SignUp.
     *
     * @param event Un evento del Hyperlink.
     */
    public void hyperlinkClickadoContrasenia(ActionEvent event) {
        LOGGER.log(Level.INFO, "Evento del Hyperlink clickado. ");
        //Se ha pulsado el botón y los datos han sido validados en la BBDD.
        LOGGER.log(Level.INFO,"Abriendo ventana Send Mail. ");
        try{
            //New FXMLLoader Añadir el fxml de logout que es la escena a la que se redirige si todo va bien
            FXMLLoader loader = new FXMLLoader(getClass().
                    getResource("/view/FXMLSendMail.fxml"));
            //Parent es una clase gráfica de nodos. xml son nodos.
            Parent root = (Parent)loader.load();
            //Relacionamos el documento FXML con el controlador que le va a controlar.
            SendMailController controladorMail = (SendMailController)loader.getController();
            //Llamada al método setStage del controlador de la ventana signIn. Pasa la ventana.
            controladorMail.setStage(stage);
            //Llamada al método initStage del controlador de la ventana LogOut. Pasa el documento fxml en un nodo.
            controladorMail.initStage(root);
        //Error al cargar la nueva escena, mostrar mensaje.
        }catch(IOException e){
            lblErrorExcepcion.setText("Se ha producido un error. Lo sentimos. Inténtalo mas tarde");
            //Cambia de color el texto del label, en este caso a rojo
            lblErrorExcepcion.setTextFill(Color.web("#ff0000"));
        }
    }

    /**
     * Evento del Hyperlink clickado. Redirige a la escena SignUp.
     *
     * @param event Un evento del Hyperlink.
     */
    public void hyperlinkClickadoRegistro(ActionEvent event) {
        //A este método llegamos cuando se clicka en el Hyperlink.
        LOGGER.log(Level.INFO,"Abriendo ventana SignUp. ");
        try{
            //New FXMLLoader Añadir el fxml de signUp que es la escena a la que se redirige si todo va bien
            FXMLLoader loader = new FXMLLoader(getClass().
                getResource("/view/FXMLDocumentSignUp.fxml"));
            //Parent es una clase gráfica de nodos xml son nodos.
            Parent root = (Parent)loader.load();
            //Relacionamos el documento FXML con el controlador que le va a controlar.
            SignUpController controladorSignUp = (SignUpController)loader.getController();   
            //Pasa la ventana al controlador de la ventana signUp
            controladorSignUp.setStage(stage);
            //Llamada al método initStage del controlador de la ventana signUp. Pasa el documento fxml en un nodo.
            controladorSignUp.initStage(root);
        //Error al cargar la nueva escenamostrar mensaje.
        }catch(IOException e){
            lblErrorExcepcion.setText("Se ha producido un error. Lo sentimos. Inténtalo mas tarde");
            //Cambia de color el texto del label, en este caso a rojo
            lblErrorExcepcion.setTextFill(Color.web("#ff0000"));
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
        lblErrorUsuarioContrasena.setText("");
        //Vaciar label de error tras escribir en cualquiera de los campos.
        lblErrorExcepcion.setText("");
        //Condición.Si está vacío cualquiera de los dos campos. Trim Quita espacios delante y detrás del texto del campo.
        if (this.txtFieldUsuario.getText().trim().equals("")
                || this.pswFieldContrasena.getText().trim().equals("")) {
            //Deshabilitar botón
            btnEntrar.setDisable(true);
        } else {//Los dos campos están informados.
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
     *
     * @param event Un evento del botón
     */
    private void accionBoton(ActionEvent event) {
        LOGGER.info("Iniciando ControllerSignIn.accionBoton");
        //Si cumplen las condiciones enviar datos.
        //El campo usuario tiene una longitud que no está entre 4 y 20 caracteres y no tiene espacios.
        if (txtFieldUsuario.getText().trim().length()> TEXTFIELD_MAX_LENGTH
                || txtFieldUsuario.getText().trim().length()< TEXTFIELD_MIN_LENGTH
                || !SignInController.comprobarEspaciosBlancos(txtFieldUsuario)) {
            LOGGER.info("Longitud del textfield erronea y espacios blancos ControllerSignIn.accionBoton");
            //El foco lo pone en el campo usuario
            txtFieldUsuario.requestFocus();
            //Selecciona el texto para borrar.
            txtFieldUsuario.selectAll();
            //Mostrar un mensaje de error en el label.
            //Vaciar el campo contraseña
            pswFieldContrasena.setText("");
            lblErrorUsuarioContrasena.setText("Usuario incorrecto.");
            //Cambia de color el texto del label, en este caso a rojo
            lblErrorUsuarioContrasena.setTextFill(Color.web("#ff0000"));
            //El campo contraseña tiene una longitud que no está entre 4 y 20 caracteres y no tiene espacios.
        } else if (pswFieldContrasena.getText().trim().length()> TEXTFIELD_MAX_LENGTH
                || pswFieldContrasena.getText().trim().length()< TEXTFIELD_MIN_LENGTH
                || !SignInController.comprobarEspaciosBlancos(pswFieldContrasena)) {
            LOGGER.info("Longitud del passwordField erronea y espacios blancos ControllerSignIn.accionBoton");
            pswFieldContrasena.requestFocus();
            pswFieldContrasena.selectAll();
            //Mostrar un mensaje de error en el label.
            lblErrorUsuarioContrasena.setText("Contraseña incorrecta.");
            //Cambia de color el texto del label, en este caso a rojo
            lblErrorUsuarioContrasena.setTextFill(Color.web("#ff0000"));
        } else //Todos los campos cumplen la condición validar datos en la base de datos
        {
            enviarDatosServidorBBDD();
        }
    }

    /**
     * Enviar datos del usuario a la BBDD haciendo uso de los métodos de la
     * interfaz Signable y el objeto de la clase SignImplementation que tiene
     * como atributo esta clase y que implementa la Interfaz.
     */
    private void enviarDatosServidorBBDD() {
        User user = null;
        try {
            //Cuando entra a este método sabemos que los campos usuario y contraseña cumplen todas las condiciones preestablecidas.
            LOGGER.info("Iniciando ControllerSignIn.EnviarDatosServidorBBDD");
            //Almacenar en el objeto de la clase User los datos recogidos de los campos de la ventana.
            userImp.loginUser(txtFieldUsuario.getText().trim(),ClavePublicaCliente.cifrarTexto(pswFieldContrasena.getText().trim()));
            //Le llamo otra vez para que asi me dice que tipo de usuario es. Para evitar el error de xml
            user = userImp.findUsersByLogin(txtFieldUsuario.getText().trim());
            user.setLogin(txtFieldUsuario.getText().trim());
            abrirVentanaSector(user);
            //Vaciar campos de texto
            txtFieldUsuario.setText("");
            pswFieldContrasena.setText("");

        } catch (BusinessLogicException ex) {
            Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, ex);
            //Colocar el texto de la excepción en el label
            lblErrorExcepcion.setText(ex.getMessage());
            //Cambia de color el texto del label, en este caso a rojo
            lblErrorExcepcion.setTextFill(Color.web("#ff0000"));           
            LOGGER.log(Level.SEVERE, "CAtch enviardatosalservidor");
        } catch (Exception ex) {
            Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, ex);
        }          
    }

    /**
     * Cargar la escena de LogOut en la ventana. Se le pasa el usuario para que
     * pueda cerrar la sesión.
     */
  
    private void abrirVentanaSector(User usuario) throws Exception{
        //Se ha pulsado el botón y los datos han sido validados en la BBDD.
        LOGGER.log(Level.INFO,"Abriendo ventana Sector. ");
        try{
            //New FXMLLoader Añadir el fxml de logout que es la escena a la que se redirige si todo va bien
            FXMLLoader loader = new FXMLLoader(getClass().
                    getResource("/view/FXMLDocumentSector.fxml"));
            //Parent es una clase gráfica de nodos. xml son nodos.
            Parent root = (Parent)loader.load();
            //Relacionamos el documento FXML con el controlador que le va a controlar.
            SectorManagementController controladorSector = (SectorManagementController)loader.getController();
            //Pasar el usuario al controlador de la ventana logOut.
            controladorSector.setUser(usuario);
            //Llamada al método setStage del controlador de la ventana signIn. Pasa la ventana.
            controladorSector.setStage(stage);
            //Llamada al método initStage del controlador de la ventana LogOut. Pasa el documento fxml en un nodo.
            controladorSector.initStage(root);
        //Error al cargar la nueva escena, mostrar mensaje.
        }catch(IOException e){
            lblErrorExcepcion.setText("Se ha producido un error. Lo sentimos. Inténtalo mas tarde");
            //Cambia de color el texto del label, en este caso a rojo
            lblErrorExcepcion.setTextFill(Color.web("#ff0000"));
        }
    }
  
    private static Boolean comprobarEspaciosBlancos(TextField field) {
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
