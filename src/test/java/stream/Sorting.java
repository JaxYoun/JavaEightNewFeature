package stream;

import org.junit.Test;

import java.util.Comparator;
import java.util.stream.Stream;

/**
 * 排序
 */
public class Sorting {

    /**
     * 自然排序：按照泛型的compareTo()方法（Comparable）
     * 定制排序：（Comparetor）
     */
    @Test
    public void test0() {
        //自然排序
        Stream.of("cc", "dd", "aa").sorted().forEach(System.out::println);

        //自然排序，逆序
        Stream.of("cc", "dd", "aa").sorted(Comparator.reverseOrder()).forEach(System.out::println);

        //自定义排序
        Stream.of("cc", "dd6", "a666a").sorted(Comparator.comparing(String::length)).forEach(System.out::println);

        //自定义排序，逆序
        Stream.of("cc", "dd6", "a666a").sorted(Comparator.comparing(String::length).reversed()).forEach(System.out::println);
    }

}
