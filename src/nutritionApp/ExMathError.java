package nutritionApp;

public class ExMathError extends Exception {
	
	public ExMathError(String message) {
		super(message);
	}
	
	public ExMathError() {
		
		super("Math error, no negative number allowed.");
	}
	
	

}
