package com.example.demo.dto;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("group")
public class GroupDTO 
{
    @XStreamAlias("college")
    private CollegeDTO college;

    @XStreamAlias("student")
    private List<StudentDTO> list;

	public GroupDTO() 
	{
		super();
	}

	public CollegeDTO getCollege() {
		return college;
	}

	public void setCollege(CollegeDTO college) {
		this.college = college;
	}

	public List<StudentDTO> getList() {
		return list;
	}

	public void setList(List<StudentDTO> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "GroupDTO [college=" + college + ", list=" + list + "]";
	}

}