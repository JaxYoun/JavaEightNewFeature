package stream;

import org.junit.Test;

import java.util.Comparator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 筛选和切片
 */
public class FilterAndSlice {

    /**
     * 内部迭代：迭代操作由Stream API完成
     */
    @Test
    public void test0() {
        //中间操作只定义了计算过程，不会触发真正的计算
        Stream<Integer> stream = Stream.of(11, 22, 33, 44).filter(it -> {
            System.err.println("中间惰性操作");
            return it > 20;
        }).sorted(Comparator.naturalOrder());

        //当调用了终止函数时就会触发前面流水线定义的迭代操作
        stream.forEach(System.out::println);
    }

    /**
     * 截断流：只对前n个元素执行计算
     */
    @Test
    public void test1() {
        IntStream.of(1, 2, 3, 4, 5).map(x -> x * 4).map(it -> {
            System.out.println("短路");
            return it;
        }).limit(3).forEach(System.err::print);
    }

    /**
     * 跳过流：跳过前n个元素不执行计算
     */
    @Test
    public void test2() {
        IntStream.of(1, 2, 3, 4, 5).map(x -> x * 4).skip(3).forEach(System.err::print);
    }

    /**
     * 跳过流：跳过前n个元素不执行计算
     */
    @Test
    public void test3() {
        IntStream.of(1, 2, 2, 1, 2).distinct().forEach(System.err::print);
    }

}
