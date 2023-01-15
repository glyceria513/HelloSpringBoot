package hello.hellospring.controller;

public class MemberForm {
    private String name;    //html FORM태그 안의 name과 같아야 한다.

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
