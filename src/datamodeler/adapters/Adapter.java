package datamodeler.adapters;

import java.sql.SQLException;
import java.sql.Statement;

import datamodeler.models.Column;
import datamodeler.models.Connection;
import datamodeler.models.Database;
import datamodeler.models.Table;

/**
 * A level of abstraction that keeps us from writing to a particular database
 * 
 * @author Kyle Sletten
 */
public interface Adapter {
    /**
     * Attempt to create a connection using the specified parameters
     * 
     * @param host
     *            the hostname or IP address of the server
     * @param port
     *            the port (the adapter specifies a default port if not
     *            specified)
     * @param username
     *            the username to give the server
     * @param password
     *            the password to give the server
     * @return A string that can be used with DriverManager.getConnection(String
     *         string)
     */
    public Connection getConnection(String host, String port, String username,
	    String password) throws SQLException;

    /**
     * Get information for all of the databases on this server
     * 
     * @param statement
     *            the statement to use
     * @return All of the databases on the server
     * @throws SQLException
     *             passed on from one of the queries performed in the method
     */
    public Iterable<Database> getDatabases(Statement statement)
	    throws SQLException;

    /**
     * Get information for all of the tables in the specified database
     * 
     * @param statement
     *            the statement to use
     * @param database
     *            the database to query
     * @return All of the tables in the specified database
     * @throws SQLException
     *             passed on from one of the queries performed in the method
     */
    public Iterable<Table> getTables(Statement statement, Database database)
	    throws SQLException;

    /**
     * Get information for all of the columns in the specified table
     * 
     * @param statement
     *            the statement to use
     * @param table
     *            the table to query
     * @return All of the columns in the specified table
     * @throws SQLException
     *             passed on from one of the queries performed in the method
     */
    public Iterable<Column> getColumns(Statement statement, Table table)
	    throws SQLException;
}
