package cryptoboyz.zero_knowledge;

import java.math.BigInteger;

public class GroupNumber {
	
	private BigInteger value;
	private Group group;
	
	public GroupNumber(BigInteger n, Group g){
		this.value = n;
		this.group = g;
	}
	
	public GroupNumber inverse(){
		return new GroupNumber(this.value.modInverse(this.group.getOrder()), this.group);
	}
	
	public GroupNumber exp(GroupNumber k){
		return new GroupNumber(this.value.modPow(k.value, this.group.getOrder()), this.group);
	}
	
	public GroupNumber add(GroupNumber n){
		BigInteger newval = this.value.add(n.value);
		newval = newval.mod(this.group.getOrder());
		return new GroupNumber(newval, this.group);
	}
	
	public GroupNumber multiply(GroupNumber n){
		BigInteger newval = this.value.multiply(n.value);
		newval = newval.mod(this.group.getOrder());
		return new GroupNumber(newval, this.group);
	}
	
	public Group getGroup(){
		return this.group;
	}
	
	public boolean equals(GroupNumber e){
		return this.value.equals(e.value);
	}
	
}
