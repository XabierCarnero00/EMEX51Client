/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import controller.SignInController;
import emex51crudclient.EMEX51CRUDClient;
import java.awt.Button;
import java.awt.TextField;
import java.util.concurrent.TimeoutException;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import org.testfx.matcher.control.TableViewMatchers;
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
 @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(EMEX51CRUDClient.class);
    }
    
    /**
     * class empty constructor
     */
    
    public FXMLVisitorControllerIT(){
        
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
        verifyThat("#btnBuscar", isEnabled());
    }
    
    /**
     * Verificar que la busqueda se hacer correctamente
     */
    @Test
    public void testE_BuscarTodos() {
        tblVisitors = lookup("#tblVisitors").queryTableView();
        clickOn("#cbxBuscar");
        clickOn("Todos");
        clickOn("#txtBuscar");
        write("a");
        verifyThat(tblVisitors, TableViewMatchers.hasNumRows(10));
    }
    
    /**
     * Verificar que la busqueda se hacer correctamente
     */
    @Test
    public void testF_BuscarNombre() {
        tblVisitors = lookup("#tblVisitors").queryTableView();
        clickOn("#cbxBuscar");
        clickOn("Nombre");
        clickOn("#txtBuscar");
        write("Asier");
        verifyThat(tblVisitors, TableViewMatchers.hasNumRows(1));
    }
    
    /**
    * Test to verify the datePicker
    */
    @Test
    public void testG_DatePicker() {
        clickOn(366, 144);
        clickOn("23");
        tblVisitors = lookup("#tblVisitors").queryTableView();
        verifyThat(tblVisitors, TableViewMatchers.hasNumRows(3));
    }
    
    /**
     * Tests that when you click on a TableView Item Button Borrar is Enabled.
     */
    @Test
    public void TestH_ButtonBorrarEnabled() {
        Node row = lookup(".tblVisitors-row-cell").nth(0).query();
        clickOn(row);
        verifyThat("#btnBorrar", isEnabled());
    }
 
    /**
    * Tests that when you click Button Borrar and then on the alert cancelar nothing happens.
    */
    @Test
    public void testI_ButtonBorrarCancelar() {        
        tblVisitors = lookup("#tblVisitors").queryTableView();
        int totalRow = tblVisitors.getItems().size();
        Node row = lookup(".tblVisitors-row-cell").nth(totalRow - 1).query();
        clickOn(row);
        clickOn("#btnBorrar");
        verifyThat("Cancelar", NodeMatchers.isVisible());
        clickOn("Cancelar");
        verifyThat(tblVisitors, TableViewMatchers.hasNumRows(totalRow));
    }
    
    /**
    * Tests that when you click Button Borrar and then on the alert Aceptar the action happens.
    */
    @Test
    public void testJ_ButtonBorrarAceptar() {        
        tblVisitors = lookup("#tblVisitors").queryTableView();
        int totalRow = tblVisitors.getItems().size();
        Node row = lookup(".tblVisitors-row-cell").nth(totalRow - 1).query();
        clickOn(row);
        clickOn("#btnBorrar");
        verifyThat("Aceptar", NodeMatchers.isVisible());
        clickOn("Aceptar");
        verifyThat(tblVisitors, TableViewMatchers.hasNumRows(totalRow - 1));

    }
    
    /**
    * Test that the table is editable 
    */
    //@Test
    public void testK_EditableTable() {
         doubleClickOn("#colEmail");
         write("editable");
    }
    
    
    
}
