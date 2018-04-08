package designpattern.extendsandinteface;


/**
 * @author panqiang 2017/12/11
 *
 */
public class Person {
    private String name ="";
    private String sex = "";
    private int age = 0;

    protected Person(String name){
        this.name = name;
        System.out.println("new Person: "+name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
