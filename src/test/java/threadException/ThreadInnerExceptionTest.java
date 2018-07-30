package threadException;

/**
 * @Auther: Yang
 * @Date: 2018/7/30 14:38
 * @Description:
 * 1.在一个线程内如果遇到线程内部异常，并且没有捕获，本线程会立即终止。
 * 2.在一个线程内如果遇到线程内部异常，做了捕获处理，本线程会继续执行。
 * 3.外部线程（如main线程）是捕捉不到其子线程的内部异常的（即便是抛出了），所以子线程的内部异常要在其内部捕获并处理。
 */
public class ThreadInnerExceptionTest {

    public static void main(String[] args) {
        try {
            new Thread(() -> {
                try {
                    int i = 2 / 0;
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("内部捕获到了！");
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("外部捕获到了！");
        }
        System.out.println("main end");
    }

}
