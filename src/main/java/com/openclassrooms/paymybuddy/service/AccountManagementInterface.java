package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.User;

public interface AccountManagementInterface {

    public void createAccount(User newUser);

    public void updateUserInfos(User newUser);

}
