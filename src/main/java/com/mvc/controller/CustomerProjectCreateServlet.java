package com.mvc.controller;

import com.google.gson.Gson;
import com.mvc.bean.JobBean;
import com.mvc.bean.SpecificationBean;
import com.mvc.bean.UserBean;
import com.mvc.dao.JobDao;
import com.mvc.dao.LoginDao;
import com.mvc.dao.SpecificationDao;
import com.mvc.dao.UserDao;
import com.mvc.util.Validation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "CustomerProjectCreateServlet")
public class CustomerProjectCreateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //validation
        UserBean userBean = Validation.validateWithRole(request, response);

        com.google.gson.JsonObject data = new Gson().fromJson(request.getReader(), com.google.gson.JsonObject.class);

        //add specification
        SpecificationBean specificationBean = new SpecificationBean();
        specificationBean.setName(data.get("name").getAsString());
        specificationBean.setDescription(data.get("description").getAsString());

        SpecificationDao specificationDao = new SpecificationDao();
        int specificationId = specificationDao.addSpecification(specificationBean, userBean.getId());

        //add jobs
        JobDao jobDao = new JobDao();
        com.google.gson.JsonArray jobs = data.get("jobs").getAsJsonArray();
        jobs.forEach((job) -> {
            JobBean jobBean = new JobBean();
            jobBean.setName(job.getAsJsonObject().get("name").getAsString());
            jobBean.setRequiredDevNumber(job.getAsJsonObject().get("devNum").getAsInt());
            jobBean.setRequiredQualification(job.getAsJsonObject().get("devQual").getAsString());
            jobDao.addJob(jobBean, specificationId);

        });
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //validation
        Validation.validateWithRole(request, response);

        request.getRequestDispatcher("/JSP/MainPages/CustomerPages/CustomerCreateProject.jsp").forward(request, response);
    }
}
