/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import emex51crudclient.EMEX51CRUDClient;
import java.util.concurrent.TimeoutException;
import javafx.scene.control.TableView;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import model.Creature;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.testfx.api.FxToolkit;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;

/**
 *Testing class for Creature management controller. Tests using TestFX framework.
 * @author Endika Ubierna Lopez.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CreatureManagementControllerIT extends ApplicationTest{
    private TableView tableCreature;
    private TableView tableSectores;
    private ComboBox cmbBuscar;
    
   @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(EMEX51CRUDClient.class);
    }
    public CreatureManagementControllerIT() {
    }
    /**
     * Test road to the window
     */
    @Test
    public void testA_caminoAVentana() {
        clickOn("#txtFieldUsuario");
        write("endikau22");
        clickOn("#pswFieldContrasena");
        write("abcd");
        clickOn("#btnEntrar");
        clickOn("#menu");
        clickOn("#ItemSectores");
        //Comprobar que la tabla no está vacía
        tableSectores = lookup("#tbSectores").queryTableView();
        assertNotEquals("Table has no data: Cannot test.",tableSectores.getItems().size(),0);
        //Clickar en la primera fila de la tabla
        Node row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        clickOn("#btnIr");
    }
    /**
     * Test initial state of the components.
     */
    @Test
    public void testB_estadoVentanaInicial() {
        verifyThat("#txtFieldNombre", hasText(""));
        verifyThat("#txtFieldEspecie", hasText(""));
        verifyThat("#txtFieldBuscar", hasText(""));
        verifyThat("#tbCreature", isVisible());
        verifyThat("#colNombre", isVisible());
        verifyThat("#colEspecie", isVisible());
        verifyThat("#colFecha", isVisible());
        verifyThat("#cmbBuscar", isVisible());
        verifyThat("#btnBuscar", isEnabled());
        verifyThat("#btnModificar", isDisabled());
        verifyThat("#btnAnadir", isDisabled());
        verifyThat("#btnVolver", isEnabled());  
    }
    /**
     * Test button anadir is enabled when the textfields are not empty. Also lokk at the button modificar 
     * this button is only active when a table row is selected.
     */
    @Test
    public void testC_botonesEnabledAnadir() {
        clickOn("#txtFieldNombre");
        write("Embiid");
        clickOn("#txtFieldEspecie");
        write("Embiid");
        verifyThat("#btnAnadir", isEnabled());
        verifyThat("#btnModificar", isDisabled());
    }
    /**
     * Tests the behaviour of the textFields when a table row is selected and later deselected.
     */
    @Test
    public void testD_SeleccionDeseleccionTabla(){
        tableCreature = lookup("#tbCreature").queryTableView();
        int rowCount = tableCreature.getItems().size();
        //Es una notEquals si hay info es true y sigue si son iguales rowCount y 0 salta el mensaje de error
        assertNotEquals("Table has no data: Cannot test.", rowCount,0);
        //Coger el valor de la primera fila para seleccionarlo
        
        Node row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        //Guardar en una criatura los datos

        Creature selectedCreature = (Creature) tableCreature.getSelectionModel().getSelectedItem();
        int selectedIndex = tableCreature.getSelectionModel().getSelectedIndex();
        //Comprobar que los botones ir y borrar están habilitados.
        verifyThat("#txtFieldNombre", hasText(selectedCreature.getName()));
        verifyThat("#txtFieldEspecie",hasText(selectedCreature.getSpecies()));
        
        //Ahora deseleccionar la fila se hace con control + click.
        press(KeyCode.CONTROL);
        clickOn(row);
        release(KeyCode.CONTROL);
        //Comprobar que los textfields están vacíos..
        verifyThat("#txtFieldNombre", hasText(""));
        verifyThat("#txtFieldEspecie",hasText("")); 
        verifyThat("#btnAnadir", isDisabled());
        verifyThat("#btnModificar", isDisabled());        
    }
    /**
     * Tests the button limpiar. This button when clicked empties the textFields.
     */
    @Test
    public void testE_BotonLimpiar(){
        clickOn("#txtFieldNombre");
        write("Embiid");
        clickOn("#txtFieldEspecie");
        write("Embiid");
        //Ahora deseleccionar la fila se hace con control + click.
        clickOn("#btnLimpiar");
        //Comprobar que los textfields están vacíos..
        verifyThat("#txtFieldNombre", hasText(""));
        verifyThat("#txtFieldEspecie",hasText(""));      
    }
    /**
     * Tests a creature update is recorded in the table.
     */
    @Test
    public void testF_Modificar(){
        tableCreature = lookup("#tbCreature").queryTableView();
        int rowCount = tableCreature.getItems().size();
        //Es una notEquals si hay info es true y sigue si son iguales rowCount y 0 salta el mensaje de error
        assertNotEquals("Table has no data: Cannot test.", rowCount,0);
        //Coger el valor de la primera fila para seleccionarlo
        Node row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        //Guardar en unaa criatura los datos
        Creature selectedCreature = (Creature) tableCreature.getSelectionModel().getSelectedItem();
        int selectedIndex = tableCreature.getSelectionModel().getSelectedIndex();
        //Comprobar que los botones ir y borrar están habilitados y está bien informado.
        verifyThat("#txtFieldNombre", hasText(selectedCreature.getName()));
        verifyThat("#txtFieldEspecie",hasText(selectedCreature.getSpecies()));
        
        doubleClickOn("#txtFieldEspecie");
        write("");
        write("Rex");
        String especie = "Rex";
        //Modificar la especie.
        clickOn("#btnModificar");  
        clickOn("Aceptar");
        //Coger el valor de la primera fila para seleccionarlo
        row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        selectedCreature = (Creature) tableCreature.getSelectionModel().getSelectedItem();
        assertEquals("El valor no coincide no se ha actualizado.",selectedCreature.getSpecies(),especie);
    }
    /**
     * Tests a creature update by an existing name.
     */
    @Test
    public void testG_ModificarNombreExistenteError(){
        tableCreature = lookup("#tbCreature").queryTableView();
        int rowCount = tableCreature.getItems().size();
        //Es una notEquals si hay info es true y sigue si son iguales rowCount y 0 salta el mensaje de error
        assertNotEquals("Table has no data: Cannot test.", rowCount,0);
        //Coger el valor de la primera fila para seleccionarlo
        Node row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        //Guardar en unaa criatura los datos   
        Creature selectedCreature = (Creature) tableCreature.getSelectionModel().getSelectedItem();
        int selectedIndex = tableCreature.getSelectionModel().getSelectedIndex();
        //Comprobar que los botones ir y borrar están habilitados.
        verifyThat("#txtFieldNombre", hasText(selectedCreature.getName()));
        verifyThat("#txtFieldEspecie",hasText(selectedCreature.getSpecies()));
        
        doubleClickOn("#txtFieldNombre");
        write("Iga");
        String nombre = "Iga";
        //Modificar la especie.
        clickOn("#btnModificar");  
        clickOn("Aceptar");
        //Coger el valor de la primera fila para seleccionarlo
        row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        selectedCreature = (Creature) tableCreature.getSelectionModel().getSelectedItem();
        assertEquals("El valor coincide se ha actualizado.",nombre,selectedCreature.getName());
    }
    /**
     * Tests a creature create.
     */
    @Test
    public void testH_Anadir(){
        //Guardar en unaa criatura los datos
        tableCreature = lookup("#tbCreature").queryTableView();
        int rowCount = tableCreature.getItems().size();
        
        doubleClickOn("#txtFieldNombre");
        write("Masahiro");
        doubleClickOn("#txtFieldEspecie");
        write("Makun");
        //Clicka en el datepicker y luego el dia 25
        clickOn(1040,580);
        clickOn("25");
        
        clickOn("#btnAnadir");
        clickOn("Cancelar");
        
        doubleClickOn("#txtFieldNombre");
        write("Masahiro");
        doubleClickOn("#txtFieldEspecie");
        write("Makun");        
        
        clickOn("#btnAnadir");
        clickOn("Aceptar");

        //Es una notEquals si hay info es true y sigue si son iguales rowCount y 0 salta el mensaje de error
        //assertNotEquals("Table has no data: Cannot test.", rowCount+1,tableCreature.getItems().size());
    }
    /**
     * Tests a search by especie.
     */
    @Test
    public void testI_BuscarPorEspecie(){
        //Guardar en unaa criatura los datos
        tableCreature = lookup("#tbCreature").queryTableView();
        int rowCount = tableCreature.getItems().size();
        assertNotEquals("Table has no data: Cannot test.", rowCount,0); 
        //Coger la especie de la primera fila
        //Coger el valor de la primera fila para seleccionarlo
        Node row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        //Guardar en unaa criatura los datos   
        Creature selectedCreature = (Creature) tableCreature.getSelectionModel().getSelectedItem();        
        doubleClickOn("#txtFieldBuscar");    
        write(selectedCreature.getSpecies());
        cmbBuscar = lookup("#cmbBuscar").queryComboBox();
        clickOn(cmbBuscar);
        clickOn(850,300);
        clickOn("#btnBuscar");
        //Coger la primera fila
        row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        selectedCreature = (Creature) tableCreature.getSelectionModel().getSelectedItem();
        //Comprobar que el valor del textfield de busqueda es igual a la especie del primer elemento de la tabla
        verifyThat("#txtFieldBuscar",hasText(selectedCreature.getSpecies()));       
    }
    /**
     * Tests a search by name.
     */
    @Test
    public void testJ_BuscarPorNombre(){
        //Guardar en unaa criatura los datos
        tableCreature = lookup("#tbCreature").queryTableView();
        int rowCount = tableCreature.getItems().size();
        assertNotEquals("Table has no data: Cannot test.", rowCount,0); 
        //Coger la especie de la primera fila
        //Coger el valor de la primera fila para seleccionarlo
        Node row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        //Guardar en unaa criatura los datos   
        Creature selectedCreature = (Creature) tableCreature.getSelectionModel().getSelectedItem();        
        doubleClickOn("#txtFieldBuscar");    
        write(selectedCreature.getName());
        cmbBuscar = lookup("#cmbBuscar").queryComboBox();
        clickOn(cmbBuscar);
        clickOn(850,275);
        clickOn("#btnBuscar");
        //Coger la primera fila
        row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        selectedCreature = (Creature) tableCreature.getSelectionModel().getSelectedItem();
        //Comprobar que el valor del textfield de busqueda es igual a la especie del primer elemento de la tabla
        verifyThat("#txtFieldBuscar",hasText(selectedCreature.getName()));    
    }
    /**
     * Tests a searching action. Searches every creature from the sector
     */
    @Test
    public void testK_BuscarTodos(){
        //Guardar en unaa criatura los datos
        tableCreature = lookup("#tbCreature").queryTableView();
        int rowCountBefore = tableCreature.getItems().size();
        assertNotEquals("Table has no data: Cannot test.", rowCountBefore,0); 

        cmbBuscar = lookup("#cmbBuscar").queryComboBox();
        clickOn(cmbBuscar);
        clickOn(850,250);
        clickOn("#btnBuscar");
        //Ahora despues de la busqueda mirar cuantas filas hay.
        int rowCountAfter = tableCreature.getItems().size();
        assertEquals("The number of rows has to be the same but is not.", rowCountAfter,rowCountBefore);
    }  
    /**
     * Test exit window operation
     */
    @Test
    public void testL_Volver(){
        clickOn("#btnVolver");
        clickOn("Aceptar");
        verifyThat("#paneSector", isVisible());
    }
}
