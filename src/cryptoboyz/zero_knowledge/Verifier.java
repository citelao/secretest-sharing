package cryptoboyz.zero_knowledge;


import java.math.BigInteger;

import cryptoboyz.commitment.CommitMessage;
import cryptoboyz.commitment.TrustException;

public class Verifier implements IVerifier {
	
	private static boolean DEBUG = false;
	
	private GroupNumber[] gs;
	private GroupNumber[] hs;
	private GroupNumber[] gxs;
	private GroupNumber[] hxs;
	
	public Verifier(GroupNumber g, GroupNumber h, GroupNumber gx, GroupNumber hx) {
		this.gs = new GroupNumber[1];
		this.gs[0] = g;
		this.hs = new GroupNumber[1];
		this.hs[0] = h;
		
		this.gxs = new GroupNumber[1];
		this.gxs[0] = gx;
		this.hxs = new GroupNumber[1];
		this.hxs[0] = hx;
	}
	
	public Verifier(GroupNumber g, GroupNumber h, GroupNumber gx, GroupNumber hx,
					GroupNumber gprime, GroupNumber hprime,
					GroupNumber gprimex, GroupNumber hprimex) {
		this.gs = new GroupNumber[2];
		this.gs[0] = g;
		this.gs[1] = gprime;
		this.hs = new GroupNumber[2];
		this.hs[0] = h;
		this.hs[1] = hprime;
		
		this.gxs = new GroupNumber[2];
		this.gxs[0] = gx;
		this.gxs[1] = gprimex;
		this.hxs = new GroupNumber[2];
		this.hxs[0] = hx;
		this.hxs[1] = hprimex;
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
		
		if(DEBUG) {
			System.out.println("\t\t(c = " + challenge + ")");
			System.out.println("\tc = " + cm);
		}
		
		// Step 2: receive message of g^r h^r
		GroupNumber[] messages = p.getMessages(cm);
		
		// Step 3: send over proof of challenge bit
		// Step 4: receive z
		if(DEBUG) {
			System.out.println("\t(challenge, key) = \n"
					+ "\t\t challenge: " + challenge + "\n"
					+ "\t\t key: " + key);
		}
		//these are the z's
		VerifyPackage[] responses = p.getResponses(challenge, key);
		
		// Step 5: confirm!
		// (gh)^z ?= m*(g^x*h^x)^e?
		// g, h given
		// z = response
		// m = message
		// g^x = this.gx
		// h^x = this.hx
		// e = challenge
		if(responses.length != messages.length) {
			throw new TrustException("Not enough messages or responses");
		}
		for(int i = 0; i < responses.length; i++) {
			VerifyPackage pack = responses[i];
			GroupNumber message = messages[i];
			GroupNumber subChallenge = pack.getChallenge();
			GroupNumber g = this.gs[i];
			GroupNumber gx = this.gxs[i];
			GroupNumber h = this.hs[i];
			GroupNumber hx = this.hxs[i];
			
			GroupNumber response = pack.getResponse();
			
			response.upConvertOrder(g.getGroup());
			challenge.upConvertOrder(g.getGroup());
			
			GroupNumber modMinusOne = new GroupNumber(g.getGroup().getOrder().subtract(BigInteger.ONE), g.getGroup());
			response = response.mod(modMinusOne);
			
			GroupNumber ghz = (g.multiply(h)).exp(response);
			GroupNumber gxhxe = (gx.multiply(hx)).exp(subChallenge);
			
			if(DEBUG) {
				System.out.println("\tg = " + g);
				System.out.println("\th = " + h);
				System.out.println("\tgx = " + gx);
				System.out.println("\thx = " + hx);
				System.out.println("\te = " + subChallenge);
				System.out.println("\t(gh)^z = " + ghz);
				System.out.println("\t(g^x*h^x)^e = " + gxhxe);
				System.out.println("\tm*(g^x*h^x)^e = " + message.multiply(gxhxe));
				System.out.println("\t\tconvinced: " + ghz.equals(message.multiply(gxhxe)));
			}
			
			if(!ghz.equals(message.multiply(gxhxe))) {
				return false;
			}
		}
		
		// check that received challenges xor to original challenge
		if(responses.length > 1){
			GroupNumber e0 = responses[0].getChallenge();
			GroupNumber e1 = responses[1].getChallenge();
			if(!(e0.xor(e1)).equals(challenge)){
				return false;
			}
		}
		
		return true;
	}
}
