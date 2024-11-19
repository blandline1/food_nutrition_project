import java.util.HashMap;
import java.util.*;

public abstract class AllPlans {

    private static HashMap<Member, ArrayList<AllPlans>> MemberPlans = new HashMap<>();

    public void append(Member member, AllPlans plan){

        ArrayList<AllPlans> temp = MemberPlans.get(member);
        temp.add(plan);
        MemberPlans.put(member, temp);

    }

    public AllPlans getActivePlan(Member member) throws CloneNotSupportedException {
        ArrayList<AllPlans> plans = MemberPlans.get(member);
        return (AllPlans) plans.getLast().clone();
    }
}
