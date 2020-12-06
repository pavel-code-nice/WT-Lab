<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=utf-8" %>

<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
    <c:set var="context" value="${pageContext.request.contextPath}"/>

    <script>
        $(document).ready(function () {
            //LoadProjects
            for(let i = 15 ; i < (${requestScope.dataCount}); i+= 15) {
                AddPageLink('projects-pagination', Math.floor(i/15))
            }

            loadProjects(1);

            function AddProject(parentId,name, description, specification_id) {
                let jobTextTemplate = '<div class="text-dark">' +
                    '<h5 class="mb-2 border-bottom p-1">'+ name +'</h5>' +
                    '<p class="mb-2">'+ description +'</p>' +
                    '<button data-id="'+ specification_id +'" class="btn btn-outline-dark">Open</button>' +
                    '</div>'

                let jobTemplate = document.createElement('a');
                jobTemplate.classList.add("card-item");
                jobTemplate.classList.add("bg-white");
                jobTemplate.classList.add("p-3");
                jobTemplate.classList.add("mb-2");
                jobTemplate.classList.add("text-decoration-none")
                jobTemplate.setAttribute('href', "${context}/project-registration?id="+specification_id);
                jobTemplate.innerHTML = jobTextTemplate;

                document.getElementById(parentId).appendChild(jobTemplate);
            }

            function AddPageLink(parentId, number) {
                let linkTemplate = '<li class="page-item"><a class="page-link" onclick="loadProjects('+number+')">'+number+'</a></li>'
                document.getElementById(parentId).innerHTML += linkTemplate;
            }

            function loadProjects(page) {
                $.ajax({
                    type: "POST",
                    url: "${context}/Projects",
                    data: JSON.stringify({
                        page: page
                    }),
                    success: function (response) {
                        console.log(response);
                        let projectList = response;

                        jQuery('#projects-spinner').remove();
                        for(let i = 0 ; i < projectList.length; i++) {
                            AddProject('projects', projectList[i].specification.name, projectList[i].specification.description, projectList[i].id);
                        }
                    }
                });
            }
        });
    </script>

    <style>
        .card-item {
            margin-bottom: 15px;
            background: #f7f7f7;
            box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
            padding: 20px;
        }
    </style>
</head>

<c:if test="${sessionScope.Manager == null}">
    <jsp:forward page="/JSP/Login.jsp"></jsp:forward>
</c:if>
<body>
<%@ include file="../../templates/header.jsp" %>
<div class="container">
    <h3>Проекты</h3>
    <div id="projects" class="card-item mb-2 d-flex flex-column">
        <div id="projects-spinner" class="d-flex w-100 justify-content-center">
            <div class="spinner-border justify-content-center my-4" role="status">
                <span class="sr-only">Загрузка...</span>
            </div>
        </div>
    </div>
    <div>
        <nav aria-label="paging">
            <ul id="projects-pagination" class="pagination justify-content-center">
            </ul>
        </nav>
    </div>
</div>
</body>
</html>
