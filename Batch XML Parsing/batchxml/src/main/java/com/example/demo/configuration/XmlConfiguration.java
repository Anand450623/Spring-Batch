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
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.example.demo.batchutils.Processor;
import com.example.demo.batchutils.Writer;
import com.example.demo.dto.StudentDTO;
import com.example.demo.listeners.JobListener;

@Configuration
public class XmlConfiguration 
{
	
    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    StepBuilderFactory stepBuilderFactory;
    
	@Bean(name="xmlReader")
	@StepScope
    public ItemStreamReader<StudentDTO> reader() 
	{
        StaxEventItemReader<StudentDTO> xmlFileReader = new StaxEventItemReader<>();
        xmlFileReader.setResource(new ClassPathResource("students.xml"));
        xmlFileReader.setFragmentRootElementName("student");
 
        Jaxb2Marshaller studentMarshaller = new Jaxb2Marshaller();
        studentMarshaller.setClassesToBeBound(StudentDTO.class);
        xmlFileReader.setUnmarshaller(studentMarshaller);
 
        return xmlFileReader;
    }
	
    @Bean(name="xmlProcessor")
    public ItemProcessor<StudentDTO, StudentDTO> processor() 
    {
        return new Processor();
    }

    @Bean(name="xmlWriter")
    public ItemWriter<StudentDTO> writer() 
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
        executor.setCorePoolSize(50);
        executor.setMaxPoolSize(100);
        return executor;
    }
    
    @Bean(name="xmlStep")
    public Step xmlFileToDatabaseStep() 
    {
        return stepBuilderFactory.get("xmlStep")
                .<StudentDTO, StudentDTO>chunk(1)
                .reader(this.reader())
                .processor(this.processor())
                .writer(this.writer())
                //.taskExecutor(this.taskExecutor())
                .build();
    }

    @Bean(name="xmlJob")
    public Job xmlFileToDatabaseJob(@Autowired @Qualifier("xmlStep") Step step) 
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
