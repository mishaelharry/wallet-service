package com.wallet.app.walletservice.controller;

import com.wallet.app.walletservice.request.FundWalletRequest;
import com.wallet.app.walletservice.request.TransferRequest;
import com.wallet.app.walletservice.request.UserRequest;
import com.wallet.app.walletservice.response.BaseResponse;
import com.wallet.app.walletservice.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/wallets")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @GetMapping("/{accountNumber}")
    public ResponseEntity<BaseResponse> getWallet(@PathVariable String accountNumber){
        //get user wallet by account number
        BaseResponse response = walletService.getWallet(accountNumber);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("/transfer")
    public ResponseEntity<BaseResponse> transfer(@Valid @RequestBody TransferRequest request){
        //transfer fund to other wallet
        BaseResponse response = walletService.transfer(request);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("/topup")
    public ResponseEntity<BaseResponse> topup(@Valid @RequestBody FundWalletRequest request){
        //topup wallet balance
        BaseResponse response = walletService.topup(request);
        return new ResponseEntity(response, HttpStatus.OK);
    }
}
