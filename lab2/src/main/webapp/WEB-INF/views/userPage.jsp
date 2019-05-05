
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
    <head>
        <title>Title</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>


    </head>
    <body>
    <c:if test="${success != null}">
        <h1>${success}</h1>
    </c:if>
    <c:if test="${warning != null}">
        <div class="alert alert-danger">
            <p style="color: red">  <c:out value = "${warning}"/></p>
        </div>
    </c:if>
    <c:if test="${topup != null}">
        <div class="alert alert-success">
            <p style="color: green">  <c:out value = "${topup}"/></p>
        </div>
    </c:if>
    <h2>Hello, <c:out value="${sessionScope.loggedinuser}" />!</h2><a  href="<c:url value='/logout'/>">Logout</a><br/>
    <!-- ------------------------------------------------------------------------ -->
    <c:forEach items="${sessionScope.cardAccountMap}" var="item">
        Card Number: ${item.key.cardNumber} ----- ${item.value.moneyAmount}$<br>
    </c:forEach>
    <!-- ------------------------------------------------------------------------ -->
    <div class="container">
        <div class="row">
            <div class="col-sm">
                <form method="post" action='<spring:url value="/operation"/>' id="payForm"
                      style=" margin:20px;  padding:10px; border: 2px solid LightGray;border-radius:5px;">
                    Info:
                    <div class="form-row align-items-center">
                        <input type="text" id="info" name="info" class="form-control" />
                    </div>
                    Money:
                    <div class="form-row align-items-center">
                        <input type="text" id="money" name="money" class="form-control" />
                    </div>
                    Card:
                    <div class="form-row align-items-center">
                        <select name="card">
                            <c:forEach items="${sessionScope.cardAccountMap}" var="card">
                                <option value="${card.key.cardNumber}">${card.key.cardNumber}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <br/>
                    <input type="hidden" name="command" id="commandPay"/>
                    <input type="submit" id="submitPay" name="submit" value="Add payment" onclick="setCommand('Pay', 'payForm')"
                           class="btn btn-primary">
                </form>
            </div>
            <!-- ------------------------------------------------------------------------ -->
            <div class="col-sm">
                <form method="post" action='<spring:url value="/operation"/>' id="topUpForm"
                      style=" margin:20px;  padding:10px; border: 2px solid LightGray;border-radius:5px;">
                    Money:
                    <div class="form-row align-items-center">
                        <input type="text" id="topUpMoney" name="money" class="form-control" />
                    </div>
                    Card:
                    <div class="form-row align-items-center">
                        <select name="card">
                            <c:forEach items="${sessionScope.cardAccountMap}" var="card">
                                <option value="${card.key.cardNumber}">${card.key.cardNumber}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <br/>
                    <input type="hidden" name="command" id="commandTopUp"/>
                    <input type="submit" id="submitTopUp" name="submit" value="Top up account" onclick="setCommand('TopUp', 'topUpForm')"
                           class="btn btn-success">
                </form>
            </div>
            <!-- ------------------------------------------------------------------------ -->
            <div class="col-sm">
                <form method="post" action='<spring:url value="/operation"/>' id="blockForm"
                      style=" margin:20px;  padding:10px; border: 2px solid LightGray;border-radius:5px;">
                    Card:
                    <div class="form-row align-items-center">
                        <select name="card">
                            <c:forEach items="${sessionScope.cardAccountMap}" var="card">
                                <option value="${card.key.cardNumber}">${card.key.cardNumber}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <br/>
                    <input type="hidden" name="command" id="commandBlock"/>
                    <input type="submit" id="submitBlock" name="submit" value="Block" onclick="setCommand('Block', 'blockForm')"
                           class="btn btn-danger">
                </form>
            </div>
        </div>
    </div>
    <!-- ------------------------------------------------------------------------ -->
    <script>
        function setCommand(command, formName)
        {
            document.getElementById("command" + command).value = command;
            var form = document.getElementById(formName);
            form.submit();
        }
    </script>
    <br>
    <br>
    <br>

    <ul class="list-group">
        <c:forEach items="${sessionScope.payments}" var="item">
            <li class="list-group-item">
                <c:out value="${item}" />
            </li>
        </c:forEach>
    </ul>
    </body>
</html>
