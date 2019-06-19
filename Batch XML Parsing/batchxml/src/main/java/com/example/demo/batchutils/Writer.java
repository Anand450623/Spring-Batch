package com.example.demo.batchutils;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

import com.example.demo.dto.StudentDTO;

public class Writer implements ItemWriter<StudentDTO>
{
	@Override
	public void write(List<? extends StudentDTO> items) throws Exception 
	{
		items.stream().forEach(i->System.err.println(i));
	}
}
