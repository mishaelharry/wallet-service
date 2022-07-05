package com.wallet.app.walletservice.service;

import com.wallet.app.walletservice.model.User;
import com.wallet.app.walletservice.model.Wallet;
import com.wallet.app.walletservice.repository.UserRepository;
import com.wallet.app.walletservice.repository.WalletRepository;
import com.wallet.app.walletservice.request.BvnKycRequest;
import com.wallet.app.walletservice.request.IdentityKycRequest;
import com.wallet.app.walletservice.request.UserRequest;
import com.wallet.app.walletservice.response.BaseResponse;
import com.wallet.app.walletservice.response.UserResponse;
import com.wallet.app.walletservice.response.WalletResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean existsByMobile(String mobile){
        return userRepository.existsByMobile(mobile);
    }

    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    public BaseResponse register(UserRequest request) {
        //create user profile
        User user = new User();
        BeanUtils.copyProperties(request, user);
        user.setPin(passwordEncoder.encode(request.getPin()));
        user.setStatus(true);
        user.setKycLevel(1);

        User userResult = userRepository.save(user);
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(userResult, userResponse);

        //generate wallet account number
        String accountNumber = generateAccount();

        //create and initialize wallet
        Wallet wallet = new Wallet();
        wallet.setAccountNumber(accountNumber);
        wallet.setBalance(0D);
        wallet.setUser(userResult);

        Wallet walletResult = walletRepository.save(wallet);
        WalletResponse walletResponse = new WalletResponse();
        BeanUtils.copyProperties(walletResult, walletResponse);
        String accountName = wallet.getUser().getFirstName() + " " + wallet.getUser().getLastName();
        walletResponse.setAccountName(accountName);
        userResponse.setWallet(walletResponse);

        return new BaseResponse<>(true, "User created successfully", userResponse);
    }

    //generate 10 digit number
    private String generateAccount(){
        SecureRandom random = new SecureRandom();
        int num = random.nextInt(1000000000);
        return String.format("%05d", num);
    }

    public BaseResponse updateBvn(Long userId, BvnKycRequest request) {
        //retrieve user profile
        User user = userRepository.findById(userId).orElse(null);
        if (user != null){
            if (user.getBvn() != null && user.getBvn().length() == 11){
                return new BaseResponse<>(true, "Bvn already updated", null);
            }else {
                UserResponse userResponse;
                user.setBvn(request.getBvn());
                user.setKycLevel(user.getKycLevel() + 1); //update kyc level

                //persist bvn
                User result = userRepository.save(user);
                userResponse = new UserResponse();
                BeanUtils.copyProperties(result, userResponse);

                return new BaseResponse<>(true, "User bvn updated", userResponse);
            }
        } else {
            return new BaseResponse<>(false, "User not found", null);
        }
    }

    public BaseResponse updateIdentity(Long userId, IdentityKycRequest request) {
        //retrieve user profile
        User user = userRepository.findById(userId).orElse(null);
        if (user != null){
            if (user.getIdentityNumber() != null){
                return new BaseResponse<>(true, "Identity number already updated", null);
            }else if (user.getIdentityType() != null){
                return new BaseResponse<>(true, "Identity type already updated", null);
            } else {
                UserResponse userResponse;
                user.setIdentityNumber(request.getIdentityNumber());
                user.setIdentityType(request.getIdentityType());
                user.setKycLevel(user.getKycLevel() + 1); //update kyc level

                //persist identity number and identity type
                User result = userRepository.save(user);
                userResponse = new UserResponse();
                BeanUtils.copyProperties(result, userResponse);

                return new BaseResponse<>(true, "User identity updated", userResponse);
            }
        } else {
            return new BaseResponse<>(false, "User not found", null);
        }
    }
}
