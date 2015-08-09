package fivemegs;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class UpvoteServlet
 */
@WebServlet("/upvote")
public class UpvoteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpvoteServlet() {
        super();
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String key=request.getParameter("k");
		HttpSession sss=request.getSession();
		if (sss==null || sss.getAttribute(key)==null){
			Posts ps=(Posts)request.getServletContext().getAttribute("posts");
			response.setContentType("application/json");
			if (ps!=null){
				Post p=ps.upvote(key);
				if (p!=null){
					sss.setAttribute(key, p.getPost());
					response.getWriter().print(p.getPost().toString());
				}
			}
		}
	}

}
