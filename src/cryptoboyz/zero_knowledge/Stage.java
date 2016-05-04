package cryptoboyz.zero_knowledge;

public enum Stage {
	COMMIT, MSG, RESPONSE, VERIFY;
	 private static Stage[] vals = values();
	    public Stage next()
	    {
	        return vals[(this.ordinal()+1) % vals.length];
	    }
}
