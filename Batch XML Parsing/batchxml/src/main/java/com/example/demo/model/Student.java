package com.example.demo.model;

import java.math.BigDecimal;
import java.util.Date;

public class Student 
{
	
    private String name;
    private Integer rollNo;    
    private Date enrollmentDate;
    private Date sampleTimeStamp;
    private BigDecimal salary;
	
    public Student() 
	{
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getRollNo() {
		return rollNo;
	}

	public void setRollNo(Integer rollNo) {
		this.rollNo = rollNo;
	}

	public Date getEnrollmentDate() {
		return enrollmentDate;
	}

	public void setEnrollmentDate(Date enrollmentDate) {
		this.enrollmentDate = enrollmentDate;
	}

	public Date getSampleTimeStamp() {
		return sampleTimeStamp;
	}

	public void setSampleTimeStamp(Date sampleTimeStamp) {
		this.sampleTimeStamp = sampleTimeStamp;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "StudentDTO [name=" + name + ", rollNo=" + rollNo + ", enrollmentDate=" + enrollmentDate
				+ ", sampleTimeStamp=" + sampleTimeStamp + ", salary=" + salary + "]";
	}

}