package com.openclassrooms.paymybuddy.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests d'intégration pour vérifier la configuration Spring Security.
 * Ces tests valident que les routes sont correctement protégées
 * et que l'authentification fonctionne comme attendu.
 */
@SpringBootTest
@AutoConfigureMockMvc
class SecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Nested
    @DisplayName("Tests des routes publiques")
    class PublicRoutesTests {

        @Test
        @DisplayName("La page d'accueil est accessible sans authentification")
        void homePage_withoutAuth_isAccessible() throws Exception {
            mockMvc.perform(get("/"))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("La page de login est accessible sans authentification")
        void loginPage_withoutAuth_isAccessible() throws Exception {
            mockMvc.perform(get("/login"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("login"));
        }

        @Test
        @DisplayName("La page d'inscription est accessible sans authentification")
        void signupPage_withoutAuth_isAccessible() throws Exception {
            mockMvc.perform(get("/signup"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("signup"));
        }
    }

    @Nested
    @DisplayName("Tests des routes protégées - Utilisateur non authentifié")
    class ProtectedRoutesUnauthenticatedTests {

        @Test
        @DisplayName("Accès à /transfert sans auth redirige vers login")
        void transferPage_withoutAuth_redirectsToLogin() throws Exception {
            mockMvc.perform(get("/transfert"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/login"));
        }

        @Test
        @DisplayName("Accès à /profil sans auth redirige vers login")
        void profilPage_withoutAuth_redirectsToLogin() throws Exception {
            mockMvc.perform(get("/profil"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/login"));
        }

        @Test
        @DisplayName("Accès à /ajout-relation sans auth redirige vers login")
        void addRelationPage_withoutAuth_redirectsToLogin() throws Exception {
            mockMvc.perform(get("/ajout-relation"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/login"));
        }
    }

    @Nested
    @DisplayName("Tests du processus d'authentification")
    class AuthenticationProcessTests {

        @Test
        @DisplayName("Login avec identifiants invalides échoue et redirige vers login avec erreur")
        void login_withInvalidCredentials_fails() throws Exception {
            mockMvc.perform(formLogin("/login")
                            .user("username", "wronguser")
                            .password("password", "wrongpassword"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/login?error=true"))
                    .andExpect(unauthenticated());
        }

        @Test
        @DisplayName("Logout redirige vers la page de login")
        void logout_redirectsToLogin() throws Exception {
            mockMvc.perform(logout("/logout"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/login?logout=true"))
                    .andExpect(unauthenticated());
        }

        @Test
        @DisplayName("Accès à une route protégée sans auth retourne une redirection 302")
        void protectedRoute_withoutAuth_returns302() throws Exception {
            mockMvc.perform(get("/transfert"))
                    .andExpect(status().isFound()) // 302
                    .andExpect(unauthenticated());
        }
    }
}
