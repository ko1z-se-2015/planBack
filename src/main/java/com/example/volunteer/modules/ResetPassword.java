package com.example.volunteer.modules;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResetPassword {
    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;

    public ResetPassword(String newPassword, String confirmNewPassword) {
        this.newPassword = newPassword;
        this.confirmNewPassword = confirmNewPassword;
    }
}
