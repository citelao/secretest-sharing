package cryptoboyz.zero_knowledge;

public class Verifier {
	
	public Verifier(GroupNumber g, GroupNumber h) {
		// TODO
	}

	public boolean verify(Prover p) {
		// TODO
		// Step 1: choose a choose bit & send it, encrypted-like
		
		
		
		// Step 2: receive message of g^x h^x
		
		// Step 3: send over proof of challenge bit
		
		// Step 4: receive z
		
		// Step 5: confirm!
		
		return false;
	}
	
	public boolean verify(Prover p, int times) {
		for(int i = 0; i < times; i++) {
			if(!this.verify(p)) {
				return false;
			}
		}
		
		return true;
	}
	
}
