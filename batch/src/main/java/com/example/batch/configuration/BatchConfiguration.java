package com.example.batch.configuration;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
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
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.io.InputStreamResource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.example.batch.DTO.FinalItem;
import com.example.batch.DTO.InputMapperDTO;
import com.example.batch.DTO.Processor;
import com.example.batch.DTO.Reader;
import com.example.batch.DTO.Writer;
import com.example.batch.listener.JobCompletionNotificationListener;

@Configuration
public class BatchConfiguration
{

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Bean(name="jobCompletionListener")
    public JobCompletionNotificationListener jobCompletionlistener() 
    {
        return new JobCompletionNotificationListener();
    }

    @Bean(name="taskExecutor")	// This bean is creating issues...
    @Scope(value="step", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public ThreadPoolTaskExecutor taskExecutor() 
    {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(5);
        return executor;
    }

    @Bean(name="fileReader")
    @Scope(value="step", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public FlatFileItemReader<InputMapperDTO> reader(@Value("#{jobParameters['fileName']}") String fileName) throws IOException 
    {
        FlatFileItemReader<InputMapperDTO> newBean = new FlatFileItemReader<>();
        newBean.setName("fileReader");
        newBean.setResource(new InputStreamResource(FileUtils.openInputStream(new File(fileName))));
        newBean.setLineMapper(this.lineMapper());
        newBean.setLinesToSkip(1);
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
        tokenizer.setNames("field1","field3");
        tokenizer.setIncludedFields(0,2);
        return tokenizer;
    }

    @Bean(name="fileProcessor")
    public ItemProcessor<InputMapperDTO, FinalItem> processor() 
    {
        return new Processor();
    }

    @Bean(name="fileWriter")
    public ItemWriter<FinalItem> writer() 
    {
        return new Writer();
    }

    @Bean(name="fileStep")
    public Step step1() throws IOException 
    {
        return stepBuilderFactory.get("fileStep")
                .<InputMapperDTO, FinalItem>chunk(10)
                .reader(this.reader(null))
                .processor(this.processor())
                .writer(this.writer())
                .taskExecutor(this.taskExecutor())
                .build();
    }

    @Bean(name="fileJob")
    public Job importUserJob(@Autowired @Qualifier("fileStep") Step step1) 
    {
        return jobBuilderFactory
                .get("fileJob"+new Date())
                .incrementer(new RunIdIncrementer())
                .listener(this.jobCompletionlistener())
                .flow(step1)
                .end()
                .build();
    }
    
}
