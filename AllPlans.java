import java.util.HashMap;
import java.util.*;

public abstract class AllPlans implements Cloneable{

    private static HashMap<User, ArrayList<AllPlans>> MemberPlans = new HashMap<>();

    protected void append(User usr, AllPlans plan){

        ArrayList<AllPlans> temp = MemberPlans.get(usr);
        temp.add(plan);
        MemberPlans.put(usr, temp);
    }



}
