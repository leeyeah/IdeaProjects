package two.newattr;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Java8NewAttrStream {

    public static void main(String[] args) {

        Stream<String> list = Stream.of("abc", "def", "acd");
        String rstr = list.reduce("",String::concat);
        System.out.println(rstr);

        /*
        list.sorted()
                .map((item) -> {
                    return item.concat(" End");
                })
                .forEach(System.out::println);
        */

        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        //integers.stream().mapToInt(i->i).sum();
        int resint = integers.stream().reduce(0,Integer::sum);


        System.out.println(resint);

    }

}
