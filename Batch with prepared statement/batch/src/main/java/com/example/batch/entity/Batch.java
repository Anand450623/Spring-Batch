package com.example.batch.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Batch implements Serializable 
{
	
	private static final long serialVersionUID = -5687736664713755991L;

	@Id
	@Column(name="field1")
	private String field1;
	
	@Column(name="field2")
	private Integer field2;

	@Column(name="field3")
	private Date field3;
	
	public Batch() 
	{
		super();
	}

	public Batch(String field1, Integer field2, Date field3) {
		super();
		this.field1 = field1;
		this.field2 = field2;
		this.field3 = field3;
	}

	public String getField1() {
		return field1;
	}

	public void setField1(String field1) {
		this.field1 = field1;
	}

	public Integer getField2() {
		return field2;
	}

	public void setField2(Integer field2) {
		this.field2 = field2;
	}

	public Date getField3() {
		return field3;
	}

	public void setField3(Date field3) {
		this.field3 = field3;
	}

	@Override
	public String toString() {
		return "Batch [field1=" + field1 + ", field2=" + field2 + ", field3=" + field3 + "]";
	}
	
}