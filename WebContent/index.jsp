<%@page import="fivemegs.Constants"%>
<%@page import="fivemegs.Utils"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.Locale"%>
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
<jsp:include page="head.jsp"></jsp:include>
<title>5 megs</title>

<%
HttpSession s=request.getSession();
if (s==null || s.getAttribute(Constants.ATTRIBUTE_UPLOAD)==null){
	request.getSession(true).setAttribute(Constants.ATTRIBUTE_UPLOAD,System.currentTimeMillis());
	%>
	<script type="text/javascript">
	_5megs.upload();
	</script>
	<%
}
boolean mine="mine".equals(request.getParameter(Constants.PARAM_WHOSE));

%>

</head>
<body>
<div class="container-fluid">
<div>
<a href="submit.jsp">Submit new content!</a>&nbsp;|&nbsp;<%
if (mine){
	%><a href="index.jsp">View all content</a><%
} else {
	%><a href="index.jsp?<%=Constants.PARAM_WHOSE %>=mine">View my content only</a><%
}
%>
</div>

<%

Posts ps=(Posts)request.getServletContext().getAttribute(Constants.ATTRIBUTE_POSTS);
if (ps!=null){
	List<Post> lps=null;
	int start=0;
	int next=-1;
	int previous=-1;
	if (mine){
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
		String sts=request.getParameter(Constants.PARAM_START);
		if (sts!=null && sts.length()>0){
			try {
				start=Math.max(0,Integer.parseInt(sts)-1); // zero based
			} catch (NumberFormatException nfe){
				// ignore
			}
		}
		int pageSize=3;
		lps=ps.getPosts(start,pageSize);
		if (start>1){
			previous=Math.max(0, start-pageSize)+1;
		}
		if (ps.size()>start+pageSize){
			next=start+pageSize+1;
		}
	}
	if (lps!=null){
		if (mine && lps.size()>0){
			%>
			<div><a href="javascript:_5megs.clear()" class="btn btn-danger btn-sm" role="button">Clear all</a> (downvote everything and removes it from local storage)</div>
			<%
		}
		
		if (previous>-1 || next>-1){
			%>
			<div>
			<% if (previous>-1){ %>
			<a href="index.jsp?<%=Constants.PARAM_START %>=<%=previous %>">Previous</a>
			<%
				if (next>-1){
					%>&nbsp;|&nbsp;<%
				}
			}  %>
			<% if (next>-1){ %>
			<a href="index.jsp?<%=Constants.PARAM_START %>=<%=next %>">Next</a>
			<% } %>
			</div>
			<%
		}
		
		int ix=start;
		for (Post p:lps){
			ix++;
			String k=p.getKey();
			String title=Utils.escapeHTML(p.getPost().getString("h"));
			String link=p.getPost().optString("l");
			if (link!=null && link.length()>0 && !link.toLowerCase(Locale.ENGLISH).startsWith("javascript")){
				title="<a href='"+link+"' target='_new'>"+title+"</a>";
			} else {
				title="<a href='comments.jsp?k="+URLEncoder.encode(k,"UTF8")+"'>"+title+"</a>";
			}
			
			%>
			<div id="a_<%=k %>" class="row">
				<div class="col-sm-12">
				<h4><%=ix%>.&nbsp;
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
					<span id="s_<%=k %>"><%=p.getScore() %></span>&nbsp;<%=title %></h4>
					<div class="row">
     		 			<div class="col-sm-2 text-right">
			By <strong><%=Utils.escapeHTML(p.getPost().getString("p")) %></strong></div><div class="col-sm-1">
						<a href='comments.jsp?k=<%=URLEncoder.encode(k,"UTF8")%>'>Comments</a>
						</div>
				   </div>
				</div>
			</div><%
		}
		
		if (previous>-1 || next>-1){
			%>
			<div>
			<% if (previous>-1){ %>
			<a href="index.jsp?<%=Constants.PARAM_START %>=<%=previous %>">Previous</a>
			<%
				if (next>-1){
					%>&nbsp;|&nbsp;<%
				}
			}  %>
			<% if (next>-1){ %>
			<a href="index.jsp?<%=Constants.PARAM_START %>=<%=next %>">Next</a>
			<% } %>
			</div>
			<%
		}
	}
}

%>

</div>
</body>
</html>