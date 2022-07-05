package com.wallet.app.walletservice.service;

import com.wallet.app.walletservice.model.User;
import com.wallet.app.walletservice.model.Wallet;
import com.wallet.app.walletservice.repository.WalletRepository;
import com.wallet.app.walletservice.request.FundWalletRequest;
import com.wallet.app.walletservice.request.TransferRequest;
import com.wallet.app.walletservice.request.UserRequest;
import com.wallet.app.walletservice.response.BaseResponse;
import com.wallet.app.walletservice.response.WalletResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private PasswordEncoder encoder;

    public BaseResponse getWallet(String accountNumber) {
        Wallet wallet = walletRepository.findByAccountNumber(accountNumber);
        if (wallet != null){
            String accountName = wallet.getUser().getFirstName() + " " + wallet.getUser().getLastName();
            WalletResponse response = new WalletResponse();
            BeanUtils.copyProperties(wallet, response);
            response.setAccountName(accountName);
            response.setBalance(null);
            return new BaseResponse<>(true, "Wallet retrieved successfully", response);
        } else {
            return new BaseResponse<>(false, "Wallet detail not found.", null);
        }
    }

    public BaseResponse transfer(TransferRequest request) {
        //get debit wallet
        Wallet debitWallet = walletRepository.findByAccountNumber(request.getSourceAccount());
        if (debitWallet == null){
            return new BaseResponse<>(false, "Invalid source account number.", null);
        }

        //validate user pin
        BaseResponse validatePin = validatePin(request.getPin(), debitWallet.getUser());
        if (!validatePin.getStatus()){
            return validatePin;
        }

        //get credit wallet
        Wallet creditWallet = walletRepository.findByAccountNumber(request.getDestinationAccount());
        if (creditWallet == null){
            return new BaseResponse<>(false, "Invalid destination account number.", null);
        }

        //valid amount
        if (request.getAmount() <= 0){
            return new BaseResponse<>(false, "Invalid amount.", null);
        }

        //check sufficient fund
        if (debitWallet.getBalance() < request.getAmount()){
            return new BaseResponse<>(false, "Insufficient fund.", null);
        }

        //validate user level
        BaseResponse validateLevel = validateLevel(request.getAmount(), debitWallet.getUser());
        if (!validateLevel.getStatus()){
            return validateLevel;
        }

        debitWallet.setBalance(debitWallet.getBalance() - request.getAmount());

        creditWallet.setBalance(creditWallet.getBalance() + request.getAmount());

        walletRepository.save(debitWallet);
        walletRepository.save(creditWallet);

        return new BaseResponse<>(true, "Transfer was successful", null);

    }

    private BaseResponse validateLevel(Double amount, User user){
        if (user.getKycLevel() == 1 && amount >= 50000){
            return new BaseResponse<>(false, "Maximum amount for level 1 kyc is 50,000", null);
        } else if (user.getKycLevel() == 2 && amount >= 100000){
            return new BaseResponse<>(false, "Maximum amount for level 2 kyc is 100,000", null);
        } else {
            return new BaseResponse<>(true, "Valid level", null);
        }
    }

    private BaseResponse validatePin(String pin, User user){
        if (!encoder.matches(pin, user.getPin())){
            return new BaseResponse<>(false, "Invalid pin", null);
        }
        return new BaseResponse<>(true, "Valid pin", null);
    }

    public BaseResponse topup(FundWalletRequest request) {
        //get wallet by account number
        Wallet wallet = walletRepository.findByAccountNumber(request.getAccountNumber());
        if (wallet != null){
            if (request.getAmount() <= 0){
                return new BaseResponse<>(false, "Invalid amount.", null);
            }

            //credit wallet with fund
            wallet.setBalance(wallet.getBalance() + request.getAmount());

            String accountName = wallet.getUser().getFirstName() + " " + wallet.getUser().getLastName();

            //update wallet
            Wallet result = walletRepository.save(wallet);
            WalletResponse walletResponse = new WalletResponse();
            BeanUtils.copyProperties(result, walletResponse);
            walletResponse.setAccountName(accountName);

            return new BaseResponse<>(true, "Wallet retrieved successfully", walletResponse);
        } else {
            return new BaseResponse<>(false, "Wallet detail not found.", null);
        }
    }

}
