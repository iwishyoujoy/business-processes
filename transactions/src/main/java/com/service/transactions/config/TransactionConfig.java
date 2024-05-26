package main.java.com.service.transactions.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import com.atomikos.icatch.jta.UserTransactionManager;

public class TransactionConfig implements ApplicationListener<ContextRefreshedEvent> {

    private UserTransactionManager userTransactionManager;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (userTransactionManager == null) {
            userTransactionManager = new UserTransactionManager();
            userTransactionManager.setForceShutdown(false);
            ApplicationContext context = event.getApplicationContext();
            context.getBeanFactory().registerSingleton("transactionManager", userTransactionManager);
        }
    }

    public UserTransactionManager getUtm() {
        return userTransactionManager;
    }
}

