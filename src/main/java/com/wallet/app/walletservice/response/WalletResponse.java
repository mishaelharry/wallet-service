package com.wallet.app.walletservice.response;

import lombok.Data;

@Data
public class WalletResponse {

    private String accountName;

    private String accountNumber;

    private Double balance;

}
