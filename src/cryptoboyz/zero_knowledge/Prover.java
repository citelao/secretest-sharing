package cryptoboyz.zero_knowledge;

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
	private GroupNumber k, g, h, w, r, e;
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
		this.k = t.generateMember();
		GroupNumber alpha = g.exp(k);
		currStage = currStage.next();
		return alpha;
	}
	
	public GroupNumber getMessage(CommitMessage cm) throws TrustException{
		if(currStage != Stage.MSG){
			throw new TrustException("Invalid stage");
		}
		this.cm = cm;
		currStage = currStage.next();
		this.r = group.generateMember();
		GroupNumber m = (g.multiply(h)).exp(r); //m = (gh)^x
		return m;
	}
	
	public GroupNumber getResponse(GroupNumber challenge, GroupNumber key) throws TrustException{
		if(currStage != Stage.RESPONSE){
			throw new TrustException("Invalid stage");
		}
		currStage = currStage.next();
		GroupNumber z = r.add(e.multiply(w)); //z = r + ew
		return z;
	}
	
	
}
