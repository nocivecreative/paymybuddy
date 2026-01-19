package com.openclassrooms.paymybuddy.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "transactions")
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false) // optional = false -> contrôle au niveau JPA / objet
    @JoinColumn(name = "id_user_sender", nullable = false)
    private User sender;

    @ManyToOne(optional = false) // optional = false -> contrôle au niveau JPA / objet
    @JoinColumn(name = "id_user_receiver", nullable = false) // nullable = false -> contrôle au niveau SQL
    private User receiver;

    @Column(nullable = false, precision = 10, scale = 2) // type SQL DECIMAL(10,2) sur ce champ
    private BigDecimal amount;

    @Column(length = 255) // Pas de name : nom exact auto géré
    private String description;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private LocalDateTime updatedAt;

    public Transaction(User sender, User receiver, BigDecimal amount, String description) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.description = description;
    }

}
