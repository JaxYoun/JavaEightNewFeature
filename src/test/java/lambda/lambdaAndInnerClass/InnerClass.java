package lambda.lambdaAndInnerClass;

/**
 * @Auther: Yang
 * @Date: 2018/8/7 21:11
 * @Description:探索lambda和匿名内部内的关系
 */
public class InnerClass {

    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("匿名内部内型");
            }
        };
        new Thread(runnable).start();
    }

}
