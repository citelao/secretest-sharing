package cryptoboyz;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

public class Deconstructor {
	
	private int p;
	private Polynomial polynomial;
	
	public Deconstructor(String message, int n) {
		// TODO generate some prime
		// TODO generate a polynomial for our message
		
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
		
		for(int i=1; i<n; i++){
			Polynomial fuckJava = new Polynomial(coeff[i-1], i);
			poly.plus(fuckJava);
		}
		this.polynomial = poly;
		
	}
	
	public Share generate(int n) {
		// TODO use our polynomial to generate a share
		int y = polynomial.evaluate(n);
		return new Share(n, y); 
	}
	
	public static void main(String[] args) {
		// Parse arguments here, TODO
		Deconstructor s = new Deconstructor("message", 5);
		
		int m = 10;
		for(int i = 0; i < m; i++) {
			System.out.println(s.generate());
		}
	}
}
