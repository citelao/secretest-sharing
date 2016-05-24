package cryptoboyz.zero_knowledge;

import cryptoboyz.commitment.CommitMessage;
import cryptoboyz.commitment.TrustException;

public interface IProver {

	GroupNumber getAlpha(Group g) throws TrustException;
	GroupNumber[] getMessages(CommitMessage challengeCommitment) throws TrustException;
	VerifyPackage[] getResponses(GroupNumber challenge, GroupNumber key) throws TrustException;
	
}
