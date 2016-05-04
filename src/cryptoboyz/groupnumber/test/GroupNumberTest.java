package cryptoboyz.groupnumber.test;

import static org.junit.Assert.*;

import java.math.BigInteger;

import org.junit.Test;

import cryptoboyz.zero_knowledge.Group;
import cryptoboyz.zero_knowledge.GroupNumber;

public class GroupNumberTest {
	
	@Test
	public void exponentiationTest(){
		Group g = new Group(4);
		
		for(int i=0; i<48; ++i){
			GroupNumber a = g.generateMember();
			GroupNumber b = g.generateMember();
			while(a.equals(b)){
				b = g.generateMember();
			}
			
			System.out.println("THE ORDER IS " + g.getOrder());
			
			System.out.println("a = " + a);
			System.out.println("b = " + b);
			
			GroupNumber c = a.exp(b);
			
			GroupNumber cprime = manualExponentiation(a, b);
			
			System.out.println("c = " + c);
			System.out.println("c' = " + cprime);
			assertEquals(c, cprime);	
		}
		
		
	}

	//a^e
	private static GroupNumber manualExponentiation(GroupNumber a, GroupNumber e){
		
		GroupNumber m = new GroupNumber(a.getGroup().getOrder(), a.getGroup());
		
		while(!e.getValue().equals(BigInteger.ZERO)){
			a = a.multiply(a);
			
			e = new GroupNumber(e.getValue().subtract(BigInteger.ONE), e.getGroup());
			System.out.println(e);
		}
		a = a.mod(m);
		return a;
		
	}
}
