package cryptoboyz;

import java.util.HashSet;

public class Reconstructor {
	protected String message;
	
	public Reconstructor(HashSet<Share> shares) {
		this(shares, shares.size());
	}
	
	public Reconstructor(HashSet<Share> shares, int k) {
		// TODO lagrangian stuff
		// TODO solve
		
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public static void main(String[] args) {
		// TODO actually give our reconstructor a list of shares.
		Reconstructor r = new Reconstructor(null);
		System.out.println(r.getMessage());
	}

}
