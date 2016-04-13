package cryptoboyz.test;

import cryptoboyz.Util;
import static org.junit.Assert.*;

import java.math.BigInteger;
import java.util.Random;

import org.junit.Test;

public class UtilTest {

	@Test
	public void eGCDtest() {
		Random rand = new Random();

		for (int i = 1; i < 4815; i++) {
			BigInteger bi = BigInteger.valueOf(i);
			BigInteger f;
			do {
				f = new BigInteger(30, rand);
			} while (f.compareTo(bi) == -1);
			BigInteger n = f.multiply(bi);
			BigInteger[] e = Util.eGCD(n, bi);
			BigInteger[] expected = new BigInteger[] { bi, BigInteger.ZERO, BigInteger.ONE };
			for (int j = 0; j < e.length; j++) {
				assertEquals(expected[j], e[j]);
			}
		}

	}
}