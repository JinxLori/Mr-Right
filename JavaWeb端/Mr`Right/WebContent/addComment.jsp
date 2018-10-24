<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="AddComment" method="post">
post_id:<input type="text" name="post_id"/><br>
comment_content:<input type="text" name="comment_content"/><br>
comment_level:<input type="text" name="comment_level"/><br>
from_uid:<input type="text" name="from_uid"/><br>
<input type="submit" value="添加评论"/>
</form>
</body>
</html>