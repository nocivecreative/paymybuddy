package com.openclassrooms.paymybuddy.utils;

import java.io.Console;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Classe utilitaire pour générer un hash BCrypt à partir d'un mot de passe
 *
 * Exécuter depuis un terminal externe (pas dans l'IDE)
 */
public class PasswordGenerator {
    public static void main(String[] args) {
        Console console = System.console();

        System.out.println("Utilitaire de génération de hash BCrypt");
        char[] passwordChars = console.readPassword("Entrez le mot de passe : ");
        String password = new String(passwordChars);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println("Hash BCrypt correspondant : ");
        System.out.println(encoder.encode(password));

        java.util.Arrays.fill(passwordChars, ' ');
    }
}
