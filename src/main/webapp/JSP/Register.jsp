
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Login</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
    <style>
        .login-form {
            width: 340px;
            margin: 50px auto;
            font-size: 15px;
        }

        .login-form form {
            margin-bottom: 15px;
            background: #f7f7f7;
            box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
            padding: 30px;
        }

        .login-form h2 {
            margin: 0 0 15px;
        }

        .form-control, .btn {
            min-height: 38px;
            border-radius: 2px;
        }

        .btn {
            font-size: 15px;
            font-weight: bold;
        }
        .error-message {
            color:#ff0000;
        }
    </style>
    <script>
            function validate() {
                let username = document.form.username.value;
                let password = document.form.password.value;
                let role = document.form.role.value;

                if (username == null || username == "") {
                    alert("Username cannot be blank");
                    return false;
                } else if (password == null || password == "") {
                    alert("Password cannot be blank");
                    return false;
                }
                else if (role == null || role == "") {
                    alert("Role cannot be blank");
                    return false;
                }
            }

            function showDiv(divId, element)
            {
                document.getElementById(divId).style.display = element.value == "Developer" ? 'block' : 'none';
            }

    </script>
    <c:set var="context" value="${pageContext.request.contextPath}" />
</head>
<body>
<h:header/>
<div class="login-form">
    <form name="form" action="${context}/register" method="post" onsubmit="return validate()">
        <h2 class="text-center">Sign Up</h2>
        <div class="form-group">
            <input type="text" class="form-control" name="login" placeholder="Username" required="required">
        </div>
        <div class="form-group">
            <input type="password" class="form-control" name="password" placeholder="Password" required="required">
        </div>
        <div class="form-group">
            <label for="role-select">Role</label>
            <select class="form-control" id="role-select" name="role" required="required" onchange="showDiv('hidden_div', this)">
                <option value="Developer">Developer</option>
                <option value="Manager">Manager</option>
                <option value="Customer">Customer</option>
            </select>
        </div>
        <div id="hidden_div" class="form-group">
            <input value="Junior" type="text" class="form-control" name="qualification" placeholder="qualification" required="required">
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-primary btn-block">Sign Up</button>
        </div>
        <div class="form-group">
                    <span class="error-message">
                        <c:if test="${request.getAttribute('errMessage') != null}">
                            <c:out value="${request.getAttribute('errMessage')}"></c:out>
                        </c:if>
                        </span>
        </div>
    </form>
    <p class="text-center"><a href="${context}/LoginServlet">I have an account</a></p>
</div>
</body>
</html>
