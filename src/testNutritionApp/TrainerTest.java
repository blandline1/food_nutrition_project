package testNutritionApp;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nutritionApp.*;


class TrainerTest {
    private ByteArrayOutputStream outContent;
    private PrintStream originalOut;

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
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        outContent.reset();
    }

    private Trainer createAndLoginTrainer(String name) throws Exception {
        // Create and login a trainer
        Trainer t = new Trainer(name, 10, "ufxx"); // password "pass" hashed
        Authenticator auth = Authenticator.getInstance();
        Field usersField = Authenticator.class.getDeclaredField("users");
        usersField.setAccessible(true);
        @SuppressWarnings("unchecked")
        List<User> users = (List<User>)usersField.get(auth);
        users.add(t);

        Field loggedUserField = Authenticator.class.getDeclaredField("loggedUser");
        loggedUserField.setAccessible(true);
        loggedUserField.set(auth, t);

        // Add trainer to subscriber
        Subscriber sb = Subscriber.getInstance();
        sb.addTrainer(t);
        return t;
    }

    private Member createMember(String name, boolean subscribed) throws Exception {
        // Create a member and optionally subscribe
        Member m = new Member(name, 20, "ufxx"); // "pass"
        Planner.getInstance().memberCreation(m);

        Authenticator auth = Authenticator.getInstance();
        Field usersField = Authenticator.class.getDeclaredField("users");
        usersField.setAccessible(true);
        @SuppressWarnings("unchecked")
        List<User> users = (List<User>)usersField.get(auth);
        users.add(m);

        if (subscribed) {
            m.setSubscribed();
        }
        return m;
    }

    @Test
    void testTrainerConstructorAndToString() {
        Trainer t = new Trainer("TName", 1, "ufxx");
        assertEquals("TName", t.getName(), "Name should be set correctly");
        assertEquals(1, t.getId(), "Id should be set correctly");
        String str = t.toString();
        assertTrue(str.contains("Trainer [name=TName, id=1]"), "toString should contain trainer info");
    }

    @Test
    void testGetTrainer() {
        Trainer t = new Trainer("TGet", 2, "ufxx");
        List<Trainer> trainerList = new ArrayList<>();
        t.getTrainer(trainerList);
        assertTrue(trainerList.contains(t), "Trainer should be added to the list");
    }

    @Test
    void testShowOptions() {
        Trainer t = new Trainer("TOptions", 3, "ufxx");
        outContent.reset();
        t.showOptions();
        String output = outContent.toString();
        assertTrue(output.contains("1. Check Subscriptions"), "Should print options");
        assertTrue(output.contains("4. Check Member Analysis"), "Should print options");
    }

    @Test
    void testRunOpt1() throws Exception {
        Trainer t = createAndLoginTrainer("TRunOpt1");
        // runOpt1 -> cmd.trainerrunOpt1 -> showMyMembers
        // With no subscribed members, it will just show empty list
        outContent.reset();
        Scanner sc = new Scanner(new ByteArrayInputStream(new byte[0]));
        t.runOpt1(sc);
        sc.close();
        String output = outContent.toString();
        assertTrue(output.contains("--- SUBSCRIBED MEMBERS ---"), "Should show subscribed members (empty)");
    }

    @Test
    void testRunOpt2() throws Exception {
        Trainer t = createAndLoginTrainer("TRunOpt2");
        Member m = createMember("MForOpt2", true);

        // Subscribe member to trainer
        Subscriber sb = Subscriber.getInstance();
        Field subscribeTrainersField = Subscriber.class.getDeclaredField("subscribeTrainers");
        subscribeTrainersField.setAccessible(true);
        @SuppressWarnings("unchecked")
        HashMap<Trainer,List<Member>> subsMap = (HashMap<Trainer,List<Member>>)subscribeTrainersField.get(sb);
        subsMap.putIfAbsent(t, new ArrayList<>());
        subsMap.get(t).add(m);

        // runOpt2 -> cmd.trainerrunOpt2 -> showMyMembersAndChoose and showPlan
        // Input "1" to choose the first member
        outContent.reset();
        Scanner sc = new Scanner(new ByteArrayInputStream("1\n".getBytes()));
        t.runOpt2(sc);
        sc.close();
        String output = outContent.toString();
        // No plan scenario -> "User does not have any plan yet!!" is printed from showPlan
        assertTrue(output.contains("User does not have any plan yet!!"), "No plan scenario displayed");
    }

    @Test
    void testRunOpt3() throws Exception {
        Trainer t = createAndLoginTrainer("TRunOpt3");
        // runOpt3 -> cmd.trainerrunOpt3 -> showTrainerPlannerMenu
        // just give invalid input "3" to print menu and return
        outContent.reset();
        Scanner sc = new Scanner(new ByteArrayInputStream("3\n".getBytes()));
        t.runOpt3(sc);
        sc.close();
        String output = outContent.toString();
        assertTrue(output.contains("1. Make your default plan."), "Trainer planner menu shown");
    }

}
