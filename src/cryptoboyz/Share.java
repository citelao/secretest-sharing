package cryptoboyz;

import java.math.BigInteger;

public class Share {
	
	private int x;
	private BigInteger y;
	
	public Share(int x, BigInteger y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX(){
		return this.x;
	}
	
	public BigInteger getY(){
		return this.y;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "(" + this.x + ", " + this.y + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
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
		Share other = (Share) obj;
		if (x != other.x)
			return false;
		return true;
	}

	
}
