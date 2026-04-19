package com.hotguy.warehouse13.dataservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Provides JDBC connections to the warehouse MySQL database. */
public class DatabaseConnection {

    private static final String URL  = "jdbc:mysql://bbdd:3306/warehouse13";
    private static final String USER = "warehouse13_user";
    private static final String PASS = "onlyforyoureyes";

    /** Utility class — no instances needed. */
    private DatabaseConnection() {}

    /**
     * Opens and returns a new JDBC connection.
     *
     * @return live {@link Connection}
     * @throws SQLException           if the connection cannot be established
     * @throws ClassNotFoundException if the MySQL JDBC driver is not on the classpath
     */
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
