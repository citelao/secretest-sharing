package cryptoboyz;

public class Util {
	
	public static int[] eGCD(int a, int b){
		
		int r = a % b;
		if(r == 0){
			return new int[]{b, 0, 1};
		}
		
		int q = a / b;
		
		//{d, X, Y} = eGCD(b, r), with Xb + Yr = d
		int[] result = eGCD(b, r);
		
		//return {d, Y, X - qY}
		return new int[] {result[0], result[2], result[1] - q*result[2]};
	}
	
	//calculate inverse of a mod N
	public static int inverse(int a, int N){
		int[] ext = eGCD(a, N);
		int d = ext[0];
		if(d != 1){
			return -1; //inverse does not exist
		}
		//return X mod N
		return (ext[1] % N);
	}
}
