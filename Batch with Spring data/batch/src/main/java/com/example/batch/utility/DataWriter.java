/*package com.example.batch.utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataWriter 
{
	public static void main(String[] args) 
	{
		try
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File("src\\main\\resources\\Test.txt")));
			for(int i=0;i<25000;i++)
			{
				writer.write("field"+i+"|"+i+"|"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+"\n");
			}
			writer.close();
		}
		catch(Exception ee)
		{
			ee.printStackTrace();
		}
	}
}
*/