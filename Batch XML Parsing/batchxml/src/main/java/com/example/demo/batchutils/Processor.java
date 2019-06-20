package com.example.demo.batchutils;

import org.springframework.batch.item.ItemProcessor;

import com.example.demo.dto.StudentDTO;

public class Processor implements ItemProcessor<StudentDTO, StudentDTO>
{
    @Override
    public StudentDTO process(StudentDTO item) throws Exception 
    {
        StudentDTO st = item;
        return st;
    }
}