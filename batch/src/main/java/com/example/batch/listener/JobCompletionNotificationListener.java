package com.example.batch.listener;


import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class JobCompletionNotificationListener extends JobExecutionListenerSupport
{

    @Autowired
    ThreadPoolTaskExecutor taskExecutor; 

    @Override
    public void afterJob(JobExecution jobExecution) 
    {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) 
        {
            taskExecutor.shutdown();
            System.err.println("*****************");
            System.err.println("\t\tJob Completed");
            System.err.println("*****************");
        }
    }

}