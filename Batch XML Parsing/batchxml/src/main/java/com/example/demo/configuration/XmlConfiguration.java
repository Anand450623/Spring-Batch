package com.example.demo.configuration;

import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.SynchronizedItemStreamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.example.demo.batchutils.Processor;
import com.example.demo.batchutils.Reader;
import com.example.demo.batchutils.Writer;
import com.example.demo.dto.StudentDTO;
import com.example.demo.listeners.JobListener;
import com.example.demo.model.Student;

@Configuration
public class XmlConfiguration 
{
	
    @StepScope
    @Bean(name="xmlReader")
    public SynchronizedItemStreamReader<StudentDTO> reader() 
    {
    	Reader reader = new Reader();
    	return reader.reader();
    } 

    @Bean(name="xmlProcessor")
    public ItemProcessor<StudentDTO, Student> processor() 
    {
        return new Processor();
    }

    @Bean(name="xmlWriter")
    public ItemWriter<Student> writer() 
    {
        return new Writer();     
    }

    @Bean(name="xmljobListener")
    public JobExecutionListenerSupport jobListener() 
    {
        return new JobListener();
    }

    @JobScope
    @Bean(name="xmltaskExecutor")   
    public ThreadPoolTaskExecutor taskExecutor() 
    {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        return executor;
    }

    @Bean(name="xmlStep")
    public Step xmlFileToDatabaseStep(StepBuilderFactory stepBuilderFactory) 
    {
        return stepBuilderFactory.get("xmlStep")
                .<StudentDTO, Student>chunk(2)
                .reader(this.reader())
                .processor(this.processor())
                .writer(this.writer())
                .taskExecutor(this.taskExecutor())
                .build();
    }

    @Bean(name="xmlJob")
    public Job xmlFileToDatabaseJob(JobBuilderFactory jobBuilderFactory,@Autowired @Qualifier("xmlStep") Step step) 
    {
        return jobBuilderFactory
                .get("xmlJob"+new Date())
                .incrementer(new RunIdIncrementer())
                .listener(this.jobListener())
                .flow(step)
                .end()
                .build();
    }

}