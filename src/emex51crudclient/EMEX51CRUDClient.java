/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emex51crudclient;

import controller.MenuPrincipalController;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 * Emex51 client application. 
 * @version 1.0
 * @since 23/10/2020
 * @author Endika, Xabier, Markel
 */
public class EMEX51CRUDClient extends Application {

    /**
     * Logger object used to control activity from class.
     */
    private static final Logger LOGGER = 
            Logger.getLogger("emex51.signinsignupapplication.cliente.application");
    /**
     * Application entry method.
     * @param stage A window
     * @throws Exception 
     */
    @Override
    public void start(Stage stage) throws Exception {
        //Mensaje Logger al acceder al método
        LOGGER.log(Level.INFO, "Método start de la aplicación");
        //New FXMLLoader Añadir el fxml de MenuPrincipal que es la ventana principal
        FXMLLoader loader = new FXMLLoader(getClass().
                getResource("/view/MenuPrincipal.fxml"));
        //Parent es una clase gráfica de nodos xml son nodos.
        Parent root = (Parent) loader.load();
        //Relacionamos el documento FXML con el controlador que le va a controlar.
        MenuPrincipalController menuCreatureController = (MenuPrincipalController) loader.getController();
        //Llamada al método setStage del controlador de la ventana Menu principal. Pasa la ventana.
        menuCreatureController.setStage(stage);
        //Llamada al método initStage del controlador de la ventana Menu principal. Pasa el documento fxml en un nodo.
        menuCreatureController.initStage(root);
    }

    /**
     * JavaFX project initialization method.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
