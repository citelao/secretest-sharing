package cryptoboyz.zero_knowledge;

public class Main {
	
	public static void main(String[] args){
		Group Z = new Group();
		GroupNumber g = Z.generateMember();
		GroupNumber h = Z.generateMember();
		while(h == g){
			h = Z.generateMember();
		}
		
		GroupNumber x = Z.generateMember();
		
		Prover p = new Prover(g, h, x);
		Verifier v = new Verifier(g, h);
		
	}
}
