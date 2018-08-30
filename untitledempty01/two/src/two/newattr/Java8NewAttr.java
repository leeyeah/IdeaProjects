package two.newattr;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Java8NewAttr {

    public static void main(String[] ars) {

        IMyMath myMath = (a, b) -> {
            return a + b;
        };

        int j = myMath.add(10, 10);
        //System.out.println(j);

        IQuery iQuery = a->{System.out.println(a);};
        iQuery.Query(5);
        iQuery.print();

        Java8NewAttr obj = new Java8NewAttr();
        //obj.MainTest();

        obj.TestPerson();


    }


    void TestPerson() {

        Person p = PersonFactory.create(Person::new);
        p.setAge(11);
        p.setName("hello");

        ArrayList<Person> list = new ArrayList<Person>(2);
        list.add(new Person());
        list.get(0).setName("world");
        list.add(p);

        List<Person> newlist = list.stream()
                .filter(item -> item.age < 1)
                .collect(Collectors.toList());


        newlist.forEach(item->{
            System.out.println(item.name);
        });
    }


    void MainTest() {
        /*
        CusMath cusMath = new CusMath();
        int j = cusMath.sub(10,2);
        System.out.println(j);
        */
    }


    @FunctionalInterface
    interface IMyMath {
        public int add(int a, int b);

        default int sub(int a, int b) {
            return a - b;
        }

        default int sub2(int a, int b) {
            return a - b;
        }
    }

    class CusMath implements IMyMath {

        @Override
        public int add(int a, int b) {
            return a + b;
        }
    }


    @FunctionalInterface
    interface IQuery{
        void Query(int i);
        default void print(){
            System.out.println("IQuery");
        }

    }
}
