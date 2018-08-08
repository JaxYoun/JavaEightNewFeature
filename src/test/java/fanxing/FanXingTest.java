package fanxing;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Yang
 * @Date: 2018/8/7 23:06
 * @Description: 1.数组的协变性：指的是在声明一个数组时，元素的内型用的是父类的类型，在使用该数组时是可以用来装子类对象的。
 * 2.集合的非协变性：指的是在声明一个集合时，元素的内型用的是父类的类型，在使用该集合时是不可以用来装子类对象的。
 * 由于集合的非协变性，给集合的使用带了了不便，所以引入了泛型加以弥补。
 */
public class FanXingTest {

    @Test
    public void test() {
        Son[] arr = new Son[2];
        arr[0] = new Son();
        arr[1] = new Son();
        arrTest(arr);

        List<Son> list = new ArrayList<>(2);
        list.add(new Son());
        list.add(new Son());
        collectionTest(list);

        erase();
    }

    public void arrTest(Parent[] arr) {
        for (Parent it : arr) {
            System.out.println(it.id);
        }
    }

    /**
     * 用泛型的PESC机制解决集合的非协变性的缺陷
     *
     * @param list
     */
    public void collectionTest(List<? extends Parent> list) {
        for (Parent it : list) {
            System.out.println(it.id);
        }
    }

    /**
     * 数组的类型擦出
     */
    public void erase() {
        String[] strings = new String[2];
        strings[0] = "00";
        Object[] objects = strings;

        objects[1] = 1;
    }

    /**
     * 集合的类型擦出
     */
    public void erase0() {
        List<String> strings = new ArrayList<>(2);
        strings.add("00");
//        List<Object> objects = strings;

//        objects[1] = 1;
    }

    //基于上面两种原因，所以不能实现一个泛型类型的数组，List<String>[]，这种用法

}

class Parent {
    public int id;
    public String name;
}

class Son extends Parent {
    public int age;
}
