package com.example.batch.controller;

import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BatchController 
{
	@Autowired
	Job job;
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@GetMapping(value="/run")
	public String run() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException
	{
		long st = System.currentTimeMillis();
		JobParametersBuilder builder = new JobParametersBuilder();
		builder.addDate("date", new Date());
		builder.addString("fileName","src/main/resources/Test.txt");
		jobLauncher.run(job, builder.toJobParameters());
		return "The processing took = "+(System.currentTimeMillis()-st)+" ms<br>The current timeStamp is = "+new Date();
	}
}
