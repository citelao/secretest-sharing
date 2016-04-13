package cryptoboyz;

import java.math.BigInteger;
import java.util.HashSet;

public class Reconstructor {
	protected String message;
	private BigInteger p;
	
	public Reconstructor(HashSet<Share> shares, BigInteger p) {
		this.p = p;
		this(shares, shares.size());
	}
	
	private Reconstructor(HashSet<Share> shares, int k) {
		// TODO lagrangian stuff
		// TODO solve
		for(Share i: shares){
			int m = 1;
			for (Share j: shares){
				if(!j.equals(i)){
					int xj = j.getX();
					int xi = i.getX();
					int inv = Util.inverse(xj-xi, p);
				}
			}
		}
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
