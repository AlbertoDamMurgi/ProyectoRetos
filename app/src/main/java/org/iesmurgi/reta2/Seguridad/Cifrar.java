/*

Reta2  Copyright (C) 2018  Alberto Fernández Fernández, Santiago Álvarez Fernández, Joaquín Pérez Rodríguez

This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program. If not, see http://www.gnu.org/licenses/.


Contact us:

fernandez.fernandez.angel@gmail.com
santiago.alvarez.dam@gmail.com
perezrodriguezjoaquin0@gmail.com

*/

package org.iesmurgi.reta2.Seguridad;

import android.util.Base64;

import java.security.MessageDigest;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Clase para encriptar y desencriptar
 * @author Alberto Fernández
 * @author Santiago Álvarez
 * @author Joaquín Pérez
 * @see Cipher
 * @see Base64
 */
public class Cifrar {

    private static String secretKey = "rw82[8N-&Nu=AN9X9c{9Tvx$";

    /**
     * Método para desencriptar una cadena
     * @param encryptedText texto encriptado
     * @return texto desencriptado
     * @throws Exception error en la desencriptación
     */
    public static String decrypt(String encryptedText) throws Exception {
        byte[] message = Base64.decode(encryptedText.getBytes(), Base64.DEFAULT);
        Cipher decipher = Cipher.getInstance("DESede");
        decipher.init(Cipher.DECRYPT_MODE, getSecreteKey(secretKey));
        byte[] texto = decipher.doFinal(message);
        return new String(texto, "UTF-8");
    }

    /**
     * Método que genera una clave a partir de una cadena
     * @param secretKey string a partir de la cual se forma la key
     * @return la clave generada
     * @throws Exception error a la hora de crear la clave
     */
    public static SecretKey getSecreteKey(String secretKey) throws Exception {
        byte[] bPasswd = secretKey.getBytes("utf-8");
        byte[] uBPasswd = Arrays.copyOf(bPasswd, 24);
        SecretKey key = new SecretKeySpec(uBPasswd, "DESede");
        return key;
    }
}