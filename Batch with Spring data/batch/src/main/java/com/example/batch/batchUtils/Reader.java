package com.example.batch.batchUtils;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.example.batch.DTO.InputMapperDTO;

public class Reader implements FieldSetMapper<InputMapperDTO>
{

	@Override
	public InputMapperDTO mapFieldSet(FieldSet fieldSet) throws BindException 
	{
		InputMapperDTO mapper = new InputMapperDTO();
		mapper.setField1(fieldSet.readString("field1"));
		mapper.setField2(fieldSet.readString("field2"));
		mapper.setField3(fieldSet.readString("field3"));
		return mapper;
	}

}