package com.wallet.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wallet.service.model.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long>{
    
}
