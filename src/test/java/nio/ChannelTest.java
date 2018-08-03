package nio;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @Auther: Yang
 * @Date: 2018/8/2 23:13
 * @Description: 一、用于源节点和目标节点的连接，在JavaNIO里负责Buffer中数据的传输，需要配合Buffer才能传输数据。
 * <p>
 * 二、|--FileChannel  //用于文件IO
 * |--SocketChannel  //用于TCP客户端
 * |--ServerSocketChannel  //用于TCP服务器端
 * |--DatagramChannel  //用于UDP
 * <p>
 * 三、获取通道
 * 1.Java针对支持通道的类提供了getChannel()方法
 * 文件IO：
 * FileInputStream
 * FileOutputStream
 * RandomAccessFile
 * 网络IO：
 * Socket
 * ServerSocket
 * DatagramSocket
 * 2.JDK1.7后，提供了NIO.2针对各个通道类提供了静态的open()方法
 * 3.JDK1.7后，提供了NIO.2 Files工具类提供了newByteChannel()方法
 * <p>
 * 四、通道间的数据传输
 * transferFrom()
 * tranferTo()
 * <p>
 * 五、分散读取与聚集写入（不支持直接缓冲区）
 * Scatter：将一个通道中的数据从前到后依此按缓冲区容量分散到多个缓冲区中
 * Gathering：将多个缓冲区中的数据按先后顺序依此聚集写入到一个通道中
 */
public class ChannelTest {

    /**
     * 利用流获取通道，建立非直接缓冲区，完成文件复制
     *
     * @throws IOException
     */
    @Test
    public void test1() throws IOException {
        try (
                FileInputStream in = new FileInputStream("D:\\channel\\nginx.conf");
                FileOutputStream out = new FileOutputStream("D:\\channel\\nginx.conf2");
                //1.获取通道
                FileChannel inChannel = in.getChannel();
                FileChannel outChannel = out.getChannel()
        ) {
            //2.分配指定大小的缓冲区
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

            //3.将源通道中的数据读到缓冲区
            while (inChannel.read(byteBuffer) != -1) {
                //切换为写模式
                byteBuffer.flip();
                //4.将缓冲区中的数据写入目的通道
                outChannel.write(byteBuffer);
                //清空缓冲区
                byteBuffer.clear();
            }
        }
    }

    /**
     * 通过通道类的静态方法获取通道，建立直接缓冲区，完成文件复制
     *
     * @throws IOException
     */
    @Test
    public void test2() throws IOException {
        try (
                //1.获取通道
                FileChannel inChannel = FileChannel.open(Paths.get("D:\\channel\\nginx.conf"), StandardOpenOption.READ);
                FileChannel outChannel = FileChannel.open(Paths.get("D:\\channel\\nginx.conf3"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE)
        ) {
            //2.内存映射文件(直接缓冲区)
            MappedByteBuffer inMappedByteBuffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
            MappedByteBuffer outMappedByteBuffer = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());

            //3.直接操作缓冲区
            byte[] dst = new byte[inMappedByteBuffer.limit()];
            inMappedByteBuffer.get(dst);
            outMappedByteBuffer.put(dst);
        }
    }

    /**
     * 用transfer方式实现文件复制（直接缓冲区）
     *
     * @throws IOException
     */
    @Test
    public void test3() throws IOException {
        try (
                //1.获取通道
                FileChannel inChannel = FileChannel.open(Paths.get("D:\\channel\\nginx.conf"), StandardOpenOption.READ);
                FileChannel outChannel = FileChannel.open(Paths.get("D:\\channel\\nginx.conf4"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE)
        ) {
//            inChannel.transferTo(0, inChannel.size(), outChannel);  //等效
            outChannel.transferFrom(inChannel, 0, inChannel.size());

        }
    }

    @Test
    public void test4() throws IOException {
        try (
                //源
                RandomAccessFile randomAccessFile = new RandomAccessFile("D:\\channel\\nginx.conf", "rw");
                //目标
                RandomAccessFile randomAccessFile0 = new RandomAccessFile("D:\\channel\\nginx.conf5", "rw")
        ) {
            //1.获取通道
            FileChannel inChannel = randomAccessFile.getChannel();

            //2.分配指定大小的缓冲区
            ByteBuffer byteBuffer = ByteBuffer.allocate(2);
            ByteBuffer byteBuffer0 = ByteBuffer.allocate(8);

            //3.分散读取
            ByteBuffer[] byteBufferArr = {byteBuffer, byteBuffer0};
            inChannel.read(byteBufferArr);
            
            //将缓冲区转换模式
            for (ByteBuffer it : byteBufferArr) {
                it.flip();
            }
            System.out.println(new String(byteBufferArr[0].array(), 0, byteBufferArr[0].limit()));
            System.out.println("==========");
            System.out.println(new String(byteBufferArr[1].array(), 0, byteBufferArr[1].limit()));

            //4.聚集写入
            randomAccessFile0.getChannel().write(byteBufferArr);
        }
    }

}
