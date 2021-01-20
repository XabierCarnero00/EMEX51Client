/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import bussinesLogic.UserManager;
import clientREST.BossREST;
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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.ws.rs.WebApplicationException;
import model.Boss;
import model.User;
import securityClient.ClavePublicaCliente;

/**
 *
 * @author endika
 */
public class SignUpController {
    /**
     * Logger para trazar los pasos del código.
     */
    private static final Logger LOGGER = Logger.getLogger("grupog5.signinsignupapplication.cliente.controlador.FXMLDocumentSignUpController");
    /**
     * Una ventana
     */
    private Stage stage;
    /**
     * Longitud máxima de los campos de texto permitida.
     */
    private static final Integer TEXTFIELD_MAX_LENGTH = 20;
    /**
     * Longitud mínima de los campos de texto permitida.
     */
    private static final Integer TEXTFIELD_MIN_LENGTH = 4;
    /**
     * Longitud máxima del campo email permitida.
     */
    private static final Integer TEXTFIELD_EMAIL_MAX_LENGTH = 45;
    /**
     * Interface UserManager instance.
     */
    private UserManager userManager;
    /**
     * Botón de confirmación de crear cuenta.
     */
    @FXML private Button btnCrearCuenta;
    /**
     * Hyperlink a la escena SignIn
     */
    @FXML private Hyperlink hplIniciaSesion;
    /**
     * Campo de texto del nombre del usuario
     */
    @FXML private TextField txtFieldNombre;
    /**
     * Campo de texto del email del usuario
     */
    @FXML private TextField txtFieldEmail;
    /**
     * Campo de texto del nombre de usuario o login.
     */
    @FXML private TextField txtFieldUsuario;
    /**
     * Campo de texto de tipo contraseña.
     */
    @FXML private PasswordField pswFieldContrasena;
    /**
     * Campo de texto de tipo contraseña para la confirmación de la contraseña.
     */
    @FXML private PasswordField pswFieldRepetirContrasena;
    /**
     * Label informátivo gráfico de los distintos errores.
     */
    @FXML private Label lblMensajeError; 
       
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
        //Este método es el primero que se ejecuta de la escena al haber sido llamado desde la clase anterior
        LOGGER.log(Level.INFO,"Inicializando la escena signUp. ");
        //Creación de  una nueva escena
        Scene scene = new Scene(root);
        //Añadir escena a la ventana
        stage.setScene(scene);
        //Asignación de un título a la ventana
        stage.setTitle("Sign Up");
        //Asignar tamaño fijo a la ventana
        stage.setResizable(false); 
        
