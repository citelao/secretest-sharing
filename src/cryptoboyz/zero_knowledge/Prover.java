package cryptoboyz.zero_knowledge;

import java.math.BigInteger;

import cryptoboyz.commitment.CommitMessage;
import cryptoboyz.commitment.TrustException;

public class Prover {
	
	/**
	 * k = random groupnumber for commitment scheme (based off order of commitment group, t)
	 * g, h = generators
	 * w = witness
	 * r = random groupnumber to encrypt initial message
	 * e = challenge string from verifier
	 */
	private GroupNumber k, g, h, w, r, alpha, g2;
	private Group group;
	private Stage currStage;
	private CommitMessage cm;
	
	public Prover(GroupNumber g, GroupNumber h, GroupNumber x, Group group) {
		// So we want to prove we know X.
		this.g = g;
		this.h = h;
		this.w = x;
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
		System.out.println("alpha = " + alpha);
		return alpha;
	}
	
	public GroupNumber getMessage(CommitMessage cm) throws TrustException{
		if(currStage != Stage.MSG){
			throw new TrustException("Invalid stage");
		}
		this.cm = cm;
		currStage = currStage.next();
		this.r = group.generateMember();
		GroupNumber m = (g.multiply(h)).exp(r); //m = (gh)^r
		System.out.println("m = " + m);
		return m;
	}
	
	public GroupNumber getResponse(GroupNumber challenge, GroupNumber key) throws TrustException{
		
		if(currStage != Stage.RESPONSE){
			throw new TrustException("Invalid stage");
		}
		
		//don't need the value
		System.out.println("(c = " + cm.decommit(challenge, key) + ")");
	
		currStage = currStage.next();
		challenge.upConvertOrder(group);
		GroupNumber z = r.add(challenge.multiply(w)); //z = r + ew
		System.out.println("z = " + z);
		return z;
	}
	
	
}
