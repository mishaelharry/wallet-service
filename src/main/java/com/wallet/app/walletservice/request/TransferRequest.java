package com.wallet.app.walletservice.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class TransferRequest {

    @NotBlank(message="Source account is required")
    private String sourceAccount;

    @NotBlank(message="Destination account is required")
    private String destinationAccount;

    @NotNull(message="Amount is required")
    private Double amount;

    @NotBlank(message="Narration is required")
    private String narration;

    @NotBlank(message="Pin is required")
    private String pin;

}
