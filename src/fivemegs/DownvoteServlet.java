package fivemegs;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Downvote a post
 */
@WebServlet("/downvote")
public class DownvoteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownvoteServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String key=request.getParameter("k");
		HttpSession sss=request.getSession();
		if (sss!=null && sss.getAttribute(key)!=null){
			String ctxKey=Post.getCtxKey(key);
			Posts ps=(Posts)request.getServletContext().getAttribute(ctxKey);
			response.setContentType("application/json");
			if (ps!=null){
				Post p=ps.downvote(key);
				if (p!=null){
					sss.removeAttribute(key);
					response.getWriter().print(p.getPost().toString());
				}
			}
		}
	}

}
