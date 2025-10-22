package com.david.catalogue.user;

import lombok.Getter;
import lombok.Setter;

public class UpdateUserRequestDTO {
    private String newUsername;
    private String newPassword;
    
    public String getNewUsername() {
        return newUsername;
    }

    public void setNewUsername(String newUsername) {
        this.newUsername = newUsername;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
