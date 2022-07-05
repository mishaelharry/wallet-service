package com.wallet.app.walletservice.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class IdentityKycRequest {

    @NotBlank(message="Identity number is required")
    private String identityNumber;

    @NotBlank(message="Identity type is required")
    private String identityType;

}
