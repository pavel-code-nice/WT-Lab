package com.mvc.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mvc.bean.LoginBean;
import com.mvc.dao.LoginDao;

public class LoginServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/JSP/Login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String userName = request.getParameter("login");
        String password = request.getParameter("password");
        System.out.println(userName);
        System.out.println(password);

        LoginBean loginBean = new LoginBean();

        loginBean.setUserName(userName);
        loginBean.setPassword(password);

        LoginDao loginDao = new LoginDao();

        try
        {
            String[] userValidate = loginDao.authenticateUser(loginBean);
            HttpSession session = request.getSession(); //Creating a session

            if(userValidate[0].equals("Customer"))
            {
                System.out.println("Customer's Home");

                session.setAttribute("Customer", userName); //setting session attribute
                session.setAttribute("userKey", userValidate[1]);
                request.setAttribute("userName", userName);

                response.sendRedirect(request.getContextPath() + "/MyProjects");
            }
            else if(userValidate[0].equals("Manager"))
            {
                System.out.println("Manager's Home");

                session.setAttribute("Manager", userName);
                session.setAttribute("userKey", userValidate[1]);
                request.setAttribute("userName", userName);

                response.sendRedirect(request.getContextPath() + "/Projects");
            }
            else if(userValidate[0].equals("Developer"))
            {
                System.out.println("Developer's Home");

                session.setAttribute("Developer", userName);
                session.setAttribute("userKey", userValidate[1]);
                request.setAttribute("userName", userName);

                request.getRequestDispatcher("/JSP/MainPages/Developer.jsp").forward(request, response);
            }
            else
            {
                System.out.println("Error message = "+userValidate[0]);
                request.setAttribute("errMessage", userValidate[0]);

                request.getRequestDispatcher("/JSP/Login.jsp").forward(request, response);
            }
        }
        catch (IOException e1)
        {
            e1.printStackTrace();
        }
        catch (Exception e2)
        {
            e2.printStackTrace();
        }
    }
}