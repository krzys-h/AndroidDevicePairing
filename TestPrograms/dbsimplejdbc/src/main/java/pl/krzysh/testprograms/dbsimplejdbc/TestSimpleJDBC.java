package pl.krzysh.testprograms.dbsimplejdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestSimpleJDBC {
	private static final String DB_URL = "jdbc:mysql://localhost:3306/jdbc-testing";
	private static final String DB_USER = "jdbc-testing";
	private static final String DB_PASS = "12345";

	private static Connection connectToDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("MySQL JDBC Driver not found!");
			e.printStackTrace();
			return null;
		}

		Connection connection = null;
		try {
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
		} catch (SQLException e) {
			System.out.println("Connection failed!");
			e.printStackTrace();
			return null;
		}

		return connection;
	}

	public static void main(String[] args) {
		Connection conn = connectToDB();
		if (conn == null) {
			System.out.println("connectToDB() returned null");
			return;
		}
		System.out.println("Connected!");

		Statement stmt = null;
		
		try {
			stmt = conn.createStatement();
			String query = "CREATE TABLE IF NOT EXISTS `test` (`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY, `data` VARCHAR(128))";
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.println("Exception while creating table!");
			e.printStackTrace();
			return;
		} finally {
			if(stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				stmt = null;
			}
		}
		System.out.println("Created table `test`");
		
		try {
			stmt = conn.createStatement();
			String query = "INSERT INTO `test` (`data`) VALUES ('Hello'), ('World!')";
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.println("Exception while inserting data!");
			e.printStackTrace();
			return;
		} finally {
			if(stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				stmt = null;
			}
		}
		System.out.println("Inserted some data");
		
		try {
			stmt = conn.createStatement();
			String query = "SELECT * FROM `test`";
			ResultSet rs = stmt.executeQuery(query);
			System.out.println("Data from table `test`:");
	        while ( rs.next() ) {
	        	System.out.println("* Row "+rs.getObject("id")+": "+rs.getObject("data"));
	        }
		} catch (SQLException e) {
			System.out.println("Exception while reading data!");
			e.printStackTrace();
			return;
		} finally {
			if(stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				stmt = null;
			}
		}
		
		try {
			stmt = conn.createStatement();
			String query = "DROP TABLE `test`";
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.println("Exception while deleting table!");
			e.printStackTrace();
			return;
		} finally {
			if(stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				stmt = null;
			}
		}
		System.out.println("Removed table `test`");

		try {
			conn.close();
		} catch (SQLException e) {
			System.out.println("Failed to disconnect (how is that even possible?)");
			e.printStackTrace();
		}
		System.out.println("Done!");
	}
}
