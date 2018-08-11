package lambda.lambdaAndInnerClass;

public class Lambda {
    public static void main(String[] args) {
        new Thread(() -> System.out.println("匿名内部类，用Lambda赋值型")).start();
    }
}