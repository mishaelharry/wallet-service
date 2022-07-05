package com.wallet.app.walletservice.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class BvnKycRequest {

    @Size(min = 11, max = 11, message = "Bvn is must be 11 digits")
    @NotBlank(message="Bvn is required")
    private String bvn;

}
