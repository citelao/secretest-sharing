package cryptoboyz.zero_knowledge;

public class Prover {
	
	private GroupNumber g, h, x, k;
	private Group group;
	private Stage currStage;
	
	public Prover(GroupNumber g, GroupNumber h, GroupNumber x, Group group) {
		// So we want to prove we know X.
		this.g = g;
		this.h = h;
		this.x = x;
		this.group = group;
		this.k = group.generateMember();
		this.currStage = Stage.COMMIT;
	}
	
	public GroupNumber sendAlpha() throws Exception{
		if(currStage != Stage.COMMIT){
			throw new Exception("Invalid stage");
		}
		GroupNumber alpha = g.exp(k);
		currStage = currStage.next();
		return alpha;
	}
	
	public GroupNumber sendMessage() throws Exception{
		if(currStage != Stage.MSG){
			throw new Exception("Invalid stage");
		}
		currStage = currStage.next();
		return g.exp(x);
	}
	
	
}
