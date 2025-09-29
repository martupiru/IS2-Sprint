package com.sprint.part2ej1.utils;

import java.security.MessageDigest;

//tecnica para encriptar la clave del usuario
public class HashForLogin {
    public static String hashClave(String clave)  {
        try {
            //Se utiliza el algoritmo SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            //Calcula el hash y devuelve los bytes
            byte[] hashedBytes = md.digest(clave.getBytes());

            // Convierte los bytes en String
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                //Convierte cada byte a hexadecimal y lo añade al StringBuilder
                sb.append(String.format("%02x", b));
            }
            //Se devuelve la clave con el hash como String
            return sb.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error de sistema", ex);
        }
    }
}