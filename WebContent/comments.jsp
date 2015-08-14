<%@page import="fivemegs.Utils"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Locale"%>
<%@page import="fivemegs.Post"%>
<%@page import="fivemegs.Posts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
String k=request.getParameter("k");
Posts ps=(Posts)request.getServletContext().getAttribute("posts");
Post p=null;
String title="Post not found";
if (ps!=null){
	p=ps.getByKey(k);
	if (p!=null){
		title=Utils.escapeHTML(p.getPost().getString("h"));
	}
}

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>5megs: <%=title %></title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="js/util.js"></script>
<script type="text/javascript" src="js/5megs.js"></script>
</head>
<body>
<div>
<a href="submit.jsp">Submit new content!</a>&nbsp;|&nbsp;<a href="index.jsp">View all content</a>
</div>

<%
if (p!=null){
	HttpSession s=request.getSession();
	String link=p.getPost().optString("l");
	String pseudo=Utils.escapeHTML(p.getPost().getString("p"));
	if (link!=null && link.length()>0 && !link.toLowerCase(Locale.ENGLISH).startsWith("javascript")){
		title="<a href='"+link+"' target='_new'>"+title+"</a>";
	} 		
	%><div id="a_<%=k %>"><br/>
	<% 
	  String uv="none";
	  String dv="none";
	  if (s==null || s.getAttribute(k)==null){
		  uv="inline";
	  } else if (s!=null && s.getAttribute(k)!=null){
		  dv="inline";
	  }
		  %>
		  <a id="u_<%=k %>" href="javascript:_5megs.upvote('<%=k%>')" style="display:<%=uv%>;text-decoration:none;" title="Upvote">&#8679;</a>
		  <a id="d_<%=k %>" href="javascript:_5megs.downvote('<%=k%>')" style="display:<%=dv%>;text-decoration:none;" title="Downvote">&#8681;</a><%
	%>
	<span id="s_<%=k %>"><%=p.getScore() %></span>&nbsp;<%=title %>
	<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;By <%=pseudo %>
	</div>
	<%
	String txt=p.getPost().optString("t");
	if (txt!=null && txt.length()>0){
	%>
	<div>
	<%=Utils.escapeHTML(txt) %>
	</div>
	<%
	}
	%>
	<div>
<form onSubmit="return _5megs.commentForm(this,'comments.jsp?k=<%=URLEncoder.encode(k,"UTF8")%>');">
<input type="hidden" name="mother" value="<%=k %>"/>
Pseudo name:<br/><input name="pseudo"/><br/>
Comment:<br/><textarea name="text" rows="5" cols="50"></textarea>
<input type="submit" value="Send!"/>
</form>

</div>

<div>
<%
ps=(Posts)request.getServletContext().getAttribute(k);
if (ps!=null){
	List<Post> lps=ps.getPosts();
	for (Post pc:lps){
		String kc=pc.getKey();
		String textc=Utils.escapeHTML(pc.getPost().getString("t"));
		String pseudoc=Utils.escapeHTML(pc.getPost().getString("p"));
		%><div id="a_<%=kc %>"><br/>
		<% 
		  String uvc="none";
		  String dvc="none";
		  if (s==null || s.getAttribute(kc)==null){
			  uvc="inline";
		  } else if (s!=null && s.getAttribute(kc)!=null){
			  dvc="inline";
		  }
			  %>
			  <a id="u_<%=kc %>" href="javascript:_5megs.upvote('<%=kc%>')" style="display:<%=uvc%>;text-decoration:none;" title="Upvote">&#8679;</a>
			  <a id="d_<%=kc %>" href="javascript:_5megs.downvote('<%=kc%>')" style="display:<%=dvc%>;text-decoration:none;" title="Downvote">&#8681;</a><%
		%>
		<span id="s_<%=kc %>"><%=pc.getScore() %></span>&nbsp;By <%=pseudoc %>
		<br/>
		<%= textc %>
		</div><%
	}
}
%>
</div>
	
	<%
} else {
	%>
	<div>
<%=title %>
</div>
	<% 
}
%>

</body>
</html>