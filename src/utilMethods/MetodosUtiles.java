
package utilMethods;

import javafx.scene.control.TextField;

/**
 * @version 1.0
 * @author Endika, Markel
 */
public class MetodosUtiles {
              
    /**
     * Comprueba que el texto de un campo no tenga espacios intermedios.
     * @param field Un campo de texto.
     * @return Un boolean true si hay espacios en blanco en el texto, false si por el contrario no los hay.
     */
    public static Boolean comprobarEspaciosBlancos(TextField field) {
        //Guardamos valos textField en string sin espacios delante ni detras.
        String textoSinEspacios = field.getText().trim();
        //VAriable de retorno. Inicializar a false
        Boolean textoCorrecto = true;
        //ForEach de character. Recorremos letra a letra
        for(Character t:textoSinEspacios.toCharArray()){
            //Condición de igualdad. Propiedad equals de Character. Si el caracter actual igual a espacio.
            if(t.equals(' '))
                textoCorrecto = false;
        }
        return textoCorrecto;
    }
           
      /**
     * Comprueba que el texto de un campo no tenga más caracteres que el Integer pasado como parámetro.
     * @param field Un campo de texto.
     * @return Un boolean true si contiene los caracteres deseados, false si los caracteres superan el preestablecido.
     */
    public static Boolean maximoCaracteres(TextField field,Integer max) {
        //booleano iniciado a true. Por defecto cumple la condición
        Boolean numeroCaracteresCorrectos = true;
        //si el texto del textfield quitados los espacios delante y detrás su longitud mayor a lo preestablecido error
        if(field.getText().trim().length()>max){
            //booleana a false
                numeroCaracteresCorrectos = false;
        }
        return numeroCaracteresCorrectos;
    }
    
    /**
     * Comprueba que el texto de un campo no tenga menos caracteres que el Integer pasado como parámetro.
     * @param field Un campo de texto.
     * @return Un boolean true si contiene los caracteres deseados, false si los caracteres son menos que el preestablecido.
     */
    public static Boolean minimoCaracteres(TextField field, Integer min) {
        //booleano iniciado a true.
        Boolean numeroCaracteresCorrectos = true;
        if(field.getText().trim().length()< min){
                numeroCaracteresCorrectos = false;
        }
        return numeroCaracteresCorrectos;
    }
    
    
    /**
     * Comprueba que el String recibido como parámetro cumple las condiciones de un email.
     * @param email El email del campo contraseña.
     * @return Un booleano.  True si la contraseña es correcta.
     */
    public static boolean emailCorrecto(String email) {
        //Boolean, va a ser el return. Inicializar a true, hasta que se demuestre lo contrario.
        boolean ok=true;
        if(email.length()>45)
            ok = false;
        else{
            //Dividir el string por el caracter indicado.
            String emailArray [] = email.split("@");
            //Declaración array de caracteres para guardar la parte del nombre del email
            char [] nombre;
            //Si la longitud del array es distintode 2 significa que no hay un arroba.
            if(emailArray.length!=2) {
                //El email no es correcto
                ok=false;
                //System.out.println("Has metido "+(emailArray.length -1)+" @s");
            }
            else {//Vamos bien hay un arroba. Estudiar el nombre. Posición 0 del array.
                String nomUsuario = emailArray[0];
                nombre = nomUsuario.toCharArray();
                //Confirmar que los caracteres son mínimo 3
                if(nomUsuario.length()<3) {//Error no cumple el mínimo de carcteres en el nombre.
                    //System.out.println("Nombre usuario tiene que tener minimo 3.");
                    ok = false;
                }		
                else {//Vamos bien. Cumple la longitud de caracteres del nombre.
                    //Recorrer el nombre letra a letra.Solo puede tener letra numero y _ .
                    for(int i=0;i<nombre.length;i++) {
                        //Si no se cumple error.
                        if((Character.isLetterOrDigit(nombre[i])||Character.compare(nombre[i],'.')==0||Character.compare(nombre[i], '_')==0)==false) {
                            ok=false;
                            //System.out.println("\"Caracter incorrecto en el nombre de usuario solo letras numeros, . o _\"");
                            break;
                        }
                    }//Si va bien por ahra mira el dominio.
                    if(ok) {//Dividir el dominio Nombre y extensión
                        String dominio [] = emailArray[1].split("\\.");//o('.');
                        if(dominio.length!=2) {//Si no hay solo un punto. 
                            ok=false;
                            //System.out.println("Hay que separar el nombre del dominio y la extension con solo un punto");
                        }
                        else {
                            for(int j=0;j<2;j++) {
                                nombre =  dominio[j].toCharArray();
                                if(j==1){//La extensión del dominio 2 o 3 letras.
                                    if(nombre.length>3 || nombre.length<2){
                                        ok= false;
                                    }
                                }
                                for(int letra=0;letra<nombre.length;letra++) {
                                    if(Character.isAlphabetic(nombre[letra])==false) {
                                        ok=false;
                                        //System.out.println("En el dominio solo letras nada mas");
                                        break;
                                    }	
                                }
                            }
                        }
                    }
                }
            }
        }

	return ok;
    }
    
}
