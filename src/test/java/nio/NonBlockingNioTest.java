package nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.Iterator;

/**
 * @Auther: Yang
 * @Date: 2018/8/4 22:55
 * @Description:非阻塞式
 */
public class NonBlockingNioTest {

    @Test
    public void client() throws IOException {
        try (
                //1.获取Socket通道
                SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9999))
        ) {
            //2.将通道切换为非阻塞模式
            socketChannel.configureBlocking(false);
            //3.分配指定大小的缓冲区
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            //4.将内容放入缓冲区
            byteBuffer.put(LocalDateTime.now().toString().getBytes());
            //5.将缓冲区切换为写模式
            byteBuffer.flip();
            //6.将缓冲区及数据写入socket通道
            socketChannel.write(byteBuffer);
            byteBuffer.clear();
        }
    }

    @Test
    public void server() throws IOException {
        //TODO 客户端发来的请求，服务器端 未能打印出来
        try (
                //1.获取服务器连接
                ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()
        ) {
            //2.将通道配置为非阻塞模式
            serverSocketChannel.configureBlocking(false);
            //3.绑定监听端口
            serverSocketChannel.bind(new InetSocketAddress(9999));

            //4.获取选择器
            Selector selector = Selector.open();

            //5.将通道注册到选择器上，并指定监听事件
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            //6.轮询获取选择器上已经准备就绪的事件
            while (selector.select() > 0) {
                //7.获取当前选择器中所有注册的选择键（已就绪监听事件）
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    //8.判断具体是什么事件准备就绪了
                    if (key.isAcceptable()) {
                        //9.若接收就绪，获取客户端连接
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        //10.切换为非阻塞模式
                        socketChannel.configureBlocking(false);
                        //12.将该通道也注册到选择器上
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    } else if (key.isReadable()) {
                        //13.获取当前选择器上读就绪的通道
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        //14.读取数据
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

                        int len;
                        while ((len = socketChannel.read(byteBuffer)) > 0) {
                            byteBuffer.flip();
                            System.out.println(new String(byteBuffer.array(), 0, len));
                            byteBuffer.clear();
                        }
                    }
                    iterator.remove();
                }
            }
        }
    }
}
