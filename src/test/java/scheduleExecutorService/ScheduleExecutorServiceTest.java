package scheduleExecutorService;

import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

/**
 * @Auther: Yang
 * @Date: 2018/7/30 11:38
 * @Description:利用ScheduleExecutorService线程池实现定时任务，同时对比Timer. 1.Timer不支持多线程。全部挂在Timer下的任务都是单线程的，任务仅仅能串行运行。假设当中一个任务运行时间过长。
 * 会影响到其它任务的运行，然后就可能会有各种接踵而来的问题。
 * <p>
 * 2.Timer的线程不会捕获异常。TimerTask假设抛出异常，那么Timer唯一的进程就会挂掉，这样挂在Timer下的全部任务都会无法继续运行。
 * <p>
 * 第一个问题，随着业务数据的猛增，我们生产上有几个任务如今每次运行须要1-3个小时。在这段时间内，该timer下的其它任务仅仅能等待，
 * 这是让人无法忍受的。重开一个Timer？难道要为全部的耗时的Task都单开一个Timer。显然是不太可能。这样就太乱了。
 * <p>
 * 第二个问题，是极其致命的，好多业务数据都是晚上的定时任务跑出来的。结果因为程序的问题或者内存资源不足，导致线程被kill了。
 * 该timer下的全部任务都未运行。结果第二天整整忙活了一天，主要任务就是——跑任务，调整数据。
 * 深受其害呀！
 */
public class ScheduleExecutorServiceTest {


    public static void main(String[] args) {
//        scheduleExecutorServiceTest();
        nestSchedu();
    }

    public static void scheduleExecutorServiceTest() {
        LongAdder roundAdder = new LongAdder();
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            int roundId = roundAdder.intValue();
            if (roundId < 5) {
                System.out.println("第" + roundId + "回合开始");
                countDown(roundId);
                roundAdder.increment();
            } else {
                scheduledExecutorService.shutdownNow();
            }
        }, 2L, 10L, TimeUnit.SECONDS);

    }

    public static void countDown(int roundId) {
        LongAdder countDownAdder = new LongAdder();
        ScheduledExecutorService scheduledExecutorService0 = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService0.scheduleAtFixedRate(() -> {
            int countDownId = countDownAdder.intValue();
            if (countDownId < 10) {
                try {
                    int div = roundId / (roundId - 2);
                    System.out.println(roundId + "-------->" + countDownId);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownAdder.increment();
                }
            } else {
                System.err.println("第" + roundId + "轮倒计时已结束");
                scheduledExecutorService0.shutdownNow();
            }
        }, 0L, 1L, TimeUnit.SECONDS);
    }

    public static void nestSchedu() {
        TimeWaper timeWaper = new TimeWaper(3, 5, System.currentTimeMillis());
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(2);

        scheduledExecutorService.scheduleAtFixedRate(() -> {
            System.out.println("第" + timeWaper.longAdder.intValue() + "轮！");
            timeWaper.longAdder.increment();
            timeWaper.startTime = System.currentTimeMillis();
        }, timeWaper.readyTime, timeWaper.roundPeriod, TimeUnit.SECONDS);

        scheduledExecutorService.scheduleAtFixedRate(() -> {
            long delta = timeWaper.startTime + (1000L * timeWaper.roundPeriod) - System.currentTimeMillis();
            System.out.println("第" + timeWaper.longAdder.intValue() + "轮还剩：" + delta + "秒！");
        }, timeWaper.readyTime, 1L, TimeUnit.SECONDS);

    }

    private static class TimeWaper {

        private LongAdder longAdder = new LongAdder();

        private long readyTime;

        private long roundPeriod;

        volatile private long startTime;

        public TimeWaper(long readyTime, long roundPeriod, long startTime) {
            this.readyTime = readyTime;
            this.roundPeriod = roundPeriod;
            this.startTime = startTime;
        }
    }
}
