package nutritionApp;

import java.util.Scanner;

public class Command {

	private static Command instance = new Command();
	
	private Command() {}
	
	public static Command getInstance() {
		return instance;
	}
	

    public void trainerrunOpt1(Scanner scanner) {
        Subscriber.getInstance().showMyMembers();
    }

    public void trainerrunOpt2(Scanner scanner) {
        Member member = Subscriber.getInstance().showMyMembersAndChoose(scanner);
        Planner planner = Planner.getInstance();
        planner.showPlan(member, scanner);
    }


    public void trainerrunOpt3(Scanner scanner) {
    	Planner.getInstance().showTrainerPlannerMenu(scanner);
    }


    public void trainerrunOpt4(Scanner scanner) {
        Member member = Subscriber.getInstance().showMyMembersAndChoose(scanner);
        member.conductAnalysis();
    }
    
    

    public void MemberrunOpt2(Scanner scanner) throws ExNotSubscribed, ExNoTrainerPlan {
    	Planner.getInstance().showMemberPlannerMenu(scanner);
    }


    public void MemberrunOpt1(Scanner scanner) {
        LoggerMenu.getInstance().showLoggerMenu(scanner);
    }

    public void MemberrunOpt3(Scanner scanner) {
        Subscriber subscriber = Subscriber.getInstance();
        subscriber.showSubscriberMenu(scanner);
    }

    
    public void MemberrunOpt4(Scanner scanner) throws ExNotSubscribed {
        Member member = (Member) Authenticator.getInstance().getLoggedUser();
        if (member.checkPremium()) {
            member.conductAnalysis();
        } else {
            throw new ExNotSubscribed();
        }
    }
}
