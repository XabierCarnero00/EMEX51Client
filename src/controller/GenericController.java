/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Boss;
import model.User;

/**
 *
 * @author markel
 */
public class GenericController {

    /**
     * Logger object used to log messages for application.
     */
    protected static final Logger LOGGER = Logger.getLogger("EMEX51CRUDClient.controller.GenericController");

    /**
     * The sig in user
     */
    private User user;
    /**
     * La ventana
     */
    protected Stage stage;

    @FXML
    private Menu MenuLogout;
    /**
     * The menu Item to exit
     */
    @FXML
    private MenuItem ItemExit;
    /**
     * The menu Item of logout
     */
    @FXML
    private MenuItem ItemLogout;
    /**
     * The menu Item of sector
     */
    @FXML
    private MenuItem ItemSectores;
    /**
     * The menu Item of employee
     */
    @FXML
    private MenuItem ItemEmpleados;
    /**
     * The menu Item of visitor
     */
    @FXML
    private MenuItem ItemVisitantes;
    /**
     * Label informatimo para saber el tipo de usuario
     */
    private Label lblTipoUsuario;

    @FXML
    private Menu menu;

    /**
     * Returns the User that operates in the Window.
     *
     * @return the User
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the User that operates in the Window.
     *
     * @param user the User to be set.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets the Stage object related to this controller.
     *
     * @return The Stage object initialized by this controller.
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Sets the Stage object related to this controller.
     *
     * @param stage The Stage object to be initialized.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void genericController(ActionEvent event) {

        //Barra menu Visitante
        ItemExit.setOnAction(this::openWindowExit);
        //Barra menu Visitante
        ItemLogout.setOnAction(this::openWindowLogout);
        //Menu Sector
        ItemSectores.setOnAction(this::openWindowSector);
        //Menu Empleado
        ItemEmpleados.setOnAction(this::openWindowEmployee);
        //Menu Visitante
        ItemVisitantes.setOnAction(this::openWindowVisitor);

        //Label para el tipo de usuario
        if (user instanceof Boss) {
            lblTipoUsuario.setText(user.getLogin() + "(BOSS)");
        } else {
            lblTipoUsuario.setText(user.getLogin() + "(EMPLEADO)");
            ItemEmpleados.setDisable(true);
        }

    }

    @FXML
    private void openWindowLogout(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Seguro que quieres cerrar sesion?");
            alert.setTitle(null);
            alert.setHeaderText(null);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                //New FXMLLoader Añadir el fxml de MenuPrincipal que es la ventana principal
                FXMLLoader loader = new FXMLLoader(getClass().
                        getResource("/view/FXMLSignIn.fxml"));
                //getResource("/view/FXMLArmyManagement.fxml"));
                //getResource("/view/FXMLSectorManagement.fxml"));
                //Parent es una clase gráfica de nodos xml son nodos.
                Parent root = (Parent) loader.load();
                //Relacionamos el documento FXML con el controlador que le va a controlar.
                SignInController signInController = (SignInController) loader.getController();
                //ArmyManagementController armyManagementController = (ArmyManagementController) loader.getController();
                //SectorManagementController sectorManagementController = (SectorManagementController) loader.getController();
                //Llamada al método setStage del controlador de la ventana SignIn. Pasa la ventana.
                Stage st = new Stage();
                signInController.setStage(st);
                //armyManagementController.setStage(stage);
                //sectorManagementController.setStage(stage);
                //Llamada al método initStage del controlador de la ventana SignIn. Pasa el documento fxml en un nodo.
                signInController.initStage(root);
                //armyManagementController.initStage(root);
                //sectorManagementController.initStage(root);
                //Llamada al método inicializarComponenentesVentana del controlador de la ventana signIn.
            }
        } catch (IOException ex) {
            LOGGER.log(Level.INFO, "Execepción abrir ventana Sector");
        }

    }

    @FXML
    private void openWindowExit(ActionEvent event) {
        LOGGER.log(Level.INFO, "Cerrando aplicación");
        stage.close();
    }

    @FXML
    private void openWindowEmployee(ActionEvent event) {
        try {
            //New FXMLLoader Añadir el fxml de logout que es la escena a la que se redirige si todo va bien
            FXMLLoader loader = new FXMLLoader(getClass().
                    getResource("/view/FXMLEmployeeManagement.fxml"));
            //Parent es una clase gráfica de nodos. xml son nodos.
            Parent root = (Parent) loader.load();
            //Relacionamos el documento FXML con el controlador que le va a controlar.
            EmployeeManagementController gestionarEmployeeController = (EmployeeManagementController) loader.getController();
            //Llamada al método setStage del controlador de la ventana signIn. Pasa la ventana.
            gestionarEmployeeController.setStage(stage);
            //Llamada al método initStage del controlador de la ventana LogOut. Pasa el documento fxml en un nodo.
            gestionarEmployeeController.initStage(root);
        } catch (IOException ex) {
            LOGGER.log(Level.INFO, "Execepción abrir ventana Empleado");
        }
    }

    @FXML
    private void openWindowVisitor(ActionEvent event) {
        try {
            //New FXMLLoader Añadir el fxml de logout que es la escena a la que se redirige si todo va bien
            FXMLLoader loader = new FXMLLoader(getClass().
                    getResource("/view/VisitorManagementController.fxml"));
            //Parent es una clase gráfica de nodos. xml son nodos.
            Parent root = (Parent) loader.load();
            //Relacionamos el documento FXML con el controlador que le va a controlar.
            //GestionarVisitorController gestionarVisitorController = (GestionarVisitorController) loader.getController();
            //Llamada al método setStage del controlador de la ventana signIn. Pasa la ventana.
            //gestionarVisitorController.setStage(stage);
            //Llamada al método initStage del controlador de la ventana LogOut. Pasa el documento fxml en un nodo.
            //gestionarVisitorController.initStage(root);
        } catch (IOException ex) {
            LOGGER.log(Level.INFO, "Execepción abrir ventana Visitante");
        }
    }

    private void alertaCerrarPestaña(WindowEvent event) {
        LOGGER.info("Iniciando Controller::alertaCerrarPestaña");
        Alert alert;

        alert = new Alert(Alert.AlertType.CONFIRMATION, "Estás seguro que quieres salir?.");
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();
        event.consume();
        if (result.get() == ButtonType.OK) {
            //Cerrar ventana
            stage.close();
        }
    }

    @FXML
    private void openWindowSector(ActionEvent event) {
        try {
            //New FXMLLoader Añadir el fxml de sector management que es la escena a la que se redirige si todo va bien
            FXMLLoader loader = new FXMLLoader(getClass().
                    getResource("/view/FXMLSectorManagement.fxml"));
            //Parent es una clase gráfica de nodos. xml son nodos.
            Parent root = (Parent) loader.load();
            //Relacionamos el documento FXML con el controlador que le va a controlar.
            SectorManagementController sectorManagementController = (SectorManagementController) loader.getController();
            //Llamada al método setStage del controlador de la ventana signIn. Pasa la ventana.
            sectorManagementController.setStage(stage);
            //Llamada al método initStage del controlador de la ventana LogOut. Pasa el documento fxml en un nodo.
            sectorManagementController.initStage(root);
        } catch (IOException ex) {
            LOGGER.log(Level.INFO, "Execepción abrir ventana Sector");
        }
    }
}
