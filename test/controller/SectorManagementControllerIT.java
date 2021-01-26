/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import emex51crudclient.EMEX51CRUDClient;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import model.Sector;
import org.junit.Assert;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;

/**
 *
 * @author endika
 */
public class SectorManagementControllerIT  extends ApplicationTest{
    
    private TableView tableSectores;
    private ComboBox cbTipo;
    /**
     * Starts application to be tested.
     * @param stage Primary Stage object
     * @throws Exception If there is any error
     */
    @Override
    public void start(Stage stage) throws Exception{
        new EMEX51CRUDClient().start(stage);
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
        verifyThat("#sectorsPane", isVisible());
    }

    /**
     * Tests the initial state of the textField and the Buttons.
     */
    @Test
    public void testB_InicioVentana() {
        verifyThat("#sectorsPane", isVisible());
        verifyThat("#txtFieldNombre", hasText(""));
        verifyThat("#btnIr", isDisabled());
        verifyThat("#btnBorrar", isDisabled());
        verifyThat("#btnAnadir", isDisabled());
        verifyThat("#tbSectores", isVisible());
        verifyThat("#colNombre", isVisible());
        verifyThat("#colTipo", isVisible());
        verifyThat("#cbTipo", isVisible());
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
    public void testF_BotonAnadirEnabledMensajeErrorComboNoSeleccionada() {
        clickOn("#txtFieldNombre");
        write("Embiid");
        verifyThat("#btnAnadir", isEnabled());
        verifyThat("Cerrar", NodeMatchers.isVisible());
    }

    /**
     * Tests Button ir and modificar are enabled when a table row is selected.
     * First see if the table is not empty.
     */
    @Test
    public void testD_BotonesIrBorrarEnabled() {
        //Comprobar que la tabla no está vacía
        tableSectores = lookup("#tbSectores").queryTableView();
        assertNotEquals("Table has no data: Cannot test.", tableSectores.getItems().size(), 0);
        //Clickar en la primera fila de la tabla
        Node row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        verifyThat("#btnIr", isEnabled());
        verifyThat("#btnBorrar", isEnabled());
    }

    /**
     * Tests create sector but after clicking the button cancels the creation.
     */
    @Test
    public void testE_CrearSectorCancelar() {
        clickOn("#txtFieldNombre");
        write("Embiid");
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
        verifyThat("Aceptar", NodeMatchers.isVisible());
        clickOn("Aceptar");
    }

    /**
     * Tests create sector but after clicking the button cancels the creation.
     */
    @Test
    public void testF_CrearSectorNombreYaExiste() {
        doubleClickOn("#txtFieldNombre");
        write("Tenistas");
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
        verifyThat("Cerrar", NodeMatchers.isVisible());
        clickOn("Cerrar");
    }

    /**
     * Tests updated Sector.
     */
    @Test
    public void testG_ModificarSector() {
        //Comprobar que la tabla no está vacía
        tableSectores = lookup("#tbSectores").queryTableView();
        assertNotEquals("Table has no data: Cannot test.", tableSectores.getItems().size(), 0);
        //Clickar en la primera fila de la tabla
        Node row = lookup(".table-row-cell").nth(0).query();
        doubleClickOn(row);
        Sector sector = (Sector) tableSectores.getSelectionModel().getSelectedItem();
        System.out.println(sector.getName());
        String nombre = "Armaggedon";
        write(nombre);
        press(KeyCode.ENTER);
        //Comparar el string con el valor guardado para ver que se ha modificado
        assertEquals("The sector has not been modified", nombre, sector.getName());

    }

    /**
     * Tests delete sector.
     * First see if the table is not empty.
     */
    @Test
    public void testH_EliminarSector() {
        //Comprobar que la tabla no está vacía
        tableSectores = lookup("#tbSectores").queryTableView();
        int rowCount = tableSectores.getItems().size();

        assertNotEquals("Table has no data: Cannot test.", tableSectores.getItems().size(), 0);
        //Clickar en la primera fila de la tabla
        Node row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        clickOn("#btnBorrar");

        Assert.assertEquals("The sector has not been deleted", rowCount - 1, tableSectores.getItems().size());
    }

    /**
     * Tests create sector.
     */
    @Test
    public void testI_CrearSector() {
        //Primero contar las filas de la tabla para que al añadir comparar si hay una mas.
        tableSectores = lookup("#tbSectores").queryTableView();
        int rowCount = tableSectores.getItems().size();

        clickOn("#txtFieldNombre");
        //Guardar el nombre del sector en una variable para comparar despues
        String nombreSector = "Embiid";
        write("Embiid");
        cbTipo = lookup("#cbTipo").queryComboBox();
        clickOn("#cbTipo");
        int typeCount = cbTipo.getItems().size();
        if (typeCount > 1) {
            if (cbTipo.getSelectionModel().getSelectedIndex() < 1) {
                press(KeyCode.DOWN);
            }
        }
        clickOn("#btnAnadir");
        verifyThat("Aceptar", NodeMatchers.isVisible());
        verifyThat("Cancelar", NodeMatchers.isVisible());
        clickOn("Aceptar");
        //Comprobar que hay una fila mas
        Assert.assertEquals("The sector has not been added", rowCount + 1, tableSectores.getItems().size());

        List<Sector> sectores = tableSectores.getItems();
        assertEquals("The sector has not been added", sectores.stream().filter(s -> s.getName().equals(nombreSector)).count(), 1);
    }
    /**
     * Tests delete sector with content inside.
     * First see if the table is not empty.
     */
    @Test
    public void testJ_EliminarSectorConContenido() {
        //Comprobar que la tabla no está vacía
        tableSectores = lookup("#tbSectores").queryTableView();
        int rowCount = tableSectores.getItems().size();

        assertNotEquals("Table has no data: Cannot test.", tableSectores.getItems().size(), 0);
        //Clickar en la primera fila de la tabla
        Node row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        clickOn("#btnBorrar");
        verifyThat("Cerrar", NodeMatchers.isVisible());
    }
    /**
     * Tests the behaviour of the buttons when a table row is selected and later deselected.
     */
    @Test
    public void testk_SeleccionDeseleccionTabla(){
        int rowCount = tableSectores.getItems().size();
        //Es una notEquals si hay info es true y sigue si son iguales rowCount y 0 salta el mensaje de error
        assertNotEquals("Table has no data: Cannot test.", rowCount,0);
        //Coger el valor de la primera fila para seleccionarlo
        Node row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        //Comprobar que los botones ir y borrar están habilitados.
        verifyThat("#btnIr", isEnabled());
        verifyThat("#btnModificar",isEnabled());
        
        //Ahora deseleccionar la fila se hace con control + click.
        press(KeyCode.CONTROL);
        clickOn(row);
        release(KeyCode.CONTROL);
        //Comprobar que los botones ir y borrar están deshabilitados.
        verifyThat("#btnIr", isDisabled());
        verifyThat("#btnModificar",isDisabled());
        
    }
}
