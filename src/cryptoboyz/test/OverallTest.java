package cryptoboyz.test;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.junit.Test;

import cryptoboyz.Deconstructor;
import cryptoboyz.Reconstructor;

public class OverallTest {

	@Test
	public void testInverseness() {
		SecureRandom random = new SecureRandom();
		String message = new BigInteger(10, random).toString(32);
		int k = 10;
		Deconstructor d = new Deconstructor(message, k);
		Reconstructor r = new Reconstructor(d.generate(k), d.getPrime());
		if (!r.getMessage().equals(message)) {
			fail("Reconstructor does not reconstruct properly.");
		}
	}
	
	public void testInversenessFail() {
		SecureRandom random = new SecureRandom();
		String message = new BigInteger(130, random).toString(32);
		int k = 10;
		Deconstructor d = new Deconstructor(message, k);
		try {
			new Reconstructor(d.generate(5), k, d.getPrime());
		} catch(IllegalArgumentException e) {
			return;
		}
		
		fail("Expected exception");
	}
}
