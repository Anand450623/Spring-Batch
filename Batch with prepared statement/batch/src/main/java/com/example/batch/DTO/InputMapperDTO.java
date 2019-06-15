package com.example.batch.DTO;

public class InputMapperDTO 
{
	private String field1;
	private String field2;
	private String field3;
	
	public InputMapperDTO() {
		super();
	}

	public String getField1() {
		return field1;
	}

	public void setField1(String field1) {
		this.field1 = field1;
	}

	public String getField2() {
		return field2;
	}

	public void setField2(String field2) {
		this.field2 = field2;
	}

	public String getField3() {
		return field3;
	}

	public void setField3(String field3) {
		this.field3 = field3;
	}
	
	@Override
	public String toString() {
		return "InputMapperDTO [field1=" + field1 + ", field2=" + field2 + ", field3=" + field3 + "]";
	}

}