<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<jsp:include page="head.jsp"></jsp:include>
<title>5 megs: Submit content</title>
</head>
<body>
<div class="container-fluid">
<div>
<a href="index.jsp">View all content</a>&nbsp;|&nbsp;<a href="index.jsp?whose=mine">View my content only</a>
</div>
<div>
<form onSubmit="return _5megs.storeForm(this);">
<div class="form-group"><label for="pseudo">Pseudo name</label><input name="pseudo" id="pseudo" class="form-control"/></div>
<div class="form-group"><label for="title">Title</label><input name="title" id="title" class="form-control"/></div>
<div class="form-group"><label for="link">Link (optional)</label><input name="link" type="url" id="link" class="form-control"/></div>
<div class="form-group"><label for="text">Text (optional)</label><textarea name="text" id="text" class="form-control" rows="5" cols="50"></textarea>
</div>
<input type="submit" value="Send!" class="btn btn-primary"/>
</form>

</div>

</div>
</body>
</html>