package cryptoboyz.commitment;

import cryptoboyz.zero_knowledge.GroupNumber;

public class CommitMessage {
	
	private GroupNumber committed;
	private GroupNumber alpha;
	private GroupNumber g;
	
	public CommitMessage(GroupNumber committed, GroupNumber alpha, GroupNumber g) {
		this.committed = committed;
		this.alpha = alpha;
		this.g = g;
	}
	
	public GroupNumber decommit(GroupNumber info, GroupNumber c) throws TrustException {
		if(!this.alpha.exp(info).multiply(this.g.exp(c)).equals(committed)) {
			throw new TrustException("Committed value does not match sent information " + info);
		}
		
		return info;
	}
	
}
