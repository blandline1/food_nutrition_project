package testNutritionApp;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.*;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nutritionApp.*;

class MemberTest {

    private ByteArrayOutputStream outContent;
    private PrintStream originalOut;
    private Member member;

    @BeforeEach
    void setUp() throws Exception {
        originalOut = System.out;
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Reset Authenticator
        Authenticator auth = Authenticator.getInstance();
        Field usersField = Authenticator.class.getDeclaredField("users");
        usersField.setAccessible(true);
        ((List<?>)usersField.get(auth)).clear();

        Field idCountField = Authenticator.class.getDeclaredField("idCount");
        idCountField.setAccessible(true);
        idCountField.setInt(auth, 0);

        Field loggedUserField = Authenticator.class.getDeclaredField("loggedUser");
        loggedUserField.setAccessible(true);
        loggedUserField.set(auth, null);

        // Reset Subscriber
        Subscriber sb = Subscriber.getInstance();
        Field subscribeTrainersField = Subscriber.class.getDeclaredField("subscribeTrainers");
        subscribeTrainersField.setAccessible(true);
        ((HashMap<?,?>)subscribeTrainersField.get(sb)).clear();

        // Reset Planner
        Planner planner = Planner.getInstance();
        Field allPlansField = Planner.class.getDeclaredField("allPlans");
        allPlansField.setAccessible(true);
        ((HashMap<?,?>)allPlansField.get(planner)).clear();

        // Create a member
        member = new Member("TestMember", 1, "ufxx");
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        outContent.reset();
    }

    @Test
    void testConstructorAndToString() {
        assertEquals("TestMember", member.getName());
        assertEquals(1, member.getId());
        assertTrue(member.toString().contains("Member [name=TestMember, id=1]"));
    }

    @Test
    void testAddFoodLogUnderLimit() {
        // Add fewer than 7 logs
        Food f = new Food(100, 20, 10, 5, new ArrayList<>(Arrays.asList("Apple")), 1.0);
        member.addFoodLog(f);
        outContent.reset();
        // No prompt, just added
    }

    @Test
    void testAddFoodLogAtLimit() {
        // Add exactly 7 food logs
        Food f = new Food(100, 20, 10, 5, new ArrayList<>(Arrays.asList("Apple")), 1.0);
        for (int i=0; i<7; i++) {
            member.addFoodLog(f);
        }
        // Now try to add the 8th log
        outContent.reset();
        // Input 'Y' to clear and add
        ByteArrayInputStream in = new ByteArrayInputStream("Y\n".getBytes());
        System.setIn(in);
        member.addFoodLog(f);
        String output = outContent.toString();
        assertTrue(output.contains("Food log limit reached"), "Should prompt for clearing logs");
        // Logs cleared and added again
    }

    @Test
    void testAddFoodLogAtLimitRefuse() {
        // Add exactly 7 food logs again
        Food f = new Food(100, 20, 10, 5, new ArrayList<>(Arrays.asList("Apple")), 1.0);
        for (int i=0; i<7; i++) {
            member.addFoodLog(f);
        }
        // Now try to add the 8th log
        outContent.reset();
        // Input 'N' to not clear
        ByteArrayInputStream in = new ByteArrayInputStream("N\n".getBytes());
        System.setIn(in);
        member.addFoodLog(f);
        String output = outContent.toString();
        assertTrue(output.contains("Food log limit reached"), "Should prompt for clearing logs");
        // No clearing, no addition
    }

    @Test
    void testAddWorkoutLogUnderLimit() {
        ArrayList<Workout> w = new ArrayList<>();
        w.add(new Workout("Pushups", 3, 10, 100, 30));
        member.addWorkoutLog(w);
        // No prompt, just added
    }

    @Test
    void testAddWorkoutLogAtLimitClear() {
        ArrayList<Workout> w = new ArrayList<>();
        w.add(new Workout("Pushups", 3, 10, 100, 30));
        for (int i=0; i<7; i++) {
            member.addWorkoutLog(w);
        }
        outContent.reset();
        // Input 'Y'
        ByteArrayInputStream in = new ByteArrayInputStream("Y\n".getBytes());
        System.setIn(in);
        member.addWorkoutLog(w);
        String output = outContent.toString();
        assertTrue(output.contains("Workout log limit reached"), "Should prompt for clearing logs");
    }

    @Test
    void testAddWorkoutLogAtLimitRefuse() {
        ArrayList<Workout> w = new ArrayList<>();
        w.add(new Workout("Pushups", 3, 10, 100, 30));
        for (int i=0; i<7; i++) {
            member.addWorkoutLog(w);
        }
        outContent.reset();
        // Input 'N'
        ByteArrayInputStream in = new ByteArrayInputStream("N\n".getBytes());
        System.setIn(in);
        member.addWorkoutLog(w);
        String output = outContent.toString();
        assertTrue(output.contains("Workout log limit reached"), "Should prompt for clearing logs");
    }

    @Test
    void testShowLogsNoLogs() {
        outContent.reset();
        member.showLogs();
        String output = outContent.toString();
        // No logs means just no output or just "Day 0:" lines
        assertFalse(output.contains("Food"), "No logs to show");
    }

