/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securityClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

/**
 *
 * @author xabig
 */
public class ClavePublicaCliente {
    /**
     * This method figures the text that recieve using the Public Key
     * @param mensaje the text to figure
     * @return the figured String
     */
    public static String cifrarTexto(String mensaje) {
        byte[] encodedMessage = null;
        String encodedMessageHex = null;
        try {
            byte fileKey[] = fileReader("securityClient/Public.key");

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
     * @param path
     * @return El texto del fichero
     */
    public static byte[] fileReader(String path) throws IOException {

        InputStream keyfis = ClavePublicaCliente.class.getClassLoader()
                .getResourceAsStream(path);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        // read bytes from the input stream and store them in buffer
        while ((len = keyfis.read(buffer)) != -1) {
            // write bytes from the buffer into output stream
            os.write(buffer, 0, len);
        }
        keyfis.close();
        return os.toByteArray();
    }
}
