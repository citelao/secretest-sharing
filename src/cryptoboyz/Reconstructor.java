package cryptoboyz;

import java.math.BigInteger;
import java.util.HashSet;

public class Reconstructor {
	protected String message;

	public Reconstructor(HashSet<Share> shares, BigInteger prime) {
		this(shares, shares.size(), prime);
	}

	private Reconstructor(HashSet<Share> shares, int k, BigInteger p) {
		BigInteger sum = BigInteger.ZERO;
		for (Share i : shares) {
			BigInteger f = i.getY(); // f(x_i)
			BigInteger m = BigInteger.ONE;
			for (Share j : shares) {
				if (!j.equals(i)) {
					int xj = j.getX();
					int xi = i.getX();
					BigInteger sub = BigInteger.valueOf(xj - xi);
					BigInteger inv = Util.inverse(sub, p);

					BigInteger num = BigInteger.valueOf(xj);
					m = m.multiply(num.multiply(inv));

				}
			}
			sum.add(f.multiply(m));
		}
	}

	public String getMessage() {
		return this.message;
	}

	public static void main(String[] args) {
		// TODO actually give our reconstructor a list of shares.
		Reconstructor r = new Reconstructor(null, null);
		System.out.println(r.getMessage());
	}

}
