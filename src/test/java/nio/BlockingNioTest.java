package nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @Auther: Yang
 * @Date: 2018/8/4 22:17
 * @Description: 一、使用NIO完成网络通信的三个核心：
 * 1.通道：负责连接和数据传递
 * （SelectableChannel接口有如下实现类：
 * SocketChannel、
 * ServerSocketChannel、
 * DatagramChannel
 * <p>
 * Pipe.SinkChannel、
 * Pipe.SourceChannel）
 * 2.缓冲区：负责数据包装
 * 3.选择器：负责监控SelectableChannel的IO状态
 */
public class BlockingNioTest {

    @Test
    public void client() throws IOException {
        try (
                //1.获取Socket通道
                SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9999));
                //2.获取本地文件通道
                FileChannel fileChannel = FileChannel.open(Paths.get("D:\\channel\\nginx.conf"), StandardOpenOption.READ)) {
            //3.分配指定大小的缓冲区
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            //4.读取本地文件并发送到远程服务器
            while (fileChannel.read(byteBuffer) != -1) {
                byteBuffer.flip();
                socketChannel.write(byteBuffer);
                byteBuffer.clear();
            }
        }
    }

    @Test
    public void server() throws IOException {
        try (
                //1.获取服务器连接
                ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
                //2.获取本地文件通道
                FileChannel fileChannel = FileChannel.open(Paths.get("D:\\channel\\nginx.confserver"), StandardOpenOption.WRITE, StandardOpenOption.CREATE)) {
            //3.绑定监听端口
            serverSocketChannel.bind(new InetSocketAddress("127.0.0.1", 9999));
            //4.获取客户端请求的通道
            SocketChannel socketChannelFromClient = serverSocketChannel.accept();
            //5.分配指定大小的缓冲区
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            //6.接收客户端的数据并保存到本地磁盘
            while (socketChannelFromClient.read(byteBuffer) != -1) {
                byteBuffer.flip();
                fileChannel.write(byteBuffer);
                byteBuffer.clear();
            }
        }
    }

}
