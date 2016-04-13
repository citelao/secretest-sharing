package cryptoboyz.test;

import cryptoboyz.Util;
import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

public class UtilTest {
	
	@Test
	public void eGCDtest(){
		Random rand = new Random();
		
		//base case
		for(int i = 1; i < 4815; i++){
			int f = rand.nextInt(101) + 1;
			int N = f*i;
			int[] e = Util.eGCD(N, i);
			int[] expected = new int[]{i, 0, 1};
			for(int j = 0; j < e.length; j++){
				assertEquals(expected[j], e[j]);
			}
		}
		
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
