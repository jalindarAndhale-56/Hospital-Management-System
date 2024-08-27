package Hospital_management_System;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Hospital_management_Sy {
	public static final String url = "jdbc:mysql://localhost:3306/hospital";
	public static final String uName = "root";
	public static final String password = "root";

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		try {
			Connection connection = DriverManager.getConnection(url, uName,
					password);
			Patient patient = new Patient(connection, scanner);
			Doctor doctor = new Doctor(connection);
			while (true) {
				System.out.println("----HOSPITAL MANAGEMENT SYSTEM----");
				System.out.println("1. Add Patient");
				System.out.println("2. View Patients");
				System.out.println("3. View Doctors");
				System.out.println("4. Book Appointment");
				System.out.println("5. Exit");
				System.out.println(" Enter your choice: ");
				int choice = scanner.nextInt();

				switch (choice) {
					case 1 :
						patient.addPatient();
						break;
					case 2 :
						patient.viewPatients();
						break;
					case 3 :
						doctor.viewDoctors();
						break;
					case 4 :
						bookAppointment(patient, doctor, connection, scanner);
						break;
					case 5 :
						return;
					default :
						System.out.println(" enter valid the choice ");
						break;
				}
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public static void bookAppointment(Patient p1, Doctor d1,
			Connection connection, Scanner scanner) {
		System.out.println(" enter the patient id: ");
		int patient_id = scanner.nextInt();
		System.out.println(" enter the doctor id: ");
		int doctor_id = scanner.nextInt();
		System.out.println(" enter the appointment date(DD-MM-YYYY): ");
		String appointmentDate = scanner.next();
		if (p1.getPatientById(patient_id) && d1.getDoctorById(doctor_id)) {
			if (checkDoctorAvailability(doctor_id, appointmentDate,
					connection)) {
				String appoint = " insert into appointment(PATIENT_ID, DOCTOR_ID,APPOINTMENT_DATE) values(?,?,?)";
				try {
					PreparedStatement ps = connection.prepareStatement(appoint);
					ps.setInt(1, patient_id);
					ps.setInt(2, doctor_id);
					ps.setString(3, appointmentDate);
					int rowAffect = ps.executeUpdate();
					if (rowAffect > 0) {
						System.out.println(" Appointment booked ");
					} else {
						System.out.println(" faile to book Appointment!!!!");
					}

				} catch (Exception e) {

				}
			} else {
				System.out.println(" doctor is not available on this date!!! ");
			}

		} else {
			System.out
					.println(" entered doctor or patient does not exist !!!!");
		}
	}

	static public boolean checkDoctorAvailability(int doctorid,
			String appointdate, Connection connection) {
		String query = " select count(*) from appointment where DOCTOR_ID=? and APPOINTMENT_DATE=? ";
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, doctorid);
			ps.setString(2, appointdate);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int count = rs.getInt(1);
				if (count == 0) {
					return true;
				} else {
					return false;
				}
			}
		} catch (Exception e) {

		}
		return false;
	}
}
