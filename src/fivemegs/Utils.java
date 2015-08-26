package fivemegs;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
}
