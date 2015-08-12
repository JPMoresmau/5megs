package fivemegs;

import java.io.Serializable;
import java.util.Comparator;

import org.json.JSONObject;

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
			return post.getString("h")+post.getString("p")+post.getLong("d");
		}
		return null;
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
		if (!post.getString("h").equals(other.post.getString("h"))){
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
