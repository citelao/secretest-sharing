package cryptoboyz.zero_knowledge;

public class Verifier {
	
	public Verifier(GroupNumber g, GroupNumber h) {
		// TODO
	}

	public boolean verify(Prover v) {
		// TODO, duh
		return false;
	}
	
	public boolean verify(Prover v, int times) {
		for(int i = 0; i < times; i++) {
			if(!this.verify(v)) {
				return false;
			}
		}
		
		return true;
	}
	
}
