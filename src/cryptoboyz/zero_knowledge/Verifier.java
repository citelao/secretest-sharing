package cryptoboyz.zero_knowledge;

import java.math.BigInteger;

import cryptoboyz.commitment.CommitMessage;
import cryptoboyz.commitment.TrustException;

public class Verifier {
	
	private GroupNumber g;
	private GroupNumber h;
	private GroupNumber gx;
	private GroupNumber hx;

	public Verifier(GroupNumber g, GroupNumber h, GroupNumber gx, GroupNumber hx) {
		this.g = g;
		this.h = h;
		this.gx = gx;
		this.hx = hx;
	}

	/**
	 * 
	 * @param p
	 * @param t number of bits to generate
	 * @return
	 * @throws TrustException
	 */
	public boolean verify(Prover p, int t) throws TrustException {
		// Step 1: choose a choose bit & send it, encrypted-like
		Group commitmentGroup = new Group(t);
		GroupNumber alpha = p.getAlpha(commitmentGroup);
		GroupNumber challenge = commitmentGroup.generateMember();	
		GroupNumber key = commitmentGroup.generateMember();
		GroupNumber generator = commitmentGroup.generateGenerator();
		CommitMessage cm = CommitMessage.Generate(generator, alpha, key, challenge);
		
		System.out.println("c = " + cm);
		
		// Step 2: receive message of g^x h^x
		
		GroupNumber message = p.getMessage(cm);
		System.out.println("(challenge, key)\n"
					+ "\t challenge: " + challenge + "\n"
					+ "\t key: " + key);
		
		// Step 3: send over proof of challenge bit
		// Step 4: receive z
		GroupNumber response = p.getResponse(challenge, key);
		
		// Step 5: confirm!
		// (gh)^z ?= m*(g^x*h^x)^e?
		// g, h given
		// z = response
		// m = message
		// g^x = this.gx
		// h^x = this.hx
		// e = challenge
		GroupNumber ghz = (this.g.multiply(this.h)).exp(response);
		GroupNumber gxhxe = (this.gx.multiply(this.hx)).exp(challenge);
		System.out.println("I am convinced: " + ghz.equals(message.multiply(gxhxe)));
		return ghz.equals(message.multiply(gxhxe));
	}
}
