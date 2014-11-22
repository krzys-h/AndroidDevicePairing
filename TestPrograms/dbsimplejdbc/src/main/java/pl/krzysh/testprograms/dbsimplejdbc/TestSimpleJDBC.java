package pl.krzysh.testprograms.dbsimplejdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestSimpleJDBC {
	private static String DB_URL = "jdbc:mysql://localhost:3306/jdbc-testing";
	private static String DB_USER = "jdbc-testing";
	private static String DB_PASS = "12345";

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("MySQL JDBC Driver not found!");
			e.printStackTrace();
			return;
		}

		Connection connection = null;
		try {
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
		} catch (SQLException e) {
			System.out.println("Connection failed!");
			e.printStackTrace();
			return;
		}
		
		if(connection == null) {
			System.out.println("connection == null!");
			return;
		}

		System.out.println("Connected!");
	}
}
