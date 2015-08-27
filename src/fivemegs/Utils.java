package fivemegs;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

/**
 * utility methods
 *
 */
public class Utils {

	/**
	 * sanitize a string, only digits, characters and underscores are accepted
	 * @param s
	 * @return
	 */
	public static String sanitize(String s){
		if (s!=null){
			StringBuilder sb=new StringBuilder(s.length());
			for (int i = 0; i < s.length(); i++){
			    char c = s.charAt(i);        
			    if (Character.isLetterOrDigit(c)){
			    	sb.append(c);
			    } else {
			    	sb.append('_');
			    }
			}
			return sb.toString();
	
		}
		return s;
	}

	/**
	 * HTML escape without a library
	 * http://stackoverflow.com/a/25228492/827593
	 * @param s
	 * @return
	 */
	public static String escapeHTML(String s) {
	    StringBuilder out = new StringBuilder(Math.max(16, s.length()));
	    for (int i = 0; i < s.length(); i++) {
	        char c = s.charAt(i);
	        if (c > 127 || c == '"' || c == '<' || c == '>' || c == '&') {
	            out.append("&#");
	            out.append((int) c);
	            out.append(';');
	        } else {
	            out.append(c);
	        }
	    }
	    return out.toString();
	}
	
	private static List<TimeUnit> tus=Arrays.asList(TimeUnit.DAYS,TimeUnit.HOURS,TimeUnit.MINUTES);
	
	private static Map<TimeUnit,String> pluralNames=new HashMap<>();
	private static Map<TimeUnit,String> singularNames=new HashMap<>();
	
	static {
		for (TimeUnit tu:tus){
			String s=tu.toString().toLowerCase(Locale.ENGLISH);
			pluralNames.put(tu, s);
			singularNames.put(tu, s.substring(0, s.length()-1));
		}
	}
	
	private static String getTimeAgo(long duration,TimeUnit tu){
		String s=duration>=2?pluralNames.get(tu):singularNames.get(tu);
		return duration+" "+s+" ago";
	}
	
	public static String getTimeAgo(long time){
		long duration=System.currentTimeMillis()-time;
		if (duration<=0){
			return "right now";
		}
		for (TimeUnit tu:tus){
			long conv=tu.convert(duration, TimeUnit.MILLISECONDS);
			if (conv>0){
				return getTimeAgo(conv,tu);
			}
		}
		return "right now";
	}
	
	public static String capitalize(String s){
		if (s==null || s.length()==0){
			return s;
		}
		return s.substring(0,1).toUpperCase(Locale.ENGLISH)+s.substring(1).toLowerCase(Locale.ENGLISH);
	}
	
	/**
	 * as a convenience, get the pseudo used during this session
	 * @param request
	 * @return
	 */
	public static String getCurrentPseudo(HttpServletRequest request){
		String mypseudo="";
		HttpSession s=request.getSession(false);
		if (s!=null){
			mypseudo=(String)s.getAttribute(Constants.ATTRIBUTE_PSEUDO);
			if (mypseudo==null){
				mypseudo="";
			} else {
				mypseudo=Utils.escapeHTML(mypseudo);
			}
		}
		return mypseudo;
	}
	
	public static void writeStorageWarning(HttpServletRequest request,JspWriter out) throws IOException{
		HttpSession s=request.getSession(false);
		if (s==null || !Boolean.TRUE.equals(s.getAttribute(Constants.ATTRIBUTE_ACCEPTED))){
			out.print("<div class='bg-danger row' id='storage_warning'><div class='col-sm-11'>Submitting and upvoting content means storing it on your machine. Only submit and upvote if you're OK with that!</div><div><a class='btn btn-xs btn-danger' href='javascript:_5megs.accept()' role='button'>Close</a></div></div>");
		}
	}
}
