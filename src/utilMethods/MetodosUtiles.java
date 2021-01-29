package utilMethods;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.TextField;

/**
 * This class stores util methods used in project application.
 * @version 1.0
 * @author Endika, Markel
 */
public class MetodosUtiles {

    /**
     * Comprueba que el texto de un campo no tenga espacios intermedios.
     *
     * @param field Un campo de texto.
     * @return Un boolean true si hay espacios en blanco en el texto, false si
     * por el contrario no los hay.
     */
    public static Boolean comprobarEspaciosBlancos(TextField field) {
        //Guardamos valos textField en string sin espacios delante ni detras.
        String textoSinEspacios = field.getText().trim();
        //VAriable de retorno. Inicializar a false
        Boolean textoCorrecto = true;
        //ForEach de character. Recorremos letra a letra
        for (Character t : textoSinEspacios.toCharArray()) {
            //Condición de igualdad. Propiedad equals de Character. Si el caracter actual igual a espacio.
            if (t.equals(' ')) {
                textoCorrecto = false;
            }
        }
        return textoCorrecto;
    }

    /**
     * Comprueba que el texto de un campo no tenga más caracteres que el Integer
     * pasado como parámetro.
     *
     * @param field Un campo de texto.
     * @param max Maximo de caracteres que tiene que tener el texto
     * @return Un boolean true si contiene los caracteres deseados, false si los
     * caracteres superan el preestablecido.
     */
    public static Boolean maximoCaracteres(TextField field, Integer max) {
        //booleano iniciado a true. Por defecto cumple la condición
        Boolean numeroCaracteresCorrectos = true;
        //si el texto del textfield quitados los espacios delante y detrás su longitud mayor a lo preestablecido error
        if (field.getText().trim().length() > max) {
            //booleana a false
            numeroCaracteresCorrectos = false;
        }
        return numeroCaracteresCorrectos;
    }

    /**
     * Comprueba que el texto de un campo no tenga menos caracteres que el
     * Integer pasado como parámetro.
     *
     * @param field Un campo de texto.
     * @param min Minimo de caracteres que tiene que tener el texto
     * @return Un boolean true si contiene los caracteres deseados, false si los
     * caracteres son menos que el preestablecido.
     */
    public static Boolean minimoCaracteres(TextField field, Integer min) {
        //booleano iniciado a true.
        Boolean numeroCaracteresCorrectos = true;
        if (field.getText().trim().length() < min) {
            numeroCaracteresCorrectos = false;
        }
        return numeroCaracteresCorrectos;
    }

    /**
     * Comprueba que el String recibido como parámetro cumple las condiciones de
     * un email.
     *
     * @param email El email del campo email.
     * @return Un booleano. True si la email es correcto.
     */
    public static Boolean validateEmail(String email) {
        // Patrón para validar el email
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(email);

        return mather.find();
    }

    /**
     * Comrupueba que el String recibido como parametro contiene tan solo letras.
     *
     * @param cadena El String a comprobar.
     * @return Un booleano.
     */
    public static Boolean contieneSoloLetras(String cadena) {
        for (int x = 0; x < cadena.length(); x++) {
            char c = cadena.charAt(x);
            // Si no está entre a y z, ni entre A y Z, ni es un espacio
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == ' ')) {
                return false;
            }
        }
        return true;
    }

    /**
     * Comrupueba que el String recibido como parametro puede ser parseado a Float
     * @param cadena la cadena a comprobar
     * @return un booleano
     */
    public static Boolean verifyFloat(String cadena) {
        try {
            Float.parseFloat(cadena);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

}
