-- ===============================================================================
-- Création du MPD pour application Pay My Buddy 
-- Date: 2026-01-08
-- Version : 1.0
-- Description: Construction du MPD complet pour l'application Pay My Buddy
--              Génère la database complète, regnération complète (drop if exist)
-- ===============================================================================

-- Supprime si DB déjà existante
DROP DATABASE IF EXISTS `paymybuddy`;

-- Création de la DB
CREATE DATABASE `paymybuddy` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;

-- Création de la table users
CREATE TABLE `paymybuddy`.`users` (
    `id` INT NOT NULL AUTO_INCREMENT COMMENT 'Id de l''utilisateur',
    `username` VARCHAR(50) NOT NULL,
    `email` VARCHAR(100) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP() ON UPDATE CURRENT_TIMESTAMP(),
    PRIMARY KEY (`id`),
    UNIQUE INDEX `username_UNIQUE` (`username` ASC),
    UNIQUE INDEX `email_UNIQUE` (`email` ASC)
    );


-- Création de la table transactions
CREATE TABLE `paymybuddy`.`transactions` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `id_user_sender` INT NOT NULL,
    `id_user_receiver` INT NOT NULL,
    `amount` DECIMAL(10,2) NOT NULL,
    `description` VARCHAR(255) NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP() ON UPDATE CURRENT_TIMESTAMP(),
    PRIMARY KEY (`id`),
    CONSTRAINT fk_sender FOREIGN KEY (id_user_sender) REFERENCES users(id),
    CONSTRAINT fk_receiver FOREIGN KEY (id_user_receiver) REFERENCES users(id)
    );

-- Création de la table liaison user_user
CREATE TABLE `paymybuddy`.`users_relations` (
    `id_user` INT NOT NULL,
    `id_friend` INT NOT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP() ON UPDATE CURRENT_TIMESTAMP(),
    PRIMARY KEY (`id_user`, `id_friend`),
    CONSTRAINT fk_user  FOREIGN KEY (id_user) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_friend  FOREIGN KEY (id_friend) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT chk_not_self CHECK (id_user <> id_friend)
);  


-- Insertion de vlaurs de test

-- Table "users" :
------------------------
-- User de test : 
---- username: user1
---- pass:  user
------------------------
INSERT INTO `paymybuddy`.`users` (`username`, `email`, `password`) VALUES ('user1', 'user1@gmail.com', '$2a$10$AfZPpxE82weRCehwfE.0qeTetnz1igtcYk9NxtKITs1dTQ5ocoDv.');
INSERT INTO `paymybuddy`.`users` (`username`, `email`, `password`) VALUES ('user2', 'user2@gmail.com', 'user2');
INSERT INTO `paymybuddy`.`users` (`username`, `email`, `password`) VALUES ('user3', 'user3@gmail.com', 'user3');


