package nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * @Auther: Yang
 * @Date: 2018/8/5 10:34
 * @Description:Pipe是两个线程间的单向连接
 */
public class PipeTest {

    public static void main(String[] args) {
        try {
            server();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void server() throws IOException {
        //1.获取流水线
        Pipe pipe = Pipe.open();

        //2.分配指定大小的缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        //2.将buffer中的数据写入Pipe
        Pipe.SinkChannel sinkChannel = pipe.sink();
        byteBuffer.put("jjjjj".getBytes());
        byteBuffer.flip();
        sinkChannel.write(byteBuffer);

        //4.读取缓冲区中的数据
        Pipe.SourceChannel sourceChannel = pipe.source();
        byteBuffer.flip();
        int len = sourceChannel.read(byteBuffer);
        System.out.println(new String(byteBuffer.array(), 0, byteBuffer.limit()));
        sourceChannel.close();
        sinkChannel.close();

    }
}