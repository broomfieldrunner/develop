package test.demo.com;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {

	static Connection conn;
	static String url = "jdbc:sqlite:C:/sqlite/sqlite_test.db";

	public static void createNewDatabase() throws ClassNotFoundException {

		try (Connection conn = DriverManager.getConnection(url)) {
			if (conn != null) {
				DatabaseMetaData meta = conn.getMetaData();
				System.out.println("The driver name is " + meta.getDriverName());
				System.out.println("A new database has been created.");
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void createNewTable() throws ClassNotFoundException, SQLException {

		System.out.println("Creating table in given database...");

		// SQL statement for creating a new table
		String sql = "CREATE TABLE IF NOT EXISTS LastConsAssignment  (last_cons_id  VARCHAR(50) UNIQUE)";

		try {
			System.out.println(" before Table is creating");
			conn = DriverManager.getConnection(url);
			Statement stmt = conn.createStatement();
			stmt.execute(sql);
			System.out.println("Table is created");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	public static String initialCheck() {
		String query = "SELECT last_cons_id FROM LastConsAssignment";
		String lastConsVal = null;
		
		try {
			createNewDatabase();
			createNewTable();
			conn = DriverManager.getConnection(url);

			try (Statement stmt = conn.createStatement(); 
				 ResultSet rs = stmt.executeQuery(query)) 
			    {

				// loop through the result set
				while (rs.next()) {

					lastConsVal = rs.getString("last_cons_id");
					//System.out.println(rs.getString(" Last Cons Id " + lastConsVal));

				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return lastConsVal;
	}

	public static void insert(String lasttracking) {
		PreparedStatement pstmt = null;

		System.out.println(" This insert lasttracking ***************" + lasttracking);
		

		try {

			pstmt = conn.prepareStatement("insert into LastConsAssignment(last_cons_id ) values (?)");
			pstmt.setString(1, lasttracking);

			int row = pstmt.executeUpdate();
			System.out.println(row);

			System.out.println("lastConsAssignment" + lasttracking);

			// conn.commit();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			// pstmt.close();
			// fis.close();
			// conn.close();
		}
	}
	
	public static void update(String oldLastConsId, String newLastConsId) {
		

		System.out.println(" This is update newLastConsId ***************" + newLastConsId);

		try {
			
			 String sqlUpdate = "UPDATE LastConsAssignment " +
                                "SET last_cons_id = ? where last_cons_id = ?";

			
			PreparedStatement pstmt = conn.prepareStatement(sqlUpdate);
				 
	            // prepare data for update
	           
	            pstmt.setString(1, newLastConsId);
	            pstmt.setString(2, oldLastConsId);
	            
	 
	            int rowAffected = pstmt.executeUpdate();
	            System.out.println(String.format("Row affected %d", rowAffected));
				      
			
			// conn.commit();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			// pstmt.close();
			// fis.close();
			// conn.close();
		}
	}
}
