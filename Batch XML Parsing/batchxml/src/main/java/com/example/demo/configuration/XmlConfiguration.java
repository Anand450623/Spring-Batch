package com.example.demo.configuration;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.example.demo.batchutils.Processor;
import com.example.demo.batchutils.Writer;
import com.example.demo.dto.StudentDTO;
import com.example.demo.listeners.JobListener;
import com.example.demo.utility.StudentConverter;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;

@Configuration
public class XmlConfiguration 
{

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @StepScope
    @Bean(name="xmlReader")
    public SynchronizedItemStreamReader<StudentDTO> reader() 
    {
        StaxEventItemReader<StudentDTO> xmlFileReader = new StaxEventItemReader<>();
        xmlFileReader.setResource(new ClassPathResource("students.xml"));
        xmlFileReader.setFragmentRootElementName("student");

        Map<String, Class<?>> aliases = new HashMap<>();
        aliases.put("student", StudentDTO.class);

        StudentConverter converter = new StudentConverter();
        
        XStreamMarshaller xStreamMarshaller = new XStreamMarshaller();
        xStreamMarshaller.setAliases(aliases);
        xStreamMarshaller.setConverters(converter);
        
        xStreamMarshaller.getXStream().addPermission(NoTypePermission.NONE);
        xStreamMarshaller.getXStream().addPermission(NullPermission.NULL);
        xStreamMarshaller.getXStream().addPermission(PrimitiveTypePermission.PRIMITIVES);
        xStreamMarshaller.getXStream().allowTypeHierarchy(Collection.class);        
        xStreamMarshaller.getXStream().allowTypesByWildcard(new String[] {"com.example.demo.**"});        
        
        xmlFileReader.setUnmarshaller(xStreamMarshaller);
        

        SynchronizedItemStreamReader< StudentDTO> synchronizedItemStreamReader = new SynchronizedItemStreamReader<>();
        synchronizedItemStreamReader.setDelegate(xmlFileReader);
        return synchronizedItemStreamReader;
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
                .taskExecutor(this.taskExecutor())
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