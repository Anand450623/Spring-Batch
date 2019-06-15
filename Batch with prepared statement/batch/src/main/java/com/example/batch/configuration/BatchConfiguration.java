package com.example.batch.configuration;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
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
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.InputStreamResource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.example.batch.DTO.InputMapperDTO;
import com.example.batch.batchUtils.Processor;
import com.example.batch.batchUtils.Reader;
import com.example.batch.batchUtils.Writer;
import com.example.batch.entity.Batch;
import com.example.batch.listener.BatchJobListener;

@Configuration
public class BatchConfiguration
{

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @StepScope
    @Bean(name="fileReader")
    public FlatFileItemReader<InputMapperDTO> reader(@Value("#{jobParameters['fileName']}") String fileName) throws IOException 
    {
        FlatFileItemReader<InputMapperDTO> newBean = new FlatFileItemReader<>();
        newBean.setName("fileReader");
        newBean.setResource(new InputStreamResource(FileUtils.openInputStream(new File(fileName))));
        newBean.setLineMapper(this.lineMapper());
        newBean.setLinesToSkip(0);
        return newBean;
    }

    @Bean(name="fileLineMapper")
    public DefaultLineMapper<InputMapperDTO> lineMapper() 
    {
        DefaultLineMapper<InputMapperDTO> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(lineTokenizer());
        Reader reader = new Reader();
        lineMapper.setFieldSetMapper(reader);
        return lineMapper;
    }

    @Bean(name="fileLineTokenizer")
    public DelimitedLineTokenizer lineTokenizer() 
    {
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter("|");
        tokenizer.setNames("field1","field2","field3");
        tokenizer.setIncludedFields(0,1,2);
        return tokenizer;
    }

    @Bean(name="fileProcessor")
    public ItemProcessor<InputMapperDTO, Batch> processor() 
    {
        return new Processor();
    }

    @Bean(name="fileWriter")
    public ItemWriter<Batch> writer() 
    {
        return new Writer();
    }
    
    @JobScope
    @Bean(name="taskExecutor")	
    public ThreadPoolTaskExecutor taskExecutor() 
    {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        return executor;
    }

    @Bean(name="jobCompletionListener")
    public JobExecutionListenerSupport jobCompletionlistener() 
    {
        return new BatchJobListener();
    }
    
    @Bean(name="fileStep")
    public Step step() throws IOException 
    {
        return stepBuilderFactory.get("fileStep")
                .<InputMapperDTO, Batch>chunk(500)
                .reader(this.reader(null))
                .processor(this.processor())
                .writer(this.writer())
                .taskExecutor(this.taskExecutor())
                .build();
    }

    @Bean(name="fileJob")
    public Job importUserJob(@Autowired @Qualifier("fileStep") Step step) 
    {
        return jobBuilderFactory
                .get("fileJob"+new Date())
                .incrementer(new RunIdIncrementer())
                .listener(this.jobCompletionlistener())
                .flow(step)
                .end()
                .build();
    }
    
}
