package testNutritionApp;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nutritionApp.*;


class SubscriberTest {
	
	public class TestTrainer extends Trainer {
	    private final String name;
	    private final int trainerId;

	    public TestTrainer(String name, int id) {
	        super(name, id, "stubPass");
	        this.name = name;
	        this.trainerId = id;
	    }

	    @Override
	    public String getName() {
	        return name;
	    }

	    @Override
	    public boolean equals(Object obj) {
	        if(!(obj instanceof TestTrainer)) return false;
	        TestTrainer other = (TestTrainer) obj;
	        return this.trainerId == other.trainerId && this.name.equals(other.name);
	    }

	    @Override
	    public String toString() {
	        return "Trainer[name="+name+", id="+trainerId+"]";
	    }

	    @Override
	    public void getTrainer(List<Trainer> trainers) {
	        trainers.add(this);
	    }

	    @Override
	    public void showOptions() {
	        // No-op
	    }

	    @Override
	    public void runOpt1(Scanner scanner) {
	        // No-op
	    }

	    @Override
	    public void runOpt2(Scanner scanner) {
	        // No-op
	    }

	    @Override
	    public void runOpt3(Scanner scanner) {
	        // No-op
	    }

	    @Override
	    public void runOpt4(Scanner scanner) {
	        // No-op
	    }
	}
	
	public class TestMember extends Member {
	    private boolean premium = false;
	    private final String name;
	    private final int memberId;

	    public TestMember(String name, int id) {
	        super(name, id, "stubPass");
	        this.name = name;
	        this.memberId = id;
	    }

	    @Override
	    public boolean checkPremium() {
	        return premium;
	    }

	    @Override
	    public void setSubscribed() {
	        premium = true;
	    }

	    @Override
	    public String toString() {
	        return "Member[name="+name+", id="+memberId+"]";
	    }

	    @Override
	    public boolean equals(Object obj) {
	        if(!(obj instanceof TestMember)) return false;
	        TestMember other = (TestMember) obj;
	        return this.memberId == other.memberId && this.name.equals(other.name);
	    }

	    @Override
	    public void getTrainer(List<Trainer> trainers) {
	        // No-op
	    }

	    @Override
	    public void showOptions() {
	        // No-op
	    }

	    @Override
	    public void runOpt2(Scanner scanner) throws ExNotSubscribed, ExNoTrainerPlan {
	        // No-op
	    }

	    @Override
	    public void runOpt1(Scanner scanner) {
	        // No-op
	    }

	    @Override
	    public void runOpt3(Scanner scanner) {
	        // No-op
	    }

	    @Override
	    public void runOpt4(Scanner scanner) throws ExNotSubscribed {
	        // No-op
	    }
	}


    private Subscriber subscriber;
    private Authenticator auth;
    private TestMember testMember;
    private TestTrainer testTrainer;
    private TestTrainer testTrainer2;

    @BeforeEach
    void setUp() throws Exception {
        subscriber = Subscriber.getInstance();
        auth = Authenticator.getInstance();

        // Reset Authenticator: log out any user
        auth.Logout();

        // Create our stub member and trainers
        testMember = new TestMember("StubMember", 1);
        testTrainer = new TestTrainer("StubTrainer", 10);
        testTrainer2 = new TestTrainer("StubTrainer2", 20);

        // Inject our testMember as the logged in user
        setLoggedUser(auth, testMember);

        // Inject our trainers into the Authenticator user list so getAllTrainers() can find them
        addUserToAuthenticator(auth, testTrainer);
        addUserToAuthenticator(auth, testTrainer2);

        // Add trainers to subscriber
        subscriber.addTrainer(testTrainer);
        subscriber.addTrainer(testTrainer2);
    }

    @AfterEach
    void tearDown() throws Exception {
        auth.Logout();
    }

    // Helper to set the Authenticator's loggedUser via reflection
    private void setLoggedUser(Authenticator auth, User user) throws Exception {
        Field loggedUserField = Authenticator.class.getDeclaredField("loggedUser");
        loggedUserField.setAccessible(true);
        loggedUserField.set(auth, user);
    }

    // Helper to add a user to Authenticator's user list via reflection
    @SuppressWarnings("unchecked")
    private void addUserToAuthenticator(Authenticator auth, User user) throws Exception {
        Field usersField = Authenticator.class.getDeclaredField("users");
        usersField.setAccessible(true);
        List<User> users = (List<User>) usersField.get(auth);
        users.add(user);
    }

