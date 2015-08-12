package fivemegs;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Clear all the session has upvoted
 */
@WebServlet("/clear")
public class ClearServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClearServlet() {
        super();
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sss=request.getSession();
		if (sss!=null){
			Posts ps=(Posts)request.getServletContext().getAttribute("posts");
			response.setContentType("application/json");
			if (ps!=null){
				Enumeration<String> e=sss.getAttributeNames();
				Set<String> ns=new HashSet<>();
				while (e.hasMoreElements()){
					String key=e.nextElement();
					Post p=ps.downvote(key);
					if (p!=null){
						ns.add(key);
					}
				}
				for (String n:ns){
					sss.removeAttribute(n);
				}
				
			}
		}
	}

}
