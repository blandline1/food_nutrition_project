package testNutritionApp;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nutritionApp.*;

public class PlannerTest {

    private ByteArrayOutputStream outContent;
    private PrintStream originalOut;
    private Planner planner;

    @BeforeEach
    void setUp() throws Exception {
        originalOut = System.out;
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        planner = Planner.getInstance();

        // Reset Authenticator internal state
        Authenticator.getInstance().Logout(); // sets loggedUser = null
        Field usersField = Authenticator.class.getDeclaredField("users");
        usersField.setAccessible(true);
        ((List<?>)usersField.get(Authenticator.getInstance())).clear();

        Field idCountField = Authenticator.class.getDeclaredField("idCount");
        idCountField.setAccessible(true);
        idCountField.setInt(Authenticator.getInstance(), 0);

        // Reset Subscriber internal state
        Field subscribeTrainersField = Subscriber.class.getDeclaredField("subscribeTrainers");
        subscribeTrainersField.setAccessible(true);
        ((java.util.HashMap<?,?>)subscribeTrainersField.get(Subscriber.getInstance())).clear();
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        outContent.reset();
    }

    @Test
    void testGetInstance() {
        Planner p1 = Planner.getInstance();
        Planner p2 = Planner.getInstance();
        assertSame(p1,p2, "Should return same instance");
    }

    @Test
    void testMakePlan() throws Exception {
        Member loggedMember = new Member("LM",2,"pass");
        Field loggedUserField = Authenticator.class.getDeclaredField("loggedUser");
        loggedUserField.setAccessible(true);
        loggedUserField.set(Authenticator.getInstance(), loggedMember);

        // Input for 7 foods and 7 workouts
        StringBuilder input = new StringBuilder();
        for(int i=0;i<7;i++){
            input.append("100\n20\n10\n5\nApple,Banana\n1.0\n"); 
        }
        for(int i=0;i<7;i++){
            input.append("1\nPushups\n3\n10\n100\n30\n");
        }

        Scanner sc = new Scanner(new ByteArrayInputStream(input.toString().getBytes()));
        planner.makePlan(sc);
        sc.close();
        AllPlans plan = planner.getPlan(loggedMember);
        assertNotNull(plan,"Plan should be created");
    }

    @Test
    void testMakePlanForCustomer() throws Exception {
        Trainer loggedTrainer = new Trainer("T1",3,"tpass");
        Field loggedUserField = Authenticator.class.getDeclaredField("loggedUser");
        loggedUserField.setAccessible(true);
        loggedUserField.set(Authenticator.getInstance(), loggedTrainer);

        Member targetMember = new Member("TargetM",4,"mpass");
        planner.memberCreation(targetMember);

        StringBuilder input = new StringBuilder();
        for(int i=0;i<7;i++){
            input.append("100\n20\n10\n5\nApple,Banana\n1.0\n");
        }
        for(int i=0;i<7;i++){
            input.append("1\nPushups\n3\n10\n100\n30\n");
        }
        input.append("Y\n"); // Approve plan

        Scanner sc = new Scanner(new ByteArrayInputStream(input.toString().getBytes()));
        planner.makePlanForCustomer(targetMember, sc);
        sc.close();

        AllPlans plan = planner.getPlan(targetMember);
        assertNotNull(plan, "Plan for customer created");
    }

    // Removed testMemberCreation() and testGetPlanNoPlan() as they are redundant.

    @Test
    void testDisplayPlan() throws Exception {
        Member m = new Member("Disp",6,"ppass");
        planner.memberCreation(m);

        StringBuilder input = new StringBuilder();
        for(int i=0;i<7;i++){
            input.append("100\n20\n10\n5\nApple,Banana\n1.0\n");
        }
        for(int i=0;i<7;i++){
            input.append("1\nPushups\n3\n10\n100\n30\n");
        }

        Field loggedUserField = Authenticator.class.getDeclaredField("loggedUser");
        loggedUserField.setAccessible(true);
        loggedUserField.set(Authenticator.getInstance(), m);

        Scanner sc = new Scanner(new ByteArrayInputStream(input.toString().getBytes()));
        planner.makePlan(sc);
        sc.close();
        outContent.reset();

        assertThrows(ExNoTrainerPlan.class, ()->planner.displayPlan(m));
        String output = outContent.toString();
        assertTrue(output.contains("Day 0: Food"),"Should print at least first day's food");
    }

