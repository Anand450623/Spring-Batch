package com.example.batch.DTO;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

public class Writer implements ItemWriter<FinalItem>
{

	@Override
	public void write(List<? extends FinalItem> items) throws Exception 
	{
		System.err.println(items);
	}

}
