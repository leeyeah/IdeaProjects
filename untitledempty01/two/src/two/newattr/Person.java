package two.newattr;

public class Person {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public void repaire(){
        System.out.println("repare  " +this);

    }

    public void update(Person p){
        System.out.println("update "+this);
    }

    public String name;

    public int age;
}
