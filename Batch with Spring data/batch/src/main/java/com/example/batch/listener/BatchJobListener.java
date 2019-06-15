package com.example.batch.listener;


import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.example.batch.Service.BatchService;

public class BatchJobListener extends JobExecutionListenerSupport
{

    @Autowired
    ThreadPoolTaskExecutor taskExecutor;
    
    @Autowired
    BatchService batchService;
    
    @Override
	public void beforeJob(JobExecution jobExecution) 
    {
    	batchService.flush();
	}

    @Override
    public void afterJob(JobExecution jobExecution) 
    {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) 
        {
            taskExecutor.shutdown();
        }
    }

}