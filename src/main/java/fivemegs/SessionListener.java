package fivemegs;

import java.util.Enumeration;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Listen on session destroy and downvote accordingly
 *
 */
@WebListener
public class SessionListener implements HttpSessionListener {

    /**
     * Default constructor. 
     */
    public SessionListener() {
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent arg0)  { 
    }

	/**
	 * Everything upvoted in that session gets downvoted again when the session expires. Tough, but fair.
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent arg0)  { 
    	HttpSession sss=arg0.getSession();
    	if (sss!=null){
			Enumeration<String> e=sss.getAttributeNames();
			while (e.hasMoreElements()){
				String key=e.nextElement();
				String ctxKey=Post.getCtxKey(key);
				Posts ps=(Posts)sss.getServletContext().getAttribute(ctxKey);
				if (ps!=null){
					ps.downvote(key);
				}
			}
			
		}
    }
	
}
