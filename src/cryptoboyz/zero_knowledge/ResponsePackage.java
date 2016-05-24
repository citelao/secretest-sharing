package cryptoboyz.zero_knowledge;

public class ResponsePackage {
	//message = initial protocol message
	//challenge = challenge bit or bit-string
	//response = z = r + ew
	private GroupNumber message, challenge, response;
	
	public ResponsePackage(GroupNumber a, GroupNumber e, GroupNumber z){
		this.message = a;
		this.challenge = e;
		this.response = z;
	}
	
	public GroupNumber getMessage(){
		return message;
	}
	
	public GroupNumber getChallenge(){
		return challenge;
	}
	
	public GroupNumber getResponse(){
		return response;
	}
}
