/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author xabig
 */
public class MenuPrincipalController {
    /**
     * Logger object used to control activity from class FXMLDocumentSectorController.
     */
    private static final Logger LOGGER = Logger.getLogger("emex51.cliente.controlador.MenuPrincipalController");
    /**
     * Una ventana sobre la que se coloca una escena.
     */
    private Stage stage;

    @FXML
    private Menu menuMenu;
    @FXML
    private Menu menuSector;
    @FXML
    private Menu menuEmpleado;
    @FXML
    private Menu menuVisitante;
    @FXML
    private Button buttonSectores;
    @FXML
    private Button buttonEmpleados;
    @FXML
    private Button buttonVisitantes;
    @FXML
    private MenuItem EmpleadoDos;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Initializes the controller class.
     *
     * @param root
     */
    public void initStage(Parent root) {
        Scene scene = new Scene(root);

        stage.setScene(scene);

        stage.setTitle("Menu Principal");

        stage.setResizable(false);

        stage.show();

        buttonEmpleados.setOnAction(this::openEmpleados);

        menuEmpleado.setOnAction(this::openEmpleados);
        
        EmpleadoDos.setOnAction(this::openEmpleados);
        
        buttonSectores.setOnAction(this::openSectores);

    }

    private void openEmpleados(ActionEvent event) {
        try {
            //New FXMLLoader Añadir el fxml de logout que es la escena a la que se redirige si todo va bien
            FXMLLoader loader = new FXMLLoader(getClass().
                    getResource("/view/GestionarEmployee.fxml"));
            //Parent es una clase gráfica de nodos. xml son nodos.
            Parent root = (Parent) loader.load();
            //Relacionamos el documento FXML con el controlador que le va a controlar.
            GestionarEmployeeController gestionarEmployeeController = (GestionarEmployeeController) loader.getController();
            //Llamada al método setStage del controlador de la ventana signIn. Pasa la ventana.
            gestionarEmployeeController.setStage(stage);
            //Llamada al método initStage del controlador de la ventana LogOut. Pasa el documento fxml en un nodo.
            gestionarEmployeeController.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(MenuPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Action event handler for Sector button. Redirects to the sectors scene.
     * @param event The ActionEvent object for the event.
     */
    private void openSectores(ActionEvent event) {
        LOGGER.info("Iniciando SectorController::metodo openSectores");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().
                    getResource("/view/FXMLDocumentSector.fxml"));
            Parent root = (Parent) loader.load();
            FXMLDocumentSectorController controladorSector = (FXMLDocumentSectorController) loader.getController();
            controladorSector.setStage(stage);
            controladorSector.initStage(root);
        } catch (IOException ex) {
            System.out.println("gjjiangjnjankjgnskjdn");
            Logger.getLogger(MenuPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
