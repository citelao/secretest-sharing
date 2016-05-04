package cryptoboyz.zero_knowledge;

import java.math.BigInteger;
import java.util.Random;

public class Group {
	
	/**
	 * generates finite group of order q, where q is PROBABLY (as defined by Java?) prime
	 */
	
	private final BigInteger order;
	
	public Group(){
		Random rnd = new Random();
		this.order = BigInteger.probablePrime(1000, rnd);
	}
	
	public GroupNumber generateMember(){
		Random rnd = new Random();
		BigInteger number = new BigInteger(1000, rnd);
		//a member of the group cannot be greater than or equal to the order of the group
		//alternatively we could generate a random BigInteger and mod it by order
		while (number.compareTo(order) >= 0) {
			number = new BigInteger(1000, rnd);
		}
		return new GroupNumber(number, this);
	}
	
	public BigInteger getOrder(){
		return this.order;
	}
}
