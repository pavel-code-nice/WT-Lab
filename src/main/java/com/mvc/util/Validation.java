package com.mvc.util;

import com.mvc.bean.UserBean;
import com.mvc.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Null;
import java.io.IOException;

public class Validation {
    public static UserBean validateWithRole(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        //Get user | user validation
        HttpSession session = request.getSession();
        UserDao userDao = new UserDao();
        UserBean userBean = userDao.getUser((String)session.getAttribute("userKey"));

        if (userBean != null) {
            return userBean;
        }
        else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
        return null;
    }
}
