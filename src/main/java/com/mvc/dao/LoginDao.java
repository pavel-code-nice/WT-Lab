package com.mvc.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import com.mvc.bean.LoginBean;
import com.mvc.util.DBConnection;

public class LoginDao {
    public String[] authenticateUser(LoginBean loginBean)
    {
        String userName = loginBean.getUserName();
        String password = loginBean.getPassword();

        Connection con = null;
        Statement statement = null;
        ResultSet resultSet = null;

        String userNameDB = "";
        String passwordDB = "";
        int roleDB = 0;

        try
        {
            con = DBConnection.createConnection();
            statement = con.createStatement();

            //get user data
            resultSet = statement.executeQuery("select id ,login, password, role from users where login =\'" + userName+"\'");

            while(resultSet.next())
            {
                userNameDB = resultSet.getString("login");
                passwordDB = resultSet.getString("password");
                roleDB = resultSet.getInt("role");

                String result = "";

                if(userName.equals(userNameDB) && password.equals(passwordDB) && roleDB == 0)
                    result =  "Customer";
                else if(userName.equals(userNameDB) && password.equals(passwordDB) && roleDB == 1)
                    result =  "Manager";
                else if(userName.equals(userNameDB) && password.equals(passwordDB) && roleDB == 2)
                    result = "Developer";

                if (result != "") {
                    //set user_key
                    String key = UUID.randomUUID().toString();
                    statement.executeUpdate("UPDATE users SET user_key = \'"+key+"\' WHERE id =" +
                            Integer.toString(resultSet.getInt("id")) + "");
                    return new String[]{result, key};
                }
                else
                    return new String[]{"Invalid user credentials"};
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return new String[]{"Invalid user credentials"};
    }

    public String[] registerUser(LoginBean loginBean, String role, String qualification)
    {
        String userName = loginBean.getUserName();
        String password = loginBean.getPassword();
        int role_int = role.equals("Customer") ? 0 : (role.equals("Manager") ? 1 : 2);

        Connection con = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try
        {
            con = DBConnection.createConnection();
            statement = con.createStatement();

            //insert
            resultSet = statement.executeQuery("" +
                    "INSERT INTO users(login, password, role, qualification) " +
                    "VALUES ('" + userName + "','" + password + "','" + role_int + "',\'"  + qualification + "\')"
            );
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return new String[]{"Invalid user credentials"};
    }
}
