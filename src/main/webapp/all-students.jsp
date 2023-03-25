<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<body>
<h2>All Students</h2>

<br>

<table>
    <tr>
        <th>Name</th>
        <th>University</th>
    </tr>

    <c:forEach var="s" items="${students}">

        <c:url var="updateButton" value="//updateInfo">
            <c:param name="empId" value="${emp.id}"/>
        </c:url>

        <c:url var="deleteButton" value="//deleteEmployee">
            <c:param name="empId" value="${emp.id}"/>
        </c:url>

        <tr>
            <td>${s.name}</td>
            <td>${s.university}</td>
            <td>
                <input type="button" value="Update"
                       onclick="window.location.href = '${updateButton}'"/>

                <input type="button" value="Delete"
                       onclick="window.location.href = '${deleteButton}'"/>
            </td>
        </tr>
    </c:forEach>



</table>

<br>
<input type="button" value="Add"
       onclick="window.location.href = '/student/add'"/>
</body>
</html>