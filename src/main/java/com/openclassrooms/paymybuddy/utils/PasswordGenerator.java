package com.openclassrooms.paymybuddy.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Classe utilitaire pour générer un pass encrypté avec BCrypt
 * 
 * Run Java pour exec
 * 
 */
public class PasswordGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("user"));
    }
}
