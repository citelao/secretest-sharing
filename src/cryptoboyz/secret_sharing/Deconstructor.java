package cryptoboyz.secret_sharing;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Random;

public class Deconstructor {

	private BigInteger prime;
	private Polynomial polynomial;
	private int sharesGenerated;

	/**
	 * Create a Deconstructor to spread the word to your friends!
	 * 
	 * @param message the message to send
	 * @param k the minimum number of shares required to reconstruct the
	 *            polynomial
	 */
	public Deconstructor(String message, int k) {
		// TODO check p > n (size of field should be larger than number of
		// participants)

		Random rnd = new Random();
		prime = BigInteger.probablePrime(1000, rnd);
		
		// after generating p, convert message to integer value
		// 	this will be the constant term of the polynomial
		byte[] b = message.getBytes();
		BigInteger msgtoint = new BigInteger(b);
		Polynomial poly = new Polynomial(msgtoint, 0);

		// polynomial of degree k-1
		for (int i = 1; i < k - 1; i++) {
			BigInteger coeff = new BigInteger(1000, rnd);
			while (coeff.compareTo(prime) > 0) {
				coeff = new BigInteger(1000, rnd);
			}
			Polynomial term = new Polynomial(coeff, i);
			poly = poly.plus(term);
		}
		this.polynomial = poly;
	}

	public Share generate() {
		this.sharesGenerated += 1;
		int x = this.sharesGenerated;
		BigInteger y = polynomial.evaluate(x).mod(this.prime); // each share is
															   // (x, f(x) mod p)
		return new Share(x, y);
	}

	public HashSet<Share> generate(int n) {
		HashSet<Share> shares = new HashSet<Share>();
		for (int x = 1; x < n; x++) {
			shares.add(this.generate());
		}
		
		return shares;
	}

	public BigInteger getPrime() {
		return this.prime;
	}
}
