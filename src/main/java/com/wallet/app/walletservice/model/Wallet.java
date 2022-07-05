package com.wallet.app.walletservice.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "wallets")
public class Wallet extends DateAudit{

    @Id
    @GeneratedValue(generator = "wallet_generator")
    @SequenceGenerator(
            name = "wallet_generator",
            sequenceName = "wallet_sequence",
            initialValue = 1000
    )
    private Long id;

    @NotBlank
    private String accountNumber;

    @NotNull
    private Double balance;

    @OneToOne
    User user;

}
