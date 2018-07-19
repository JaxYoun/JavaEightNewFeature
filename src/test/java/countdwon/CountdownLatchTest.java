package countdwon;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.LongAdder;

public class CountdownLatchTest {

    /**
     * 1.简单控制：倒计时没有走完，await()方法后面的逻辑将被阻塞
     */
    @Test
    public void simple() {
        CountDownLatch countDownLatch = new CountDownLatch(11);
        for (int i = 0; i < 10; i++) {
            countDownLatch.countDown();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("主函数执行完毕---" + countDownLatch.getCount());
    }

    /**
     * 2.多个工作线程执行完毕前，主线程处于阻塞状态，只有所有工作线程运行结束后，主线程才继续执行
     */
    @Test
    public void oneWaitMany() {
        LongAdder longAdder = new LongAdder();
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep(3000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                longAdder.increment();
                System.out.println(Thread.currentThread().getId() + "：执行完毕=====");
                countDownLatch.countDown();
            }).start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("主函数执行完毕---" + longAdder.intValue());
    }

    /**
     * 3.多个线程准备完毕前主线程阻塞，所有工作线程准备就绪后，主线程被唤醒，之后主线程会同时唤醒所有工作线程继续执行
     */
    @Test
    public void oneStartMany() {
        CountDownLatch once = new CountDownLatch(1);
        CountDownLatch more = new CountDownLatch(10);

        for (int i = 0; i < 10; i++) {
            new Thread(new Slave(once, more, i * 1000L)).start();
        }
        System.out.println("主线程已发出准备指令");
        try {
            more.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        once.countDown();
    }

    /**
     * 工作线程
     */
    private static class Slave implements Runnable {

        private CountDownLatch once;

        private CountDownLatch more;

        private long sleepMill;

        public Slave(CountDownLatch once, CountDownLatch more, long sleepMill) {
            this.once = once;
            this.more = more;
            this.sleepMill = sleepMill;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(this.sleepMill);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getId() + "准备就绪-----");
            more.countDown();
            try {
                once.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.err.println(Thread.currentThread().getId() + "执行完毕======");
        }
    }
}
