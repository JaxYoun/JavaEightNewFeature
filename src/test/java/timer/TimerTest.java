package timer;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.LongAdder;

/**
 * Timer提供了两个方法schedule和scheduleAtFixedRate，两个方法适用于不同的场景
 * （1）schedule方法：下一次执行时间相对于 上一次 实际执行【完毕】的时间点 ，
 * 因此如果执行过程超过周期时执行时间会累积延后，各个执行过程是串联衔接的，
 * 但是有个好处就是：如果有各次执行都去修改共享变量，不会引起在线程安全问题，不需要考虑同步。
 * （2）scheduleAtFixedRate方法：下一次执行时间相对于上一次【开始】的时间点 ，
 * 因此执行时间不会延后，这时如果执行时间超过周期相邻两次执行会重叠，两次是同时执行的，
 * 这时如果有各次执行都去修改共享变量，会引起在线程安全问题，需要考虑线程同步。
 */
public class TimerTest {

    /**
     * 方法执行时，delay一段时间后开启一个线程，并周期性的执行它
     */
    public static void test0() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("111");
            }
        }, 1000L, 1000L);
    }


    /**
     * 方法执行时，指定首次执行的开始时间点，到达这个时间点后开启一个线程，并周期性的执行它
     */
    public static void test1() {
        Date now = new Date();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("111");
            }
        }, now, 1000L);
    }

    /**
     * 除了可以控制首次执行时间和周期外，开可以在线程内通过变量来停止定时任务
     */
    public static void test2() {
        LongAdder longAdder = new LongAdder();
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                long value = longAdder.longValue();
                if (value < 100) {
                    System.out.println(value);
                    longAdder.increment();
                } else {
                    timer.cancel();  //定时任务
                }
            }
        };
        timer.schedule(timerTask, 1000L, 3000L);
    }

    public static void main(String[] args) {
//        test0();
        test2();
    }

}
