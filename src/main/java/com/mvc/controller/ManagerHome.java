package com.mvc.controller;

import com.google.gson.Gson;
import com.mvc.bean.ProjectBean;
import com.mvc.bean.UserBean;
import com.mvc.dao.ProjectDao;
import com.mvc.util.Validation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "ManagerHome")
public class ManagerHome extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //validation
        UserBean userBean = Validation.validateWithRole(request, response);

        com.google.gson.JsonObject data = new Gson().fromJson(request.getReader(), com.google.gson.JsonObject.class);

        ProjectDao projectDao = new ProjectDao();
        ArrayList<ProjectBean> projects = projectDao.getAllSpecificationsAndProjectsManager(data.get("page").getAsInt());
        String projectsSerialized = new Gson().toJson(projects);
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(projectsSerialized);
        out.flush();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //validation
        UserBean userBean = Validation.validateWithRole(request, response);

        ProjectDao projectDao = new ProjectDao();

        request.setAttribute("dataCount", projectDao.getProjectsCount(userBean.getId()));
        request.getRequestDispatcher("/JSP/MainPages/ManagerPages/ManagerHome.jsp").forward(request, response);
    }
}
