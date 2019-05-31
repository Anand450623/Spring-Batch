package com.example.batch.utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class DataWriter 
{
	public static void main(String[] args) 
	{
		try
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File("C:\\Users\\ak450\\OneDrive\\Desktop\\Test.txt")));
			for(int i=0;i<1000;i++)
			{
				writer.write("field1|field2|field3\n");
			}
			writer.close();
		}
		catch(Exception ee)
		{
			ee.printStackTrace();
		}
	}
}
