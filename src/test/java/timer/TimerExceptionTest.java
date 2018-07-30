package timer;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.LongAdder;

/**
 * @Auther: Yang
 * @Date: 2018/7/30 14:58
 * @Description:
 */
public class TimerExceptionTest {

    public static void main(String[] args) {
        new Timer().schedule(new TimerTask() {
            LongAdder count = new LongAdder();
            @Override
            public void run() {
                int countId = count.intValue();
                try {
                    int i = countId / (countId - 4);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    count.increment();
                }
                System.out.println("endddddddddd");
            }
        }, 1000L, 1000L);
    }

}
