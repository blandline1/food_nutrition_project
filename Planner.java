import java.lang.reflect.Array;
import java.util.*;

public class Planner {
    private static Planner instance = new Planner();
    public static Planner getInstance(){
        return instance;
    }

    public void makePlan(ArrayList<Food> fd, ArrayList<Workout> wk, Member mb) {
        if (mb.getPremium()) {
            ApprovedPlan ap = new ApprovedPlan();
            ap.appendPlan(fd, wk, mb);
        }
        else {
            NonApprovedPlan nap = new NonApprovedPlan();
            nap.appendPlan(fd, wk, mb);
        }

    }

    public void makePlan(ArrayList<Food> fd, ArrayList<Workout> wk, Trainer tr) {
        ApprovedPlan ap = new ApprovedPlan();
        ap.appendPlan(fd, wk, tr);
    }

}
