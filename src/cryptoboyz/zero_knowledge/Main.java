package cryptoboyz.zero_knowledge;

import java.math.BigInteger;

import cryptoboyz.commitment.TrustException;

public class Main {

	public static void main(String[] args){
		Group group = new Group();
		GroupNumber g = group.generateMember();
		GroupNumber h = group.generateMember();
		while(h.equals(g)){
			h = group.generateMember();
		}

		GroupNumber x = group.generateNonTrivialMember();

		System.out.println("The problem:");
		System.out.println("g: " + g + "\t(g^x: " + g.exp(x) + ")");
		System.out.println("h: " + h + "\t(h^x: " + h.exp(x) + ")");
		System.out.println("\t(x:  " + x + ")");
		System.out.println("\t(gh^x: " + g.multiply(h).exp(x) + ")");
		System.out.println("|group|: " + group.getOrder() + "");

		System.out.println("\nThe protocol:");
		

		try {
			for(int i=0; i<4815; ++i){
				Prover p = new Prover(g, h, x, group);
				Verifier v = new Verifier(g, h, g.exp(x), h.exp(x));
				if(!v.verify(p, 6)){
					System.err.println("Failed to verify!!!!!!!!!!");
				}
			}
			
		} catch (TrustException e) {
			System.err.println("You cheating little rascal");
			e.printStackTrace();
		}
	}
}
