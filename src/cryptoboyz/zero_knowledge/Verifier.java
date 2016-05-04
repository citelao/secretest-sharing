package cryptoboyz.zero_knowledge;

import java.math.BigInteger;

import cryptoboyz.commitment.CommitMessage;

public class Verifier {
	
	private GroupNumber g;
	private GroupNumber h;
	private Group group;
	private GroupNumber gx;
	private GroupNumber hx;

	public Verifier(GroupNumber g, GroupNumber h, GroupNumber gx, GroupNumber hx, Group group) {
		this.g = g;
		this.h = h;
		this.gx = gx;
		this.hx = hx;
		this.group = group;
	}

	public boolean verify(Prover p, BigInteger t) {
		// Step 1: choose a choose bit & send it, encrypted-like
		Group commitmentGroup = new Group(t); // TODO use t as the order
		GroupNumber alpha = p.sendAlpha(commitmentGroup); // p.getAlpha(commitmentGroup)
		GroupNumber challenge = commitmentGroup.generateMember();
		GroupNumber key = commitmentGroup.generateMember();
		GroupNumber generator = commitmentGroup.generateGenerator();
		CommitMessage cm = CommitMessage.Generate(generator, alpha, key, challenge);
		
		// Step 2: receive message of g^x h^x
		GroupNumber message = p.sendMessage(cm); // p.getMessage(cm)
		
		// Step 3: send over proof of challenge bit
		// Step 4: receive z
		GroupNumber response = p.sendResponse(challenge, key); // p.getResponse()
		
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
		return ghz.equals(gxhxe);
	}
}
