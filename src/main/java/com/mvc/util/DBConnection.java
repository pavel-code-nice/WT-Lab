package com.mvc.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static Connection createConnection()
    {
        Connection con = null;
        String url = "jdbc:postgresql://pgsql-server:5432/postgres";
        String username = "postgres";
        String password = "postgres";
        String driverClassName = "org.postgresql.Driver";
        try
        {
            try
            {
                Class.forName(driverClassName);
            }
            catch (ClassNotFoundException e)
            {
                System.out.println("Driver not found "+ driverClassName);
                e.printStackTrace();
            }
            con = DriverManager.getConnection(url, username, password);
            System.out.println("Printing connection object "+con);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        System.out.println(con);
        return con;
    }
}