package com.hangum.db.commons.sql;

import com.hangum.db.define.Define;

public class MSSQLTest {
	private java.sql.Connection con = null;
	private final String url = "jdbc:jtds:sqlserver://";
	private final String serverName = "172.16.31.133";
	private final String portNumber = "1433";
	private final String databaseName = "testdb";
	private final String userName = "sa";
	private final String password = "1234";
	// Informs the driver to use server a side-cursor,
	// which permits more than one active statement
	// on a connection.
//	private final String selectMethod = "cursor";

	// Constructor
	public MSSQLTest() {
	}

	private String getConnectionUrl() {
		return url + serverName + ":" + portNumber + "/"+ databaseName;// + ";selectMethod=" + selectMethod + Define.SQL_DILIMITER;
	}

	private java.sql.Connection getConnection() {
		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			con = java.sql.DriverManager.getConnection(getConnectionUrl(), userName, password);
			if (con != null)
				System.out.println("Connection Successful!");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Trace in getConnection() : "
					+ e.getMessage());
		}
		return con;
	}

	/*
	 * Display the driver properties, database details
	 */

	public void displayDbProperties() {
		java.sql.DatabaseMetaData dm = null;
		java.sql.ResultSet rs = null;
		try {
			con = this.getConnection();
			if (con != null) {
				dm = con.getMetaData();
				System.out.println("Driver Information");
				System.out.println("\tDriver Name: " + dm.getDriverName());
				System.out
						.println("\tDriver Version: " + dm.getDriverVersion());
				System.out.println("\nDatabase Information ");
				System.out.println("\tDatabase Name: "
						+ dm.getDatabaseProductName());
				System.out.println("\tDatabase Version: "
						+ dm.getDatabaseProductVersion());
				System.out.println("Avalilable Catalogs ");
				
				rs = dm.getCatalogs();
				while (rs.next()) {
					System.out.println("\tcatalog: " + rs.getString(1));
				}
				rs.close();
				rs = null;
				closeConnection();
			} else
				System.out.println("Error: No active Connection");
		} catch (Exception e) {
			e.printStackTrace();
		}
		dm = null;
	}

	private void closeConnection() {
		try {
			if (con != null)
				con.close();
			con = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		MSSQLTest myDbTest = new MSSQLTest();
		myDbTest.displayDbProperties();
	}
}
