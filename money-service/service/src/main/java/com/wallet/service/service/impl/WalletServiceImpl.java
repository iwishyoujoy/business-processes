package com.wallet.service.service.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.wallet.service.exception.WalletNotFoundException;
import com.wallet.service.model.Wallet;
import com.wallet.service.repository.WalletRepository;
import com.wallet.service.service.WalletService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    @Override
    public Wallet createWallet(Long userId, Float money){
        Wallet wallet = new Wallet();
        wallet.setUserId(userId);
        wallet.setMoney(money);
        walletRepository.save(wallet);
        return wallet;
    }

    @Override
    public void deleteWallet(Long userId){
        Wallet wallet = getWalletByUserId(userId);
        walletRepository.deleteById(wallet.getId());
    }

    @Override
    public Wallet getWalletById(Long id) {
        return walletRepository.getById(id);
    }

    @Override
    public Wallet getWalletByUserId(Long id) {
        return walletRepository.findByUserId(id)
            .orElseThrow(() -> new WalletNotFoundException("userId", String.valueOf(id)));
    }

    @Transactional
    @Override 
    public Boolean transactAuthorMoney(Long authorId, Float amount){
        Wallet authorWallet = getWalletByUserId(authorId);
        Float currentMoney = authorWallet.getMoney();

        if (currentMoney < amount) return false;

        authorWallet.setMoney(currentMoney - amount);
        walletRepository.save(authorWallet);

        return true;
    }

    @Override
    public Boolean transactAuthorAndReaderMoney(Long authorId, Long readerId, Float amount){
        Wallet authorWallet = getWalletByUserId(authorId);
        Wallet readerWallet = getWalletByUserId(readerId);
        Float authorCurrentMoney = authorWallet.getMoney();
        Float readerCurrentMoney = readerWallet.getMoney();

        if (readerCurrentMoney < amount) return false;

        authorWallet.setMoney(authorCurrentMoney + amount);
        readerWallet.setMoney(readerCurrentMoney - amount);

        walletRepository.save(authorWallet);
        walletRepository.save(readerWallet);

        return true;
    }

}
