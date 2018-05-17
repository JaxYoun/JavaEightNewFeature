package stream;

import org.junit.Test;

import java.util.stream.Stream;

/**
 * map()方法接受一个函数作为参数，内部迭代时将这个函数引用到每个元素，然后将计算结果作为新流的元素返回
 * flatMap()方法接受一个函数作为参数，内部迭代将这个函数应用到每个元素，针对每个元素的计算都返回一个子流，然后将多个子流组装为新的父流返回
 */
public class Mapping {

    /**
     * map操作
     */
    @Test
    public void test0() {
        Stream.of("aa", "bb").map(it -> it.toUpperCase()).forEach(System.out::print);
    }

    /**
     * flatMap操作
     */
    @Test
    public void test1() {
//        Stream.of("a at", "bg b", "p r o g ram ing").flatMap(it -> it.split(" ")).forEach(System.out::print);
    }

}
