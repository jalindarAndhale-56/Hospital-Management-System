package Hospital_management_System;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Patient 
{
	private Connection connection;
	
	private Scanner scanner;
	
	public Patient(Connection connection, Scanner scanner)
	{
		this.connection=connection;
		this.scanner=scanner;
	}
	
	public void addPatient()
	{
		System.out.println(" enter patient name:");
		String name=scanner.next();
		System.out.println(" enter patient age:");
		int age=scanner.nextInt();
		System.out.println(" enter patient gender:");
		String gender=scanner.next();
		
		// inserting the data into data base
		try
		{
			String query=" insert into patients(name,age,gender) values(?,?,?)";
			PreparedStatement ps=connection.prepareStatement(query);
			ps.setString(1, name);
			ps.setInt(2, age);
			ps.setString(3, gender);
			int affect=ps.executeUpdate();
			if(affect>0)
			{
				System.out.println(" patient detail inserted ");
			}else {
				System.out.println(" not affected ");
			}
			
		}catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	public void viewPatients()
	{
		String viewQuery=" select * from patients";
		try
		{
			PreparedStatement ps=connection.prepareStatement(viewQuery);
			ResultSet rs=ps.executeQuery();
			System.out.println("Patients:--> ");
			System.out.println("+--------------+----------------+----------+-----------------+");
			System.out.println("| Patient Id   | Name           | Age      | Gender          |");
			
			while(rs.next())
			{
				int pId=rs.getInt("id");
				String pname=rs.getString("name");
				int page=rs.getInt("age");
				String pgen=rs.getString("gender");
				System.out.printf("|%-14s|%-16s|%-10s|%-17s|\n",pId, pname, page, pgen);
				System.out.println("+--------------+----------------+----------+-----------------+");
			}		
		}catch(Exception e)
		{
			System.out.println(e);
		}		
	}
		
	public boolean getPatientById( int id)
	{
		String query=" select * from patients where id=?";
			try 
			{
				PreparedStatement ps=connection.prepareStatement(query);
				ps.setInt(1, id);
				ResultSet rs=ps.executeQuery();
				if(rs.next())
				{
					return true;
				}else {
					return false;
				}
								
			}catch(Exception e)
			{
				System.out.println(e);
			}
			return false;

	}	
}
