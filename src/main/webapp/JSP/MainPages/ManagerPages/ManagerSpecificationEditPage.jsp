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
            document.getElementById("add-project-btn").addEventListener("click", function (event) {
                event.preventDefault();
                document.getElementById("add-project-btn").disabled = true;
                UpdateProject();
                document.getElementById("add-project-btn").disabled = false;
            });

            limitSelect(id, num) {
                    if ($(id).val().length > num) {
                        $(id).val(last_valid_selection);
                    } else {
                        last_valid_selection = $(this).val();
                    }
            }

            function RegisterProject() {

                let projectCost = document.getElementById("project-cost").value;

                let errorElem = document.getElementById("error-message");
                errorElem.innerHTML = '';

                errorElem.innerHTML += !projectCost ? 'Стоимость проекта не указана. ' : ''

                let developers = [];
                for (let i = 1; i < document.getElementById("add-job-btn").dataset.job; i++) {
                    developers.push({
                        id : document.getElementById("job-name-" + i).dataset.id,
                        name: document.getElementById("job-name-" + i).value,
                        devNum: document.getElementById("job-dev-num-" + i).value,
                        devQual: document.getElementById("job-qual-" + i).value,
                    });

                    errorElem.innerHTML += !projectJobs[i - 1].name || !projectJobs[i - 1].devNum || !projectJobs[i - 1].devQual ?
                        'Не все поля работы №' + i + ' заполнены.' : '';
                }

                if (errorElem.innerHTML) {
                    return;
                }

                let data = {
                    id: ${requestScope.project.id},
                    cost: projectCost,
                    developers: developers,
                };

                $.ajax({
                    type: "POST",
                    url: "${context}/project-register",
                    data: JSON.stringify(data),
                    success: function (response) {
                        alert('ТЗ успешно оформлено');
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
<body>
<%@ include file="../../templates/header.jsp" %>
<div class="container">
    <h4>Обзор проекта</h4>
    <div class="card-item">
        <div>
            <div class="form-group">
                <label for="project-name">Название проекта</label>
                <input disabled value="${requestScope.project.specification.name}" type="text" class="form-control"
                       id="project-name" placeholder="Новый проект" readonly>
            </div>
            <div class="form-group">
                <label for="project-description">Описание проекта</label>
                <textarea disabled class="form-control" id="project-description"
                          rows="3" readonly>${requestScope.project.specification.description}</textarea>
            </div>
            <div class="form-group">
                <label for="project-description">Стоимость проекта</label>
                <input type="number" disabled class="form-control" id="project-cost"
                          rows="3" readonly value="${requestScope.project.cost}"></input>
            </div>
            <h5 class="mt-3">Работы</h5>
            <div class="mb-3">
                <div id="job-list" class="mb-1">
                    <c:forEach items="${requestScope.jobs}" var="job" varStatus="loop">
                        <div class="card-item bg-white mb-2">
                            <h6>Работа №${loop.index + 1}</h6>
                            <div class="form-group">
                                <label for="job-name-${loop.index}">Название работы</label>
                                <input disabled data-id=${job.id} value="${job.name}" type="text" class="form-control" id="job-name-${loop.index}"
                                       rows="3" readonly>
                            </div>
                            <div class="d-flex flex-row">
                                <div class="form-group mr-2">
                                    <label for="job-dev-num-${loop.index}">Необходимое число разработчиков</label>
                                    <input disabled value="${job.requiredDevNumber}" type="number" class="form-control"
                                           id="job-dev-num-${loop.index}" rows="3" min="1" max="1000" readonly>
                                </div>
                                <div class="form-group">
                                    <label for="job-qual-${loop.index}">Необходимая квалификация</label>
                                    <input disabled value="${job.requiredQualification}" type="text" class="form-control"
                                           id="job-qual-${loop.index}" rows="3" readonly>
                                </div>
                                <div class="form-group">
                                    <label for="dev-select-${loop.index}">Назначенные разработчики</label>
                                    <select multiple class="form-control" id="dev-select-${loop.index}">
                                        <c:forEach items="${requestScope.devs}" var="dev" varStatus="loop_dev">
                                            <c:choose>
                                            <c:when test="${dev.qualification eq job.requiredQualification}">
                                                <c:choose>
                                                <c:when test="${dev.job_id eq job.id}">
                                                    <option selected value="${dev.id}">${dev.login}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:choose>
                                                    <c:when test="${dev.job_id eq 0}">
                                                        <option value="${dev.id}">${dev.login}</option>
                                                    </c:when>
                                                    <c:otherwise></c:otherwise>
                                                </c:choose>
                                                </c:otherwise>
                                                </c:choose>
                                            </c:when>
                                            <c:otherwise></c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>

            <div id="error-message" class="text-danger mb-2"></div>
            <button id="update-project-btn" class="btn btn-dark w-100">Оформить проект</button>
        </div>
    </div>
</div>
</body>
</html>
