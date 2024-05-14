package com.wallet.service.service;

import com.wallet.service.model.Wallet;

public interface WalletService {
    Wallet createWallet(Long userId, Float money);
    void deleteWallet(Long userId);
    Wallet getWalletByUserId(Long id);
    Wallet getWalletById(Long id);
    Boolean transactAuthorMoney(Long authorId, Float amount);
    Boolean transactAuthorAndReaderMoney(Long authorId, Long readerId, Float amount);
}
