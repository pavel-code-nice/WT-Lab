package com.mvc.dao;

import com.mvc.bean.JobBean;
import com.mvc.bean.SpecificationBean;
import com.mvc.util.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class JobDao {
    public void addJob(JobBean jobBean, int specificationId) {
        String name = jobBean.getName();
        int devNum = jobBean.getRequiredDevNumber();
        String devQual = jobBean.getRequiredQualification();

        Connection con = null;
        Statement statement = null;
        try
        {
            con = DBConnection.createConnection();
            statement = con.createStatement();
            statement.executeUpdate("" +
                    "insert into jobs " +
                    "(specification_id, required_dev_number, required_qualification, name)" +
                    "values(" + specificationId +", \'" + devNum + "\', \'"+ devQual + "\', \'"+ name + "\')");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public ArrayList<JobBean> getProjectAssociatedJobs(int specificationId) {
        Connection con = null;
        Statement statement = null;
        try
        {
            con = DBConnection.createConnection();
            statement = con.createStatement();

            ResultSet result = statement.executeQuery("" +
                    "SELECT * " +
                    "FROM jobs " +
                    "WHERE specification_id=" + specificationId);

            ArrayList<JobBean> jobBeans = new ArrayList<>();
            while(result.next()) {
                JobBean jobBean = new JobBean();
                jobBean.setId(result.getInt("id"));
                jobBean.setName(result.getString("name"));
                jobBean.setRequiredDevNumber(result.getInt("required_dev_number"));
                jobBean.setRequiredQualification(result.getString("required_qualification"));
                jobBeans.add(jobBean);
            }

            return jobBeans;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public void updateJob(JobBean jobBean) {
        int id = jobBean.getId();
        String name = jobBean.getName();
        int devNum = jobBean.getRequiredDevNumber();
        String devQual = jobBean.getRequiredQualification();

        Connection con = null;
        Statement statement = null;
        try
        {
            con = DBConnection.createConnection();
            statement = con.createStatement();
            statement.executeUpdate("" +
                    "UPDATE jobs " +
                    "SET  name=\'" + name + "\', required_dev_number=\'" + devNum + "\', required_qualification=\'" + devQual + "\' " +
                    "WHERE id=" + id);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
}
