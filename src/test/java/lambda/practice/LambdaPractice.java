package lambda.practice;

import lambda.User;
import lambda.whyLambda.WhyLambda;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LambdaPractice {

    @Test
    public void test0() {
        User[] userArr = {
                new User(1, "yang", 28, 10004D),
                new User(4, "luo", 50, 38888D),
                new User(3, "zhong", 33, 342D)
        };
        List<User> userList = Arrays.asList(userArr);
        userList.forEach(System.err::println);

        //自然排序
        Collections.sort(userList);
        userList.forEach(System.out::println);

        //定制排序，实现一
        Collections.sort(userList, (arg0, arg1) -> Integer.compare(arg0.getAge(), arg1.getAge()));
        userList.forEach(it -> System.err.println(it + "=============="));//定制排序

        //定制排序，实现二
        Collections.sort(userList, Comparator.comparingInt(User::getAge));
        Collections.reverse(userList);
        userList.forEach(it -> System.out.println(it + "=============="));
    }

    @Test
    public void test1() {
        System.out.println(operate("agb", arg -> arg.toUpperCase()));
        System.out.println(operate("  agb  ", arg -> arg.trim()));

    }

    public String operate(String arg, UperCase uperCase) {
        return uperCase.gan(arg);
    }


    @Test
    public void test2() {
        System.out.println(numOperate((x, y) -> (x / y) * 1.0, 12, 5));
        System.out.println(numOperate((x, y) -> x * y * 1.0, 12, 5));
    }

    public Double numOperate(MyCaltulator<Integer, Double> myCaltulator, Integer arg0, Integer arg1) {
        return myCaltulator.calculate(arg0, arg1);
    }

}
