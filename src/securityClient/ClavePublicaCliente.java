/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securityClient;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * This class uses a public key to encrypt the password of an user.
 * @author Xabier Carnero, Endika Ubierna, Markel Lopez de Uralde.
 */
public class ClavePublicaCliente {
    /**
     * Logger object used to control activity from class.
     */
    private static final Logger LOGGER = 
            Logger.getLogger("emex51.ClavePublicaClienteClass");
    /**
     * This method encrypts a String.
     * @param mensaje The String to encrypt.
     * @return The String received encrypted.
     */
    public static String cifrarTexto(String mensaje) {
        LOGGER.info("MetodoCifrarMensaje");
        byte[] encodedMessage = null;
        try {
           ResourceBundle fichero = ResourceBundle.getBundle("securityClient.PublicKeyFile");
           String rutaFichero = fichero.getString("filepath");
           byte fileKey[] = fileReader(rutaFichero);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(fileKey);
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            encodedMessage = cipher.doFinal(mensaje.getBytes());
        } catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
        }
        return byteToHex(encodedMessage);
    }
 
    /**
     * This method converts the byte array text received to hexadecimal String.
     * @param byteText byte array text to convert.
     * @return converted text in hexadecimal.
     */
    private static String byteToHex(byte[] byteText) {
        LOGGER.info("Metodo pasa el cifrado de contraseña a hexadecimal");
        String hexText = "";
        for (int i = 0; i < byteText.length; i++) {
            String h = Integer.toHexString(byteText[i] & 0xFF);
            if (h.length() == 1) {
                hexText += "0";
            }
            hexText += h;
        }
        return hexText.toUpperCase();
    }
    
    /**
     *This method returns the public key.
     * @param path File path
     * @return The public key in a bytes array. 
     */
    private static byte[] fileReader(String path) {
        LOGGER.info("Metodo para la lectura del fichero donde está la clave pública.");
        byte ret[] = null;
        File file = new File(path);
        try {
            ret = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }
    
}
