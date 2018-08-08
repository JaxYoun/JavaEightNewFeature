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
 * @Date: 2018/8/4 22:55
 * @Description:添加了服务器响应
 */
public class BlockingNioTest0 {

    @Test
    public void client() throws IOException {
        try (
                SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8888));
                FileChannel fileChannel = FileChannel.open(Paths.get("D:\\channel\\nginx.conf"), StandardOpenOption.READ)
        ) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            while (fileChannel.read(byteBuffer) != -1) {
                byteBuffer.flip();
                socketChannel.write(byteBuffer);
                byteBuffer.clear();
            }

            socketChannel.shutdownOutput();
            //接受服务端的响应
            int len;
            while ((len = socketChannel.read(byteBuffer)) != -1) {
                byteBuffer.flip();
                System.out.println(new String(byteBuffer.array(), 0, len));
                byteBuffer.clear();
            }
        }

    }

    @Test
    public void server() throws IOException {
        try (
                ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
                FileChannel fileChannel = FileChannel.open(Paths.get("D:\\channel\\nginx.conf___"), StandardOpenOption.WRITE, StandardOpenOption.CREATE)
        ) {
            serverSocketChannel.bind(new InetSocketAddress("127.0.0.1", 8888));
            SocketChannel socketChannel = serverSocketChannel.accept();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

            while (socketChannel.read(byteBuffer) != -1) {
                byteBuffer.flip();
                fileChannel.write(byteBuffer);
                byteBuffer.clear();
            }

            //发送响应信息给客户端
            byteBuffer.put("success".getBytes());
            byteBuffer.flip();
            socketChannel.write(byteBuffer);
        }
    }

}
