package fivemegs;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

/**
 * Generate comment code
 */
public class Comment {

	public static boolean writeComment(HttpServletRequest request,JspWriter out,HttpServletResponse response,String k,int pos) throws IOException{
		Posts ps=(Posts)request.getServletContext().getAttribute(k);
		
		if (ps!=null){
			HttpSession s=request.getSession();
			List<Post> lps=ps.getPosts();
			for (Post pc:lps){
				String kc=pc.getKey();
				String textc=Utils.escapeHTML(pc.getPost().getString("t"));
				String pseudoc=Utils.escapeHTML(pc.getPost().getString("p"));
				out.print("<div id='a_"+kc+"' style='padding-left:"+pos+"px'><br/>");
			
				String time=Utils.getTimeAgo(pc.getPost().getLong("d"));
			  String uvc="none";
			  String dvc="none";
			  if (s==null || s.getAttribute(kc)==null){
				  uvc="inline";
			  } else if (s!=null && s.getAttribute(kc)!=null){
				  dvc="inline";
			  }
			  out.print("<a id=\"u_"+kc+"\" href=\"javascript:_5megs.upvote('"+kc+"')\" style=\"display:"+uvc+";text-decoration:none;\" title=\"Upvote\">&#8679;</a>");
			  out.print("<a id=\"d_"+kc+"\" href=\"javascript:_5megs.downvote('"+kc+"')\" style=\"display:"+dvc+";text-decoration:none;\" title=\"Downvote\">&#8681;</a>");
			  out.print("<span id=\"s_"+kc+"\"><strong>"+pc.getScore() +"</strong></span>&nbsp;"+pseudoc+"&nbsp;"+time);
			  out.print("<br/>");
			  out.print(textc);
			  out.print("<br/>");
			  out.print("<a id=\"r_"+kc +"\" href=\"javascript:reply('"+kc+"')\" class=\"btn btn-default btn-sm\">Reply</a>");
			  out.print("</div>");
			  out.print("<div>");
			  writeComment(request,out,response,kc,pos+20);
			  out.print("</div>");
			}
			return true;
		} 
		return false;
	}
}
