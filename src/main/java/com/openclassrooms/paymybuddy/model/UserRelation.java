package com.openclassrooms.paymybuddy.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "users_relations")
@Data
public class UserRelation {

    @EmbeddedId
    private UserRelationId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId") // PK Composite
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("friendId") // PK Composite
    @JoinColumn(name = "id_friend", nullable = false)
    private User friend;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private LocalDateTime updatedAt;

    public UserRelation() {
    }

    public UserRelation(User user, User friend) {
        this.user = user;
        this.friend = friend;
        this.id = new UserRelationId(user.getId(), friend.getId());
    }
}
