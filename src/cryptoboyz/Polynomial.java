package cryptoboyz;

import java.math.BigInteger;

public class Polynomial {

	/**
	 * @authors: Robert Sedgewick and Kevin Wayne
	 */

	private BigInteger[] coef; // coefficients
	private int deg; // degree of polynomial (0 for the zero polynomial)

	// a * x^b
	public Polynomial(BigInteger a, int b) {
		coef = new BigInteger[b + 1];
		for (int i = 0; i < b; i++) {
			coef[i] = BigInteger.ZERO;
		}
		coef[b] = a;
		deg = degree();
	}

	// return the degree of this polynomial (0 for the zero polynomial)
	public int degree() {
		int d = 0;
		for (int i = 0; i < coef.length; i++)
			if (coef[i] != BigInteger.ZERO)
				d = i;
		return d;
	}

	// return c = a + b
	public Polynomial plus(Polynomial b) {
		Polynomial a = this;
		Polynomial c = new Polynomial(BigInteger.ZERO, Math.max(a.deg, b.deg));
		for (int i = 0; i <= a.deg; i++)
			c.coef[i] = c.coef[i].add(a.coef[i]);
		for (int i = 0; i <= b.deg; i++)
			c.coef[i] = c.coef[i].add(b.coef[i]);
		c.deg = c.degree();
		return c;
	}

	// do a and b represent the same polynomial?
	public boolean eq(Polynomial b) {
		Polynomial a = this;
		if (a.deg != b.deg)
			return false;
		for (int i = a.deg; i >= 0; i--)
			if (a.coef[i] != b.coef[i])
				return false;
		return true;
	}

	// use Horner's method to compute and return the polynomial evaluated at x
	public BigInteger evaluate(int x) {
		BigInteger p = BigInteger.ZERO;
		BigInteger n = BigInteger.valueOf(x);
		for (int i = deg; i >= 0; i--) {
			p = coef[i].add(p.multiply(n));
		}

		return p;
	}
	

    // convert to string representation
    public String toString() {
        if (deg ==  0) return "" + coef[0];
        if (deg ==  1) return coef[1] + "x + " + coef[0];
        String s = coef[deg] + "x^" + deg;
        for (int i = deg-1; i >= 0; i--) {
            if      (coef[i] == BigInteger.ZERO) continue;
            else if (coef[i].compareTo(BigInteger.ZERO) > 0) s = s + " + " + ( coef[i]);
            if      (i == 1) s = s + "x";
            else if (i >  1) s = s + "x^" + i;
        }
        return s;
    }

}
