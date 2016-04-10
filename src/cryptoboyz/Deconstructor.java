package cryptoboyz;

public class Deconstructor {	
	public Deconstructor(String message, int k) {
		// TODO generate some prime
		// TODO generate a polynomial for our message
	}
	
	public Share generate() {
		// TODO use our polynomial to generate a share
		return new Share();
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
