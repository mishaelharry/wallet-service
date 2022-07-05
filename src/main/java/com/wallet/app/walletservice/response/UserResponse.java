package com.wallet.app.walletservice.response;

import lombok.Data;

@Data
public class UserResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String mobile;

    private String bvn;

    private String identityType;

    private String identityNumber;

    private String country;

    private Boolean status;

    private WalletResponse wallet;

}
