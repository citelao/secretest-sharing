package cryptoboyz.zero_knowledge;

import java.math.BigInteger;

import cryptoboyz.commitment.CommitMessage;
import cryptoboyz.commitment.TrustException;

public class Simulator {

	private static final boolean DEBUG = false;


	public Simulator(GroupNumber gprime, GroupNumber hprime, 
			GroupNumber gprimex, GroupNumber hprimex) {
		// TODO Auto-generated constructor stub
	}


	public ResponsePackage simulate(IProver prover, GroupNumber challenge) {
		// Step 1: use the precommited challenge bit.
		Group commitmentGroup = challenge.getGroup();
	
		GroupNumber alpha = prover.getAlpha(commitmentGroup);
		GroupNumber key = commitmentGroup.generateMember();
		GroupNumber generator = commitmentGroup.generateGenerator();
		CommitMessage cm = CommitMessage.Generate(generator, alpha, key, challenge);
		
		if(DEBUG) {
			System.out.println("\t\t(c = " + challenge + ")");
			System.out.println("\tc = " + cm);
		}
		
		// Step 2: receive message of g^r h^r
		GroupNumber[] messages = prover.getMessages(cm);
		
		// Step 3: send over proof of challenge bit
		// Step 4: receive z
		if(DEBUG) {
			System.out.println("\t(challenge, key) = \n"
					+ "\t\t challenge: " + challenge + "\n"
					+ "\t\t key: " + key);
		}
		//these are the z's
		VerifyPackage[] responses = prover.getResponses(challenge, key);
		
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
			GroupNumber gxhxe = (gx.multiply(hx)).exp(challenge);
			
			if(DEBUG) {
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
