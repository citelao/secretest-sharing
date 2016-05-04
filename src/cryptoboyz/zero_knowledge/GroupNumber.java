package cryptoboyz.zero_knowledge;

import java.math.BigInteger;

public class GroupNumber {
	
	private BigInteger value;
	private BigInteger order;
	
	public GroupNumber(BigInteger n, BigInteger q){
		this.value = n;
		this.order = q;
	}
	
	public GroupNumber inverse(){
		return new GroupNumber(this.value.modInverse(order), order);
	}
	
	public GroupNumber exp(GroupNumber k){
		return new GroupNumber(this.value.modPow(k.value, order), order);
	}
	
	
}
