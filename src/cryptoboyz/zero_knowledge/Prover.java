package cryptoboyz.zero_knowledge;

import cryptoboyz.commitment.CommitMessage;
import cryptoboyz.commitment.TrustException;

public class Prover {
	
	private static boolean DEBUG = false;
	
	/**
	 * k = random groupnumber for commitment scheme (based off order of commitment group, t)
	 * g, h = generators
	 * w = witness
	 * r = random groupnumber to encrypt initial message
	 * e = challenge string from verifier
	 */
	private GroupNumber k, g, h, w, r, alpha, g2, gprime, hprime;
	private Group group;
	private Stage currStage;
	private CommitMessage cm;
	private boolean orComposition;
	private GroupNumber[] simulatorResult;
	
	public Prover(GroupNumber g, GroupNumber h, GroupNumber x, Group group, boolean orComposition) {
		this.g = g;
		this.h = h;
		this.w = x;
		this.orComposition = orComposition;
		if(orComposition){
			this.gprime = group.generateMember();
			this.hprime = group.generateMember();
			this.simulatorResult = new GroupNumber[3]; //simulatorResult will hold a1, e1, and z1
		}											   //all in response to the statement the prover cannot prove
		
		this.group = group;
		this.currStage = Stage.COMMIT;
	}
	
	public GroupNumber getAlpha(Group t) throws TrustException{
		if(currStage != Stage.COMMIT){
			throw new TrustException("Invalid stage");
		}
		
		this.k = t.generateNonTrivialMember();
		this.g2 = t.generateGenerator();
		this.alpha = g2.exp(k);
		currStage = currStage.next();
		
		if(DEBUG) {
			System.out.println("alpha = " + alpha);
		}
		
		return alpha;
	}
	
	public GroupNumber[] getMessage(CommitMessage cm) throws TrustException{
		if(currStage != Stage.MSG){
			throw new TrustException("Invalid stage");
		}
		GroupNumber simulatorChallenge;
		GroupNumber[] messages = new GroupNumber[2];
		
		if(orComposition){
			simulatorChallenge = group.generateMember();
			simulatorResult = runSimulation(simulatorChallenge);
			messages[1] = simulatorResult[0]; //a1
		}
		
		this.cm = cm;
		currStage = currStage.next();
		this.r = group.generateMember();
		messages[0] = (g.multiply(h)).exp(r); //m = (gh)^r
		
		if(DEBUG) {
			System.out.println("(r = " + r + ")");
			System.out.println("m = " + messages[0] + " = (gh)^r");
		}
		
		return messages; //a0 and a1
	}
	
	public GroupNumber getResponse(GroupNumber challenge, GroupNumber key) throws TrustException{
		
		if(currStage != Stage.RESPONSE){
			throw new TrustException("Invalid stage");
		}
		
		//don't need the value
		if(DEBUG) {
			System.out.println("(c = " + cm.decommit(challenge, key) + ")");
		} else {
			cm.decommit(challenge, key);
		}
	
		currStage = currStage.next();
		challenge.upConvertOrder(group);
		GroupNumber z = r.add(challenge.multiplyNoMod(w)); //z = r + ew
		
		if(DEBUG) {
			System.out.println("z = " + z + " = r + challenge * x");
		}
		
		return z;
	}
	
	private GroupNumber[] runSimulation(GroupNumber simulatorChallenge) throws TrustException{
		Prover simProver = new Prover(this.gprime, this.hprime, this.w, this.group, false);
		Verifier simVerifier = new Verifier(this.gprime, this.hprime, this.gprime.exp(this.w), this.hprime.exp(this.w));
		
		return simVerifier.simVerify(simProver, simulatorChallenge);
	}
}
