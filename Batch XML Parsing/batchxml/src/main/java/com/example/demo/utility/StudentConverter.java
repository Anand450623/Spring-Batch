package com.example.demo.utility;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.example.demo.dto.StudentDTO;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class StudentConverter implements Converter
{

    @Override
    public boolean canConvert(@SuppressWarnings("rawtypes") Class type) 
    {
        return type.equals(StudentDTO.class);
    }

    @Override
    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) 
    {
        // Nothing to marshal
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) 
    {
        reader.moveDown();      
        StudentDTO student = new StudentDTO();
        student.setName(reader.getValue());

        reader.moveUp();
        reader.moveDown();
        student.setRollNo(Integer.parseInt(reader.getValue()));

        reader.moveUp();
        reader.moveDown();
        try 
        {
            student.setEnrollmentDate(new SimpleDateFormat("yyyy-MM-dd").parse(reader.getValue()));
        } 
        catch (ParseException e) 
        {
            e.printStackTrace();
        }

        reader.moveUp();
        reader.moveDown();
        try 
        {
            student.setSampleTimeStamp(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(reader.getValue()));
        }
        catch (ParseException e) 
        {
            e.printStackTrace();
        }

        reader.moveUp();
        reader.moveDown();
        student.setSalary(new BigDecimal(reader.getValue()));

        return student;
    }

}