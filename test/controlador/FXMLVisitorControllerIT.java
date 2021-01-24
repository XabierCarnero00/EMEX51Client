/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import controller.SectorManagementController;
import java.awt.Button;
import java.awt.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;


/**
 *
 * @author markel
 */
 @FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FXMLVisitorControllerIT extends ApplicationTest{
     
    private TableView tblVisitors;
    private ComboBox cbxBuscar;
    private TextField txtBuscar;
    private Button btnBuscar;
    private DatePicker datePicker;
    private Button btnBorrar;
     
     /**
     * Starts application to be tested.
     * @param stage Primary Stage object
     * @throws Exception If there is any error
     */
@Override 
    public void start(Stage stage) throws Exception {
        //start JavaFX application to be tested    
        new SectorManagementController().start(stage);
        //lookup for some nodes to be used in testing
        datePicker=lookup("#datePicker").query();
        txtBuscar=lookup("#txtBuscar").query();
        btnBuscar=lookup("#btnBuscar").query();
        btnBorrar=lookup("#btnBorrar").query();
        tblVisitors=lookup("#tblVisitors").queryTableView();
        cbxBuscar=lookup("#cbxBuscar").queryComboBox();
    }
    
    
    /**
     * This method allows to see users' table view by interacting with login 
     * view.
     */
    @Test
    public void testA_InicioVentana() {
        clickOn("#txtFieldUsuario");
        write("username");
        clickOn("#pswFieldContrasena");
        write("password");
        clickOn("#btnEntrar");
        
        //iteracion Menu
        
        verifyThat("#visitorsPane", isVisible());
    }
    
    /**
     * This method allows to see users' table view by interacting with login 
     * view.
     */
    @Test
    public void testB_InitStage() {
        verifyThat("#txtBuscar", hasText(""));
        verifyThat("#btnBuscar", isDisabled());
        verifyThat("#cbxBuscar", isVisible());
        verifyThat("#datePicker", hasText(""));
        verifyThat("#colEmail", isVisible());
        verifyThat("#colDni", isVisible());
        verifyThat("#colFullName", isVisible());
        verifyThat("#colVisitDate", isVisible());
        verifyThat("#colVisited", isVisible());
        verifyThat("#colVisitReply", isVisible());
        verifyThat("#btnBorrar", isDisabled());
    }
    
    /**
     * Verificar que el boton buscar esta habilitado.
     */
    @Test
    public void testC_ButtonBuscarEnabled() {
        clickOn("#txtBuscar");
        write("username");
        clickOn("#cbxBuscar");
        clickOn("Todos");
        verifyThat("#btnBuscar", isEnabled());
    }
    
    /**
     * Verificar que el boton buscar esta habilitado.
     */
    @Test
    public void testD_BuscarValidations() {
        clickOn("#txtBuscar");
        write("username");
        clickOn("#cbxBuscar");
        clickOn("Todos");
        clickOn("btnBuscar");
        verifyThat();

        
    }
    
    /**
    * 
    */
    @Test
    public void testE_DatePicker() {

    }
    
    /**
    * 
    */
    @Test
    public void testF_ButtonBorrarAceptar() {

    }
    
        /**
    * 
    */
    @Test
    public void testG_ButtonBorrarCancelar() {

    }
    
    /**
    * 
    */
    @Test
    public void testH_EditableTable() {

    }
    
    
    
}
