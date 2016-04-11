package cryptoboyz;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

public class Deconstructor {
	
	private int p;
	private Polynomial polynomial;
	
	//n - number of shares to be handed out
	//k - min number shares needed to reconstruct polynomial
	//	-> polynomial should therefore be degree k-1
	
	public Deconstructor(String message, int n, int k) {
		// TODO generate some prime p
		// TODO generate a polynomial for our message
		// TODO check p > n (size of field should be larger than number of participants)
		
		
		
		//after generating p, convert message to integer value, this will be the constant term of the polynomial
		int msgtoint = 4815; //FIXME
		Polynomial poly = new Polynomial(msgtoint, 0);
		
		//construct array of possible coefficients (members of Z^*_p)
		Integer[] coeff = new Integer[p];
		for(int i=0; i<p; i++){
			coeff[i] = i;
		}
		//shuffle to choose random coefficients
		Collections.shuffle(Arrays.asList(coeff));
		
		//polynomial of degree k-1
		for(int i=1; i<k; i++){
			Polynomial term = new Polynomial(coeff[i-1], i);
			poly.plus(term);
		}
		this.polynomial = poly;
		 
	}
	
	public Share generate(){
		return new Share(0,0);
	}
	
	public HashSet<Share> generate(int n) {
		// TODO use our polynomial to generate all shares
		HashSet<Share> shares = new HashSet<Share>();
		for(int x=1; x<n; x++){
			int y = polynomial.evaluate(x) % this.p; //each share is (x, f(x) mod p)
			Share s = new Share(x, y);
			shares.add(s);
		}
		return shares;
	}
	
	public static void main(String[] args) {
		// Parse arguments here, TODO
		Deconstructor s = new Deconstructor("message", 4, 8);
		
		int m = 10;
		for(int i = 0; i < m; i++) {
			System.out.println(s.generate());
		}
	}
}
