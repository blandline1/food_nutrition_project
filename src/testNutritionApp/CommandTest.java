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

public class CommandTest {

    private ByteArrayOutputStream outContent;
    private PrintStream originalOut;
    private Command cmd;

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

        cmd = Command.getInstance();
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        outContent.reset();
    }

    /**
     * Helper method to register and login a trainer.
     */
    private Trainer loginTrainer(String name) throws Exception {
        Authenticator auth = Authenticator.getInstance();
        // Register trainer
        Field usersField = Authenticator.class.getDeclaredField("users");
        usersField.setAccessible(true);
        @SuppressWarnings("unchecked")
        List<User> users = (List<User>)usersField.get(auth);

        Trainer t = new Trainer(name, 100, "tpass");
        users.add(t);

        // Add trainer to subscriber map
        Subscriber sb = Subscriber.getInstance();
        sb.addTrainer(t);

        // Set logged user to trainer
        Field loggedUserField = Authenticator.class.getDeclaredField("loggedUser");
        loggedUserField.setAccessible(true);
        loggedUserField.set(auth, t);

        return t;
    }

    /**
     * Helper method to register and login a member.
     */
    private Member loginMember(String name, boolean premium) throws Exception {
        Authenticator auth = Authenticator.getInstance();

        Field usersField = Authenticator.class.getDeclaredField("users");
        usersField.setAccessible(true);
        @SuppressWarnings("unchecked")
        List<User> users = (List<User>)usersField.get(auth);

        Member m = new Member(name, 200, "mpass");
        if (premium) {
            m.setSubscribed(); // make member premium
        }

        users.add(m);
        Planner.getInstance().memberCreation(m);

        Field loggedUserField = Authenticator.class.getDeclaredField("loggedUser");
        loggedUserField.setAccessible(true);
        loggedUserField.set(auth, m);

        return m;
    }

    @Test
    void testTrainerrunOpt1() throws Exception {
        Trainer t = loginTrainer("TrainerOpt1");
        // trainerrunOpt1 calls Subscriber.getInstance().showMyMembers()
        // If trainer has no members, it just prints empty.
        Scanner sc = new Scanner(new ByteArrayInputStream(new byte[0]));
        cmd.trainerrunOpt1(sc);
        sc.close();
        String output = outContent.toString();
        assertTrue(output.contains("--- SUBSCRIBED MEMBERS ---"), "Should show empty subscribed members list");
    }

    @Test
    void testTrainerrunOpt2() throws Exception {
        // trainerrunOpt2 chooses a member and shows their plan
        Trainer t = loginTrainer("TrainerOpt2");
        Member m = new Member("MForOpt2",201,"mpass");
        Planner.getInstance().memberCreation(m);

        // Subscribe member to trainer
        Subscriber sb = Subscriber.getInstance();
        Field subscribeTrainersField = Subscriber.class.getDeclaredField("subscribeTrainers");
        subscribeTrainersField.setAccessible(true);
        @SuppressWarnings("unchecked")
        HashMap<Trainer,List<Member>> subsMap = (HashMap<Trainer,List<Member>>)subscribeTrainersField.get(sb);
        subsMap.putIfAbsent(t, new ArrayList<>());
        subsMap.get(t).add(m);

        // showMyMembersAndChoose(scanner) -> choose first member: input "1"
        // showPlan(member, scanner) -> no plan yet, so it should print "User does not have any plan yet!!"
        Scanner sc = new Scanner(new ByteArrayInputStream("1\n".getBytes()));
        cmd.trainerrunOpt2(sc);
        sc.close();
        String output = outContent.toString();
        assertTrue(output.contains("User does not have any plan yet!!"), "No plan scenario covered");
    }

    @Test
    void testTrainerrunOpt3() throws Exception {
        // showTrainerPlannerMenu requires input
        Trainer t = loginTrainer("TrainerOpt3");
        // Input invalid choice "3" to cover default scenario
        Scanner sc = new Scanner(new ByteArrayInputStream("3\n".getBytes()));
        cmd.trainerrunOpt3(sc);
        sc.close();
        String output = outContent.toString();
        assertTrue(output.contains("1. Make your default plan."), "Menu shown");
    }

    @Test
    void testMemberrunOpt1() throws Exception {
        // Calls LoggerMenu showLoggerMenu
        Member m = loginMember("MOpt1", false);
        // LoggerMenu: input "4" to go back immediately
        Scanner sc = new Scanner(new ByteArrayInputStream("4\n".getBytes()));
        cmd.MemberrunOpt1(sc);
        sc.close();
        String output = outContent.toString();
        assertTrue(output.contains("Logger Menu:"), "Logger menu shown");
    }

    @Test
    void testMemberrunOpt2NoException() throws Exception {
        // showMemberPlannerMenu can throw exceptions, let's set a scenario with no exception.
        // Member is premium and subscribed to a trainer who has a plan
        Member m = loginMember("MOpt2NoEx", true);

        // Create a trainer and subscribe the member
        Trainer t = new Trainer("SubT",300,"tpass");
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
        subsMap.putIfAbsent(t, new ArrayList<>());
        subsMap.get(t).add(m);

        // Give the trainer a plan so that displayPlan won't fail
        Field loggedUserField = Authenticator.class.getDeclaredField("loggedUser");
        loggedUserField.setAccessible(true);
        loggedUserField.set(auth, t);

        // Create a plan for trainer
        StringBuilder inputPlan = new StringBuilder();
        for(int i=0;i<7;i++){
            inputPlan.append("100\n20\n10\n5\nApple,Banana\n1.0\n");
        }
        for(int i=0;i<7;i++){
            inputPlan.append("0\n");
        }
        Scanner scPlan = new Scanner(new ByteArrayInputStream(inputPlan.toString().getBytes()));
        Planner.getInstance().makePlan(scPlan);
        scPlan.close();

        // Now log in as member again
        loggedUserField.set(auth, m);

        // choose option 1 (make a plan) to avoid exception path
        String input = "1\n" + inputPlan.toString(); 
        Scanner sc = new Scanner(new ByteArrayInputStream(input.getBytes()));
        cmd.MemberrunOpt2(sc); 
        sc.close();
        String output = outContent.toString();
        assertTrue(output.contains("Make a plan."), "Menu printed and no exception thrown");
    }

    @Test
    void testMemberrunOpt2ExNotSubscribed() throws Exception {
        // Member not subscribed => ExNotSubscribed
        Member m = loginMember("MOpt2NotSub", false);
        // Input "2" to pick trainer plan
        Scanner sc = new Scanner(new ByteArrayInputStream("2\n".getBytes()));
        assertThrows(ExNotSubscribed.class, ()->cmd.MemberrunOpt2(sc));
        sc.close();
    }

    @Test
    void testMemberrunOpt2ExNoTrainerPlan() throws Exception {
        // Member subscribed but trainer has no plan => ExNoTrainerPlan
        Member m = loginMember("MOpt2NoPlan", true);
        Trainer t = new Trainer("TNoPlan",400,"tpass");
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
        subsMap.putIfAbsent(t, new ArrayList<>());
        subsMap.get(t).add(m);

        // No plan for trainer
        // choose option 2 => displayPlan(trainer) => ExNoTrainerPlan
        String input = "2\n";
        Scanner sc = new Scanner(new ByteArrayInputStream(input.getBytes()));
        assertThrows(ExNoTrainerPlan.class, ()->cmd.MemberrunOpt2(sc));
        sc.close();
    }

    @Test
    void testMemberrunOpt4NotPremium() throws Exception {
        Member m = loginMember("MOpt4NotPremium", false);
        Scanner sc = new Scanner(new ByteArrayInputStream(new byte[0]));
        assertThrows(ExNotSubscribed.class, ()->cmd.MemberrunOpt4(sc));
        sc.close();
    }

}
