import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.*;

public abstract class AllPlans implements Cloneable{


    public abstract void updatePlan(ArrayList<Food> fd, ArrayList<ArrayList<Workout>> wk, User usr);
    public abstract ArrayList<Food> getFoodPlan();
    public abstract ArrayList<ArrayList<Workout>> getWorkoutPlan();




}
