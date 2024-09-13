package net.avalon.quartz.jobs;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @Author: Heda
 * @Create: 2024/9/13
 */
//@Component
@Slf4j
public class TestJob implements Job {


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        log.info("当前时间：{}", LocalDateTime.now());
    }
}
