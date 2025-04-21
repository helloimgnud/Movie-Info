package com.recflix.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserDTO {
    @NotBlank(message = "Tên tài khoản không được trống")
    private String username;

    @NotBlank(message = "Mật khấu không được trống")
    private String password;

    @NotBlank(message = "Phải có role")
    private String role;

    // @NotNull(message = "Phải liên kết với resident thông qua residentId")
    // private Long residnetId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // public Long getResidnetId() {
    //     return residnetId;
    // }

    // public void setResidnetId(Long residnetId) {
    //     this.residnetId = residnetId;
    // }
}
