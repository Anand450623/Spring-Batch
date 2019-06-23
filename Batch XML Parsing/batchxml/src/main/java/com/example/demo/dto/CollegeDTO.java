package com.example.demo.dto;

public class CollegeDTO 
{
    private String name;
    private String city;
	
    public CollegeDTO() 
	{
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "CollegeDTO [name=" + name + ", city=" + city + "]";
	}
    
}