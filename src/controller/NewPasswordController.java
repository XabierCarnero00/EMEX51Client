/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import bussinesLogic.UserFactory;
import bussinesLogic.UserInterface;
import exceptions.ExcepcionContraseñaNoCoincide;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author xabig
 */
public class NewPasswordController {

    /**
     * Logger para trazar los pasos del código.
     */
    private static final Logger LOGGER = Logger.getLogger("grupog5.signinsignupapplication.cliente.controlador.FXMLDocumentControllerSignIn");

    /**
     * Una ventana sobre la que se coloca una escena.
     */
    private Stage stage;

    /**
     * The User interface from logic.
     */
    UserInterface userInt = UserFactory.getUserImp();

    /**
     * Email del User al que hay que cambiarle la contraseña.
     */
    private String email;

    @FXML
    private TextField textfieldContraseñaDePaso;

    @FXML
    private TextField textfieldContraseñaNueva;

    @FXML
    private Button buttonEnviar;

    @FXML
    private Label labelError;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void initStage(Parent root) {
        Scene scene = new Scene(root);

        stage.setScene(scene);

        stage.setTitle("New Password");
        stage.setResizable(false);

        buttonEnviar.setDisable(true);

        stage.show();

        buttonEnviar.setOnAction(this::clickEnviar);
        textfieldContraseñaNueva.textProperty().addListener(this::passwordListener);
        textfieldContraseñaDePaso.textProperty().addListener(this::passwordListener);

    }

    public void clickEnviar(ActionEvent event) {
        try {
            userInt.changePassword(email, textfieldContraseñaDePaso.getText().trim(), textfieldContraseñaNueva.getText().trim());
            abrirSingIn();
        } catch (ExcepcionContraseñaNoCoincide ex) {
            Logger.getLogger(NewPasswordController.class.getName()).log(Level.SEVERE, null, ex);
            textfieldContraseñaDePaso.setText("");
            textfieldContraseñaNueva.setText("");
            labelError.setText("Las contraseñas no coinciden");
            labelError.setTextFill(Color.web("#ff0000"));
        }
    }

    private void passwordListener(ObservableValue observable, String oldValue, String newValue) {
        comprobarEnviar();
        labelError.setText("");
    }

    private void comprobarEnviar() {
        if (textfieldContraseñaDePaso.getText().compareToIgnoreCase("") != 0
                && textfieldContraseñaNueva.getText().compareToIgnoreCase("") != 0) {
            buttonEnviar.setDisable(false);
        } else {
            buttonEnviar.setDisable(true);
        }
    }

    private void abrirSingIn() {
        try {
            //Mensaje Logger al acceder al método
            LOGGER.log(Level.INFO, "Método start de la aplicación");
            //New FXMLLoader Añadir el fxml de MenuPrincipal que es la ventana principal
            FXMLLoader loader = new FXMLLoader(getClass().
                    getResource("/view/FXMLSignIn.fxml"));
            //Parent es una clase gráfica de nodos xml son nodos.
            Parent root = (Parent) loader.load();
            //Relacionamos el documento FXML con el controlador que le va a controlar.
            SignInController signInController = (SignInController) loader.getController();
            //MenuPrincipalController menuPrincipalController = (MenuPrincipalController)loader.getController();
            //Llamada al método setStage del controlador de la ventana SignIn. Pasa la ventana.
            signInController.setStage(stage);
            //menuPrincipalController.setStage(stage);
            //Llamada al método initStage del controlador de la ventana SignIn. Pasa el documento fxml en un nodo.
            signInController.initStage(root);
            //menuPrincipalController.initStage(root);
            //Llamada al método inicializarComponenentesVentana del controlador de la ventana signIn.
        } catch (IOException ex) {
            Logger.getLogger(NewPasswordController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
