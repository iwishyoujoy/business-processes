package com.wallet.service.messaging;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.JmsListener;

import com.wallet.service.enums.MessageTypes;
import com.wallet.service.model.Message;
import com.wallet.service.service.impl.WalletServiceImpl;

@Configuration
public class MessageReceiver {

    @Autowired
    private WalletServiceImpl walletService;

    @JmsListener(destination = "bookstore")
    public void receiveMessage(Message message) {
        switch (message.getType()) {
            case CREATE:
                walletService.createWallet(message.getAuthorId(), message.getMoney());
                break;
            case DELETE:
                walletService.deleteWallet(message.getAuthorId());
                break;
            case CHECKOUT_AUTHOR:
                walletService.transactAuthorMoney(message.getAuthorId(), message.getMoney());
                break;
            case CHECKOUT_READER:
                walletService.transactAuthorAndReaderMoney(message.getAuthorId(), message.getReaderId(), message.getMoney());
                break;
            default:
                break;
        }
        System.out.println("Received message: " + message.getType());
    }
}
