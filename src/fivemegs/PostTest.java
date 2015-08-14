package fivemegs;

import static org.junit.Assert.*;

import org.json.JSONObject;
import org.junit.Test;

/**
 * test post
 *
 */
public class PostTest {

	@Test
	public void testKeyPost() {
		JSONObject o1=PostsTest.story1();
		assertTrue(Post.isPost(o1));
		assertFalse(Post.isComment(o1));
		String k=Post.getKey(o1);
		assertNull(Post.getMother(k));
		assertEquals("posts",Post.getCtxKey(k));
		String k2=Post.getKey(PostsTest.story2());
		assertFalse(k.equals(k2));
	}
	
	@Test
	public void testKeyComment() {
		JSONObject o1=comment1();
		assertFalse(Post.isPost(o1));
		assertTrue(Post.isComment(o1));
		String k=Post.getKey(o1);
		assertNotNull(Post.getMother(k));
		assertEquals("mother1",Post.getCtxKey(k));
		String k2=Post.getKey(PostsTest.story1());
		assertFalse(k.equals(k2));
	}
	
	@Test
	public void testSanitizeKey(){
		JSONObject s1=new JSONObject();
		s1.put("t", "comment1");
		s1.put("p", "/:\\\"'abc123");
		s1.put("m", "mother1");
		long t=System.currentTimeMillis();
		s1.put("d", t);
		String k=Post.getKey(s1);
		assertEquals("c_7_mother1_____abc123"+t,k);
	}
	
	public static JSONObject comment1(){
		JSONObject s1=new JSONObject();
		s1.put("t", "comment1");
		s1.put("p", "pseudo1");
		s1.put("m", "mother1");
		s1.put("d", System.currentTimeMillis());
		return s1;
	}

}
