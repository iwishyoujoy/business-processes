package com.service.schedule.scheduler;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail jobDetail() {
        return JobBuilder.newJob(StatisticsJob.class).withIdentity("statisticsJob").storeDurably().build();
    }

    @Bean
    public Trigger trigger() {
        return TriggerBuilder.newTrigger()
               .forJob(jobDetail())
               .withIdentity("statisticsTrigger")
               .startNow()
               .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(1)
                    //    .withIntervalInHours(1) // Задает интервал выполнения каждые 24 часа
                       .repeatForever())
               .build();
    }

    @Bean
    public Scheduler scheduler(SchedulerFactoryBean schedulerFactoryBean) throws Exception {
        return schedulerFactoryBean.getObject();
    }
}

