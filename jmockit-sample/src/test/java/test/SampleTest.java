package test;

import org.junit.Test;
import mockit.*;
import org.junit.runner.RunWith;

/**
 * Created by IntelliJ IDEA.
 * User: junichi
 * Date: 10/12/26
 * Time: 17:36
 * To change this template use File | Settings | File Templates.
 */
public class SampleTest {

    @Mocked private Person person;

    @Test
    public void test() {
        new NonStrictExpectations(){
            {
                person.getName(anyInt); returns("Kato");
                person.getAge(); returns(38);
            }
        };

        System.out.println(person.getName(0)+person.getName(1)+person.getAge());


        new Verifications(){
            {
                person.getName(anyInt);
                person.getAge();
            }
        };
    }

}
