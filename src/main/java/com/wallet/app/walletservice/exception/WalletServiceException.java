package com.wallet.app.walletservice.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@ToString
@Slf4j
public class WalletServiceException extends RuntimeException {

    private HttpStatus status;

    private Object errors;

    public WalletServiceException(String message) {
        super(message);
        status = HttpStatus.BAD_REQUEST;
        log.info(message);
    }

    public WalletServiceException(String message, HttpStatus status) {
        super(message);
        this.status = status;
        log.info(message);
    }

}
