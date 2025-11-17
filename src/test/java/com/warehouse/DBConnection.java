package com.warehouse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBConnection {
    private static final String DB_URL = "jdbc:sqlserver://DESKTOP-GNGEAP1\\SQLEXPRESS:1433;databaseName=warehouse;encrypt=false;trustServerCertificate=true";
    private static final String DB_USER = "Testercandidate";
    private static final String DB_PASS = "Abc_123";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }
}
