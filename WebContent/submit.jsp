<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="js/util.js"></script>
<script type="text/javascript" src="js/5megs.js"></script>
<title>5 megs: Submit content</title>
</head>
<body>
<div>
<a href="index.jsp">View all content</a>&nbsp;|&nbsp;<a href="index.jsp?whose=mine">View my content only</a>
</div>
<div>
<form onSubmit="return _5megs.storeForm(this);">
Pseudo name:<br/><input name="pseudo"/><br/>
Title:<br/><input name="title"/><br/>
Link (optional):<br/><input name="link"/><br/>
Text (optional):<br/><textarea name="text" rows="5" cols="50"></textarea>
<input type="submit" value="Send!"/>
</form>

</div>


</body>
</html>