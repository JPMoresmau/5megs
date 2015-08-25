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
<jsp:include page="head.jsp"></jsp:include>
<title>5megs: <%=title %></title>

</head>
<body>
<div class="container-fluid">
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
	%><div id="a_<%=k %>"><h3>
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
	</h3>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;By <strong><%=pseudo %></strong>
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
		$(where+ " > form > h4").hide();
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
<h4 class="text-info">Leave a comment!</h4>
<input type="hidden" name="mother" value="_k_"/>
<div class="form-group"><label for="pseudo">Pseudo name</label><br/><input name="pseudo" id="pseudo" class="form-control"/></div>
<div class="form-group"><label for="text">Comment</label><textarea name="text" id="text" rows="5" cols="50" class="form-control"></textarea></div>
<input name="cancel" type="button" value="Cancel" style="display:none" class="btn btn-default"/>
<input type="submit" value="Send!" class="btn btn-primary"/>
</form>

</div>


<%
}
%>
<div>
<%

if (p==null){
	response.getWriter().print(title);
} else {
	Comment.writeComment(request,out,response,k,0);
}

%>
</div>
</div>
</body>
</html>