package cryptoboyz.commitment;

import cryptoboyz.zero_knowledge.GroupNumber;

public class CommitMessage {
	
	private GroupNumber committed;
	private GroupNumber alpha;
	private GroupNumber g;
	
	/**
	 * Do you have information that you want to prove that you have, without sharing that
	 * information with any of those other losers (I'm looking at you, Bob)? Are you tired
	 * of people insinuating that you are choosing bits in a way that secretly gets you 
	 * information about DLOG?
	 * 
	 * Boy do we have an answer for your prayers.
	 * 
	 * Generate a CommitMessage to share with the world! Commit to values and 
	 * make people believe that you have!
	 * 
	 * First, you're gonna need a group generator g, and you're gonna want to ask the other
	 * party to generate some alpha g^u (YOU don't get to know "u", get it?).
	 * 
	 * Then you need some information. Store it in a GroupNumber. Pass it as an argument.
	 * 
	 * @param g the magical generator
	 * @param alpha your untrusting "friend"'s random number
	 * @param key your secret, randomly generated key
	 * @param info your hidden information
	 * @return a CommitMessage that you can share with anybody!
	 */
	public static CommitMessage Generate(GroupNumber g, GroupNumber alpha, GroupNumber key, GroupNumber info) {
		GroupNumber commitment = alpha.exp(info).multiply(g.exp(key));
		return new CommitMessage(g, alpha, commitment);
	}
	
	/**
	 * A CommitMessage is the sharable result of a commitment :)
	 * 
	 * It can be decommited if given the "commit" result and a key. See the decommit function.
	 * 
	 * @param g the generator used in our amazing Commit sequence
	 * @param alpha the verifier-generated random number used at the beginning of the sequence
	 * @param commitment the encrypted value---our commitment.
	 */
	protected CommitMessage(GroupNumber g, GroupNumber alpha, GroupNumber commitment) {
		this.committed = commitment;
		this.alpha = alpha;
		this.g = g;
	}
	
	/**
	 * De-commit a CommitMessage into its original value.
	 * 
	 * Throws a TrustException if what you de-committed to was not correct.
	 * 
	 * @param info what Alice says she committed too
	 * @param key what Alice says she used to generate her commitment
	 * @return what Alice committed too, unless she was lying
	 * @throws TrustException
	 */
	public GroupNumber decommit(GroupNumber info, GroupNumber key) throws TrustException {
		if(!this.alpha.exp(info).multiply(this.g.exp(key)).equals(committed)) {
			throw new TrustException("Committed value does not match sent information " + info);
		}
		
		return info;
	}
	
}
