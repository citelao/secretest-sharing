package cryptoboyz.zero_knowledge;

import java.math.BigInteger;

public class Simulator {

	private static final boolean DEBUG = false;
	private GroupNumber g;
	private GroupNumber h;
	private GroupNumber gx;
	private GroupNumber hx;
	private Group group;


	public Simulator(GroupNumber g, GroupNumber h, 
			GroupNumber gx, GroupNumber hx,
			Group group) {
		this.g = g;
		this.h = h;
		this.gx = gx;
		this.hx = hx;
		this.group = group;
	}

	public ResponsePackage simulate(GroupNumber challenge) {
		// We need to generate a message, challenge, and response.
		
		// (gh)^z = (gh)^r(g^x*h^x)^e
		
		// We have our challenge e => (g^x*h^x)^e
		GroupNumber gxhxe = gx.multiply(hx).exp(challenge); 
		// Generate a random z => (gh)^z
		GroupNumber modMinusOne = new GroupNumber(this.group.getOrder().subtract(BigInteger.ONE), this.group);
		GroupNumber z = this.group.generateNonTrivialMember().mod(modMinusOne);
		GroupNumber ghz = g.multiply(h).exp(z);
		// (gh)^z / (g^x*h^x)^e = (gh)^r = m
		GroupNumber m = ghz.multiply(gxhxe.inverse());
		
		if(DEBUG) {
			System.out.println("|group|: " + this.group.getOrder());
			System.out.println("challenge: " + challenge);
			System.out.println("gxhxe: " + gxhxe);
			System.out.println("z: " + z);
			System.out.println("ghz: " + ghz);
			System.out.println("m: " + m);
		}
		
		return new ResponsePackage(m, challenge, z);
	}

}
