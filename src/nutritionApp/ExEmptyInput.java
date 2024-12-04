package nutritionApp;

public class ExEmptyInput extends Exception{
	
	public ExEmptyInput(String message) {
		super(message);
	}
	
	public ExEmptyInput() {
		super("Empty input.");
	}

}
