package fivemegs;

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
	
}
