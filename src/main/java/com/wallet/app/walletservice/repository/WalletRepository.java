package com.wallet.app.walletservice.repository;

import com.wallet.app.walletservice.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Wallet findByAccountNumber(String accountNumber);

}
