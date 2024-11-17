public class Member extends User {

    public Member(String name, int id) {
        super(name, id);
    }

    @Override
    public String toString() {
        return "Member [name=" + name + ", id=" + id + "]";
    }
}
