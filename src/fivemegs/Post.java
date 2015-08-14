package fivemegs;

import java.io.Serializable;
import java.util.Comparator;

import org.json.JSONObject;

/**
 * one post (story or comment)
 * the data is in JSON, the score is kept separately
 *
 */
public class Post implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5297914789254645561L;
	private JSONObject post;
	private int score=1;
	
	
	
	public Post(JSONObject post) {
		super();
		this.post = post;
	}



	public String getKey(){
		return getKey(post);
	}
	
	public static String getKey(JSONObject post){
		if (post.has("h") && post.has("p") && post.has("d")){
			return Utils.sanitize("p_"+post.getString("h")+post.getString("p")+post.getLong("d"));
		} else if (post.has("m") && post.has("p") && post.has("d")){
			String m=post.getString("m");
			return Utils.sanitize("c_"+m.length()+"_"+m+post.getString("p")+post.getLong("d"));
		}
		return null;
	}
	
	public static boolean isPost(JSONObject post){
		return !post.has("m");
	}
	
	public static boolean isComment(JSONObject post){
		return post.has("m");
	}
	
	public static String getMother(String key){
		if (key!=null && key.startsWith("c_")){
			int ix=key.indexOf('_',2);
			if (ix>=2){
				try {
					int l=Integer.parseInt(key.substring(2,ix));
					return key.substring(ix+1,ix+1+l);
				} catch (NumberFormatException nfe){
					nfe.printStackTrace();
				}
			}
		}
		return null;
	}
	
	public static String getCtxKey(String key){
		String m=getMother(key);
		if (m==null){
			m="posts";
		}
		return m;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((post == null) ? 0 : getKey().hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Post other = (Post) obj;
		if (post == null) {
			if (other.post != null)
				return false;
		} else if (!getKey().equals(other.getKey()))
			return false;
		if (!post.optString("h").equals(other.post.optString("h"))){
			return false;
		}
		if (!post.getString("p").equals(other.post.getString("p"))){
			return false;
		}
		if (post.getLong("d")!=other.post.getLong("d")){
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "Post [post=" + post + ", score=" + score + "]";
	}


	public JSONObject getPost() {
		return post;
	}
	
	public int getScore() {
		return score;
	}
	
	public void incScore(){
		score++;
	}
	
	public void decScore(){
		score--;
	}
	
	public static class DateComparator implements Comparator<Post>{
		@Override
		public int compare(Post o1, Post o2) {
			return Long.compare(o2.getPost().getLong("d"),o1.getPost().getLong("d"));
		}
	}
}
