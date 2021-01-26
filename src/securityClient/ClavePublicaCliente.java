/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securityClient;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.ResourceBundle;
import javax.crypto.Cipher;

/**
 *
 * @author xabig
 */
public class ClavePublicaCliente {
    
    public static String cifrarTexto(String mensaje) {
        byte[] encodedMessage = null;
        String encodedMessageHex = null;
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
            
            encodedMessageHex = byteToHex(encodedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodedMessageHex;
    }
    
    /**
     * This method converts the byte array text received to hexadecimal String.
     *
     * @param byteText byte array text to convert.
     * @return converted text in hexadecimal.
     */
    private static String byteToHex(byte[] byteText) {
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
     * Retorna el contenido de un fichero
     * 
     * @param path Path del fichero
     * @return El texto del fichero
     */
    private static byte[] fileReader(String path) {
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
