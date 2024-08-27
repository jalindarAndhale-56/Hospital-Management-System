package Hospital_management_System;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Doctor {
	private Connection connection;
	public Doctor(Connection connection) {
		this.connection = connection;
	}

	public void viewDoctors() {
		String viewQuery = " select * from doctors";
		try {
			PreparedStatement ps = connection.prepareStatement(viewQuery);
			ResultSet rs = ps.executeQuery();
			System.out.println("DOCTORS:--> ");
			System.out.println(
					"+--------------+----------------+-----------------+");
			System.out.println(
					"| doctors Id   | Name           | specialization  |");
			System.out.println(
					"+--------------+----------------+-----------------+");
			while (rs.next()) {
				int Id = rs.getInt("id");
				String name = rs.getString("name");
				String specialization = rs.getString("specialization");
				System.out.printf("|%-14s|%-16s|%-17s|\n", Id, name,
						specialization);
				System.out.println(
						"+--------------+----------------+-----------------+");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public boolean getDoctorById(int id) {
		String query = " select * from doctors where id=?";
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}
}
