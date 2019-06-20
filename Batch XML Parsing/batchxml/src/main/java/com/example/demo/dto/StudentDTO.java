package com.example.demo.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="student")
public class StudentDTO 
{
    private String name;
    private Integer rollNo;    
    private Date enrollmentDate;
    private Date sampleTimeStamp;
    private BigDecimal salary;
    
	public StudentDTO() {
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