package fivemegs;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.json.JSONObject;
import org.junit.Test;

public class PostsTest {

	@Test
	public void testAddPost() {
		Posts ps=new Posts();
		JSONObject s1=story1();
		ps.addPost(s1);
		assertEquals(Arrays.asList(new Post(s1)),ps.getPosts(0, 50));
		assertEquals(Arrays.asList(),ps.getPosts(1, 50));
		JSONObject s2=story2();
		ps.addPost(s2);
		assertEquals(Arrays.asList(new Post(s2),new Post(s1)),ps.getPosts(0, 50));
		assertEquals(Arrays.asList(new Post(s1)),ps.getPosts(1, 50));
		assertEquals(Arrays.asList(),ps.getPosts(2, 50));
		assertEquals(Arrays.asList(new Post(s2)),ps.getPosts(0, 1));
		assertEquals(1,ps.getPosts(0, 50).get(0).getScore());
		assertEquals(1,ps.getPosts(0, 50).get(1).getScore());
		ps.addPost(s1);
		assertEquals(Arrays.asList(new Post(s1),new Post(s2)),ps.getPosts(0, 50));
		assertEquals(2,ps.getPosts(0, 50).get(0).getScore());
		assertEquals(1,ps.getPosts(0, 50).get(1).getScore());
		JSONObject s3=story3();
		ps.addPost(s3);
		assertEquals(Arrays.asList(new Post(s1),new Post(s3),new Post(s2)),ps.getPosts(0, 50));
		ps.addPost(s3);
		assertEquals(Arrays.asList(new Post(s3),new Post(s1),new Post(s2)),ps.getPosts(0, 50));
		assertEquals(2,ps.getPosts(0, 50).get(0).getScore());
		assertEquals(2,ps.getPosts(0, 50).get(1).getScore());
		assertEquals(1,ps.getPosts(0, 50).get(2).getScore());
	}

	@Test
	public void testUpvote(){
		Posts ps=new Posts();
		JSONObject s1=story1();
		ps.addPost(s1);
		JSONObject s2=story2();
		ps.addPost(s2);
		assertEquals(Arrays.asList(new Post(s2),new Post(s1)),ps.getPosts(0, 50));
		assertEquals(1,ps.getPosts(0, 50).get(0).getScore());
		assertEquals(1,ps.getPosts(0, 50).get(1).getScore());
		ps.upvote(Post.getKey(s1));
		assertEquals(Arrays.asList(new Post(s1),new Post(s2)),ps.getPosts(0, 50));
		assertEquals(2,ps.getPosts(0, 50).get(0).getScore());
		assertEquals(1,ps.getPosts(0, 50).get(1).getScore());
		ps.upvote(Post.getKey(s2));
		assertEquals(Arrays.asList(new Post(s2),new Post(s1)),ps.getPosts(0, 50));
		assertEquals(2,ps.getPosts(0, 50).get(0).getScore());
		assertEquals(2,ps.getPosts(0, 50).get(1).getScore());
		JSONObject s3=story3();
		ps.addPost(s3);
		assertEquals(Arrays.asList(new Post(s2),new Post(s1),new Post(s3)),ps.getPosts(0, 50));
		ps.upvote(Post.getKey(s3));
		assertEquals(Arrays.asList(new Post(s3),new Post(s2),new Post(s1)),ps.getPosts(0, 50));
		ps.upvote(Post.getKey(s3));
		assertEquals(Arrays.asList(new Post(s3),new Post(s2),new Post(s1)),ps.getPosts(0, 50));
		
		ps.upvote(Post.getKey(s1));
		ps.upvote(Post.getKey(s2));
		ps.upvote(Post.getKey(s3));
		assertEquals(Arrays.asList(new Post(s3),new Post(s2),new Post(s1)),ps.getPosts(0, 50));
	}
	
	@Test
	public void testDownvote(){
		Posts ps=new Posts();
		JSONObject s1=story1();
		ps.addPost(s1);
		JSONObject s2=story2();
		ps.addPost(s2);
		ps.upvote(Post.getKey(s1));
		assertEquals(Arrays.asList(new Post(s1),new Post(s2)),ps.getPosts(0, 50));
		ps.downvote(Post.getKey(s1));
		assertEquals(Arrays.asList(new Post(s2),new Post(s1)),ps.getPosts(0, 50));
		assertEquals(1,ps.getPosts(0, 50).get(0).getScore());
		assertEquals(1,ps.getPosts(0, 50).get(1).getScore());
		ps.downvote(Post.getKey(s1));
		assertEquals(Arrays.asList(new Post(s2),new Post(s1)),ps.getPosts(0, 50));
		assertEquals(1,ps.getPosts(0, 50).get(0).getScore());
		assertEquals(0,ps.getPosts(0, 50).get(1).getScore());
		JSONObject s3=story3();
		ps.addPost(s3);
		ps.upvote(Post.getKey(s3));
		ps.downvote(Post.getKey(s3));
		assertEquals(Arrays.asList(new Post(s2),new Post(s3),new Post(s1)),ps.getPosts(0, 50));
		ps.downvote(Post.getKey(s3));
		assertEquals(Arrays.asList(new Post(s2),new Post(s1),new Post(s3)),ps.getPosts(0, 50));
		ps.upvote(Post.getKey(s3));
		ps.upvote(Post.getKey(s3));
		assertEquals(Arrays.asList(new Post(s3),new Post(s2),new Post(s1)),ps.getPosts(0, 50));
		
		ps.downvote(Post.getKey(s1));
		ps.downvote(Post.getKey(s2));
		ps.downvote(Post.getKey(s3));
		assertEquals(Arrays.asList(new Post(s3),new Post(s2),new Post(s1)),ps.getPosts(0, 50));
		
		
	}
	
	private static JSONObject story1(){
		JSONObject s1=new JSONObject();
		s1.put("h", "title1");
		s1.put("p", "pseudo1");
		s1.put("d", System.currentTimeMillis());
		return s1;
	}
	
	private static JSONObject story2(){
		JSONObject s1=new JSONObject();
		s1.put("h", "title2");
		s1.put("p", "pseudo2");
		s1.put("d", System.currentTimeMillis());
		return s1;
	}
	
	private static JSONObject story3(){
		JSONObject s1=new JSONObject();
		s1.put("h", "title3");
		s1.put("p", "pseudo3");
		s1.put("d", System.currentTimeMillis());
		return s1;
	}
}
