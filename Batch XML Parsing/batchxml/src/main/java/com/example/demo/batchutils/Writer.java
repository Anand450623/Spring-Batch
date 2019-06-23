package com.example.demo.batchutils;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

import com.example.demo.model.Student;

public class Writer implements ItemWriter<Student>
{
    @Override
    public void write(List<? extends Student> items) throws Exception 
    {
    	System.err.println("*************************************************************");
        items.stream().forEach(i->System.err.println(i));
        System.err.println("*************************************************************");
    }
}