public class ExNotSubscribed extends Exception{

    public ExNotSubscribed(String message) {
        super(message);
    }
    public ExNotSubscribed() {
        super("This service is limited to subscribers only");
    }
}
