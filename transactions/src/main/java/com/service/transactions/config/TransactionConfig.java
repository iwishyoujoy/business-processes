package com.service.transactions.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import com.atomikos.icatch.jta.UserTransactionManager;

public class TransactionConfig implements ApplicationListener<ContextRefreshedEvent> {

    private UserTransactionManager userTransactionManager;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event, ConfigurableApplicationContext context) {
        if (userTransactionManager == null) {
            userTransactionManager = new UserTransactionManager();
            userTransactionManager.setForceShutdown(false);
            context.getBeanFactory().registerSingleton("transactionManager", userTransactionManager);
        }
    }

    public UserTransactionManager getUtm() {
        return userTransactionManager;
    }
}
