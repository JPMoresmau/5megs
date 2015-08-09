<%@page import="java.util.Collections"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Enumeration"%>
<%@page import="fivemegs.Post"%>
<%@page import="java.util.List"%>
<%@page import="fivemegs.Posts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="js/util.js"></script>
<script type="text/javascript" src="js/5megs.js"></script>
<title>5 megs</title>

<%
HttpSession s=request.getSession();
if (s==null || s.getAttribute("upload")==null){
	request.getSession(true).setAttribute("upload",System.currentTimeMillis());
	%>
	<script type="text/javascript">
	_5megs.upload();
	</script>
	<%
}
%>

</head>
<body>
<div>
<a href="submit.jsp">Submit new content!</a>
</div>

<%
Posts ps=(Posts)request.getServletContext().getAttribute("posts");
if (ps!=null){
	List<Post> lps=null;
	int start=0;
	if ("mine".equals(request.getParameter("whose"))){
		if (s!=null){
			Enumeration<String> keys=s.getAttributeNames();
			lps=new ArrayList<Post>();
			while (keys.hasMoreElements()){
				String k=keys.nextElement();
				Post p=ps.getByKey(k);
				if (p!=null){
					lps.add(p);
				}
			}
			Collections.sort(lps, new Post.DateComparator());
			
		}
	} else {
		String sts=request.getParameter("start");
		if (sts!=null && sts.length()>0){
			try {
				start=Integer.parseInt(sts)-1; // zero based
			} catch (NumberFormatException nfe){
				// ignore
			}
		}
		lps=ps.getPosts(start,50);
		
	}
	if (lps!=null){
		int ix=start;
		for (Post p:lps){
			ix++;
			%><div><%=ix%>.&nbsp;
			<% String k=p.getKey();
			  if (s==null || s.getAttribute(k)==null){
				%><a id="u_<%=k %>" href="javascript:_5megs.upvote('<%=k%>')"/>Upvote</a><%
			}%>
			<span id="s_<%=k %>"><%=p.getScore() %></span>&nbsp;<%=p.getPost().get("h") %></div><%
		}
	}
}

%>


</body>
</html>