 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import emex51crudclient.EMEX51CRUDClient;
import java.util.concurrent.TimeoutException;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import model.Sector;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;


/**
 * Testing class for Sector view and controller. 
 * Tests sector view behavior using TestFX framework.
 * @author endika
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SectorManagementControllerIT extends ApplicationTest{
    
    private TableView tableSectores;
    private ComboBox cbTipo;

    /**
     * Set up Java FX fixture for tests. This is a general approach for using a 
     * unique instance of the application in the test.
     * @throws java.util.concurrent.TimeoutException
     */
    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(EMEX51CRUDClient.class);
   }    
    /**
     * Constructor
     */
    public SectorManagementControllerIT() {
    }
    /**
     * Tests if the sector scene is visible after an user logs in.
     */
    @Test
    public void testA_AccesoVentana() {
        clickOn("#txtFieldUsuario");
        write("endikau22");
        clickOn("#pswFieldContrasena");
        write("abcd");
        clickOn("#btnEntrar");
        clickOn("#menu");
        clickOn("#ItemSectores");
    }

    /**
     * Tests the initial state of the textField and the Buttons.
     */
     @Test
    public void testB_InicioVentana() {
        verifyThat("#txtFieldNombre", hasText(""));
        verifyThat("#btnIr", isDisabled());
        verifyThat("#btnBorrar", isDisabled());
        verifyThat("#btnAnadir", isDisabled());
        verifyThat("#tbSectores", isVisible());
        verifyThat("#colNombre", isVisible());
        verifyThat("#colTipo", isVisible());
        verifyThat("#cbTipo", isVisible());
        verifyThat("#lblUsuario", isVisible());
        verifyThat("#cbTipo", isVisible());
        verifyThat("#menuBar", isVisible());
    }

    /**
     * Tests Button anadir is enabled when name textfield is informed with a
     * length test between 2 and 20 characters.
     */
    @Test
    public void testC_BotonAnadirEnabled() {
        clickOn("#txtFieldNombre");
        write("Embiid");
        verifyThat("#btnAnadir", isEnabled());
    }
    /**
     * Tests Button anadir is enabled when name textfield is informed with a
     * length test between 2 and 20 characters. The combobox has no type
     * selected, after clicking the anadir button an error message is shown
     */
    @Test
    public void testD_BotonAnadirEnabledMensajeErrorComboNoSeleccionada() {
        clickOn("#txtFieldNombre");
        write("");
        clickOn("#txtFieldNombre");
        write("Embiid");
        clickOn("#btnAnadir");
        clickOn("Aceptar");
    }
    /**
     * Tests create sector but after the creation confirmation alert pops up the button cancel is pressed and
     * the creation is cancelled.
     */
    @Test
    public void testE_CrearSectorCancelar() {
        clickOn("#txtFieldNombre");
        write("");
        clickOn("#txtFieldNombre");
        write("Embiid");
        cbTipo = lookup("#cbTipo").queryComboBox();
        clickOn(cbTipo);
        //NO se como bajar hasta criature. la segunda opcion del combobox
        int typeCount = cbTipo.getItems().size();
        if (typeCount > 1) {
            if (cbTipo.getSelectionModel().getSelectedIndex() <1) {
                press(KeyCode.DOWN);
            } else {
                press(KeyCode.UP);
            }
        }
        clickOn("#btnAnadir");
        verifyThat("Aceptar", isVisible());
        clickOn("Aceptar");
    }

    /**
     * Tests create sector but after clicking the button cancels the creation.
     */
    @Test
    public void testF_CrearSectorNombreYaExiste() {
        clickOn("#txtFieldNombre");
        write("");
        clickOn("#txtFieldNombre");
        write("Armaggedon");
        cbTipo = lookup("#cbTipo").queryComboBox();
        clickOn("#cbTipo");
        int typeCount = cbTipo.getItems().size();
        if (typeCount > 1) {
            if (cbTipo.getSelectionModel().getSelectedIndex() < 1) {
                press(KeyCode.DOWN);
            } else {
                press(KeyCode.UP);
            }
        }
        clickOn("#btnAnadir");
        verifyThat("Aceptar", isVisible());
        clickOn("Aceptar");
    }

    /**
     * Tests create sector. Create a valid sector. The new sector is added in the table.
     */
    @Test
    public void testG_CrearSector() {
        clickOn("#txtFieldNombre");
        write("");      
        //Primero contar las filas de la tabla para que al añadir comparar si hay una mas.
        tableSectores = lookup("#tbSectores").queryTableView();
        int rowCount = tableSectores.getItems().size();

        clickOn("#txtFieldNombre");
        //Guardar el nombre del sector en una variable para comparar despues
        String nombreSector = "Reyes Catolicos";
        write(nombreSector);
        cbTipo = lookup("#cbTipo").queryComboBox();
        clickOn(cbTipo);
        int typeCount = cbTipo.getItems().size();
        if (typeCount > 1) {
            if (cbTipo.getSelectionModel().getSelectedIndex() < 1) {
                press(KeyCode.DOWN);
            }
        }
        clickOn("#btnAnadir");
        verifyThat("Aceptar",isVisible());
        clickOn("Aceptar");
        //Comprobar que hay una fila mas
        assertEquals("The sector has not been added", rowCount + 1, tableSectores.getItems().size());
        //Comprobar que los datos que ha metido están bien. Si no ha saltado el error row count es uno mas.
        rowCount = tableSectores.getItems().size();
        //Comparar el nombre de la fila añadida con el nombre que he añadido
        Node row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        Sector sector = (Sector) tableSectores.getSelectionModel().getSelectedItem();
        assertEquals("The sector name is not the one created", nombreSector, sector.getName());
    }

    /**
     * Tests Button ir and modificar are enabled when a table row is selected. And when deselected the buttons are disabled again.
     * First see if the table is not empty. 
     */
    @Test
    public void testH_BotonesIrBorrarEnabled() {
        //Comprobar que la tabla no está vacía
        tableSectores = lookup("#tbSectores").queryTableView();
        assertNotEquals("Table has no data: Cannot test.", tableSectores.getItems().size(), 0);
        //Clickar en la primera fila de la tabla
        Node row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        verifyThat("#btnIr", isEnabled());
        verifyThat("#btnBorrar", isEnabled());
        //Ahora pulsar control + click del raton para deseleccionar los botones se tienen que inhabilitar
        press(KeyCode.CONTROL);
        clickOn(row);
        release(KeyCode.CONTROL);
        verifyThat("#btnIr", isDisabled());
        verifyThat("#btnBorrar", isDisabled());        
    }
    
    /**
     * Tests updated Sector name. The new name exists. The sector name cannot be updated
     */
    @Test
    public void testI_ModificarNombreYaExistenteSector() {
        //Comprobar que la tabla no está vacía
        tableSectores = lookup("#tbSectores").queryTableView();
        assertNotEquals("Table has no data: Cannot test.", tableSectores.getItems().size(), 0);
        //Clickar en la primera fila de la tabla
        Node row = lookup(".table-row-cell").nth(0).query();
        doubleClickOn(row);
        String nombre = "Xabier";
        write(nombre);
        press(KeyCode.ENTER);
        Sector sector = (Sector) tableSectores.getSelectionModel().getSelectedItem();
        //Comparar el string con el valor guardado para ver que se ha modificado
        assertNotEquals("The sector has not been modified", nombre, sector.getName());
        clickOn("Aceptar");
    }
    /**
     * Test update name of a sector
     */
    @Test
    public void testJ_ModificarNombreSector() {
        //Comprobar que la tabla no está vacía
        tableSectores = lookup("#tbSectores").queryTableView();
        assertNotEquals("Table has no data: Cannot test.", tableSectores.getItems().size(), 0);
        //Clickar en la primera fila de la tabla
        Node row = lookup(".table-row-cell").nth(0).query();
        doubleClickOn(row);
        String nombre = "Febrero";
        write(nombre);
        press(KeyCode.ENTER);
        Sector sector = (Sector) tableSectores.getSelectionModel().getSelectedItem();
        //Comparar el string con el valor guardado para ver que se ha modificado
        assertEquals("The sector has not been modified", nombre, sector.getName());
    }
    /**
     * Tests delete sector.
     * First see if the table is not empty.
     */
    @Test
    public void testK_EliminarSector() {
        //Comprobar que la tabla no está vacía
        tableSectores = lookup("#tbSectores").queryTableView();
        int rowCount = tableSectores.getItems().size();

        assertNotEquals("Table has no data: Cannot test.", tableSectores.getItems().size(), 0);
        //Clickar en la primera fila de la tabla
        Node row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        clickOn("#btnBorrar");
        clickOn("Aceptar");

        assertEquals("The sector has not been deleted", rowCount - 1, tableSectores.getItems().size());
    }


    /**
     * Tests delete sector with content inside.
     * First see if the table is not empty.
     */
    @Test
    public void testL_EliminarSectorConContenido() {
        //Comprobar que la tabla no está vacía
        tableSectores = lookup("#tbSectores").queryTableView();
        int rowCount = tableSectores.getItems().size();

        assertNotEquals("Table has no data: Cannot test.", tableSectores.getItems().size(), 0);
        //Clickar en la primera fila de la tabla
        Node row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        clickOn("#btnBorrar");
        clickOn("Aceptar");
    }
    /**
     * Tests the behaviour of the buttons when a table row is selected and later deselected.
     */
    @Test
    public void testM_SeleccionDeseleccionTabla(){
        tableSectores = lookup("#tbSectores").queryTableView();
        int rowCount = tableSectores.getItems().size();
        //Es una notEquals si hay info es true y sigue si son iguales rowCount y 0 salta el mensaje de error
        assertNotEquals("Table has no data: Cannot test.", rowCount,0);
        //Coger el valor de la primera fila para seleccionarlo
        Node row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        //Comprobar que los botones ir y borrar están habilitados.
        verifyThat("#btnIr", isEnabled());
        verifyThat("#btnBorrar",isEnabled());
                //Ahora deseleccionar la fila se hace con control + click.
        press(KeyCode.CONTROL);
        clickOn(row);
        release(KeyCode.CONTROL);
        //Comprobar que los botones ir y borrar están deshabilitados.
        verifyThat("#btnIr", isDisabled());
        verifyThat("#btnBorrar",isDisabled());   
    }   
}
