package com.wallet.service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.wallet.service.dto.MultipleWalletRequest;
import com.wallet.service.dto.WalletRequest;
import com.wallet.service.model.Wallet;
import com.wallet.service.service.impl.WalletServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;

@Tag(
    name = "REST APIs for Books",
    description = "REST APIs - Create Book, Update Book, Get Book by id, Get All Books"
)
@RestController
@RequestMapping("/api/wallets")
public class WalletController {

    private final WalletServiceImpl walletService;

    public WalletController(WalletServiceImpl walletService) {
        this.walletService = walletService;
    }

    @Operation(
        summary = "Create Wallet"
    )
    @ApiResponse(
        responseCode = "201",
        description = "HTTP Status 201 CREATED"
    )
    @PostMapping
    public ResponseEntity<Wallet> createWallet(@Valid @RequestBody WalletRequest walletRequest) {
        return new ResponseEntity<>(walletService.createWallet(walletRequest.getUserId(), walletRequest.getMoney()), HttpStatus.CREATED);
    }

    @Operation(
        summary = "Get Wallet by  user id"
    )
    @ApiResponse(
        responseCode = "200",
        description = "HTTP Status 200 OK"
    )
    @GetMapping("/{userId}")
    public ResponseEntity<Wallet> getBookById(@PathVariable Long userId) {
        return new ResponseEntity<>(walletService.getWalletByUserId(userId), HttpStatus.OK);
    }

    @Operation(
        summary = "Transact Author money (after creating a book)"
    )
    @PostMapping("/author")
    public ResponseEntity<Wallet> transactAuthorMoney(@RequestBody WalletRequest walletRequest) {
        return new ResponseEntity<>(walletService.transactAuthorMoney(walletRequest.getUserId(), walletRequest.getMoney()), HttpStatus.OK);    
    }

    @Operation(
        summary = "Transact Reader money (after buying a book)"
    )
    @PostMapping("/reader")
    public ResponseEntity<Wallet> transactReaderMoney(@RequestBody MultipleWalletRequest multipleWalletRequest) {
        return new ResponseEntity<>(walletService.transactAuthorAndReaderMoney(multipleWalletRequest.getAuthorId(), multipleWalletRequest.getReaderId(), multipleWalletRequest.getMoney()), HttpStatus.OK);    
    }

    @Operation(
        summary = "Update wallet"
    )
    @PatchMapping
    public ResponseEntity<Wallet> updateWallet(@RequestBody WalletRequest walletRequest) {
        return new ResponseEntity<>(walletService.updateWallet(walletRequest.getUserId(), walletRequest.getMoney()), HttpStatus.OK);
    }    
    
    @Operation(
        summary = "Delete Wallet by user id"
    )
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteBookById(@PathVariable Long userId){
        if (!walletService.isWalletExist(userId)) {
            return new ResponseEntity<String>("Couldn't find a wallet with this user ID", HttpStatus.NOT_FOUND);
        }
        walletService.deleteWallet(userId);
        return new ResponseEntity<String>("Wallet was deleted successfully", HttpStatus.OK);
    }
}

