package com.example.batch.DTO;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class Reader implements FieldSetMapper<InputMapperDTO>
{

	@Override
	public InputMapperDTO mapFieldSet(FieldSet fieldSet) throws BindException 
	{
		InputMapperDTO mapper = new InputMapperDTO();
		mapper.setField1(fieldSet.readString("field1"));
		mapper.setField2(fieldSet.readString("field3"));
		return mapper;
	}

}
