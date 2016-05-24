package cryptoboyz.zero_knowledge;

import cryptoboyz.commitment.TrustException;

public interface IVerifier {
	boolean verify(Prover prover, int confidenceBits) throws TrustException;
}