    @Test
    void testDisplayPlanNoPlan() {
        Member m = new Member("NoPlanUser",7,"pass");
        outContent.reset();
        assertThrows(ExNoTrainerPlan.class, ()->planner.displayPlan(m));
        String output = outContent.toString();
        assertFalse(output.contains("Day 0:"),"No plan means no day lines printed");
    }

    @Test
    void testShowPlanNoPlan() throws Exception {
        Member mem = new Member("NoP2",10,"ppass");
        outContent.reset();
        Scanner sc = new Scanner(new ByteArrayInputStream("Y\n".getBytes()));
        planner.showPlan(mem, sc);
        sc.close();
        String output = outContent.toString();
        assertTrue(output.contains("User does not have any plan yet!!"),"No plan message");
    }

    @Test
    void testShowMemberPlannerMenuMakePlan() throws ExNotSubscribed, ExNoTrainerPlan, Exception {
        Member m = new Member("MMenu",11,"pp");
        Field loggedUserField = Authenticator.class.getDeclaredField("loggedUser");
        loggedUserField.setAccessible(true);
        loggedUserField.set(Authenticator.getInstance(), m);

        StringBuilder input = new StringBuilder();
        input.append("1\n");
        for(int i=0;i<7;i++){
            input.append("100\n20\n10\n5\nApple,Banana\n1.0\n");
        }
        for(int i=0;i<7;i++){
            input.append("1\nPushups\n3\n10\n100\n30\n");
        }

        Scanner sc = new Scanner(new ByteArrayInputStream(input.toString().getBytes()));
        planner.showMemberPlannerMenu(sc);
        sc.close();
        AllPlans plan = planner.getPlan(m);
        assertNotNull(plan,"Plan created via member menu");
    }

    @Test
    void testShowMemberPlannerMenuExNotSubscribed() throws Exception {
        Member m = new Member("MM3",14,"p");
        Field loggedUserField = Authenticator.class.getDeclaredField("loggedUser");
        loggedUserField.setAccessible(true);
        loggedUserField.set(Authenticator.getInstance(), m);

        String input = "2\n";
        Scanner sc = new Scanner(new ByteArrayInputStream(input.getBytes()));
        assertThrows(ExNotSubscribed.class, ()->planner.showMemberPlannerMenu(sc));
        sc.close();
    }

    @Test
    void testShowTrainerPlannerMenuMakePlan() throws Exception {
        Trainer t = new Trainer("TR2",15,"tpp");
        Field loggedUserField = Authenticator.class.getDeclaredField("loggedUser");
        loggedUserField.setAccessible(true);
        loggedUserField.set(Authenticator.getInstance(), t);

        StringBuilder input = new StringBuilder();
        input.append("1\n"); 
        for(int i=0;i<7;i++){
            input.append("100\n20\n10\n5\nApple,Banana\n1.0\n");
        }
        for(int i=0;i<7;i++){
            input.append("1\nPushups\n3\n10\n100\n30\n");
        }

        Scanner sc = new Scanner(new ByteArrayInputStream(input.toString().getBytes()));
        planner.showTrainerPlannerMenu(sc);
        sc.close();
        AllPlans plan = planner.getPlan(t);
        assertNotNull(plan,"Plan created via trainer menu");
    }

    @Test
    void testShowTrainerPlannerMenuOtherChoice() throws Exception {
        Trainer t = new Trainer("TR3",16,"tpp");
        Field loggedUserField = Authenticator.class.getDeclaredField("loggedUser");
        loggedUserField.setAccessible(true);
        loggedUserField.set(Authenticator.getInstance(), t);

        String input = "2\n";
        Scanner sc = new Scanner(new ByteArrayInputStream(input.getBytes()));
        planner.showTrainerPlannerMenu(sc);
        sc.close();
        String output = outContent.toString();
        assertTrue(output.contains("1. Make your default plan."),"Menu printed");
        assertTrue(output.contains("2. Make custom plan for specific customer "),"Menu printed");
        assertNull(planner.getPlan(t),"No plan since second branch unreachable");
    }

