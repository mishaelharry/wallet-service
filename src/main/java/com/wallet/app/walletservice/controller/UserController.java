package com.wallet.app.walletservice.controller;

import com.wallet.app.walletservice.request.BvnKycRequest;
import com.wallet.app.walletservice.request.IdentityKycRequest;
import com.wallet.app.walletservice.request.UserRequest;
import com.wallet.app.walletservice.response.BaseResponse;
import com.wallet.app.walletservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    //register new user
    @PostMapping("/register")
    public ResponseEntity<BaseResponse> register(@Valid @RequestBody UserRequest request){
        //check whether user exists by mobile
        if (userService.existsByMobile(request.getMobile())) {
            return new ResponseEntity(new BaseResponse(false, "Mobile already exist", null), HttpStatus.OK);
        }

        //check whether user exists by email
        if (userService.existsByEmail(request.getEmail())) {
            return new ResponseEntity(new BaseResponse(false, "Email already exist", null), HttpStatus.OK);
        }

        //create new user account
        BaseResponse response = userService.register(request);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PutMapping("/update-bvn/{userId}")
    public ResponseEntity<BaseResponse> updateBvn(@PathVariable Long userId,
                                                  @Valid @RequestBody BvnKycRequest request) {
        //update user bvn kyc
        BaseResponse response = userService.updateBvn(userId, request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update-identity/{userId}")
    public ResponseEntity<BaseResponse> updateIdentity(@PathVariable Long userId,
                                                       @Valid @RequestBody IdentityKycRequest request) {
        //update user identity
        BaseResponse response = userService.updateIdentity(userId, request);
        return ResponseEntity.ok(response);
    }

}
