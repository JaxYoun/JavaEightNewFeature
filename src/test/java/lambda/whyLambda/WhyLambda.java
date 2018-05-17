package lambda.whyLambda;

import lambda.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 一个Lambda表达式就是是一个匿名函数，我们可以将一段Lambda表达式理解为一段可以像参数一样传递的代码。
 * 这样可以写出更简洁、灵活的代码，作为一种更紧凑的代码风格，可以提升Java语言的表达能力。
 */
public class WhyLambda {

    public static List<User> userList;

    static {
        userList = Arrays.asList(
                new User(1, "yang", 28, 10004D),
                new User(2, "luo", 50, 38888D),
                new User(3, "zhong", 33, 342D)
        );
    }

    /**
     * 匿名内部类法
     */
    @Test
    public void test0() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("我是线程");
            }
        };
        new Thread(runnable).start();
    }

    /**
     * Lambda表达式法
     */
    @Test
    public void test1() {
        Runnable runnable = () -> System.out.println("woyeshi ");
        new Thread(runnable).start();
    }

    /**
     * 原始需求：
     * 需求1：获取当前公司中员工年龄大于35的人
     * 需求2：获取当前公司员工工资大于5000的人
     * 这种情况下，针对不同字段作为过滤条件，没增加一个需求，就需要写一个类似的方法来实现，这些方法中仅有少数代码不同
     * 中间出现了大量重复性代码，有优化的余地。
     */
    @Test
    public void test2() {
        List<User> userList = filterUserByAge(WhyLambda.userList);
        for (User it : userList) {
            System.out.println(it.getAge());
        }
    }

    public List<User> filterUserByAge(List<User> userList) {
        List<User> resultUserList = new ArrayList<>();
        for (User it : userList) {
            if (it.getAge() > 35) {
                resultUserList.add(it);
            }
        }
        return resultUserList;
    }

    public List<User> filterUserBySalary(List<User> userList) {
        List<User> resultUserList = new ArrayList<>();
        for (User it : userList) {
            if (it.getSalary() > 5000D) {
                resultUserList.add(it);
            }
        }
        return resultUserList;
    }

    /**
     * 优化方案1：使用设计模式-策略模式
     * 当有新字段作为条件时需要新建实现类，仍然显得冗余
     */
    @Test
    public void test3() {
        List<User> userList1 = filterUserByAge(WhyLambda.userList, new FilterUserByAge(), 30);
        for (User it : userList1) {
            System.err.println(it.toString());
        }
        System.err.println("=========================");
        List<User> userList2 = filterUserBySalary(WhyLambda.userList, new FilterUserBySalary(), 5000D);
        for (User it : userList2) {
            System.out.println(it.toString());
        }
    }

    public List<User> filterUserByAge(List<User> userList, MyPredicate<User, Integer> myPredicate, Integer value) {
        List<User> resultUserList = new ArrayList<>();
        for (User it : userList) {
            if (myPredicate.test(it, value)) {
                resultUserList.add(it);
            }
        }
        return resultUserList;
    }

    public List<User> filterUserBySalary(List<User> userList, MyPredicate<User, Double> myPredicate, Double value) {
        List<User> resultUserList = new ArrayList<>();
        for (User it : userList) {
            if (myPredicate.test(it, value)) {
                resultUserList.add(it);
            }
        }
        return resultUserList;
    }

    /**
     * 优化方式2：使用匿名内部类
     * 但是仍然有冗余代码，仍有可优化的余地
     */
    @Test
    public void test4() {
        MyPredicate myIntegerPredicate = new MyPredicate<User, Integer>() {
            @Override
            public boolean test(User o, Integer o2) {
                return o.getAge() > o2;
            }
        };
        List<User> userList1 = filterUserByAge(WhyLambda.userList, myIntegerPredicate, 30);
        for (User it : userList1) {
            System.err.println(it.toString());
        }


        System.err.println("=========================");
        MyPredicate myDoublePredicate = new MyPredicate<User, Double>() {
            @Override
            public boolean test(User o, Double o2) {
                return o.getSalary() > o2;
            }
        };
        List<User> userList2 = filterUserBySalary(WhyLambda.userList, myDoublePredicate, 5000D);
        for (User it : userList2) {
            System.out.println(it.toString());
        }
    }

    /**
     * 优化方法3：使用lambda表达式
     */
    @Test
    public void test5(){
        List<User> users = filterUserByAge(WhyLambda.userList, (user, age) -> user.getAge() > age, 30);
        users.forEach((it) -> System.out.println(it));
    }

    /**
     * 优化方式4：直接使用StreamAPI，上面个定义的接口不用
     */
    @Test
    public void test6() {
        WhyLambda.userList.stream().filter(it -> it.getAge() > 30).forEach(System.out::println);
    }

    @Test
    public void test7() {
        Comparator<User> comparator = new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return Integer.compare(o1.getAge(), o2.getAge());
            }
        };

        System.out.println(comparator.compare(new User(1, "yang", 22, 211D), new User(1, "yang", 252, 211D)));
    }

}
