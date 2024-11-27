package nutritionApp;
public class ExNoTrainerPlan extends Exception{
    public ExNoTrainerPlan(String message){
        super(message);
    }
    public ExNoTrainerPlan() {
        super("No existing plan for trainer available");
    }

}
