package cryptoboyz.zero_knowledge;

public class VerifyPackage {
	private GroupNumber response, challenge;
	
	public VerifyPackage(GroupNumber z, GroupNumber e){
		this.response = z;
		this.challenge = e;
	}
	
	public GroupNumber getResponse(){
		return response;
	}
	
	public GroupNumber getChallenge(){
		return challenge;
	}
}
