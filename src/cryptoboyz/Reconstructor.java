package cryptoboyz;

public class Reconstructor {
	protected String message;
	
	public Reconstructor(Share[] shares) {
		this(shares, shares.length);
	}
	
	public Reconstructor(Share[] shares, int k) {
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
