package com.wallet.app.walletservice.repository;

import com.wallet.app.walletservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByMobile(String mobile);

    boolean existsByEmail(String email);

}
