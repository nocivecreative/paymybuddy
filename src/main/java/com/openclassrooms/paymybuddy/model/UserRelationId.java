package com.openclassrooms.paymybuddy.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class UserRelationId implements Serializable {

    @Column(name = "id_user")
    private int userId;

    @Column(name = "id_friend")
    private int friendId;

    public UserRelationId() {
    }

    public UserRelationId(int userId, int friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof UserRelationId))
            return false;
        UserRelationId that = (UserRelationId) o;
        return Objects.equals(userId, that.userId)
                && Objects.equals(friendId, that.friendId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, friendId);
    }
}