package com.example.demo.batchutils;

import org.springframework.batch.item.ItemProcessor;

import com.example.demo.dto.StudentDTO;
import com.example.demo.model.Student;

public class Processor implements ItemProcessor<StudentDTO, Student>
{
    @Override
    public Student process(StudentDTO item) throws Exception 
    {
    	Student st = new Student();
    	st.setEnrollmentDate(item.getEnrollmentDate());
    	st.setName(item.getName());
    	st.setRollNo(item.getRollNo());
    	st.setSalary(item.getSalary());
    	st.setSampleTimeStamp(item.getSampleTimeStamp());
    	return st;
    }
}