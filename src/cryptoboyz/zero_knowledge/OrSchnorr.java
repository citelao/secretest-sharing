package cryptoboyz.zero_knowledge;

import cryptoboyz.commitment.TrustException;

public class OrSchnorr {
	private static boolean DEBUG = false;

	public static void main(String[] args) {
		for (int i = 0; i < 30; ++i) {
			Group group = new Group();
			GroupNumber g = group.generateMember();
			GroupNumber h = group.generateMember();
			while (h.equals(g)) {
				h = group.generateMember();
			}
			
			GroupNumber gprime = group.generateMember();
			GroupNumber hprime = group.generateMember();
			while (hprime.equals(gprime)) {
				hprime = group.generateMember();
			}
			
			GroupNumber x = group.generateNonTrivialMember();

			if (DEBUG) {
				System.out.println("The problem:");
				System.out.println("g: " + g + "\t(g^x: " + g.exp(x) + ")");
				System.out.println("h: " + h + "\t(h^x: " + h.exp(x) + ")");
				System.out.println("\t(x: " + x + ")");
				System.out.println("\t(gh^x: " + g.multiply(h).exp(x) + ")");
				System.out.println("|group|: " + group.getOrder() + "");

				System.out.println("\nThe protocol:");
			}

			try {
				Prover p = new Prover(g, h, x, gprime, hprime, group); //proving two statements
				Verifier v = new Verifier(g, h, g.exp(x), h.exp(x), gprime, hprime);
				if (!v.verify(p, 40)) {
					System.err.println("Failed to verify!!!!!!!!!!");
					return;
				}
			} catch (TrustException e) {
				System.err.println("You cheating little rascal");
				e.printStackTrace();
			}

			//System.out.println("" + i + ": successful.");
		}

		System.out.println("Done, no errors.");
	}
}
