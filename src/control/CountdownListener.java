package control;

public abstract class CountdownListener {
	
	public int toReach;
	
	public CountdownListener(int seconds) {
		this.toReach = seconds;
	}
	
	public abstract void reached();
	
}
