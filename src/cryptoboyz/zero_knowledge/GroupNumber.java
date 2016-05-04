package cryptoboyz.zero_knowledge;

import java.math.BigInteger;

import cryptoboyz.commitment.TrustException;

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
	
	public GroupNumber mod(GroupNumber m){
		return new GroupNumber(this.value.mod(m.value), this.group);
	}
	
	public GroupNumber exp(GroupNumber e){
		if(!this.group.getOrder().equals(e.group.getOrder())){
			System.err.println("Operation being done on numbers from different groups!");
		}
		return new GroupNumber(this.value.modPow(e.value, this.group.getOrder()), this.group);
	}
	
	public GroupNumber add(GroupNumber n){
		if(!this.group.getOrder().equals(n.group.getOrder())){
			System.err.println("Operation being done on numbers from different groups!");
		}
		BigInteger newval = this.value.add(n.value);
		newval = newval.mod(this.group.getOrder());
		return new GroupNumber(newval, this.group);
	}
	
	public GroupNumber multiply(GroupNumber n){
		if(!this.group.getOrder().equals(n.group.getOrder())){
			System.err.println("Operation being done on numbers from different groups!");
		}
		BigInteger newval = this.value.multiply(n.value);
		newval = newval.mod(this.group.getOrder());
		return new GroupNumber(newval, this.group);
	}
	
	public Group getGroup(){
		return this.group;
	}
	
	public BigInteger getValue(){
		return this.value;
	}
	
//	public boolean equals(GroupNumber e){
//		if(!this.group.getOrder().equals(e.group.getOrder())){
//			System.err.println("Operation being done on numbers from different groups!");
//		}
//		return this.value.equals(e.value);
//	}
	
	
	
	public void upConvertOrder(Group g) throws TrustException{
		if(g.getOrder().compareTo(this.group.getOrder()) < 0){
			throw new TrustException("You promised me you would only try to increase the order of a GroupNumber!");
		}
		this.group = g;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GroupNumber other = (GroupNumber) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value)){
			return false;
		} else if (!group.getOrder().equals(other.group.getOrder())){
			return false;
		}
		return true;
	}

	@Override
	public String toString(){
		return this.value.toString();
	}
	
}