    @Test
    void testShowMemberPlannerMenuInvalidInput() throws Exception {
        Member m = new Member("Inv",18,"pp");
        Field loggedUserField = Authenticator.class.getDeclaredField("loggedUser");
        loggedUserField.setAccessible(true);
        loggedUserField.set(Authenticator.getInstance(), m);

        String input = "3\n";
        Scanner sc = new Scanner(new ByteArrayInputStream(input.getBytes()));
        planner.showMemberPlannerMenu(sc);
        sc.close();

        String output = outContent.toString();
        assertTrue(output.contains("1. Make a plan."),"Menu printed");
        assertTrue(output.contains("2. Pick trainer plan."),"Menu printed");
        assertNull(planner.getPlan(m),"No plan because invalid choice doesn't create anything");
    }

    @Test
    void testShowTrainerPlannerMenuInvalidInput() throws Exception {
        Trainer t = new Trainer("TRInv",19,"tpp");
        Field loggedUserField = Authenticator.class.getDeclaredField("loggedUser");
        loggedUserField.setAccessible(true);
        loggedUserField.set(Authenticator.getInstance(), t);

        String input = "3\n";
        Scanner sc = new Scanner(new ByteArrayInputStream(input.getBytes()));
        planner.showTrainerPlannerMenu(sc);
        sc.close();

        String output = outContent.toString();
        assertTrue(output.contains("1. Make your default plan."),"Menu printed");
        assertTrue(output.contains("2. Make custom plan for specific customer "),"Menu printed");
        assertNull(planner.getPlan(t),"No plan because invalid choice doesn't create anything");
    }

    @Test
    void testMakePlanTwiceForSameUser() throws Exception {
        Member m = new Member("Twice",17,"p");
        Field loggedUserField = Authenticator.class.getDeclaredField("loggedUser");
        loggedUserField.setAccessible(true);
        loggedUserField.set(Authenticator.getInstance(), m);

        planner.memberCreation(m);

        StringBuilder input = new StringBuilder();
        for(int i=0;i<7;i++){
           input.append("100\n20\n10\n5\nApple,Banana\n1.0\n");
        }
        for(int i=0;i<7;i++){
           input.append("1\nPushups\n3\n10\n100\n30\n");
        }

        // Make first plan
        Scanner sc = new Scanner(new ByteArrayInputStream(input.toString().getBytes()));
        planner.makePlan(sc);
        sc.close();

        // Make second plan for same user
        sc = new Scanner(new ByteArrayInputStream(input.toString().getBytes()));
        planner.makePlan(sc);
        sc.close();

        AllPlans plan = planner.getPlan(m);
        assertNotNull(plan, "Plan should be created on second attempt as well");
    }

    @Test
    void testMakePlanWithNoWorkouts() throws Exception {
        Member loggedMember = new Member("NoW",20,"pass");
        Field loggedUserField = Authenticator.class.getDeclaredField("loggedUser");
        loggedUserField.setAccessible(true);
        loggedUserField.set(Authenticator.getInstance(), loggedMember);

        StringBuilder input = new StringBuilder();
        for(int i=0;i<7;i++){
            input.append("100\n20\n10\n5\nApple,Banana\n1.0\n");
        }
        for(int i=0;i<7;i++){
            input.append("0\n");
        }

        Scanner sc = new Scanner(new ByteArrayInputStream(input.toString().getBytes()));
        planner.makePlan(sc);
        sc.close();
        AllPlans plan = planner.getPlan(loggedMember);
        assertNotNull(plan,"Plan created even with no workouts");
    }

    @Test
    void testShowPlanWithExistingPlan() throws Exception {
        Trainer t = new Trainer("TrainerA", 30, "tpass");
        Field loggedUserField = Authenticator.class.getDeclaredField("loggedUser");
        loggedUserField.setAccessible(true);
        loggedUserField.set(Authenticator.getInstance(), t);

        Member m = new Member("MemberWithPlan", 31, "mpass");
        planner.memberCreation(m);

        StringBuilder input = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            input.append("100\n20\n10\n5\nApple,Banana\n1.0\n");
        }
        for (int i = 0; i < 7; i++) {
            input.append("1\nPushups\n3\n10\n100\n30\n");
        }

        loggedUserField.set(Authenticator.getInstance(), m);
        Scanner sc = new Scanner(new ByteArrayInputStream(input.toString().getBytes()));
        planner.makePlan(sc);
        sc.close();

        loggedUserField.set(Authenticator.getInstance(), t);

        outContent.reset();
        sc = new Scanner(new ByteArrayInputStream("Y\n".getBytes()));
        planner.showPlan(m, sc);
        sc.close();

        String output = outContent.toString();
        assertTrue(output.contains("Approved"), "Should attempt to approve plan");
    }

}
