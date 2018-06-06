<%--
  Created by IntelliJ IDEA.
  User: jamal
  Date: 07.06.2018
  Time: 1:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Metropolitan lines info</title>
</head>
<body>
<h1>Lines name: </h1>
<form name="Line Input Form" action="metroline">
    <label for="selecter">Select line: </label>
    <select name="line" id="selecter">

        <option value="Red">Red line</option>
        <option value="Blue">Blue line</option>
        <option value="Green">Green line</option>
    </select>
    <input type="submit" value="OK"/>
</form>

</body>
</html>
