package cryptoboyz.zero_knowledge;

import java.math.BigInteger;
import java.util.Random;

public class Group {
	
	/**
	 * generates finite group of order q, where q is PROBABLY (as defined by Java?) prime
	 */
	
	private final int bits;
	private final BigInteger order;
	
	public Group(){
		Random rnd = new Random();
		this.bits = 8; // debug :)
//		this.bits = 1000;
		this.order = BigInteger.probablePrime(this.bits, rnd);
	}
	
	public Group(int bits) {
		Random rnd = new Random();
		this.bits = bits;
		this.order = BigInteger.probablePrime(this.bits, rnd);
	}
	
	public GroupNumber generateMember(){
		Random rnd = new Random();
		BigInteger number = new BigInteger(this.bits, rnd);
		//a member of the group cannot be greater than or equal to the order of the group
		//alternatively we could generate a random BigInteger and mod it by order
		while (number.compareTo(order) >= 0) {
			number = new BigInteger(this.bits, rnd);
		}
		return new GroupNumber(number, this);
	}
	
	public GroupNumber generateNonTrivialMember() {
		GroupNumber x;
		do {
			x = this.generateMember();
		} while (x.equals(new GroupNumber(BigInteger.ZERO, this)) ||
				x.equals(new GroupNumber(BigInteger.ONE, this)));
		
		return x;
	}

	public GroupNumber generateGenerator() {
		return this.generateNonTrivialMember();
	}
	
	public BigInteger getOrder(){
		return this.order;
	}
}
