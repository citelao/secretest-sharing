package cryptoboyz.commitment.test;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import cryptoboyz.commitment.CommitMessage;
import cryptoboyz.commitment.TrustException;
import cryptoboyz.zero_knowledge.Group;
import cryptoboyz.zero_knowledge.GroupNumber;

public class CommitMessageTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void decommitsProperly() {
		Group group = new Group();
		GroupNumber g = group.generateGenerator();
		GroupNumber alpha = group.generateMember();
		GroupNumber key = group.generateMember();
		GroupNumber info = group.generateMember();
		CommitMessage cm = CommitMessage.Generate(g, alpha, key, info);
		try {
			GroupNumber result = cm.decommit(info, key);
			if(!info.equals(result)) {
				fail("Expected to decrypt and return correct result");
			}
		} catch (TrustException e) {
			e.printStackTrace();
			fail("Expected to decrypt!");
		}
	}
	
	@Test
	public void failsWithFakeKey() throws TrustException {
		Group group = new Group();
		GroupNumber g = group.generateGenerator();
		GroupNumber alpha = group.generateMember();
		GroupNumber key = group.generateMember();
		GroupNumber info = group.generateMember();
		CommitMessage cm = CommitMessage.Generate(g, alpha, key, info);
		
		GroupNumber keyPrime = group.generateMember();
		thrown.expect(TrustException.class);
		cm.decommit(info, keyPrime);
	}
	
	@Test
	public void failsWithFakeInfo() throws TrustException {
		Group group = new Group();
		GroupNumber g = group.generateGenerator();
		GroupNumber alpha = group.generateMember();
		GroupNumber key = group.generateMember();
		GroupNumber info = group.generateMember();
		CommitMessage cm = CommitMessage.Generate(g, alpha, key, info);
		
		GroupNumber infoPrime = group.generateMember();
		thrown.expect(TrustException.class);
		cm.decommit(infoPrime, key);
	}

}
