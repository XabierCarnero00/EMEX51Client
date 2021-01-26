/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import emex51crudclient.EMEX51CRUDClient;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import model.Creature;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
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
    /**
     * Starts application to be tested.
     * @param stage Primary Stage object
     * @throws Exception If there is any error
     */
    @Override
    public void start(Stage stage) throws Exception{
        new EMEX51CRUDClient().start(stage);
    }
    public CreatureManagementControllerIT() {
    }
    /**
     * Test road to the window
     */
 /*   @Test
    public void testA_caminoAVentana() {
        clickOn("#txtFieldUsuario");
        write("endikau22");
        clickOn("#pswFieldContrasena");
        write("abcd");
        clickOn("#btnEntrar");
        //Comprobar que la tabla no está vacía
        tableSectores = lookup("#tbSectores").queryTableView();
        assertNotEquals("Table has no data: Cannot test.",tableSectores.getItems().size(),0);
        //Clickar en la primera fila de la tabla
        Node row = lookup(".table-row-cell").nth(4).query();
        clickOn(row);
        clickOn("#btnIr");
        verifyThat("#creaturePane", isVisible());
    }*/
    /**
     * Test initial state of the components.
     */
   /* @Test
    public void testB_estadoVentanaInicial() {
        clickOn("#txtFieldUsuario");
        write("endikau22");
        clickOn("#pswFieldContrasena");
        write("abcd");
        clickOn("#btnEntrar");
        //Comprobar que la tabla no está vacía
        tableSectores = lookup("#tbSectores").queryTableView();
        assertNotEquals("Table has no data: Cannot test.",tableSectores.getItems().size(),0);
        //Clickar en la primera fila de la tabla
        Node row = lookup(".table-row-cell").nth(4).query();
        clickOn(row);
        clickOn("#btnIr");
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
    } */
    /**
     * Test button modificar, anadir are enabled
     */
