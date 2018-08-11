package lambda.lambdaAndInnerClass;

public class InnerClassWithLambda {
    public static void main(String[] args) {
        Runnable runnable = () -> System.out.println("匿名内部类，用Lambda赋值型");
        new Thread(runnable).start();
    }
}