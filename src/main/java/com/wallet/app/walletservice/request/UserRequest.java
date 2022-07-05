package com.wallet.app.walletservice.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserRequest {

    @NotBlank(message="First name is required")
    private String firstName;

    @NotBlank(message="Last name is required")
    private String lastName;

    @NotBlank(message="Mobile is required")
    private String mobile;

    @NotBlank(message="Email is required")
    @Email
    private String email;

    @NotBlank(message="Pin is required")
    private String pin;

    @NotBlank(message="Country is required")
    private String country;

}
