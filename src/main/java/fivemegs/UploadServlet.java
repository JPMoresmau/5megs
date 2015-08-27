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
				boolean write=false;
				if (arr.length()==1){
					long d=obj.optLong("d", 0);
					// assign the date on the server
					if (d==0){
						obj.put("d", System.currentTimeMillis());
						write=true;
					}
				}
				String k=Post.getKey(obj);
				if (k!=null){
					HttpSession sss=request.getSession(true);
					if(sss.getAttribute(k)==null){
						sss.setAttribute(Constants.ATTRIBUTE_PSEUDO, obj.getString("p"));
						sss.setAttribute(k, obj);
						String ctxKey=Post.getCtxKey(k);
						Posts ps=(Posts)request.getServletContext().getAttribute(ctxKey);
						if (ps==null){
							ps=Post.isComment(obj)?new Comments():new Posts();
						}
						ps.addPost(obj);
						request.getServletContext().setAttribute(ctxKey, ps);
					}
					if (write){
						response.getWriter().print(obj.toString());
						response.getWriter().flush();
					}
				}
			}
		}
	}

}