    @Test
    void testGetInstance() {
        assertNotNull(subscriber);
        assertSame(subscriber, Subscriber.getInstance());
    }

    @Test
    void testSubscribeToTrainers_NoChoice() throws Exception {
        // If user enters -1 immediately, no subscription should happen.
        String input = "-1\n";
        Scanner sc = new Scanner(new ByteArrayInputStream(input.getBytes()));
        subscriber.subscribeToTrainers(testMember, sc);
        sc.close();
        assertFalse(testMember.checkPremium());
    }

    @Test
    void testSubscribeToTrainers_NoTrainers() throws Exception {
        // Remove all trainers from Authenticator to test no trainers scenario
        clearTrainersFromAuthenticator();

        String input = "1\n"; // tries to choose trainer #1
        Scanner sc = new Scanner(new ByteArrayInputStream(input.getBytes()));
        subscriber.subscribeToTrainers(testMember, sc);
        sc.close();
        // Since no trainers available, member stays non-premium
        assertFalse(testMember.checkPremium());
    }

    private void clearTrainersFromAuthenticator() throws Exception {
        Field usersField = Authenticator.class.getDeclaredField("users");
        usersField.setAccessible(true);
        List<User> users = (List<User>) usersField.get(auth);
        // Remove all trainers
        users.removeIf(u -> u instanceof Trainer);
    }

    @Test
    void testSubscribeToTrainers_ValidChoice() {
        // Choose trainer #1 (StubTrainer)
        String input = "1\n";
        Scanner sc = new Scanner(new ByteArrayInputStream(input.getBytes()));
        subscriber.subscribeToTrainers(testMember, sc);
        sc.close();
        assertTrue(testMember.checkPremium());
    }

    @Test
    void testSubscribeToTrainers_InvalidChoice() {
        // Invalid choice: choose a trainer ID greater than size
        String input = "99\n";
        Scanner sc = new Scanner(new ByteArrayInputStream(input.getBytes()));
        subscriber.subscribeToTrainers(testMember, sc);
        sc.close();
        assertFalse(testMember.checkPremium());
    }

    @Test
    void testShowSubscribedStats() {
        // Subscribe to at least one trainer
        String input = "1\n"; //subscribe to testTrainer
        Scanner sc = new Scanner(new ByteArrayInputStream(input.getBytes()));
        subscriber.subscribeToTrainers(testMember, sc);
        sc.close();

        subscriber.showSubscribedStats(); 
        // Just ensures it runs, no exception.
    }
    
    @Test
    void testShowSubscriberMenu_NonPremiumFlow() {
        // Non-premium menu sequence:
        // 2 -> show stats (no subscription yet)
        // 99 -> invalid choice
        // 1 -> subscribe trainer, then choose 1 for trainer #1
        // -1 -> exit
        String menuInput = 
            "2\n" +    // Show stats
            "99\n" +   // Invalid choice
            "1\n" +    // Subscribe to trainer
            "1\n" +    // Choose trainer #1
            "-1\n";    // Exit
        Scanner sc = new Scanner(new ByteArrayInputStream(menuInput.getBytes()));
        subscriber.showSubscriberMenu(sc);

        // After subscribing, testMember should be premium
        assertTrue(testMember.checkPremium());
    }

    @Test
    void testShowSubscriberMenu_PremiumFlow() {
        // First, make the user premium by subscribing them once
        String input = "1\n"; // subscribe to trainer #1
        Scanner sc = new Scanner(new ByteArrayInputStream(input.getBytes()));
        subscriber.subscribeToTrainers(testMember, sc);
        sc.close();
        assertTrue(testMember.checkPremium());

        // Premium menu sequence:
        // 2 -> show stats
        // 1 -> unsubscribe
        // 2 -> show stats again (no subscribers now)
        // -1 -> exit
        String menuInput = 
            "2\n" +    // Show stats
            "1\n" +    // Unsubscribe
            "2\n" +    // Show stats again
            "-1\n";    // Exit
        Scanner sc2 = new Scanner(new ByteArrayInputStream(menuInput.getBytes()));
        subscriber.showSubscriberMenu(sc2);
        sc2.close();
        // Just checking no exceptions and coverage done.
    }


}
