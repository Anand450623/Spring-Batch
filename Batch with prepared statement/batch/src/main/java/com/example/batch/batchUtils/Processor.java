package com.example.batch.batchUtils;

import java.sql.Date;
import java.text.SimpleDateFormat;

import com.example.batch.DTO.InputMapperDTO;
import com.example.batch.entity.Batch;

public class Processor implements org.springframework.batch.item.ItemProcessor<InputMapperDTO, Batch> 
{

	@Override
	public Batch process(InputMapperDTO item) throws Exception 
	{
		Batch batch = new Batch();
		batch.setField1(item.getField1());
		batch.setField2(Integer.parseInt(item.getField2()));
		batch.setField3(new Date(new SimpleDateFormat("dd-MM-yyyy").parse(item.getField3()).getTime()));
		return batch;
	}

}