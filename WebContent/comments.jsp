<%@page import="fivemegs.Comment"%>
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
	<div id="topComment">
	</div>

<script type="text/javascript">

function showCommentForm(where,k,withCancel){
	var h=$("#hiddenCommentForm").html();
	h=h.replace('_k_',k);
	$(where).append(h);
	if (withCancel){
		var frm=$(where+ " > form");
		var cancel=$(where+ " > form > input").filter(":button");
		cancel.show();
		cancel.click(function(){
			$("#r_"+k).show();
			frm.remove();
		});
	}
}

function reply(kc){
	$("#r_"+kc).hide();
	showCommentForm("#a_"+kc, kc, true);
}

$().ready(function(){
	showCommentForm("#topComment",'<%=k%>',false);
});

</script>

<div id="hiddenCommentForm" style="display:none">
<form onSubmit="return _5megs.commentForm(this,'comments.jsp?k=<%=URLEncoder.encode(k,"UTF8")%>');">
<input type="hidden" name="mother" value="_k_"/>
Pseudo name:<br/><input name="pseudo"/><br/>
Comment:<br/><textarea name="text" rows="5" cols="50"></textarea>
<br>
<input name="cancel" type="button" value="Cancel" style="display:none"/>
<input type="submit" value="Send!"/>
</form>

</div>


<%
}
%>
<div>
<%

if (!Comment.writeComment(request,out,response,k,0)){
	response.getWriter().print(title);
}

%>
</div>

</body>
</html>