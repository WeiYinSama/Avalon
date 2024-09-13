package net.avalon.quartz.config;


import net.avalon.quartz.jobs.TestJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Heda
 * @Create: 2024/9/13
 */
@Configuration
public class QuartzConfig {


    @Bean
    public JobDetail testJobDetail() {
        return JobBuilder.newJob(TestJob.class)
                .withIdentity("TestJobDetail")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger testJobTrigger(JobDetail jobDetail) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity("TestJobTrigger")
                // 每10秒执行一次
                .withSchedule(CronScheduleBuilder.cronSchedule("*/3 * * * * ?"))
                .build();
    }
}
