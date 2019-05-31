package com.example.batch.DTO;

public class Processor implements org.springframework.batch.item.ItemProcessor<InputMapperDTO, FinalItem> 
{

	@Override
	public FinalItem process(InputMapperDTO item) throws Exception 
	{
		FinalItem finalItem = new FinalItem();
		finalItem.setName1(item.getField1());
		finalItem.setName2(item.getField2());
		return finalItem;
	}

}
