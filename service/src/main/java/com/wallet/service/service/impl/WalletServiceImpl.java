package com.wallet.service.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.wallet.service.dto.Statistics;
import com.wallet.service.exception.NotEnoughMoneyException;
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
    public Wallet updateWallet(Long userId, Float money) {
        Wallet wallet = getWalletByUserId(userId);
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
    public Wallet transactAuthorMoney(Long authorId, Float amount){
        Wallet authorWallet = getWalletByUserId(authorId);
        Float currentMoney = authorWallet.getMoney();

        if (currentMoney < amount) {
            throw new NotEnoughMoneyException("authorId", String.valueOf(authorId));
        }

        authorWallet.setMoney(currentMoney - amount);
        walletRepository.save(authorWallet);

        return authorWallet;
    }

    @Transactional
    @Override
    public Wallet transactAuthorAndReaderMoney(Long authorId, Long readerId, Float amount){
        Wallet authorWallet = getWalletByUserId(authorId);
        Wallet readerWallet = getWalletByUserId(readerId);
        Float authorCurrentMoney = authorWallet.getMoney();
        Float readerCurrentMoney = readerWallet.getMoney();

        if (readerCurrentMoney < amount) {
            throw new NotEnoughMoneyException("readerId", String.valueOf(readerId));
        };

        authorWallet.setMoney(authorCurrentMoney + amount);
        readerWallet.setMoney(readerCurrentMoney - amount);

        walletRepository.save(authorWallet);
        walletRepository.save(readerWallet);

        return readerWallet;
    }

    @Override
    public Boolean isWalletExist(Long userId) {
        Optional<Wallet> walletOptional = walletRepository.findByUserId(userId);
        if (walletOptional.isPresent()) {
            return true;
        }
        return false;
    }

    @Override
    public Statistics getStatistics() {
        Long count = walletRepository.count();

        List<Wallet> wallets = walletRepository.findAll(); 
        Float totalAmount = 0.0f;
    
        if (!wallets.isEmpty()) { 
            for (Wallet wallet : wallets) {
                totalAmount += wallet.getMoney(); 
            }
            Float averageAmount = totalAmount / count;
            
            return new Statistics(count, averageAmount);
        } else {
            return new Statistics(count, 0.0f);
        }
    }

}
