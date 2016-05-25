package cryptoboyz.zero_knowledge;

import cryptoboyz.commitment.CommitMessage;
import cryptoboyz.commitment.TrustException;

public class Prover implements IProver {

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
	private ResponsePackage simulatorResult;

	public Prover(GroupNumber g, GroupNumber h, GroupNumber x, Group group) {
		this.g = g;
		this.h = h;
		this.w = x;
		this.orComposition = false;
		this.group = group;
		this.currStage = Stage.COMMIT;
	}

	public Prover(GroupNumber g, GroupNumber h, GroupNumber x, GroupNumber gprime, GroupNumber hprime, 
			Group group) {
		this.g = g;
		this.h = h;
		this.w = x;
		this.orComposition = true;
		this.gprime = gprime;
		this.hprime = hprime;
		this.group = group;
		this.currStage = Stage.COMMIT;
	}


	public GroupNumber getAlpha(Group t) throws TrustException {
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

	public GroupNumber[] getMessages(CommitMessage cm) throws TrustException{
		if(currStage != Stage.MSG){
			throw new TrustException("Invalid stage");
		}

		int size = (orComposition) ? 2 : 1;
		GroupNumber[] messages = new GroupNumber[size];

		this.cm = cm;
		currStage = currStage.next();

		this.r = group.generateMember();
		
		messages[0] = (g.multiply(h)).exp(r); // m = (gh)^r
		
		if(orComposition) {
			GroupNumber simulatorChallenge = group.generateMember();
			simulatorResult = runSimulation(simulatorChallenge);
			messages[1] = simulatorResult.getMessage(); // a1
		}
		
		if(DEBUG) {
			System.out.println("(r = " + r + ")");
			for(int i = 0; i < size; i++) {
				System.out.println("m[" + i + "] = " + messages[i] + " = (gh)^r");
			}
		}
		return messages; //a0 and (optionally) a1
	}

	public VerifyPackage[] getResponses(GroupNumber challenge, GroupNumber key) throws TrustException{

		if(currStage != Stage.RESPONSE){
			throw new TrustException("Invalid stage");
		}

		//don't need the value
		if(DEBUG) {
			System.out.println("(c = " + cm.decommit(challenge, key) + ")");
		} else {
			cm.decommit(challenge, key);
		}
		challenge.upConvertOrder(group);
		int size = (orComposition) ? 2 : 1;
		VerifyPackage[] vp = new VerifyPackage[size]; 
		
		if(orComposition) {
			challenge = simulatorResult.getChallenge().xor(challenge);
			VerifyPackage v1 = new VerifyPackage(simulatorResult.getResponse(), simulatorResult.getChallenge());
			vp[1] = v1;
		}
		currStage = currStage.next();
		
		vp[0] = new VerifyPackage(r.add(challenge.multiplyNoMod(w)), challenge);; //z = r + ew
		
		if(DEBUG) {
			for(int i = 0; i < size; i++) {
				System.out.println("z[" + i + "] = " + vp[i] + " = r + challenge * x");
			}
		}

		return vp;
	}

	private ResponsePackage runSimulation(GroupNumber simulatorChallenge) throws TrustException{
		Prover simProver = new Prover(this.gprime, this.hprime, this.w, this.group);
		Verifier simVerifier = new Verifier(this.gprime, this.hprime, this.gprime.exp(this.w), this.hprime.exp(this.w));
		return simVerifier.simVerify(simProver, simulatorChallenge);
	}
}
