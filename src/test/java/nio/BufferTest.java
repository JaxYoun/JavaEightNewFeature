package nio;

import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * @Auther: Yang
 * @Date: 2018/8/1 22:47
 * @Description:Buffer
 * @一、缓冲区Buffer在Java NIO中负责数据的包装，底层实现是数组，用于包装不同类型的数据。
 * 根据数据类型的不同（boolean除外），提供了不同的Buffer：
 * ByteBuffer
 * CharBuffer
 * ShortBuffer
 * IntBuffer
 * LongBuffer
 * FloatBuffer
 * DoubleBuffer
 * <p>
 * 不同类型的缓冲区的操作方式基本一致，通过allocate()获取缓冲区
 * @二、Buffer存取数据的两个核心方法： put()：将数据存入缓冲区
 * get()：从缓冲区读取数据
 * @三、Buffer的4个核心属性：0 <= mark <= position <= limit <= capacity
 * capacity：容量，表示缓冲区的最大容量，一旦申明不可改变。
 * limit：界限，表示缓冲区中可以操作的数据的大小（limit之后的数据是拒绝操作的）。
 * position：表示缓冲区中正在操作的位置，类似当前的游标。
 * <p>
 * mark：用于标记上次position的位置，可以通过reset方法将position恢复到上次mark的位置
 */
public class BufferTest {

    @Test
    public void test1() {

        //1.分配一个指定大小的缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        System.out.println("==allocate===");
        System.out.println(byteBuffer.capacity());
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());

        //2.调用put方法写入数据
        byteBuffer.put("abc".getBytes());
        System.out.println("==put===");
        System.out.println(byteBuffer.capacity());
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());

        //3.调用flip方法切换到读取数据模式
        byteBuffer.flip();
        System.out.println("==flip===");
        System.out.println(byteBuffer.capacity());
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());

        //4.调用get方法读取数据
        byte[] byteArr = new byte[byteBuffer.limit()];
        byteBuffer.get(byteArr);
        System.out.println("==get===");
        System.out.println(new String(byteArr, 0, byteArr.length));
        System.out.println(byteBuffer.capacity());
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());

        //5.调用rewind方法将磁头拨到0位，可重新从头读写
        byteBuffer.rewind();
        System.out.println("==rewind===");
        System.out.println(byteBuffer.capacity());
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());

        //6.调用rewind方法清空缓冲区，此时缓冲区内的数据并未并真正擦出（只是表示可以被覆盖），只是将所有位置属性恢复到初始状态
        byteBuffer.clear();
        System.out.println("==clear===");
        System.out.println(byteBuffer.capacity());
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());

        //缓冲区内的旧数据仍然可以读取到
        System.out.println((char) byteBuffer.get(2));
    }

    @Test
    public void test2() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put("abcde".getBytes());
        byteBuffer.put("fgh".getBytes());
        byteBuffer.rewind();
        byteBuffer.put("ijklmnopqrst".getBytes());

        byteBuffer.flip();
        byte[] byteArr = new byte[byteBuffer.limit()];
        byteBuffer.get(byteArr, 0, 5);
        System.out.println(new String(byteArr, 0, byteArr.length));
        System.out.println(byteBuffer.position());

        byteBuffer.mark();
        byteBuffer.get(byteArr, 5, 2);
        System.out.println(new String(byteArr, 0, byteArr.length));
        System.out.println(byteBuffer.position());
        byteBuffer.reset();
        System.out.println(byteBuffer.position());

        //判断缓冲区中还有没有可操作数据
        if (byteBuffer.hasRemaining()) {
            //获取缓冲区中可操作数据个数
            System.out.println("还剩下：" + byteBuffer.remaining());
        }
    }

    @Test
    public void test3() {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
        System.out.println(byteBuffer.isDirect());
    }

}
