package designpattern.extendsandinteface;

public class Student extends Person {

    public Student(String name) {
        super(name);
    }

    public Student() {
        super("wtf");
    }


    public static void main(String[] args){
        Person s = new Person("kris");
        s.setAge(27);
        s.setSex("male");
        System.out.println(s.getSex());
        System.out.println(s instanceof Student);
    }
}
