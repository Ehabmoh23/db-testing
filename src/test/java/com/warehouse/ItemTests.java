package com.warehouse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertTrue;
public class ItemTests {
    private Connection conn;
    private Statement stmt;

    @BeforeEach
    public void setUp() throws SQLException {
        conn = DBConnection.getConnection();
        stmt = conn.createStatement();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        if (stmt != null) stmt.close();
        if (conn != null) conn.close();
    }
    @Test
    public void testNoNullRequiredColumns() throws SQLException {

        ResultSet rs = stmt.executeQuery(
                "SELECT * FROM Items WHERE Name IS NULL OR ItemCategoryId IS NULL"
        );

        assertTrue(!rs.next(), "Some mandatory columns have NULL values!");

    }

    @Test
    public void testNoDuplicateNames() throws SQLException {

        ResultSet rs = stmt.executeQuery(
                "SELECT Name, COUNT(*) AS count FROM Items GROUP BY Name HAVING COUNT(*) > 1"
        );

        assertTrue(!rs.next(), "Duplicate item names found!");

    }

    @Test
    public void testPrimaryKeyExists() throws SQLException {
        ResultSet rs = stmt.executeQuery(
                "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE WHERE TABLE_NAME='Items' AND CONSTRAINT_NAME LIKE 'PK%'"
        );

        assertTrue(rs.next(), "Primary key not found!");

    }

    @Test
    public void testForeignKeys() throws SQLException {

        ResultSet rs = stmt.executeQuery(
                "SELECT f.name AS FK_Name, " +
                        "OBJECT_NAME(f.parent_object_id) AS TableName, " +
                        "COL_NAME(fc.parent_object_id, fc.parent_column_id) AS ColumnName, " +
                        "OBJECT_NAME(f.referenced_object_id) AS RefTable, " +
                        "COL_NAME(fc.referenced_object_id, fc.referenced_column_id) AS RefColumn " +
                        "FROM sys.foreign_keys AS f " +
                        "INNER JOIN sys.foreign_key_columns AS fc ON f.OBJECT_ID = fc.constraint_object_id " +
                        "WHERE OBJECT_NAME(f.parent_object_id) = 'Items'"
        );

        assertTrue(rs.next(), "Foreign keys not found!");

    }
}
