package fivemegs;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

/**
 * Posts in popularity order
 * This is basically a linked list where a post can be created and up/downvoted given its id
 *
 */
public class Posts implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3023177233099555366L;
	private LinkedPost first=null;
	private LinkedPost last=null;
	
	private Map<String,LinkedPost> postByKey=new HashMap<>();
	
	public Post upvote(String k){
		LinkedPost p=postByKey.get(k);
		if (p!=null){
			inc(p);
			return p.post;
		}
		return null;
	}
	
	public Post downvote(String k){
		LinkedPost p=postByKey.get(k);
		if (p!=null){
			dec(p);
			return p.post;
		}
		return null;
	}

	public void addPost(JSONObject obj){
		String k=Post.getKey(obj);
		LinkedPost p=postByKey.get(k);
		if (p==null){
			p=new LinkedPost();
			p.post=new Post(obj);
			LinkedPost lp=last;
			long md=obj.getLong("d");
			while (lp!=null && (getComparisonValue(lp.post)<getComparisonValue(p.post) || (getComparisonValue(lp.post)==getComparisonValue(p.post) && lp.post.getPost().getLong("d")<md))){
				lp=lp.previous;
			}
			if (lp==null){
				if (first!=null){
					first.previous=p;
				}
				p.next=first;
				first=p;
				if (last==null){
					last=p;
				}
			} else {
				LinkedPost n=lp.next;
				lp.next=p;
				p.next=n;
				p.previous=lp;
				if (n!=null){
					n.previous=p;
				} else {
					last=p;
				}
			}
			
			
			postByKey.put(k, p);
			
		} else {
			inc(p);
		}
	}
	
	private void inc(LinkedPost p){
		p.post.incScore();
		while (p.previous!=null && getComparisonValue(p.previous.post)<=getComparisonValue(p.post)){
			LinkedPost pn=p.next;
			LinkedPost pp=p.previous;
			LinkedPost ppp=pp.previous;
			if (ppp!=null){
				ppp.next=p;
			}
			p.previous=ppp;
			pp.previous=p;
			p.next=pp;
			pp.next=pn;
			if (pn!=null){
				pn.previous=pp;
			}
			if (p.next==first){
				first=p;
			}
			if (pn==null){
				last=pp;
			}
		}
	}
	
	private void dec(LinkedPost p){
		p.post.decScore();
		if (p.post.getScore()<1){
			if (first==p){
				first=p.next;
			}
			if (last==p){
				last=p.previous;
			}
			if (p.previous!=null){
				p.previous.next=p.next;
			}
			if (p.next!=null){
				p.next.previous=p.previous;
			}
		} else {
			while (p.next!=null && getComparisonValue(p.next.post)>=getComparisonValue(p.post)){
				LinkedPost pn=p.next;
				LinkedPost pp=p.previous;
				LinkedPost pnn=pn.next;
				if (pnn!=null){
					pnn.previous=p;
				}
				p.next=pnn;
				if (pp!=null){
					pp.next=pn;
				} else {
					first=pn;
				}
				pn.previous=pp;
				p.previous=pn;
				pn.next=p;
				
				if (p.previous==last){
					last=p;
				}
			}
		}
	}
	
	
	public Post getByKey(String k){
		LinkedPost lp=postByKey.get(k);
		if (lp!=null){
			return lp.post;
		}
		return null;
	}

	public List<Post> getPosts(int start,int length){
		List<Post> posts=new LinkedList<>();
		LinkedPost lp=first;
		int a=0;
		while (lp!=null && a<start){
			lp=lp.next;
			a++;
		}
		int end=start+length;
		while (lp!=null && a<end){
			posts.add(lp.post);
			lp=lp.next;
			a++;
		}
		return posts;
	}

	public List<Post> getPosts(){
		List<Post> posts=new LinkedList<>();
		LinkedPost lp=first;
		
		while (lp!=null){
			posts.add(lp.post);
			lp=lp.next;
		}
		return posts;
	}
	
	public int size(){
		return postByKey.size();
	}

	/**
	 * get the value to use to sort posts
	 * uses the heat by default
	 * @param p
	 * @return
	 */
	protected double getComparisonValue(Post p){
		return p.getHeat();
	}
	
	/**
	 * The linked list element
	 */
	private static class LinkedPost {
		Post post;
		LinkedPost next;
		LinkedPost previous;
	}
}
