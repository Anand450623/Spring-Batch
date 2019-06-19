package com.example.demo.listeners;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class JobListener extends JobExecutionListenerSupport 
{
	
	@Autowired
    @Qualifier("xmltaskExecutor")
    ThreadPoolTaskExecutor taskExecutor; 
	
    @Override
    public void afterJob(JobExecution jobExecution) 
    {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) 
        {
            taskExecutor.shutdown();
            System.err.println("*****************");
            System.err.println("\tJob Completed");
            System.err.println("*****************");
        }
        else
        {
            System.err.println("*****************");
            System.err.println("\tJob Failed");
            System.err.println("*****************");        	
        }
    }
    
    @Override
	public void beforeJob(JobExecution jobExecution) 
    {
    	
	}

}
