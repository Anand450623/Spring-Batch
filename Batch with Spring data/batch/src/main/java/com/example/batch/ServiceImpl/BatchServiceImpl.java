package com.example.batch.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.batch.Service.BatchService;
import com.example.batch.repository.BatchRepository;

@Service
public class BatchServiceImpl implements BatchService 
{

	@Autowired
	BatchRepository batchRepository;
	
	@Override
	public void flush() 
	{
		batchRepository.deleteAllInBatch();
	}

}
