package two.newattr;

import java.util.function.Supplier;

public class PersonFactory {
    public static Person create(Supplier<Person> supplier){
        return  supplier.get();
    }


}
