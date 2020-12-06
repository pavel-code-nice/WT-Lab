<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=utf-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Customer Home Page</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
    <c:set var="context" value="${pageContext.request.contextPath}"/>
    <script>
        $(document).ready(function () {
            document.getElementById("add-job-btn").addEventListener("click", function (event) {
                event.preventDefault();
                AddJobTemplate('job-list', document.getElementById("add-job-btn").dataset.job++)
            });

            document.getElementById("add-project-btn").addEventListener("click", function (event) {
                event.preventDefault();
                document.getElementById("add-project-btn").disabled = true;
                UpdateProject();
                document.getElementById("add-project-btn").disabled = false;
            });

         //   console.log(${requestScope.jobs});

            function AddJobTemplate(parentId, JobNumber) {
                console.log('adding job');
                let jobTextTemplate = '<h6>Работа №' + JobNumber + '</h6>\n' +
                    '<div class="form-group">\n' +
                    '<label for="job-name-' + JobNumber + '">Название работы</label>\n' +
                    '<input data-id="0" type="text" class="form-control" id="job-name-' + JobNumber + '" rows="3"></input>\n' +
                    '</div>\n' +
                    '<div class="d-flex flex-row">\n' +
                    '<div class="form-group mr-2">\n' +
                    '<label for="job-dev-num-' + JobNumber + '">Необходимое число разработчиков</label>\n' +
                    '<input  min="1" max="1000" type="number" class="form-control" id="job-dev-num-' + JobNumber + '" rows="3"></input>\n' +
                    '</div>\n' +
                    '<div class="form-group">\n' +
                    '<label for="job-qual-' + JobNumber + '">Необходимая квалификация</label>\n' +
                    '<input type="text" class="form-control" id="job-qual-' + JobNumber + '" rows="3"></input>\n' +
                    '</div>\n' +
                    '</div>';

                let jobTemplate = document.createElement('div');
                jobTemplate.classList.add("card-item");
                jobTemplate.classList.add("bg-white");
                jobTemplate.classList.add("mb-2");
                jobTemplate.classList.add("mb-2");
                jobTemplate.innerHTML = jobTextTemplate;

                document.getElementById(parentId).appendChild(jobTemplate);
            }

            function UpdateProject() {

                let projectName = document.getElementById("project-name").value;
                let projectDesc = document.getElementById("project-description").value;

                let errorElem = document.getElementById("error-message");
                errorElem.innerHTML = '';

                errorElem.innerHTML += !projectName ? 'Название проекта не заполнено. ' : ''
                errorElem.innerHTML += !projectDesc ? 'Описание проекта не заполнено. ' : ''

                let projectJobs = [];
                for (let i = 0; i < document.getElementById("add-job-btn").dataset.job; i++) {
                    projectJobs.push({
                        id : document.getElementById("job-name-" + i).dataset.id,
                        name: document.getElementById("job-name-" + i).value,
                        devNum: document.getElementById("job-dev-num-" + i).value,
                        devQual: document.getElementById("job-qual-" + i).value,
                    });

                    errorElem.innerHTML += !projectJobs[i].name || !projectJobs[i].devNum || !projectJobs[i].devQual ?
                        'Не все поля работы №' + i+1 + ' заполнены.' : '';
                }

                if (errorElem.innerHTML) {
                    return;
                }

                let data = {
                    id: ${requestScope.project.id},
                    name: projectName,
                    description: projectDesc,
                    jobs: projectJobs,
                };

                $.ajax({
                    type: "POST",
                    url: "${context}/project",
                    data: JSON.stringify(data),
                    success: function (response) {
                        alert('ТЗ успешно обновлено');
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
<c:if test="${sessionScope.Customer == null}">
    <jsp:forward page="/JSP/Login.jsp"></jsp:forward>
</c:if>
<body>
<%@ include file="../../templates/header.jsp" %>
<div class="container">
    <h4>Конструктор Проекта</h4>
    <div class="card-item">
        <div>
            <div class="form-group">
                <label for="project-name">Название проекта</label>
                <input value="${requestScope.project.specification.name}" type="text" class="form-control"
                       id="project-name" placeholder="Новый проект">
            </div>
            <div class="form-group">
                <label for="project-description">Описание проекта</label>
                <textarea class="form-control" id="project-description"
                          rows="3">${requestScope.project.specification.description}</textarea>
            </div>
            <h5 class="mt-3">Работы</h5>
            <div class="mb-3">
                <div id="job-list" class="mb-1">
                    <c:forEach items="${requestScope.jobs}" var="job" varStatus="loop">
                        <div class="card-item bg-white mb-2">
                            <h6>Работа №${loop.index + 1}</h6>
                            <div class="form-group">
                                <label for="job-name-${loop.index}">Название работы</label>
                                <input data-id="${job.id}" value="${job.name}" type="text" class="form-control" id="job-name-${loop.index}"
                                       rows="3">
                            </div>
                            <div class="d-flex flex-row">
                                <div class="form-group mr-2">
                                    <label for="job-dev-num-${loop.index}">Необходимое число разработчиков</label>
                                    <input value="${job.requiredDevNumber}" type="number" class="form-control"
                                           id="job-dev-num-${loop.index}" rows="3" min="1" max="1000">
                                </div>
                                <div class="form-group">
                                    <label for="job-qual-${loop.index}">Необходимая квалификация</label>
                                    <input value="${job.requiredQualification}" type="text" class="form-control"
                                           id="job-qual-${loop.index}" rows="3">
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <button id="add-job-btn" data-job="${requestScope.jobs.size()}" class="btn btn-dark">Добавить работу</button>
            </div>
            <div id="error-message" class="text-danger mb-2"></div>
            <button id="add-project-btn" class="btn btn-dark w-100">Редактировать проект</button>
        </div>
    </div>
</div>
</body>
</html>