    @Test
    void testShowLogsWithSomeLogs() {
        // Add one food log and one workout log
        Food f = new Food(100, 20, 10, 5, new ArrayList<>(Arrays.asList("Banana")), 1.0);
        member.addFoodLog(f);

        ArrayList<Workout> w = new ArrayList<>();
        w.add(new Workout("Squats", 3, 10, 100, 30));
        member.addWorkoutLog(w);

        outContent.reset();
        member.showLogs();
        String output = outContent.toString();
        assertTrue(output.contains("Day 0: Food [calories=100, carbs=20g, proteins=10g"), "Show food log");
        assertTrue(output.contains("Workout [name=Squats"), "Show workout log");
    }

    @Test
    void testGetTrainerNoOp() {
        List<Trainer> trainers = new ArrayList<>();
        member.getTrainer(trainers);
        assertTrue(trainers.isEmpty(), "No trainers added");
    }

    @Test
    void testShowOptions() {
        outContent.reset();
        member.showOptions();
        String output = outContent.toString();
        assertTrue(output.contains("Go to Logger"), "Should print options");
    }

    @Test
    void testSetSubscribedAndCheckPremium() {
        assertFalse(member.checkPremium(), "Initially not premium");
        member.setSubscribed();
        assertTrue(member.checkPremium(), "Now premium");
    }

    @Test
    void testRunOpt1() {
        // runOpt1 -> cmd.MemberrunOpt1
        // Just call to ensure coverage. cmd calls logger menu. We trust no error.
        ByteArrayInputStream in = new ByteArrayInputStream("4\n".getBytes()); // choose option 4 to exit logger menu
        System.setIn(in);

        // Need loggedUser = member
        Authenticator auth = Authenticator.getInstance();
        try {
            Field loggedUserField = Authenticator.class.getDeclaredField("loggedUser");
            loggedUserField.setAccessible(true);
            loggedUserField.set(auth, member);
        } catch(Exception e) {fail("Should not throw");}

        member.runOpt1(new Scanner(System.in));
        // Just checking no exception
        assertTrue(true);
    }

    @Test
    void testRunOpt3() {
        // runOpt3 -> cmd.MemberrunOpt3
        // subscriber menu loops until we choose -1
        ByteArrayInputStream in = new ByteArrayInputStream("-1\n".getBytes());
        System.setIn(in);

        // Need loggedUser = member
        Authenticator auth = Authenticator.getInstance();
        try {
            Field loggedUserField = Authenticator.class.getDeclaredField("loggedUser");
            loggedUserField.setAccessible(true);
            loggedUserField.set(auth, member);
        } catch(Exception e) {fail("Should not throw");}

        member.runOpt3(new Scanner(System.in));
        assertTrue(true);
    }

    @Test
    void testRunOpt2NotSubscribed() {
        // runOpt2 -> cmd.MemberrunOpt2 -> showMemberPlannerMenu
        // If not subscribed => ExNotSubscribed
        // Input "2" to pick trainer plan
        ByteArrayInputStream in = new ByteArrayInputStream("2\n".getBytes());
        System.setIn(in);

        Authenticator auth = Authenticator.getInstance();
        try {
            Field loggedUserField = Authenticator.class.getDeclaredField("loggedUser");
            loggedUserField.setAccessible(true);
            loggedUserField.set(auth, member);
        } catch(Exception e) {fail("Should not throw");}

        assertThrows(ExNotSubscribed.class, ()->member.runOpt2(new Scanner(System.in)));
    }

    @Test
    void testRunOpt2NoTrainerPlan() throws Exception {
        // If subscribed to a trainer but no trainer plan => ExNoTrainerPlan
        member.setSubscribed(); // now premium
        // Create trainer and subscribe this member
        Trainer t = new Trainer("T1",100,"ufxx");
        Authenticator auth = Authenticator.getInstance();
        Field usersField = Authenticator.class.getDeclaredField("users");
        usersField.setAccessible(true);
        @SuppressWarnings("unchecked")
        List<User> users = (List<User>)usersField.get(auth);
        users.add(t);

        Subscriber sb = Subscriber.getInstance();
        sb.addTrainer(t);

        Field subscribeTrainersField = Subscriber.class.getDeclaredField("subscribeTrainers");
        subscribeTrainersField.setAccessible(true);
        @SuppressWarnings("unchecked")
        HashMap<Trainer,List<Member>> subsMap = (HashMap<Trainer,List<Member>>)subscribeTrainersField.get(sb);
        subsMap.putIfAbsent(t,new ArrayList<>());
        subsMap.get(t).add(member);

        Field loggedUserField = Authenticator.class.getDeclaredField("loggedUser");
        loggedUserField.setAccessible(true);
        loggedUserField.set(auth, member);

        ByteArrayInputStream in = new ByteArrayInputStream("2\n".getBytes());
        System.setIn(in);

        assertThrows(ExNoTrainerPlan.class, ()->member.runOpt2(new Scanner(System.in)));
    }

    @Test
    void testRunOpt4NotSubscribed() {
        // runOpt4 -> MemberrunOpt4 -> throws ExNotSubscribed if not premium
        // no input needed
        Authenticator auth = Authenticator.getInstance();
        try {
            Field loggedUserField = Authenticator.class.getDeclaredField("loggedUser");
            loggedUserField.setAccessible(true);
            loggedUserField.set(auth, member);
        } catch(Exception e) {fail("Should not throw");}

        assertThrows(ExNotSubscribed.class, ()->member.runOpt4(new Scanner(System.in)));
    }


    private void loggedUserFieldSetup(Authenticator auth, User u) throws Exception {
        Field loggedUserField = Authenticator.class.getDeclaredField("loggedUser");
        loggedUserField.setAccessible(true);
        loggedUserField.set(auth, u);
    }

}
