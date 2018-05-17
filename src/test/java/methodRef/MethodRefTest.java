package methodRef;

import org.junit.Test;

import java.io.PrintStream;
import java.util.function.Consumer;

/**
 * 若Lambda体中的内容有方法已经实现，我们可以使用方法引用
 * 方法引用时Lambda的另一种表现形式
 * 要求被引用对象和抽象方法的参数返回值保持一致
 *
 * 语法一：
 *      对象::实例方法名
 * 语法二：
 *      类::静态方法
 * 语法三：
 *      类::实例方法
 */
public class MethodRefTest {

    /**
     * 对象::实例方法名
     */
    @Test
    public void test0() {
        Consumer<String> consumer = x -> System.out.println(x);
        consumer.accept("0000000");

        PrintStream printStream = System.out;
        Consumer<String> consumer1 = printStream::println;  //对象::实例方法名
        consumer1.accept("33333");

        Consumer<String> consumer2 = System.out::println;  //
        consumer2.accept("22222");
    }

}
