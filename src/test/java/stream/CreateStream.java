package stream;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * StreamAPI操作的时数据源的拷贝，不会对源数据产生影响。
 * 集合代表的时数据源，流代表的时对数据的一些列操作。
 * 可以操作集合：
 * 可以操作数组：
 * <p>
 * 1.创建Stream
 * 2.数据操作
 * 3.终止操作
 */
public class CreateStream {

    /**
     * Stream对象的初始化
     */
    @Test
    public void test0() {

        //1.通过Arrays工具类的静态方法，从数组创建流
        String[] arr = {"1", "2"};
        Arrays.stream(arr).forEach(System.out::println);

        //2.通过Collection系列集合数据结构的stream()或者parallelStream()来获取串行流或者并行流
        List<String> list = Arrays.asList("1", "2", "3");
        list.parallelStream().forEach(System.out::println);

        //3.调用Steam类的静态方法of()
        Stream.of("a", "b", "c").forEach(System.out::println);

        //4.创建无限流，从种子开始，对后续元素执行Lambda函数内的操作，无限执行下去，如果再调用limit()方法表示后续执行的终点
        //迭代法
        Stream.iterate(0, x -> x + 2).limit(10).forEach(System.err::println);
        //生成法
        Stream.generate(() -> Math.random()).limit(8).forEach(System.out::println);
    }

}
