package cryptoboyz;

import java.math.BigInteger;

public class Util {
	
	//assume 0 < b <= a
	public static BigInteger[] eGCD(BigInteger a, BigInteger b){
	
		BigInteger r = a.mod(b);
		
		if(r == BigInteger.ZERO){
			return new BigInteger[]{b, BigInteger.ZERO, BigInteger.ONE};
		}
		
		BigInteger q = a.divide(b);
		
		//{d, X, Y} = eGCD(b, r), with Xb + Yr = d
		BigInteger[] result = eGCD(b, r);
		
		//return {d, Y, X - qY}
		return new BigInteger[] {result[0], result[2], result[1].subtract(q.multiply(result[2]))};
	}
	
	//calculate inverse of a mod N
	public static BigInteger inverse(BigInteger a, BigInteger N){
		BigInteger[] ext = eGCD(a, N);
		BigInteger d = ext[0];
		if(d != BigInteger.ONE){
			throw new ArithmeticException(); //inverse does not exist
		}
		//return X mod N
		return (ext[1].mod(N));
	}
	
	public static void main(String[] args){
		//BigInteger[] g = eGCD(15,6);
//		for(int i=0; i<g.length; i++){
//			System.out.println("[" + i + "]:" + g[i]);
//		}
	}
}
