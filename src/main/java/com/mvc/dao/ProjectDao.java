package com.mvc.dao;

import com.mvc.bean.ProjectBean;
import com.mvc.bean.SpecificationBean;
import com.mvc.util.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.UUID;

public class ProjectDao {
    public ArrayList<ProjectBean> getAllSpecificationsAndProjects(int user_id, int page) {

        Connection con = null;
        Statement statement = null;

        int pageSize = 15;

        try
        {
            con = DBConnection.createConnection();
            statement = con.createStatement();

            ResultSet result = statement.executeQuery("" +
                    "SELECT specifications.name, specifications.description, projects.cost, specifications.id AS id " +
                    "FROM specifications " +
                    "LEFT JOIN projects " +
                    "ON specifications.id=projects.specification_id " +
                    "WHERE specifications.user_creator_id="+user_id + " "+
                    "OFFSET " + (page-1)*pageSize + " " +
                    "LIMIT " + pageSize);

            ArrayList<ProjectBean> resultList = new ArrayList<ProjectBean>();
            while (result.next()) {
                ProjectBean projectBean = new ProjectBean();
                SpecificationBean specificationBean = new SpecificationBean();
                specificationBean.setName(result.getString("name"));
                specificationBean.setDescription(result.getString("description"));
                projectBean.setId(result.getInt("id"));
                projectBean.setSpecification(specificationBean);
                projectBean.setCost(result.getInt("cost"));

                resultList.add(projectBean);
            }

            return resultList;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<ProjectBean> getAllSpecificationsAndProjectsManager(int page) {

        Connection con = null;
        Statement statement = null;

        int pageSize = 15;

        try
        {
            con = DBConnection.createConnection();
            statement = con.createStatement();

            ResultSet result = statement.executeQuery("" +
                    "SELECT specifications.name, specifications.description, projects.cost, specifications.id AS id " +
                    "FROM specifications " +
                    "LEFT JOIN projects " +
                    "ON specifications.id=projects.specification_id " +
                    "OFFSET " + (page-1)*pageSize + " " +
                    "LIMIT " + pageSize);

            ArrayList<ProjectBean> resultList = new ArrayList<ProjectBean>();
            while (result.next()) {
                ProjectBean projectBean = new ProjectBean();
                SpecificationBean specificationBean = new SpecificationBean();
                specificationBean.setName(result.getString("name"));
                specificationBean.setDescription(result.getString("description"));
                projectBean.setId(result.getInt("id"));
                projectBean.setSpecification(specificationBean);
                projectBean.setCost(result.getInt("cost"));

                resultList.add(projectBean);
            }

            return resultList;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public int getProjectsCount(int user_id) {
        Connection con = null;
        Statement statement = null;
        try
        {
            con = DBConnection.createConnection();
            statement = con.createStatement();

            ResultSet result;
            if (user_id != -1) {
                result = statement.executeQuery("" +
                        "SELECT COUNT(*) as full_count " +
                        "FROM specifications " +
                        "LEFT JOIN projects " +
                        "ON specifications.id=projects.specification_id " +
                        "WHERE specifications.user_creator_id="+user_id + " ");
            }
            else {
                result = statement.executeQuery("" +
                        "SELECT COUNT(*) as full_count " +
                        "FROM specifications " +
                        "LEFT JOIN projects " +
                        "ON specifications.id=projects.specification_id ");
            }


            result.next();
            return result.getInt(1);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    public ProjectBean getProject(int specification_id) {
        Connection con = null;
        Statement statement = null;

        try {
            con = DBConnection.createConnection();
            statement = con.createStatement();

            ResultSet result = statement.executeQuery("" +
                    "SELECT specifications.name, specifications.description, projects.cost, specifications.id AS id " +
                    "FROM specifications " +
                    "LEFT JOIN projects " +
                    "ON specifications.id=projects.specification_id " +
                    "WHERE specifications.id=" + specification_id);

            result.next();
            ProjectBean projectBean = new ProjectBean();
            SpecificationBean specificationBean = new SpecificationBean();
            specificationBean.setName(result.getString("name"));
            specificationBean.setDescription(result.getString("description"));
            projectBean.setId(result.getInt("id"));
            projectBean.setSpecification(specificationBean);
            projectBean.setCost(result.getInt("cost"));

            return projectBean;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