/*    @Test
    public void testC_botonesEnabledAnadirModificar() {
        clickOn("#txtFieldUsuario");
        write("endikau22");
        clickOn("#pswFieldContrasena");
        write("abcd");
        clickOn("#btnEntrar");
        //Comprobar que la tabla no está vacía
        tableSectores = lookup("#tbSectores").queryTableView();
        assertNotEquals("Table has no data: Cannot test.",tableSectores.getItems().size(),0);
        //Clickar en la primera fila de la tabla
        Node row = lookup(".table-row-cell").nth(4).query();
        clickOn(row);
        clickOn("#btnIr");
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
    }*/
    /**
     * Tests the behaviour of the textFields when a table row is selected and later deselected.
     */
 /*   @Test
    public void testD_SeleccionDeseleccionTabla(){
        clickOn("#txtFieldUsuario");
        write("endikau22");
        clickOn("#pswFieldContrasena");
        write("abcd");
        clickOn("#btnEntrar");
        //Comprobar que la tabla no está vacía
        tableSectores = lookup("#tbSectores").queryTableView();
        assertNotEquals("Table has no data: Cannot test.",tableSectores.getItems().size(),0);
        //Clickar en la primera fila de la tabla
        Node row = lookup(".table-row-cell").nth(4).query();
        clickOn(row);
        clickOn("#btnIr");

        int rowCount = tableSectores.getItems().size();
        //Es una notEquals si hay info es true y sigue si son iguales rowCount y 0 salta el mensaje de error
        assertNotEquals("Table has no data: Cannot test.", rowCount,0);
        //Coger el valor de la primera fila para seleccionarlo
        row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        //Guardar en unaa criatura los datos
        tableCreature = lookup("#tbCreature").queryTableView();
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
    }*/
    /**
     * Tests the button limpiar. This button when clicked empties the textFields.
     */
 /*   @Test
    public void testE_BotonLimpiar(){
        clickOn("#txtFieldUsuario");
        write("endikau22");
        clickOn("#pswFieldContrasena");
        write("abcd");
        clickOn("#btnEntrar");
        //Comprobar que la tabla no está vacía
        tableSectores = lookup("#tbSectores").queryTableView();
        assertNotEquals("Table has no data: Cannot test.",tableSectores.getItems().size(),0);
        //Clickar en la primera fila de la tabla
        Node row = lookup(".table-row-cell").nth(4).query();
        clickOn(row);
        clickOn("#btnIr");

        int rowCount = tableSectores.getItems().size();
        //Es una notEquals si hay info es true y sigue si son iguales rowCount y 0 salta el mensaje de error
        assertNotEquals("Table has no data: Cannot test.", rowCount,0);
        //Coger el valor de la primera fila para seleccionarlo
        row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        //Guardar en unaa criatura los datos
        tableCreature = lookup("#tbCreature").queryTableView();
        Creature selectedCreature = (Creature) tableCreature.getSelectionModel().getSelectedItem();
        int selectedIndex = tableCreature.getSelectionModel().getSelectedIndex();
        //Comprobar que los botones ir y borrar están habilitados.
        verifyThat("#txtFieldNombre", hasText(selectedCreature.getName()));
        verifyThat("#txtFieldEspecie",hasText(selectedCreature.getSpecies()));
        
        //Ahora deseleccionar la fila se hace con control + click.
        clickOn("#btnLimpiar");
        //Comprobar que los textfields están vacíos..
        verifyThat("#txtFieldNombre", hasText(""));
        verifyThat("#txtFieldEspecie",hasText(""));      
    }*/
    /**
     * Tests the button volver, when clicked the sector scene is shown.
     */
 /*   @Test
    public void testF_BotonVolver(){
        clickOn("#txtFieldUsuario");
        write("endikau22");
        clickOn("#pswFieldContrasena");
        write("abcd");
        clickOn("#btnEntrar");
        //Comprobar que la tabla no está vacía
        tableSectores = lookup("#tbSectores").queryTableView();
        assertNotEquals("Table has no data: Cannot test.",tableSectores.getItems().size(),0);
        //Clickar en la primera fila de la tabla
        Node row = lookup(".table-row-cell").nth(4).query();
        clickOn(row);
        clickOn("#btnIr");

        clickOn("#btnVolver");
        clickOn("Aceptar");
        verifyThat("#sectorsPane",isVisible());
    }*/
    /**
     * Tests a creature update is recorded in the table.
     */
 /*   @Test
    public void testG_Modificar(){
        clickOn("#txtFieldUsuario");
        write("endikau22");
        clickOn("#pswFieldContrasena");
        write("abcd");
        clickOn("#btnEntrar");
        //Comprobar que la tabla no está vacía
        tableSectores = lookup("#tbSectores").queryTableView();
        assertNotEquals("Table has no data: Cannot test.",tableSectores.getItems().size(),0);
        //Clickar en la primera fila de la tabla
        Node row = lookup(".table-row-cell").nth(4).query();
        clickOn(row);
        clickOn("#btnIr");

        int rowCount = tableSectores.getItems().size();
        //Es una notEquals si hay info es true y sigue si son iguales rowCount y 0 salta el mensaje de error
        assertNotEquals("Table has no data: Cannot test.", rowCount,0);
        //Coger el valor de la primera fila para seleccionarlo
        row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        //Guardar en unaa criatura los datos
        tableCreature = lookup("#tbCreature").queryTableView();
        Creature selectedCreature = (Creature) tableCreature.getSelectionModel().getSelectedItem();
        int selectedIndex = tableCreature.getSelectionModel().getSelectedIndex();
        //Comprobar que los botones ir y borrar están habilitados.
        verifyThat("#txtFieldNombre", hasText(selectedCreature.getName()));
        verifyThat("#txtFieldEspecie",hasText(selectedCreature.getSpecies()));
        
        doubleClickOn("#txtFieldEspecie");
        write("Unicorn");
        String especie = "Unicorn";
        //Modificar la especie.
        clickOn("#btnModificar");  
        clickOn("Aceptar");
        //Coger el valor de la primera fila para seleccionarlo
        row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        selectedCreature = (Creature) tableCreature.getSelectionModel().getSelectedItem();
        assertEquals("El valor no coincide no se ha actualizado.",selectedCreature.getSpecies(),especie);
    }*/
    /**
     * Tests a creature update by an existing name.
     */
  /*  @Test
    public void testH_ModificarNombreExistenteError(){
        clickOn("#txtFieldUsuario");
        write("endikau22");
        clickOn("#pswFieldContrasena");
        write("abcd");
        clickOn("#btnEntrar");
        //Comprobar que la tabla no está vacía
        tableSectores = lookup("#tbSectores").queryTableView();
        assertNotEquals("Table has no data: Cannot test.",tableSectores.getItems().size(),0);
        //Clickar en la primera fila de la tabla
        Node row = lookup(".table-row-cell").nth(4).query();
        clickOn(row);
        clickOn("#btnIr");

        int rowCount = tableSectores.getItems().size();
        //Es una notEquals si hay info es true y sigue si son iguales rowCount y 0 salta el mensaje de error
        assertNotEquals("Table has no data: Cannot test.", rowCount,0);
        //Coger el valor de la primera fila para seleccionarlo
        row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        //Guardar en unaa criatura los datos
        tableCreature = lookup("#tbCreature").queryTableView();
        Creature selectedCreature = (Creature) tableCreature.getSelectionModel().getSelectedItem();
        int selectedIndex = tableCreature.getSelectionModel().getSelectedIndex();
        //Comprobar que los botones ir y borrar están habilitados.
        verifyThat("#txtFieldNombre", hasText(selectedCreature.getName()));
        verifyThat("#txtFieldEspecie",hasText(selectedCreature.getSpecies()));
        
        doubleClickOn("#txtFieldNombre");
        write("Kyrie");
        String nombre = "Kyrie";
        //Modificar la especie.
        clickOn("#btnModificar");  
        clickOn("Cerrar");
        //Coger el valor de la primera fila para seleccionarlo
        row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        selectedCreature = (Creature) tableCreature.getSelectionModel().getSelectedItem();
        assertEquals("El valor coincide se ha actualizado.",nombre,selectedCreature.getName());
    }*/
    /**
     * Tests a creature create.
     */
 /*   @Test
    public void testI_Anadir(){
        clickOn("#txtFieldUsuario");
        write("endikau22");
        clickOn("#pswFieldContrasena");
        write("abcd");
        clickOn("#btnEntrar");
        //Comprobar que la tabla no está vacía
        tableSectores = lookup("#tbSectores").queryTableView();
        assertNotEquals("Table has no data: Cannot test.",tableSectores.getItems().size(),0);
        //Clickar en la primera fila de la tabla
        Node row = lookup(".table-row-cell").nth(4).query();
        clickOn(row);
        clickOn("#btnIr");
        //Guardar en unaa criatura los datos
        tableCreature = lookup("#tbCreature").queryTableView();
        int rowCount = tableCreature.getItems().size();
        
        doubleClickOn("#txtFieldNombre");
        write("Killer Joe");
        doubleClickOn("#txtFieldEspecie");
        write("Seal");        
        
        clickOn("#btnAnadir");
        clickOn("Cerrar");

        //Es una notEquals si hay info es true y sigue si son iguales rowCount y 0 salta el mensaje de error
        //assertNotEquals("Table has no data: Cannot test.", rowCount+1,tableCreature.getItems().size());
    }*/
    /**
     * Tests a search by especie.
     */
    @Test
    public void testJ_BuscarEspecie(){
        clickOn("#txtFieldUsuario");
        write("endikau22");
        clickOn("#pswFieldContrasena");
        write("abcd");
        clickOn("#btnEntrar");
        //Comprobar que la tabla no está vacía
        tableSectores = lookup("#tbSectores").queryTableView();
        assertNotEquals("Table has no data: Cannot test.",tableSectores.getItems().size(),0);
        //Clickar en la primera fila de la tabla
        Node row = lookup(".table-row-cell").nth(4).query();
        clickOn(row);
        clickOn("#btnIr");
        //Guardar en unaa criatura los datos
        tableCreature = lookup("#tbCreature").queryTableView();
        int rowCount = tableCreature.getItems().size();
        assertNotEquals("Table has no data: Cannot test.", rowCount,0);
        
        doubleClickOn("#txtFieldBuscar");
        write("Kevin");
        String valor = "Kevin";
        ComboBox cbBuscar = lookup("#cmbBuscar").queryComboBox();
        clickOn("#cmbBuscar");
        clickOn("Nombre");
        clickOn("#btnBuscar");
        rowCount = tableCreature.getItems().size();
        //Es una notEquals si hay info es true y sigue si son iguales rowCount y 0 salta el mensaje de error
        assertNotEquals("Table has no data: Cannot test.", rowCount,0);
        //Coger la primera fila
        row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        Creature creatureSelected = (Creature) tableCreature.getSelectionModel().getSelectedItem();
        verifyThat("#txtFieldBuscar",hasText(creatureSelected.getName()));
        clickOn("#btnBuscar");        
    }
}
