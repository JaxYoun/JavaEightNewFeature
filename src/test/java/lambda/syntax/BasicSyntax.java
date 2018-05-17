package lambda.syntax;

import org.junit.Test;

import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

/**
 * Lambda表达式引入了箭头操作符"->"，箭头符将Lambda表达式分为左右两部分
 * 左边：对应抽象方法的参数列表
 * 右边：对应抽象方法的实现体
 * <p>
 * Lambda表达式需要函数式接口的支持
 * Lambda表达式实质就是对函数式接口的实现
 * <p>
 * 语法格式一：无参数、无返回值
 * () -> System.out.println();
 * <p>
 * 语法格式二：有一个参数、无返回值
 * (arg) -> System.out.println(arg)
 * <p>
 * 语法格式三：有一个参数、无返回值，一个参数的小括号可以不写，但是建议写
 * arg -> System.out.println(arg)
 * <p>
 * 语法格式四：有多个参数、有返回值、多条语句，
 * (i0, i1) -> {
 * System.out.println("有多个参数，且有多条语句，且有返回值！");
 * return Integer.compare(i0, i1);
 * };
 * <p>
 * 语法格式五：如果函数式接口有泛型声明，由于编译器有上下文类型推断功能，参数列表的数据类型可以省略
 * <p>
 * 【总之】：一个参数的小括号可写可不写，多个参数的小括号必须写
 * 一条实现语句的大括号可写可不写，多条实现语句的大括号必须写
 * 实现体有无返回值不影响大括号，如果只有一条return语句，大括号和return关键字都可以省略
 * <p>
 * 左右遇一括号省
 * 左侧推断类型省
 */
public class BasicSyntax {


    /**
     * 无参数、无返回值
     */
    @Test
    public void test1() {
        new Thread(() -> System.out.println("无参数，无返回值！")).start();
    }

    /**
     * 有一个参数、无返回值
     */
    @Test
    public void test2() {
        Consumer<String> consumer = (arg) -> System.out.println(arg);
        consumer.accept("有一个参数、无返回值!");
    }

    /**
     * 有一个参数、无返回值，一个参数的小括号可以不写，但是建议写
     */
    @Test
    public void test3() {
        Consumer<String> consumer0 = arg -> System.out.println(arg);
        consumer0.accept("有一个参数、无返回值!");
    }

    /**
     *
     */
    @Test
    public void test4() {
        Comparator<Integer> comparator = (i0, i1) -> {
            System.out.println("有多个参数，且有多条语句，且有返回值！");
            return Integer.compare(i0, i1);
        };
        comparator.compare(12, 44);
    }

    /**
     * 函数式接口的支持
     */
    @Test
    public void test5() {
        System.out.println(operate((arg0, arg1) -> arg0 + arg1, 6, 9));
        System.out.println(operate((arg0, arg1) -> arg0 * arg1, 6, 9));
    }

    public Integer operate(MyFunction<Integer> myFunction, Integer arg0, Integer arg1) {
        return myFunction.calculate(arg0, arg1);
    }

}
