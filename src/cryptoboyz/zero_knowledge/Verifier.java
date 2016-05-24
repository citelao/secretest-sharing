package cryptoboyz.zero_knowledge;


import java.math.BigInteger;

import cryptoboyz.commitment.CommitMessage;
import cryptoboyz.commitment.TrustException;

public class Verifier implements IVerifier {
	
	private static boolean DEBUG = false;
	
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
		challenge = new GroupNumber(BigInteger.ONE, commitmentGroup);
		GroupNumber key = commitmentGroup.generateMember();
		GroupNumber generator = commitmentGroup.generateGenerator();
		CommitMessage cm = CommitMessage.Generate(generator, alpha, key, challenge);
		
		if(DEBUG) {
			System.out.println("\t\t(c = " + challenge + ")");
			System.out.println("\tc = " + cm);
		}
		
		// Step 2: receive message of g^x h^x
		GroupNumber message = p.getMessages(cm)[0];
		
		// Step 3: send over proof of challenge bit
		// Step 4: receive z
		if(DEBUG) {
			System.out.println("\t(challenge, key) = \n"
					+ "\t\t challenge: " + challenge + "\n"
					+ "\t\t key: " + key);
		}
		GroupNumber[] responses = p.getResponses(challenge, key);
		
		// TODO check for all!
		GroupNumber response = responses[0];
		
		// Step 5: confirm!
		// (gh)^z ?= m*(g^x*h^x)^e?
		// g, h given
		// z = response
		// m = message
		// g^x = this.gx
		// h^x = this.hx
		// e = challenge
		
		//GroupNumber messageUpconverted = new GroupNumber(message.getValue(), ghz.getGroup());
		response.upConvertOrder(g.getGroup());
		challenge.upConvertOrder(g.getGroup());
		
		GroupNumber modMinusOne = new GroupNumber(g.getGroup().getOrder().subtract(BigInteger.ONE), g.getGroup());
		
		response = response.mod(modMinusOne);
		
		GroupNumber ghz = (this.g.multiply(this.h)).exp(response);
		GroupNumber gxhxe = (this.gx.multiply(this.hx)).exp(challenge);
		if(DEBUG) {
			System.out.println("\t(gh)^z = " + ghz);
			System.out.println("\t(g^x*h^x)^e = " + gxhxe);
			System.out.println("\tm*(g^x*h^x)^e = " + message.multiply(gxhxe));
			System.out.println("\t\tconvinced: " + ghz.equals(message.multiply(gxhxe)));
		}
		return ghz.equals(message.multiply(gxhxe));
	}
	
	/**
	 * this version is for simulation!
	 * @param p
	 * @param t number of bits to generate
	 * @param fixedChallenge
	 * @return GroupNumber[2]: [0] = simulated message (a), [1] = simulated response (z)
	 */
	public ResponsePackage simVerify(Prover p, GroupNumber fixedChallenge) throws TrustException {
		// Step 1: choose a choose bit & send it, encrypted-like
		
		//technically we dont need all this commitment business for the simulation
		Group commitmentGroup = fixedChallenge.getGroup();

		GroupNumber alpha = p.getAlpha(commitmentGroup);
		GroupNumber key = commitmentGroup.generateMember();
		GroupNumber generator = commitmentGroup.generateGenerator();
		CommitMessage cm = CommitMessage.Generate(generator, alpha, key, fixedChallenge);
		
		if(DEBUG) {
			System.out.println("\t\t(c = " + fixedChallenge + ")");
			System.out.println("\tc = " + cm);
		}
		
		// Step 2: receive message of g^x h^x
		GroupNumber message = p.getMessages(cm)[0];
		
		// Step 3: send over proof of challenge bit
		// Step 4: receive z
		if(DEBUG) {
			System.out.println("\t(challenge, key) = \n"
					+ "\t\t challenge: " + fixedChallenge + "\n"
					+ "\t\t key: " + key);
		}
		GroupNumber[] responses = p.getResponses(fixedChallenge, key);
		
		// TODO confirm for all
		GroupNumber response = responses[0];
		
		// Step 5: confirm!
		// (gh)^z ?= m*(g^x*h^x)^e?
		// g, h given
		// z = response
		// m = message
		// g^x = this.gx
		// h^x = this.hx
		// e = challenge
		
		//GroupNumber messageUpconverted = new GroupNumber(message.getValue(), ghz.getGroup());
		response.upConvertOrder(g.getGroup());
		fixedChallenge.upConvertOrder(g.getGroup());
		
		GroupNumber modMinusOne = new GroupNumber(g.getGroup().getOrder().subtract(BigInteger.ONE), g.getGroup());
		
		response = response.mod(modMinusOne);
		
		GroupNumber ghz = (this.g.multiply(this.h)).exp(response);
		GroupNumber gxhxe = (this.gx.multiply(this.hx)).exp(fixedChallenge);
		if(DEBUG) {
			System.out.println("\t(gh)^z = " + ghz);
			System.out.println("\t(g^x*h^x)^e = " + gxhxe);
			System.out.println("\tm*(g^x*h^x)^e = " + message.multiply(gxhxe));
			System.out.println("\t\tconvinced: " + ghz.equals(message.multiply(gxhxe)));
		}
		if(ghz.equals(message.multiply(gxhxe))){
			return new ResponsePackage(message, fixedChallenge, response);
		}else{
			throw new TrustException("Simulator should verify correctly!!!!");
		}
	}
	
}
