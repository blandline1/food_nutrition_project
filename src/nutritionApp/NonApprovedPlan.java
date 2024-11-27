package nutritionApp;
import java.util.*;

public class NonApprovedPlan implements PlanType {

    private static NonApprovedPlan instance = new NonApprovedPlan();

    private NonApprovedPlan() {}

    public static NonApprovedPlan getInstance() {return instance;}

    @Override
    public void printPlanType() {
        System.out.println("--- NON APPROVE PLAN ---");
    }
}


