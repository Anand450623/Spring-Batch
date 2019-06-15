package com.example.batch.batchUtils;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.batch.entity.Batch;
import com.example.batch.repository.BatchRepository;

public class Writer implements ItemWriter<Batch>
{
	
	@Autowired
	BatchRepository batchRepository;

	@Override
	public void write(List<? extends Batch> batchList) throws Exception 
	{
		batchRepository.saveAll(batchList);
	}

}
