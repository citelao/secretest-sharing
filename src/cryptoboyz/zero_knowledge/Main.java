package cryptoboyz.zero_knowledge;

import cryptoboyz.commitment.TrustException;

public class Main {
	
	public static void main(String[] args){
		Group Z = new Group();
		GroupNumber g = Z.generateMember();
		GroupNumber h = Z.generateMember();
		while(h.equals(g)){
			h = Z.generateMember();
		}
		
		GroupNumber x = Z.generateMember();
		Prover p = new Prover(g, h, x, Z);
		Verifier v = new Verifier(g, h, g.exp(x), h.exp(x));
		try {
			v.verify(p, 4);
		} catch (TrustException e) {
			System.err.println("You cheating little rascal");
//			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
