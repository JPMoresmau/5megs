package fivemegs;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Upload all local content
 */
@WebServlet("/upload")
public class UploadServlet extends HttpServlet {

       
    /**
	 * 
	 */
	private static final long serialVersionUID = 8376377782008956874L;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public UploadServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONArray arr=new JSONArray(new JSONTokener(request.getInputStream()));
		for (int a=0;a<arr.length();a++){
			String s=arr.optString(a);
			if (s!=null && s.length()>0){
				JSONObject obj=new JSONObject(s);
				String k=Post.getKey(obj);
				if (k!=null){
					HttpSession sss=request.getSession(true);
					if(sss.getAttribute(k)==null){
						sss.setAttribute(k, obj);
						Posts ps=(Posts)request.getServletContext().getAttribute("posts");
						if (ps==null){
							ps=new Posts();
						}
						ps.addPost(obj);
						request.getServletContext().setAttribute("posts", ps);
					}
				}
			}
		}
	}

}
