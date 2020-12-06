package com.mvc.dao;

import com.mvc.bean.UserBean;
import com.mvc.util.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserDao {
    public UserBean getUser(String userKey) {
        Connection con = null;
        Statement statement = null;
        try
        {
            con = DBConnection.createConnection();
            statement = con.createStatement();

            ResultSet result = statement.executeQuery(
                    "select * from users where user_key =\'" + userKey + "\'");

            UserBean userBean = new UserBean();
            if(result.next()) {
                userBean.setId(result.getInt("id"));
                userBean.setLogin(result.getString("login"));
                userBean.setPassword(result.getString("password"));
                userBean.setRole(result.getInt("role"));
                userBean.setQualification(result.getString("qualification"));
                userBean.setUserKey(result.getString("user_key"));
            }

            return userBean;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<UserBean> getFreeDevsWithQualification(ArrayList<String> qualifications, ArrayList<Integer> jobs_id) {
        Connection con = null;
        Statement statement = null;
        try
        {
            con = DBConnection.createConnection();
            statement = con.createStatement();

            String qualificationListTemp = "";
            for(int i = 0 ; i < qualifications.size(); i++) {
                if (!qualificationListTemp.equals("")) {
                    qualificationListTemp += " ,";
                }
                qualificationListTemp += "'" + qualifications.get(i) + "'";
            }

            System.out.println(qualificationListTemp);

            ArrayList<UserBean> devs = new ArrayList<UserBean>();
            ResultSet result = statement.executeQuery("" +
                    "select users.id as id, users.login as login, users.qualification as qualification, developer_jobs.job_id " +
                    "from users " +
                    "LEFT JOIN developer_jobs on (users.id = developer_jobs.developer_id) " +
                    "where role=2 AND qualification IN (" + qualificationListTemp + ") " +
                    "ORDER BY login ASC"
            );
            while(result.next()) {
                UserBean userBean = new UserBean();
                userBean.setId(result.getInt("id"));
                userBean.setLogin(result.getString("login"));
                userBean.setQualification(result.getString("qualification"));
                userBean.setJob_id(result.getInt("job_id"));

                System.out.println(result.getInt("job_id"));
                if (userBean.getJob_id() == 0 || jobs_id.contains(userBean.getJob_id())) {
                    devs.add(userBean);
                }
            }


            return devs;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
