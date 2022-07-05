package com.wallet.app.walletservice.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "users")
public class User extends DateAudit{

    @Id
    @GeneratedValue(generator = "user_generator")
    @SequenceGenerator(
            name = "user_generator",
            sequenceName = "user_sequence",
            initialValue = 1000
    )
    private Long id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String email;

    @NotBlank
    private String mobile;

    private String bvn;

    private String identityType;

    private String identityNumber;

    @NotBlank
    private String pin;

    @NotBlank
    private String country;

    @NotNull
    private Integer kycLevel;

    @NotNull
    private Boolean status;

}
