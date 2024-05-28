package com.service.schedule.scheduler;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class StatisticsJob extends QuartzJobBean{

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        // Здесь должен быть ваш код для подсчета статистики
        System.out.println("Calculating statistics...");
        // Примерный код для подсчета статистики:
        // 1. Подсчет количества новых кошельков
        // 2. Вычисление среднего баланса кошельков
    }
}

