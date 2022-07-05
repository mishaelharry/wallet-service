package com.wallet.app.walletservice.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class FundWalletRequest {

    @NotBlank(message="Account number is required")
    private String accountNumber;

    @NotNull(message="Amount is required")
    private Double amount;

}
