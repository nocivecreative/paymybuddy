package com.openclassrooms.paymybuddy.service;

import java.util.List;

import com.openclassrooms.paymybuddy.dto.response.RelationDTO;

public interface RelationManagementInterface {

    public void addRelation(int currentUserId, String friendEmail);

    public List<RelationDTO> getRelations(int currentUserId);
}
