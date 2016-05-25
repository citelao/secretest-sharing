package cryptoboyz.zero_knowledge;

import cryptoboyz.commitment.CommitMessage;
import cryptoboyz.commitment.TrustException;

public class SimulatedProver implements IProver {

	public SimulatedProver(GroupNumber gprime, GroupNumber hprime, GroupNumber gprimex, GroupNumber hprimex,
			Group group) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public GroupNumber getAlpha(Group g) throws TrustException {
		throw new TrustException("You shouldn't commit to anything with a simulator.");
	}

	@Override
	public GroupNumber[] getMessages(CommitMessage challengeCommitment) throws TrustException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VerifyPackage[] getResponses(GroupNumber challenge, GroupNumber key) throws TrustException {
		// TODO Auto-generated method stub
		return null;
	}

}
