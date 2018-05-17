package lambda.inbededFunctionalInterface;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 四大核心内置函数式接口：
 * Consummer<T>：消费型接口
 *      void accept(T t);
 * Supplier<T>：供给型接口
 *      T get();
 * Function<T t, R r>：函数型接口
 *      R apply(T t);
 * Predicate<T>：断言型接口
 *      boolean test(T t);
 */
public class InbededFuncitonalInterfaceTest {


    /**
     * 消费行接口
     */
    @Test
    public void test0() {
        happy(t -> {
            t -= 33;
            System.out.println(t);
            System.out.println(t * 3);
        }, 600D);
    }

    public void happy(Consumer<Double> consumer, Double money) {
        consumer.accept(money);
    }

    /**
     * 供给型接口
     */
    @Test
    public void test1() {
        List<Integer> list = getNumList(() -> new Random().nextInt(10), 10);

        list.forEach(System.out::println);
    }

    public List<Integer> getNumList(Supplier<Integer> supplier, int count) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(supplier.get());
        }
        return list;
    }

    /**
     * 函数型接口
     */
    @Test
    public void test2() {
        char[] charArr = stringHandler(t -> t.toCharArray(), "abcd");
        for(char cha : charArr) {
            System.out.println(cha);
        }
    }

    public char[] stringHandler(Function<String, char[]> function, String string) {
        return function.apply(string);
    }

    /**
     * 断言型接口
     */
    @Test
    public void test3() {
        List<Integer> tempList = Arrays.asList(1,2,3,4, 6, 9);
        List<Integer> list = filterMyInteger(it -> it % 3 == 0, tempList);
        list.forEach(System.out::println);
    }

    public List<Integer> filterMyInteger(Predicate<Integer> predicate, List<Integer> list) {
        List<Integer> resultList = new ArrayList<>();
        for (Integer it : list) {
            if(predicate.test(it)) {
                resultList.add(it);
            }
        }
        return resultList;
    }
}
