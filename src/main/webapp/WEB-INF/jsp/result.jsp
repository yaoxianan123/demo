<%@ page import="java.io.PrintWriter" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
<head>
    <meta content="text/html;charset=utf-8" http-equiv="Content-Type"/>
    <title>表单提交</title>
</head>
<body>
<%
    PrintWriter pw = response.getWriter();
    int s = Integer.parseInt(request.getParameter("s"));
    int e = Integer.parseInt(request.getParameter("e"));
    for (int i = s; i <= e; i++) {
        for (int j = s; j <= i; j++) {
            pw.print(j + "*" + i + "=" + (j * i) + "\t");
        }
        pw.println("<br />");
    }
%>
<br />
<a href="index.jsp">返回</a>
</body>
</html>