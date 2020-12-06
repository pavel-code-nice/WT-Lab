package com.mvc.controller;

import com.google.gson.Gson;
import com.mvc.bean.JobBean;
import com.mvc.bean.ProjectBean;
import com.mvc.bean.SpecificationBean;
import com.mvc.bean.UserBean;
import com.mvc.dao.JobDao;
import com.mvc.dao.ProjectDao;
import com.mvc.dao.SpecificationDao;
import com.mvc.dao.UserDao;
import com.mvc.filter.UserFilter;
import com.mvc.util.Validation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "ManagerProjectEditServlet")
public class ManagerProjectEditServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //validation
        UserBean userBean = Validation.validateWithRole(request, response);

        com.google.gson.JsonObject data = new Gson().fromJson(request.getReader(), com.google.gson.JsonObject.class);

        //update specification
        SpecificationBean specificationBean = new SpecificationBean();
        specificationBean.setId(data.get("id").getAsInt());
        specificationBean.setName(data.get("name").getAsString());
        specificationBean.setDescription(data.get("description").getAsString());
        SpecificationDao specificationDao = new SpecificationDao();
        specificationDao.updateSpecification(specificationBean);

        //update jobs
        JobDao jobDao = new JobDao();
        com.google.gson.JsonArray jobs = data.get("jobs").getAsJsonArray();
        jobs.forEach((job) -> {
            JobBean jobBean = new JobBean();
            jobBean.setId(job.getAsJsonObject().get("id").getAsInt());
            jobBean.setName(job.getAsJsonObject().get("name").getAsString());
            jobBean.setRequiredDevNumber(job.getAsJsonObject().get("devNum").getAsInt());
            jobBean.setRequiredQualification(job.getAsJsonObject().get("devQual").getAsString());
            if (jobBean.getId() == 0) {
                jobDao.addJob(jobBean, specificationBean.getId());
            }
            else {
                jobDao.updateJob(jobBean);
            }


        });
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //validation
        UserBean userBean = Validation.validateWithRole(request, response);

        //spec id
        int specificationId = Integer.parseInt(request.getParameter("id"));

        ProjectDao projectDao = new ProjectDao();
        JobDao jobDao = new JobDao();
        UserDao userDao = new UserDao();

        ProjectBean projectBean = projectDao.getProject(specificationId);
        ArrayList<JobBean> jobs = jobDao.getProjectAssociatedJobs(specificationId);
        ArrayList<String> qual = new ArrayList<String>();
        ArrayList<Integer> jobs_id = new ArrayList<Integer>();
        for(int i = 0 ; i < jobs.size(); i++) {
            if (!qual.contains(jobs.get(i).getRequiredQualification())) {
                qual.add(jobs.get(i).getRequiredQualification());
            }
            if (!jobs_id.contains(jobs.get(i).getId())) {
                jobs_id.add(jobs.get(i).getId());
            }
        }
        System.out.println(qual.size());
        System.out.println(jobs_id.size());

        ArrayList<UserBean> devs = userDao.getFreeDevsWithQualification(qual, jobs_id);

        request.setAttribute("project", projectBean);
        request.setAttribute("jobs", jobs);
        request.setAttribute("devs", devs);
        request.getRequestDispatcher("/JSP/MainPages/ManagerPages/ManagerSpecificationEditPage.jsp").forward(request, response);
    }
}
