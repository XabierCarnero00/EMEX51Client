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
import static org.testfx.matcher.base.NodeMatchers.isNull;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.matcher.control.TableViewMatchers;

/**
 *
 * @author xabig
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestEmployee extends ApplicationTest {

    TableView table;
    ComboBox combo;

    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(EMEX51CRUDClient.class);
    }

    public TestEmployee() {

    }

    /**
     * Open the Employee Window.
     */
    @Test
    public void PreTest() {
        sleep(500);
        clickOn("#txtFieldUsuario");
        write("Xabier00");
        clickOn("#pswFieldContrasena");
        write("xabier");
        clickOn("#btnEntrar");
    }

    /**
     * Test of the initial state of Employee Window.
     */
    @Test
    public void TestA() {
        verifyThat("#buttonAniadir", isDisabled());
        verifyThat("#buttonModificar", isDisabled());
        verifyThat("#buttonBorrar", isDisabled());
        verifyThat("#buttonBuscar", isDisabled());

        table = lookup("#table").queryTableView();
        verifyThat(table, TableViewMatchers.hasNumRows(4));

        verifyThat("#textfieldBuscar", isEnabled());
        verifyThat("#textfieldEmail", isEnabled());
        verifyThat("#textfieldNombApell", isEnabled());
        verifyThat("#textfieldLogin", isEnabled());
        verifyThat("#textfieldSalario", isEnabled());
        verifyThat("#textfieldTrabajo", isEnabled());
        verifyThat("#textfieldPassword", isEnabled());

        verifyThat("#textfieldBuscar", hasText(""));
        verifyThat("#textfieldEmail", hasText(""));
        verifyThat("#textfieldNombApell", hasText(""));
        verifyThat("#textfieldLogin", hasText(""));
        verifyThat("#textfieldSalario", hasText(""));
        verifyThat("#textfieldTrabajo", hasText(""));
        verifyThat("#textfieldPassword", hasText(""));

        verifyThat("#comboBox", isEnabled());

        verifyThat("#buttonLimpiar", isEnabled());

        verifyThat("#labelError", LabeledMatchers.hasText(""));
    }

    /**
     * Tests when the Button Añadir and the Button Modificar are Enabled.
     */
    @Test
    public void TestB() {
        clickOn("#textfieldEmail");
        write("aaaa");
        clickOn("#textfieldNombApell");
        write("aaaa");
        clickOn("#textfieldLogin");
        write("aaaa");
        clickOn("#textfieldSalario");
        write("aaaa");
        clickOn("#textfieldTrabajo");
        write("aaaa");

        verifyThat("#buttonModificar", isEnabled());
        verifyThat("#buttonAniadir", isDisabled());

        clickOn("#textfieldPassword");
        write("aaaa");

        verifyThat("#buttonModificar", isDisabled());
        verifyThat("#buttonAniadir", isEnabled());

    }

    /**
     * Test that the Button Limpiar clears all the TextFields.
     */
    @Test
    public void TestC() {
        clickOn("#buttonLimpiar");

        verifyThat("#textfieldEmail", hasText(""));
        verifyThat("#textfieldNombApell", hasText(""));
        verifyThat("#textfieldLogin", hasText(""));
        verifyThat("#textfieldSalario", hasText(""));
        verifyThat("#textfieldTrabajo", hasText(""));
        verifyThat("#textfieldPassword", hasText(""));
    }

    /**
     * Tests what happens when yo click the ComboBox.
     */
    @Test
    public void TestD() {
        clickOn("#comboBox");
        clickOn("Nombre");

        verifyThat("#buttonBuscar", isEnabled());
        verifyThat("#textfieldBuscar", isEnabled());

        clickOn("#comboBox");
        clickOn("Todos");

        verifyThat("#buttonBuscar", isEnabled());
        verifyThat("#textfieldBuscar", isDisabled());
        verifyThat("#textfieldBuscar", hasText(""));
    }

    /**
     * Tests what happens when you click in the Button Buscar.
     */
    @Test
    public void TestE() {
        table = lookup("#table").queryTableView();
        clickOn("#comboBox");
        clickOn("Nombre");

        verifyThat("#buttonBuscar", isEnabled());
        verifyThat("#textfieldBuscar", isEnabled());
        verifyThat("#textfieldBuscar", hasText(""));

        clickOn("#textfieldBuscar");
        write("001234567890123456789012345678901234567891234567890123456789");
        clickOn("#buttonBuscar");

        verifyThat("#textfieldBuscar", hasText(""));
        verifyThat("#labelError", LabeledMatchers.hasText("Demasiados caracteres en el campo Buscar"));

        clickOn("#textfieldBuscar");
        write("Iratxe Urrea");
        verifyThat("#labelError", LabeledMatchers.hasText(""));
        clickOn("#buttonBuscar");
        verifyThat(table, TableViewMatchers.hasNumRows(1));

        clickOn("#comboBox");
        clickOn("Email");

        clickOn("#textfieldBuscar");
        eraseText(15);
        write("gmail");
        verifyThat("#labelError", LabeledMatchers.hasText(""));
        clickOn("#buttonBuscar");

        verifyThat(table, TableViewMatchers.hasNumRows(3));

        clickOn("#comboBox");
        clickOn("Todos");
        verifyThat("#textfieldBuscar", hasText(""));
        clickOn("#buttonBuscar");

        verifyThat(table, TableViewMatchers.hasNumRows(4));

    }

    /**
     * Tests what happens when you click on an Item of the TableView.
     */
    @Test
    public void TestF() {
        Node row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);

        verifyThat("#buttonBorrar", isEnabled());
        verifyThat("#buttonModificar", isEnabled());

        verifyThat("#textfieldEmail", hasText("iratxeurrea@gmail.com"));
        verifyThat("#textfieldNombApell", hasText("Iratxe Urrea"));
        verifyThat("#textfieldLogin", hasText("Iratxe1976"));
        verifyThat("#textfieldSalario", hasText("1500.0"));
        verifyThat("#textfieldTrabajo", hasText("Encargada de Alienigenas"));
        verifyThat("#textfieldPassword", hasText(""));

    }

    /**
     * Tests what happens when you click in the Button Añadir.
     */
    @Test
    public void TestG() {
        table = lookup("#table").queryTableView();
        clickOn("#buttonLimpiar");

        clickOn("#textfieldEmail");
        write("aaaa");
        clickOn("#textfieldNombApell");
        write("12345");
        clickOn("#textfieldLogin");
        write("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        clickOn("#textfieldSalario");
        write("aaaa");
        clickOn("#textfieldTrabajo");
        write("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        clickOn("#textfieldPassword");
        write("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        clickOn("#buttonAniadir");

        verifyThat("#labelError", LabeledMatchers.hasText("Introduzca un email en el campo Email"));

        doubleClickOn("#textfieldEmail");
        write("aaaa@aaaa");
        verifyThat("#labelError", LabeledMatchers.hasText(""));
        clickOn("#buttonAniadir");

        verifyThat("#labelError", LabeledMatchers.hasText("Introduzca un email en el campo Email"));

        clickOn("#textfieldEmail");
        write(".com");
        verifyThat("#labelError", LabeledMatchers.hasText(""));
        clickOn("#buttonAniadir");

        verifyThat("#labelError", LabeledMatchers.hasText("El campo Nombre y Apellidos solo debe de contener letras"));

        doubleClickOn("#textfieldNombApell");
        write("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        verifyThat("#labelError", LabeledMatchers.hasText(""));
        clickOn("#buttonAniadir");

        verifyThat("#labelError", LabeledMatchers.hasText("Demasiados caracteres en el campo Nombre y Apellidos"));

        doubleClickOn("#textfieldNombApell");
        write("aaaa");
        verifyThat("#labelError", LabeledMatchers.hasText(""));
        clickOn("#buttonAniadir");

        verifyThat("#labelError", LabeledMatchers.hasText("El campo Nombre y Apellidos debe de tener al menos un apellido"));

        doubleClickOn("#textfieldNombApell");
        write("aaaa aaaa");
        verifyThat("#labelError", LabeledMatchers.hasText(""));
        clickOn("#buttonAniadir");

        verifyThat("#labelError", LabeledMatchers.hasText("Demasiados caracteres en el campo Login"));

        doubleClickOn("#textfieldLogin");
        write("aaa");
        verifyThat("#labelError", LabeledMatchers.hasText(""));
        clickOn("#buttonAniadir");

        verifyThat("#labelError", LabeledMatchers.hasText("Debe haber mas caracteres en el campo Login"));

        doubleClickOn("#textfieldLogin");
        write("aaaa");
        verifyThat("#labelError", LabeledMatchers.hasText(""));
        clickOn("#buttonAniadir");

        verifyThat("#labelError", LabeledMatchers.hasText("El campo Salario debe ser un numero"));

        doubleClickOn("#textfieldSalario");
        write("1111");
        verifyThat("#labelError", LabeledMatchers.hasText(""));
        clickOn("#buttonAniadir");

        verifyThat("#labelError", LabeledMatchers.hasText("Demasiados caracteres en el campo Trabajo"));

        doubleClickOn("#textfieldTrabajo");
        write("aaa1");
        verifyThat("#labelError", LabeledMatchers.hasText(""));
        clickOn("#buttonAniadir");

        verifyThat("#labelError", LabeledMatchers.hasText("El campo Trabajo solo debe de contener letras"));

        doubleClickOn("#textfieldTrabajo");
        write("aaaa");
        verifyThat("#labelError", LabeledMatchers.hasText(""));
        clickOn("#buttonAniadir");

        verifyThat("#labelError", LabeledMatchers.hasText("Demasiados caracteres en el campo Password"));

        doubleClickOn("#textfieldPassword");
        write("aaa");
        verifyThat("#labelError", LabeledMatchers.hasText(""));
        clickOn("#buttonAniadir");

        verifyThat("#labelError", LabeledMatchers.hasText("Debe haber mas caracteres en el campo Password"));

        doubleClickOn("#textfieldPassword");
        write("aaaa");
        verifyThat("#labelError", LabeledMatchers.hasText(""));
        clickOn("#buttonAniadir");

        verifyThat("#labelError", LabeledMatchers.hasText(""));

        verifyThat("Aceptar", NodeMatchers.isVisible());
        verifyThat("Cancelar", NodeMatchers.isVisible());
        clickOn("Aceptar");

        verifyThat("#textfieldEmail", hasText(""));
        verifyThat("#textfieldNombApell", hasText(""));
        verifyThat("#textfieldLogin", hasText(""));
        verifyThat("#textfieldSalario", hasText(""));
        verifyThat("#textfieldTrabajo", hasText(""));
        verifyThat("#textfieldPassword", hasText(""));

        int totalRows = table.getItems().size();
        Node row = lookup(".table-row-cell").nth(totalRows - 1).query();
        clickOn(row);

        verifyThat("#textfieldEmail", hasText("aaaa@aaaa.com"));
        verifyThat("#textfieldNombApell", hasText("aaaa aaaa"));
        verifyThat("#textfieldLogin", hasText("aaaa"));
        verifyThat("#textfieldSalario", hasText("1111.0"));
        verifyThat("#textfieldTrabajo", hasText("aaaa"));
        verifyThat("#textfieldPassword", hasText(""));
    }

    /**
     * Tests what happens when you click Button Modificar.
     */
    @Test
    public void TestH() {
        verifyThat("#buttonModificar", isEnabled());

        clickOn("#textfieldEmail");
        eraseText(15);
        write("bbbb");
        verifyThat("#labelError", LabeledMatchers.hasText(""));
        clickOn("#buttonModificar");

        verifyThat("#labelError", LabeledMatchers.hasText("Introduzca un email en el campo Email"));

        clickOn("#textfieldEmail");
        write("@bbbb");
        verifyThat("#labelError", LabeledMatchers.hasText(""));
        clickOn("#buttonModificar");

        verifyThat("#labelError", LabeledMatchers.hasText("Introduzca un email en el campo Email"));

        clickOn("#textfieldEmail");
        write(".com");
        verifyThat("#labelError", LabeledMatchers.hasText(""));
        clickOn("#buttonModificar");

        verifyThat("Aceptar", NodeMatchers.isVisible());
        verifyThat("Cancelar", NodeMatchers.isVisible());
        clickOn("Cancelar");

        clickOn("#textfieldNombApell");
        eraseText(15);
        write("2222");
        verifyThat("#labelError", LabeledMatchers.hasText(""));
        clickOn("#buttonModificar");

        verifyThat("#labelError", LabeledMatchers.hasText("El campo Nombre y Apellidos solo debe de contener letras"));

        doubleClickOn("#textfieldNombApell");
        write("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
        verifyThat("#labelError", LabeledMatchers.hasText(""));
        clickOn("#buttonModificar");

        verifyThat("#labelError", LabeledMatchers.hasText("Demasiados caracteres en el campo Nombre y Apellidos"));

        doubleClickOn("#textfieldNombApell");
        write("bbbb");
        verifyThat("#labelError", LabeledMatchers.hasText(""));
        clickOn("#buttonModificar");

        verifyThat("#labelError", LabeledMatchers.hasText("El campo Nombre y Apellidos debe de tener al menos un apellido"));

        clickOn("#textfieldNombApell");
        write(" bbbb");
        verifyThat("#labelError", LabeledMatchers.hasText(""));
        clickOn("#buttonModificar");

        verifyThat("Aceptar", NodeMatchers.isVisible());
        verifyThat("Cancelar", NodeMatchers.isVisible());
        clickOn("Cancelar");

        doubleClickOn("#textfieldLogin");
        write("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
        verifyThat("#labelError", LabeledMatchers.hasText(""));
        clickOn("#buttonModificar");

        verifyThat("#labelError", LabeledMatchers.hasText("Demasiados caracteres en el campo Login"));

        doubleClickOn("#textfieldLogin");
        write("bbb");
        verifyThat("#labelError", LabeledMatchers.hasText(""));
        clickOn("#buttonModificar");

        verifyThat("#labelError", LabeledMatchers.hasText("Debe haber mas caracteres en el campo Login"));

        clickOn("#textfieldLogin");
        write("b");
        verifyThat("#labelError", LabeledMatchers.hasText(""));
        clickOn("#buttonModificar");

        verifyThat("Aceptar", NodeMatchers.isVisible());
        verifyThat("Cancelar", NodeMatchers.isVisible());
        clickOn("Cancelar");

        doubleClickOn("#textfieldSalario");
        write("bbbb");
        verifyThat("#labelError", LabeledMatchers.hasText(""));
        clickOn("#buttonModificar");

        verifyThat("#labelError", LabeledMatchers.hasText("El campo Salario debe ser un numero"));

        doubleClickOn("#textfieldSalario");
        write("2222");
        verifyThat("#labelError", LabeledMatchers.hasText(""));
        clickOn("#buttonModificar");

        verifyThat("Aceptar", NodeMatchers.isVisible());
        verifyThat("Cancelar", NodeMatchers.isVisible());
        clickOn("Cancelar");

        doubleClickOn("#textfieldTrabajo");
        write("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
        verifyThat("#labelError", LabeledMatchers.hasText(""));
        clickOn("#buttonModificar");

        verifyThat("#labelError", LabeledMatchers.hasText("Demasiados caracteres en el campo Trabajo"));

        doubleClickOn("#textfieldTrabajo");
        write("2222");
        verifyThat("#labelError", LabeledMatchers.hasText(""));
        clickOn("#buttonModificar");

        verifyThat("#labelError", LabeledMatchers.hasText("El campo Trabajo solo debe de contener letras"));

        doubleClickOn("#textfieldTrabajo");
        write("bbbb");
        verifyThat("#labelError", LabeledMatchers.hasText(""));
        clickOn("#buttonModificar");

        verifyThat("Aceptar", NodeMatchers.isVisible());
        verifyThat("Cancelar", NodeMatchers.isVisible());
        clickOn("Aceptar");

        table = lookup("#table").queryTableView();
        int totalRows = table.getItems().size();
        Node row = lookup(".table-row-cell").nth(totalRows - 1).query();
        clickOn(row);

        verifyThat("#textfieldEmail", hasText("bbbb@bbbb.com"));
        verifyThat("#textfieldNombApell", hasText("bbbb bbbb"));
        verifyThat("#textfieldLogin", hasText("bbbb"));
        verifyThat("#textfieldSalario", hasText("2222.0"));
        verifyThat("#textfieldTrabajo", hasText("bbbb"));
        verifyThat("#textfieldPassword", hasText(""));
        
        clickOn("#buttonLimpiar");
    }

    /**
     * Tests what happens when you click Button Borrar.
     */
    @Test
    public void TetsI() {
        table = lookup("#table").queryTableView();
        int totalRows = table.getItems().size();
        Node row = lookup(".table-row-cell").nth(totalRows - 1).query();
        clickOn(row);
        clickOn("#buttonBorrar");

        verifyThat("Aceptar", NodeMatchers.isVisible());
        verifyThat("Cancelar", NodeMatchers.isVisible());
        clickOn("Aceptar");

        verifyThat(table, TableViewMatchers.hasNumRows(totalRows - 1));

        verifyThat("#textfieldEmail", hasText(""));
        verifyThat("#textfieldNombApell", hasText(""));
        verifyThat("#textfieldLogin", hasText(""));
        verifyThat("#textfieldSalario", hasText(""));
        verifyThat("#textfieldTrabajo", hasText(""));
        verifyThat("#textfieldPassword", hasText(""));
    }

}
