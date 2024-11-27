package nutritionApp;
import java.util.*;

public class ApprovedPlan implements PlanType {

    private static ApprovedPlan approvedPlan = new ApprovedPlan();

    private ApprovedPlan() {}

    public static ApprovedPlan getInstance() {return approvedPlan;}

    @Override
    public void printPlanType() {
        System.out.println("--- APPROVE PLAN ---");
    }

}
