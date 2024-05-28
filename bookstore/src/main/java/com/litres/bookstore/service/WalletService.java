package com.litres.bookstore.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.litres.bookstore.exception.ResourceNotFoundException;
import com.litres.bookstore.model.MultipleWalletRequest;
import com.litres.bookstore.model.Wallet;
import com.litres.bookstore.model.WalletRequest;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class WalletService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String urlPathname = "http://localhost:8090/api/wallets"; 


    public Wallet createWallet(WalletRequest walletRequest) {
        ResponseEntity<Wallet> walletResponse = restTemplate.exchange(urlPathname, HttpMethod.POST, new HttpEntity<>(walletRequest), Wallet.class);
        if (walletResponse.getStatusCode() != HttpStatus.CREATED){
            throw new IllegalArgumentException("Wallet wasn't created properly, try later");
        }
        return walletResponse.getBody();
    }

    public Wallet getWalletByUserId(Long userId) {
        String url = urlPathname + "/" + String.valueOf(userId);
        ResponseEntity<Wallet> walletResponse = restTemplate.exchange(url, HttpMethod.GET, null, Wallet.class);
        if (walletResponse.getStatusCode() != HttpStatus.OK){
            throw new ResourceNotFoundException("wallet", "userId", String.valueOf(userId));
        }
        return walletResponse.getBody();
    }

    public Wallet transactAuthorMoney(WalletRequest walletRequest) {
        String url = urlPathname + "/author";
        ResponseEntity<Wallet> walletResponse = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(walletRequest), Wallet.class);
        if (walletResponse.getStatusCode() != HttpStatus.OK){
            throw new ResourceNotFoundException("wallet", "userId", String.valueOf(walletRequest.getUserId()));
        }
        return walletResponse.getBody();
    }

    public Wallet transactReaderMoney(MultipleWalletRequest multipleWalletRequest) {
        String url = urlPathname + "/reader";
        ResponseEntity<Wallet> walletResponse = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(multipleWalletRequest), Wallet.class);
        if (walletResponse.getStatusCode() != HttpStatus.OK){
            throw new IllegalArgumentException("Couldn't transact reader's money, see logs");
        }
        return walletResponse.getBody();
    }

    public Wallet updateWallet(WalletRequest walletRequest) {
        ResponseEntity<Wallet> walletResponse = restTemplate.exchange(urlPathname, HttpMethod.PATCH, new HttpEntity<>(walletRequest), Wallet.class);
        if (walletResponse.getStatusCode() != HttpStatus.OK){
            throw new ResourceNotFoundException("wallet", "userId", String.valueOf(walletRequest.getUserId()));
        }
        return walletResponse.getBody();
    }

    public String deleteWalletByUserId(Long userId) {
        String url = urlPathname + "/" + String.valueOf(userId);
        ResponseEntity<String> walletResponse = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
        if (walletResponse.getStatusCode() != HttpStatus.OK){
            throw new ResourceNotFoundException("wallet", "userId", String.valueOf(userId));
        }
        return walletResponse.getBody();
    }
}
