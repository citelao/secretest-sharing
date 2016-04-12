package cryptoboyz.test;

import cryptoboyz.Util;
import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

public class UtilTest {
	
	public void eGCDtest(){
		Random rand = new Random();
		for(int i=1; i<11; i++){
			int f = rand.nextInt(101); //random factor between 0 and 100
			int b = f*i;
			int[] e = Util.eGCD(i, b);
			int[] expected = new int[]{b, 0, 1};
			assertEquals(expected, e);
		}
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
