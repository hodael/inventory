package com.example.restwithspringboot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class dbController {

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "org.h2.Driver";
	static final String DB_URL = "jdbc:h2:~/test";

	private static final String QUERY_SELECT_BY_ID = "SELECT id,product_name ,product_amount,inventory_code FROM TBL_INVENTORY WHERE id =?";
	private static final String QUERY_SELECT_ALL = "SELECT id,product_name ,product_amount,inventory_code FROM TBL_INVENTORY";
	private static final String QUERY_UPDATE = "UPDATE TBL_INVENTORY SET product_amount = ? WHERE id = ?";
	private static final String QUERY_DELETE_BY_ID = "DELETE FROM TBL_INVENTORY WHERE id = ?";
	private final String QUERY_INSERT = "INSERT INTO TBL_INVENTORY"
			+ "  (product_name, product_amount, inventory_code) VALUES " + " (?, ?, ?);";

	// Database credentials
	String username;
	String password;
	static Connection connection;

	public dbController(String username, String password) {
		this.username = username;
		this.password = password;
		connection = null;
	}

	public static Connection getConnection() {
		try {
			connection = DriverManager.getConnection(DB_URL, "sa", "");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public void create_database() {
		Connection conn = null;
		Statement stmt = null;
		try {
			// Register JDBC driver
			Class.forName(JDBC_DRIVER);

			// Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, username, password);

			// Execute a query
			System.out.println("Creating table in given database...");
			stmt = conn.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS TBL_INVENTORY ( " + " id INT AUTO_INCREMENT  PRIMARY KEY, "
					+ " product_name VARCHAR(250), " + " product_amount INTEGER, " + " inventory_code INTEGER " + " )";
			stmt.executeUpdate(sql);
			System.out.println("Created table in given database...");

			// Clean-up environment
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		System.out.println("Goodbye!");

	}

	public String showTable() throws SQLException {

		String ans = "";
// using try-with-resources to avoid closing resources (boiler plate code)

//Establishing a Connection
		try (Connection connection = dbController.getConnection();

				// Create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(QUERY_SELECT_ALL);) {
			// Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Process the ResultSet object.
			while (rs.next()) {
				int id = rs.getInt("id");
				String product_name = rs.getString("product_name");
				String product_amount = rs.getString("product_amount");
				String inventory_code = rs.getString("inventory_code");

				ans = ans + "***|  Product id:" + id + " | Product name:" + product_name + " | Product amount:"
						+ product_amount + " | Inventory code: " + inventory_code + " |***";
			}

		}
		return ans;
	}

	public String readProductDetails(int id) {
		String ans = "";
// using try-with-resources to avoid closing resources (boiler plate code)

// Establishing a Connection
		try (Connection connection = dbController.getConnection();

				// Create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(QUERY_SELECT_BY_ID);) {
			preparedStatement.setInt(1, id);
			// Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Process the ResultSet object.
			while (rs.next()) {
				int i_id = rs.getInt("id");
				if (id == i_id) {
					String product_name = rs.getString("product_name");
					String product_amount = rs.getString("product_amount");
					String inventory_code = rs.getString("inventory_code");
					ans = ans + "***|  Product id:" + id + " | Product name:" + product_name + " | Product amount:"
							+ product_amount + " | Inventory code: " + inventory_code + " |***";
				}

			}
		} catch (SQLException e) {

		}
//try-with-resource statement will auto close the connection.
		return ans;

	}

	public String insertRecord(String product_name, int product_amount, int inventory_code) throws SQLException {
		String ans = "";
		// Establishing a Connection
		try (Connection connection = dbController.getConnection();
				// Create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INSERT)) {
			preparedStatement.setString(1, product_name);
			preparedStatement.setInt(2, product_amount);
			preparedStatement.setInt(3, inventory_code);

			// Execute the query or update query
			preparedStatement.executeUpdate();
			connection.close();
			ans = "New record was inserted successfully!";
			return ans;
		} catch (SQLException e) {
			ans = "There was a problem with the operation, please try again or ask for technical support";

		}
		return ans;
		// try-with-resource statement will auto close the connection.
	}

	public String readProductQuantity(int id) {
		String ans = "";
// using try-with-resources to avoid closing resources (boiler plate code)

//Establishing a Connection
		try (Connection connection = dbController.getConnection();

				// Create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(QUERY_SELECT_BY_ID);) {
			preparedStatement.setInt(1, id);
			// Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Process the ResultSet object.
			while (rs.next()) {
				int i_id = rs.getInt("id");
				if (id == i_id) {
					String product_amount = rs.getString("product_amount");
					String product_name = rs.getString("product_name");
					ans = ans + "*** Product quantity of the product " + product_name + " with the chosen id: " + id
							+ " is: " + product_amount + " ***";
				}

			}
		} catch (SQLException e) {

		}
//try-with-resource statement will auto close the connection.
		return ans;
	}

	public String updateRecord(int id, String value) throws SQLException {
		String ans = "";
//Establishing a Connection
		try (Connection connection = dbController.getConnection();
				// Create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(QUERY_UPDATE)) {
			preparedStatement.setString(1, value);
			preparedStatement.setInt(2, id);

			// Execute the query or update query
			preparedStatement.executeUpdate();
			ans = "The row was updated successfully!";
		} catch (SQLException e) {

			ans = "There was a problem with the operation, please try again or ask for technical support";

		}
		return ans;

		//try-with-resource statement will auto close the connection.
	}

	public String deleteProduct(int id) throws SQLException {
		String ans = "";
		//Establishing a Connection
		try (Connection connection = dbController.getConnection();
				// Create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(QUERY_DELETE_BY_ID)) {
			preparedStatement.setInt(1, id);

			// Execute the query or update query
			preparedStatement.executeUpdate();
			ans = "The row was deleted successfully!";
		} catch (SQLException e) {

			ans = "There was a problem with the operation, please try again or ask for technical support";
		}
		return ans;

	}
}