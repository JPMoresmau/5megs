package fivemegs;

/**
 * use only the score to sort
 * 
 */
public class Comments extends Posts {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8302494140104973379L;

	@Override
	protected double getComparisonValue(Post p) {
		return p.getScore();
	}
	
}
