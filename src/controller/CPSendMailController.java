/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import businessLogic.UserFactory;
import businessLogic.UserInterface;
import exceptions.ExcepcionEmailNoExiste;
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
import utilMethods.MetodosUtiles;

/**
 * FXML Controller class for sending mail scene. This scene is visible when an user forgot the password
 * and asks for recover. 
 * @author Endika, Markel, Xabier
 */
public class CPSendMailController {

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

    @FXML
    private TextField textfieldEmail;

    @FXML
    private Button buttonEnviar;

    @FXML
    private Label labelError;
    @FXML
    private Button buttonBack;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initStage(Parent root) {
        Scene scene = new Scene(root);

        stage.setScene(scene);

        stage.setTitle("Send Mail");
        stage.setResizable(false);

        buttonEnviar.setDisable(true);

        stage.show();

        buttonBack.setOnAction(this::clickBack);
        buttonEnviar.setOnAction(this::clickEnviar);
        textfieldEmail.textProperty().addListener(this::emailListener);

    }

    public void clickEnviar(ActionEvent event) {
        if (MetodosUtiles.validateEmail(textfieldEmail.getText().trim())) {
            try {
                userInt.temporalPass(textfieldEmail.getText().trim());
                openNewPasswordWindow(textfieldEmail.getText().trim());
            } catch (ExcepcionEmailNoExiste ex) {
                Logger.getLogger(CPSendMailController.class.getName()).log(Level.SEVERE, null, ex);
                textfieldEmail.setText("");
                labelError.setText("No existen usuarios con ese email");
                labelError.setTextFill(Color.web("#ff0000"));
            }
        } else {
            textfieldEmail.setText("");
            labelError.setText("Introduce un email valido");
            labelError.setTextFill(Color.web("#ff0000"));
        }
    }

    private void emailListener(ObservableValue observable, String oldValue, String newValue) {
        labelError.setText("");
        if (textfieldEmail.getText().compareToIgnoreCase("") == 0) {
            buttonEnviar.setDisable(true);
        } else {
            buttonEnviar.setDisable(false);
        }
    }

    private void openNewPasswordWindow(String email) {
        try {
            //Mensaje Logger al acceder al método
            LOGGER.log(Level.INFO, "Método para abrir el menu principal de la aplicación");
            //New FXMLLoader Añadir el fxml de MenuPrincipal que es la ventana principal
            FXMLLoader loader = new FXMLLoader(getClass().
                    getResource("/view/FXMLNewPassword.fxml"));
            //Parent es una clase gráfica de nodos xml son nodos.
            Parent root = (Parent) loader.load();
            //Relacionamos el documento FXML con el controlador que le va a controlar.
            NewPasswordController newPasswordController = (NewPasswordController) loader.getController();
            //Introducir email en la ventana NewPassword
            newPasswordController.setEmail(email);
            //Llamada al método setStage del controlador de la ventana SignIn. Pasa la ventana.
            newPasswordController.setStage(stage);
            //Llamada al método initStage del controlador de la ventana SignIn. Pasa el documento fxml en un nodo.
            newPasswordController.initStage(root);
            //Llamada al método inicializarComponenentesVentana del controlador de la ventana signIn.
        } catch (IOException ex) {
            Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void clickBack(ActionEvent event) {
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
