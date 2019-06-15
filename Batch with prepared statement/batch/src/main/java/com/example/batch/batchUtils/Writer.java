package com.example.batch.batchUtils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import com.example.batch.entity.Batch;

public class Writer implements ItemWriter<Batch>
{
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public void write(List<? extends Batch> batchList) throws Exception 
	{
		jdbcTemplate.batchUpdate("insert into batch (field1,field2,field3) VALUES (?,?,?)",
	        new BatchPreparedStatementSetter() 
	    	{
	            @Override
	            public void setValues(PreparedStatement ps, int i) throws SQLException 
	            {
	                ps.setString(1, batchList.get(i).getField1());
	                if(batchList.get(i).getField2()==null)
	                	ps.setNull(2, java.sql.Types.INTEGER);
	                else
	                	ps.setInt(2, batchList.get(i).getField2());	                
	                ps.setDate(3, batchList.get(i).getField3());
	            }
	            @Override
	            public int getBatchSize() {
	                return batchList.size();
	            }
	        });	
	}

}
