package com.example.batch.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.batch.Service.BatchService;

@Service
public class BatchServiceImpl implements BatchService 
{

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	@Transactional
	public void flush() 
	{
		jdbcTemplate.execute("delete from batch");
	}

}
