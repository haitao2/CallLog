<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>通话记录</title>
    <link rel="stylesheet" type="text/css" href="../css/my.css">
</head>
<body>
    <table id="t1" border="1px" class="t-1">
        <tr>
            <td>电话1</td>
            <td>电话2</td>
            <td>主(被)叫</td>
            <td>通话时间</td>
            <td>通话时长</td>
        </tr>
        <c:forEach items="${callLogs}" var="log">
            <tr>
                <td><c:out value="${log.caller}"/></td>
                <td><c:out value="${log.callee}"/></td>
                <td><c:if test="${log.caller == param.caller}">主叫</c:if>
                    <c:if test="${log.caller != param.caller}">被叫</c:if>
                </td>
                <td><c:out value="${log.callTime}"/></td>
                <td><c:out value="${log.callDuration}"/></td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="5" style="text-align: right">
            </td>
        </tr>
    </table>
</body>
</html>