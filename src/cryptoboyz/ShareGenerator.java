package cryptoboyz;

public class ShareGenerator {	
	public ShareGenerator(String message, int k) {
		// TODO generate some prime
		// TODO generate a polynomial for our message
	}
	
	public Share generate() {
		// TODO
		return new Share();
	}
	
	public static void main(String[] args) {
		// Parse arguments here, TODO
		ShareGenerator s = new ShareGenerator("message", 5);
		
		int m = 10;
		for(int i = 0; i < m; i++) {
			System.out.println(s.generate());
		}
	}
}
