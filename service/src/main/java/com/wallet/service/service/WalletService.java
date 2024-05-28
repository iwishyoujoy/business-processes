package com.wallet.service.service;

import com.wallet.service.dto.Statistics;
import com.wallet.service.model.Wallet;

public interface WalletService {
    Wallet createWallet(Long userId, Float money);
    void deleteWallet(Long userId);
    Wallet updateWallet(Long userId, Float money);
    Wallet getWalletByUserId(Long id);
    Wallet getWalletById(Long id);
    Boolean isWalletExist(Long userId);
    Wallet transactAuthorMoney(Long authorId, Float amount);
    Wallet transactAuthorAndReaderMoney(Long authorId, Long readerId, Float amount);
    Statistics getStatistics();
}