        //Iniciar los componentes de la escena. TextFields y botones.
        iniciarComponentesEscena();
        //Ejecutar método handleWindowShowing cuando se produzca el evento setOnShowing
        //Este evento se lanza cuando la ventana está a punto de aparecer. En nuestro caso nunca se va a ejecutar, podríamos quitarlo.
        stage.setOnShowing(this::manejarInicioVentana);
        //Añadir un evento para el cambio de texto en cada uno de los campos de texto.     
        txtFieldUsuario.textProperty().addListener(this::cambioTexto);
        txtFieldEmail.textProperty().addListener(this::cambioTexto);
        txtFieldNombre.textProperty().addListener(this::cambioTexto);
        txtFieldNombre.textProperty().addListener(this::cambioTexto);
        pswFieldContrasena.textProperty().addListener(this::cambioTexto);
        pswFieldRepetirContrasena.textProperty().addListener(this::cambioTexto);
        //Ejecutar método evento acción clickar botón
        btnCrearCuenta.setOnAction(this::accionBoton);
        //Ejecutar método Hyperlink clickado
        hplIniciaSesion.setOnAction(this::hyperlinkClickado);
        //Si se pulsa la x de la ventana para salir
        stage.setOnCloseRequest(this::manejarCierreVentana);
        //Hace visible la pantalla
        stage.show();
    }
    
    /**
     * Inicializa los componentes de la escena con los valores a mostrar al cargar la escena en la ventana.
     */
    public void iniciarComponentesEscena(){
        LOGGER.log(Level.INFO,"Iniciar componentes de la escena. ");
        //Asignar un texto de fondo en el campo contraseña, se muestra cuando el campo está desenfocado.
        pswFieldContrasena.setPromptText("Introduce tu contraseña. ("+TEXTFIELD_MIN_LENGTH+ " a "+TEXTFIELD_MAX_LENGTH+" caracteres)");
        //Asignar un texto de fondo en el campo repetir contraseña, se muestra cuando el campo está desenfocado.
        pswFieldRepetirContrasena.setPromptText("Introduce tu contraseña. ("+TEXTFIELD_MIN_LENGTH+ " a "+TEXTFIELD_MAX_LENGTH+" caracteres)");
        //Asignar texto cuando el campo está desenfocado.
        txtFieldUsuario.setPromptText("Introduce tu nombre de usuario. ("+TEXTFIELD_MIN_LENGTH+ " a "+TEXTFIELD_MAX_LENGTH+" caracteres)");
        //Asignar texto en el textField Nombre cuando el campo está desenfocado.
        txtFieldNombre.setPromptText("Introduce tu nombre.");
        //Asignar texto en el textField email cuando el campo está desenfocado.
        txtFieldEmail.setPromptText(" @ Introduce tu email."); 
        //El boton está inhabilitado al arrancar la ventana.
        btnCrearCuenta.setDisable(true);  
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
     * @param event Un evento del Hyperlink.
     */
    public void hyperlinkClickado(ActionEvent event){
        LOGGER.log(Level.INFO,"Evento del Hyperlink clickado de la clase controlador SignUp. ");
        try{
            //Llamada al método de esta clase.
            abrirVentanaSignIn(); 
        }catch(Exception e){
            //Si se produce un error mostrar un mensaje.
            lblMensajeError.setText("Intentalo mas tarde. Fallo la conexión");
        }   
    }
     /**
     * Acciones que se realizan en el momento previo a que se muestra la ventana. Inicializa los componentes.
     * @param event Evento de ventana.
     */
    private void manejarInicioVentana(WindowEvent event){
        LOGGER.info("Iniciando ControllerSignUp::handleWindowShowing manejarInicioVentana");
        //Asignar un texto de fondo en el campo contraseña, se muestra cuando el campo está desenfocado.
        pswFieldContrasena.setPromptText("Introduce tu contraseña. ("+TEXTFIELD_MIN_LENGTH+ " a "+TEXTFIELD_MAX_LENGTH+" caracteres)");
        //Asignar un texto de fondo en el campo repetir contraseña, se muestra cuando el campo está desenfocado.
        pswFieldRepetirContrasena.setPromptText("Introduce tu contraseña. ("+TEXTFIELD_MIN_LENGTH+ " a "+TEXTFIELD_MAX_LENGTH+" caracteres)");
        //Asignar texto cuando el campo está desenfocado.
        txtFieldUsuario.setPromptText("Introduce tu nombre de usuario. ("+TEXTFIELD_MIN_LENGTH+ " a "+TEXTFIELD_MAX_LENGTH+" caracteres)");
        //Asignar texto en el textField Nombre cuando el campo está desenfocado.
        txtFieldNombre.setPromptText("Introduce tu nombre.");
        //Asignar texto en el textField email cuando el campo está desenfocado.
        txtFieldEmail.setPromptText(" @ Introduce tu email."); 
        //El boton está inhabilitado al arrancar la ventana.
        btnCrearCuenta.setDisable(true);  
    }

    /**
     * Registra los cambios de texto en los textField o passwordField.
     * @param observable 
     * @param oldValue Valor anterior al lanzamiento del evento.
     * @param newValue Nuevo valor tras el cambio.
     */
    private void cambioTexto(ObservableValue observable,String oldValue,String newValue){
        LOGGER.info("Iniciando ControllerSignUp::cambioTexto");
        //Vaciar label de error tras escribir en cualquiera de los campos.
        lblMensajeError.setText("");
        //Vaciar label de error tras escribir en cualquiera de los campos.
        lblMensajeError.setText("");
        //Si está vacío cualquiera de los cinco campos.
        if(this.txtFieldNombre.getText().trim().equals("")||this.txtFieldUsuario.getText().trim().equals("")
            ||this.txtFieldEmail.getText().trim().equals("")||this.pswFieldContrasena.getText().trim().equals("")||
                this.pswFieldRepetirContrasena.getText().trim().equals("")){
            //Deshabilitar botón
            btnCrearCuenta.setDisable(true);  
        }else{//Los cinco campos están informados.
            //Habilitar botón 
            btnCrearCuenta.setDisable(false);
            //Añadir tooltip al botón. (Muestra el mensaje al tener el ratón encima del botón)
            btnCrearCuenta.setTooltip(new Tooltip("Pulsa para registrarte"));
            //Habilitar el uso del teclado para moverse por la ventana.
            btnCrearCuenta.setMnemonicParsing(true);
        }
    }

    /**
     * Carga la ventana signIn para que el usuario se loguee si el registro ha sido correcto o se clicka el Hyperlink.
     */
    private void abrirVentanaSignIn() {
        LOGGER.log(Level.INFO,"Metodo del controladorSignUp, Abriendo ventana SignIn.");
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
            lblMensajeError.setText("Se ha producido un error. Lo sentimos. Inténtalo mas tarde");
            //Cambia de color el texto del label, en este caso a rojo
            lblMensajeError.setTextFill(Color.web("#ff0000"));
        }
    }
    
    /**
     * Tratar el click del botón
     * @param event Un evento del botón
     */
    private void accionBoton(ActionEvent e){
        LOGGER.info("Iniciando ControllerSignUp.accionBoton");
            //Si cumplen las condiciones los cuatro campos de texto..
            if(!validarCampoNombre(txtFieldNombre)){
                //Selecciona el texto del campo de texto.
                txtFieldNombre.selectAll();
                //Pone el foco en el campo de texto.
                txtFieldNombre.requestFocus();
                //Mostrar mensaje error  
                lblMensajeError.setText("El campo nombre no es válido.");
                //Cambia de color el texto del label, en este caso a rojo
                lblMensajeError.setTextFill(Color.web("#ff0000"));
            }else if(! validarCampoEmail(txtFieldEmail)){
                //Selecciona el texto del campo de texto.
                txtFieldEmail.selectAll();
                //Pone el foco en el campo de texto.
                txtFieldEmail.requestFocus();
                //Mostrar mensaje error  
                lblMensajeError.setText("El campo email no es válido.");
                //Cambia de color el texto del label, en este caso a rojo
                lblMensajeError.setTextFill(Color.web("#ff0000"));
            }else if(txtFieldUsuario.getText().trim().length()>TEXTFIELD_MAX_LENGTH){
                //Selecciona el texto del campo de texto.
                txtFieldUsuario.selectAll();
                //Pone el foco en el campo de texto.
                txtFieldUsuario.requestFocus();
                //Mostrar mensaje error  
                lblMensajeError.setText("El campo usuario tiene mas de 20 caracteres.");
                //Cambia de color el texto del label, en este caso a rojo
                lblMensajeError.setTextFill(Color.web("#ff0000"));
            }else if(txtFieldUsuario.getText().trim().length()<TEXTFIELD_MIN_LENGTH){
                //Selecciona el texto del campo de texto.
                txtFieldUsuario.selectAll();
                //Pone el foco en el campo de texto.
                txtFieldUsuario.requestFocus();
                //Mostrar mensaje error  
                lblMensajeError.setText("El campo usuario tiene menos de 4 caracteres.");
                //Cambia de color el texto del label, en este caso a rojo
                lblMensajeError.setTextFill(Color.web("#ff0000"));
            }else if(!comprobarEspaciosBlancos(txtFieldUsuario)){   
                //Selecciona el texto del campo de texto.
                txtFieldUsuario.selectAll();
                //Pone el foco en el campo de texto.
                txtFieldUsuario.requestFocus();
                //Mostrar mensaje error  
                lblMensajeError.setText("El campo usuario no puede contener espacios.");
                //Cambia de color el texto del label, en este caso a rojo
                lblMensajeError.setTextFill(Color.web("#ff0000"));
            }else if(!validarContraseñas((TextField)pswFieldContrasena,(TextField)pswFieldRepetirContrasena)){
                //Sabemos que el error enstá en las contraseñas. 2 opciones No son iguales o no cumple los requisitos.
                if(!pswFieldContrasena.getText().trim().equals(pswFieldRepetirContrasena.getText().trim())){
                     //Selecciona el texto del campo de texto.
                    pswFieldContrasena.selectAll();
                    //Pone el foco en el campo de texto.
                    pswFieldContrasena.requestFocus();
                    //Mostrar mensaje error    
                    lblMensajeError.setText("Los campos contraseña no coinciden.");
                    //Cambia de color el texto del label, en este caso a rojo
                    lblMensajeError.setTextFill(Color.web("#ff0000"));
                }else{
                     //Selecciona el texto del campo de texto.
                    pswFieldContrasena.selectAll();
                    //Pone el foco en el campo de texto.
                    pswFieldContrasena.requestFocus();
                    //Mostrar mensaje error  
                    lblMensajeError.setText("El campo contraseña no es válido.");
                    //Cambia de color el texto del label, en este caso a rojo
                    lblMensajeError.setTextFill(Color.web("#ff0000"));
                }                 
            }            
        else{
            //Todos los campos cumplen la condición validar datos en la base de datos
            enviarDatosServidorBBDD(); 
        }
    }
    
    /**
     * Enviar datos del usuario a la BBDD con el objeto signable
     */
    private void enviarDatosServidorBBDD() {
        LOGGER.info("Iniciando ControllerSignUp.EnviarDatosServidorBBDD");
        //Almacenar en objeto de User los datos recogidos de los campos de la ventana.
        User myUser = new Boss (txtFieldUsuario.getText().trim(),txtFieldEmail.getText().trim(),
                txtFieldNombre.getText().trim(),ClavePublicaCliente.cifrarTexto(pswFieldContrasena.getText().trim()));
        try {
            //Pasa el usuario a la instancia signable a su método signUp.
            BossREST bossRest = new BossREST();
            bossRest.create(myUser);
            //Mostrar mensaje de usuario creado correctamente
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"Usuario creado correctamente.");
            alert.setHeaderText(null);
            alert.showAndWait();
            //Llamada al método para redireccionar aplicación a la siguiente ventana.
            abrirVentanaSignIn();
        //El método signUp de signable lanza 2 excepciones. Tratarlas en el catch.    
        } catch (WebApplicationException ex1) {
            LOGGER.info("Iniciando ControllerSignUp.EnviarDatosServidorBBDD.Entra al catch ExcepcionUserYaExiste");
            //VAciar campos de texto
            txtFieldNombre.setText("");
            txtFieldEmail.setText("");
            txtFieldUsuario.setText("");
            pswFieldContrasena.setText("");
            pswFieldRepetirContrasena.setText("");
            //Colocar el texto de la excepción en el label
            lblMensajeError.setText(ex1.getMessage());
            //Cambia de color el texto del label, en este caso a rojo
            lblMensajeError.setTextFill(Color.web("#ff0000"));
        }
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

    /**
     * Comprueba que el contenido del campo de texto Email cumple los requisitos
     * @return Un Booleano. True si el campo cumple los requisitos, false si no lo hace.
     */
    private Boolean validarCampoEmail(TextField field) {
        String email = field.getText().trim();
        //Si el String email del campo de texto email es superior a la constante preestablecida retornar false
        if (email.length()>TEXTFIELD_EMAIL_MAX_LENGTH)
            return false;
        //El String entra dentro del rango de longitud mirar su contenido
        else
            return emailCorrecto(email);
    }

    /**
     * Comprueba que el contenido del campo de texto Login cumple los requisitos
     * @return Un Booleano. True si el campo cumple los requisitos, false si no lo hace.
     */
    private Boolean validarCampoLogin(TextField field) {
        if(field.getText().trim().length()<TEXTFIELD_MAX_LENGTH && field.getText().trim().length()>TEXTFIELD_MIN_LENGTH         
                &&comprobarEspaciosBlancos(field))
            return true;
        else
            return false;
    }
       
    /**
     * Validación del campo contraseña y repetir contraseña. Deben ser iguales ambos campos y máximo 30 caracteres.
     * @return Un booleano. True si la contraseña es correcta.
     */
    private boolean validarContraseñas(TextField contraseniaUno, TextField contraseniaDos) {
      //Comparar los dos campos contraseña. Si son iguales y además tiene una longitud superior a 4 e inferior a 20 retorna true sino false.
      return contraseniaUno.getText().trim().equals(contraseniaDos.getText().trim());
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
