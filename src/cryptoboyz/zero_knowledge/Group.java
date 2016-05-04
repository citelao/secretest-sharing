package cryptoboyz.zero_knowledge;

import java.math.BigInteger;
import java.util.Random;

public class Group {
	
	private final BigInteger order;
	
	public Group(){
		Random rnd = new Random();
		this.order = BigInteger.probablePrime(1000, rnd);
	}
	
	public GroupNumber generateMember(){
		Random rnd = new Random();
		BigInteger number = new BigInteger(1000, rnd);
		while (number.compareTo(order) > 0) {
			number = new BigInteger(1000, rnd);
		}
		return new GroupNumber(number, this);
	}
	
	public BigInteger getOrder(){
		return this.order;
	}
}
