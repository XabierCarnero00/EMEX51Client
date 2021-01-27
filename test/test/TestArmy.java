/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import emex51crudclient.EMEX51CRUDClient;
import java.util.concurrent.TimeoutException;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
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
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.matcher.control.TableViewMatchers;

/**
 *
 * @author xabig
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestArmy extends ApplicationTest {

    TableView table;
    ComboBox combo;

    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(EMEX51CRUDClient.class);
    }

    public TestArmy() {

    }

    /**
     * Open the Army Window.
     */
    @Test
    public void PreTest() {
        sleep(500);
        Node row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        clickOn("#btnIr");
    }

    /**
     * Test of initial state of Army Window.
     */
    @Test
    public void TestA() {
        sleep(500);
        verifyThat("#buttonAniadir", isDisabled());
        verifyThat("#buttonBorrar", isDisabled());
        verifyThat("#buttonBuscar", isDisabled());

        verifyThat("#textfieldBuscar", isEnabled());
        verifyThat("#textfieldMunicion", isEnabled());
        verifyThat("#textfieldNombre", isEnabled());
        verifyThat("#textfieldBuscar", hasText(""));
        verifyThat("#textfieldMunicion", hasText(""));
        verifyThat("#textfieldNombre", hasText(""));

        verifyThat("#comboBox", isEnabled());

        verifyThat("#labelError", LabeledMatchers.hasText(""));

        verifyThat("#datePickerAniadir", isEnabled());
        verifyThat("#datePicker", isEnabled());
    }

    /**
     * Test of what happens when you change the ComboBox.
     */
    @Test
    public void TestB() {
        clickOn("#comboBox");
        clickOn("Todos");

        //verifyThat("#comboBox", LabeledMatchers.hasText("Todos"));
        verifyThat("#buttonBuscar", isEnabled());

        verifyThat("#textfieldBuscar", isDisabled());
        verifyThat("#textfieldBuscar", hasText(""));
    }

    /**
     * Tests that when you click on a TableView Item Button Borrar is Enabled.
     */
    @Test
    public void TestC() {
        Node row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        verifyThat("#buttonBorrar", isEnabled());
    }

    /**
     * Tests that the table is editable.
     */
    @Test
    public void TestD() {

    }

    /**
     * Tests when the Button Añadir is Enabled.
     */
    @Test
    public void TestE() {
        verifyThat("#buttonAniadir", isDisabled());
        clickOn("#textfieldNombre");
        write("aaaa");
        verifyThat("#buttonAniadir", isDisabled());
        clickOn("#textfieldMunicion");
        write("1111");
        verifyThat("#buttonAniadir", isEnabled());
    }

    /**
     * Tests what happens when you click on the Button Añadir.
     */
    @Test
    public void TestF() {
        clickOn("#buttonAniadir");
        verifyThat("Aceptar", NodeMatchers.isVisible());
        verifyThat("Cancelar", NodeMatchers.isVisible());
        clickOn("Aceptar");

        verifyThat("#labelError", LabeledMatchers.hasText("Introduce una fecha para poder añadir"));

        //Click en el DatePicker
        clickOn(1150, 630);
        clickOn("30");

        clickOn("#textfieldNombre");
        write("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        doubleClickOn("#textfieldMunicion");
        write("aaaa");

        clickOn("#buttonAniadir");
        verifyThat("#labelError", LabeledMatchers.hasText("El campo Nombre debe tener menos caracteres"));

        doubleClickOn("#textfieldNombre");
        write("aaaa");

        clickOn("#buttonAniadir");
        verifyThat("#labelError", LabeledMatchers.hasText("El campo Municion debe tener contener numeros"));

        doubleClickOn("#textfieldMunicion");
        write("1111");

        table = lookup("#tableView").queryTableView();
        int totalRows = table.getItems().size();

        clickOn("#buttonAniadir");
        verifyThat("Aceptar", NodeMatchers.isVisible());
        verifyThat("Cancelar", NodeMatchers.isVisible());
        clickOn("Aceptar");

        verifyThat(table, TableViewMatchers.hasNumRows(totalRows + 1));

        totalRows = table.getItems().size();

        clickOn("#textfieldNombre");
        write("aaaa");
        clickOn("#textfieldMunicion");
        write("1111");
        //Click en el DatePicker
        clickOn(1150, 630);
        clickOn("30");

        clickOn("#buttonAniadir");
        verifyThat("Aceptar", NodeMatchers.isVisible());
        verifyThat("Cancelar", NodeMatchers.isVisible());
        clickOn("Aceptar");

        verifyThat("#labelError", LabeledMatchers.hasText("The Army you want to Create alredy exists"));
        verifyThat(table, TableViewMatchers.hasNumRows(totalRows));
    }

    /**
     * Test what happens qhen you modify the field Municion of the TableView.
     */
    @Test
    public void TestG() {
        table = lookup("#tableView").queryTableView();
        int totalRows = table.getItems().size();
        Node node = lookup("#tableColumnNombre").nth(table.getItems().size()).query();
        doubleClickOn(node);
        write("bbbb");
        press(KeyCode.ENTER);

        doubleClickOn("#textfieldNombre");
        write("bbbb");
        doubleClickOn("#textfieldMunicion");
        write("1111");

        clickOn("#buttonAniadir");
        verifyThat("Aceptar", NodeMatchers.isVisible());
        verifyThat("Cancelar", NodeMatchers.isVisible());
        clickOn("Aceptar");

        verifyThat("#labelError", LabeledMatchers.hasText("The Army you want to Create alredy exists"));
        verifyThat(table, TableViewMatchers.hasNumRows(totalRows));
    }

    /**
     * Test what happens qhen you modify the field Municion of the TableView.
     */
    @Test
    public void TestH() {
        table = lookup("#tableView").queryTableView();
        int totalRows = table.getItems().size();
        Node node = lookup("#tableColumnMunicion").nth(table.getItems().size()).query();
        doubleClickOn(node);
        write("2222");
        press(KeyCode.ENTER);

        doubleClickOn(node);
        this.push(KeyCode.CONTROL, KeyCode.C);

        doubleClickOn("#textfieldMunicion");
        this.push(KeyCode.CONTROL, KeyCode.V);
        verifyThat("#textfieldMunicion", hasText("2222"));

    }

    /**
     * Tests what happens when you click on the Button Borrar.
     */
    @Test
    public void TestI() {
        table = lookup("#tableView").queryTableView();
        int totalRow = table.getItems().size();
        Node row = lookup(".table-row-cell").nth(totalRow - 1).query();
        clickOn(row);

        clickOn("#buttonBorrar");
        verifyThat("Aceptar", NodeMatchers.isVisible());
        verifyThat("Cancelar", NodeMatchers.isVisible());
        clickOn("Cancelar");

        verifyThat(table, TableViewMatchers.hasNumRows(totalRow));

        clickOn("#buttonBorrar");
        verifyThat("Aceptar", NodeMatchers.isVisible());
        verifyThat("Cancelar", NodeMatchers.isVisible());
        clickOn("Aceptar");

        verifyThat(table, TableViewMatchers.hasNumRows(totalRow - 1));

    }

    /**
     * Test of what happens when you click at Button Buscar.
     */
    @Test
    public void TestJ() {
        table = lookup("#tableView").queryTableView();
        clickOn("#comboBox");
        clickOn("Municion");
        clickOn("#textfieldBuscar");
        write("123456789012345678901234567890123456789012345678901234567890");
        clickOn("#buttonBuscar");
        verifyThat("#labelError", LabeledMatchers.hasText("Demasiados caracteres en el campo Buscar"));
        verifyThat("#textfieldBuscar", hasText(""));

        clickOn("#comboBox");
        clickOn("Todos");
        //verifyThat("#comboBox", LabeledMatchers.hasText("Todos"));
        clickOn("#buttonBuscar");
        verifyThat(table, TableViewMatchers.hasNumRows(8));

        clickOn("#comboBox");
        //clickOn("Nombre");
        this.push(KeyCode.UP);
        this.push(KeyCode.UP);
        this.push(KeyCode.ENTER);
        //verifyThat("#comboBox", LabeledMatchers.hasText("Nombre"));
        clickOn("#textfieldBuscar");
        write("Helicoptero");
        clickOn("#buttonBuscar");
        verifyThat(table, TableViewMatchers.hasNumRows(2));

        clickOn("#comboBox");
        clickOn("Municion");
        //verifyThat("#comboBox", LabeledMatchers.hasText("Municion"));
        doubleClickOn("#textfieldBuscar");
        write("aaaaa");
        clickOn("#buttonBuscar");
        verifyThat("#labelError", LabeledMatchers.hasText("Introduce numeros en el campo Buscar"));
        doubleClickOn("#textfieldBuscar");
        write("12000");
        verifyThat("#labelError", LabeledMatchers.hasText(""));
        clickOn("#buttonBuscar");
        verifyThat(table, TableViewMatchers.hasNumRows(3));
    }

    /**
     * Test of what happens when you click at Buscar DatePicker.
     */
    @Test
    public void TestK() {
        //Click on data picker
        clickOn(1045, 300);
        clickOn("20");
        table = lookup("#tableView").queryTableView();
        verifyThat(table, TableViewMatchers.hasNumRows(3));
    }

    /**
     * Tests that asks before closing the Window and then closes it.
     */
    @Test
    public void TestL() {
        verifyThat("#paneArmy", NodeMatchers.isVisible());
        this.push(KeyCode.ALT, KeyCode.F4);
        verifyThat("Aceptar", NodeMatchers.isVisible());
        verifyThat("Cancelar", NodeMatchers.isVisible());
        clickOn("Cancelar");

        verifyThat("#paneArmy", NodeMatchers.isVisible());
        
        this.push(KeyCode.ALT, KeyCode.F4);
        verifyThat("Aceptar", NodeMatchers.isVisible());
        verifyThat("Cancelar", NodeMatchers.isVisible());
        clickOn("Aceptar");
        
        verifyThat("#paneArmy", NodeMatchers.isNull());
    }
}
